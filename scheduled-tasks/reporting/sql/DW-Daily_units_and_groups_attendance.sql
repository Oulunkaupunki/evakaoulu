WITH staff_attendance_aggregate AS (
    SELECT group_id, arrived, departed, occupancy_coefficient
    FROM staff_attendance_realtime
    WHERE departed IS NOT NULL
        AND type = ANY('{PRESENT,OVERTIME,JUSTIFIED_CHANGE}'::staff_attendance_type[])
        AND (:date_val::DATE - interval '1 week' <= DATE(arrived) OR :date_val::DATE - interval '1 week' <= DATE(departed))
    UNION ALL
    SELECT group_id, arrived, departed, occupancy_coefficient
    FROM staff_attendance_external
    WHERE departed IS NOT NULL
        AND (:date_val::DATE - interval '1 week' <= DATE(arrived) OR :date_val::DATE - interval '1 week' <= DATE(departed))
),
caretaker_counts AS (
    SELECT
        u.id                           AS unit_id,
        g.id						   AS group_id,
        t::date						   AS date,
        COALESCE(
            SUM(
                CASE
                    WHEN saa.arrived IS NOT null AND (t::DATE = DATE(saa.arrived) OR t::DATE = DATE(saa.departed))
                        THEN ROUND(EXTRACT(EPOCH FROM (
                            LEAST(saa.departed, timezone('Europe/Helsinki', (t::DATE + 1)::DATE::TIMESTAMP)) -
                            GREATEST(saa.arrived, timezone('Europe/Helsinki', t::DATE::TIMESTAMP))
                        )) / 3600 / 7.65 * saa.occupancy_coefficient / 7, 4)
                    ELSE s.count
                END
            ), 0.0
        )                               AS caretaker_count
    FROM generate_series(:date_val::DATE - interval '1 week', :date_val::DATE, '1 day') t
        CROSS JOIN daycare_group g
        JOIN daycare u ON g.daycare_id = u.id
            AND daterange(g.start_date, g.end_date, '[]') @> t::DATE
        LEFT JOIN staff_attendance_aggregate saa ON g.id = saa.group_id
        LEFT JOIN staff_attendance s ON g.id = s.group_id
            AND t::DATE = s.date
    WHERE date_part('isodow', t::DATE) = ANY(u.operation_days)
        AND daterange(u.opening_date, u.closing_date, '[]') @> t::DATE
    GROUP BY u.id, g.id, t
)
SELECT
    now() AT TIME ZONE 'Europe/Helsinki'   AS aikaleima,
    cc.date								   as pvm,
    d.name                                 AS toimintayksikkö,
    d.id                                   AS toimintayksikkö_id,
    d.operation_days                       AS toimintapäivät,
    d.provides_shift_care                  AS vuorohoitoyksikkö,
    d.shift_care_operation_days            AS vuorohoitopäivät,
    d.shift_care_open_on_holidays          AS vuorohoitopyhäpäivinä,
    dg.name                                AS ryhmä,
    dg.id                                  AS ryhmä_id,
    d.capacity                             AS toimintayksikön_laskennallinen_lapsimäärä,
    (
        SELECT count(*)
        FROM placement p
        WHERE p.unit_id = d.id
            AND p.start_date <= cc.date
            AND cc.date <= p.end_date
    )                                      AS toimintayksikön_lapsimäärä,
    dc.amount                              AS henkilökuntaa_ryhmässä,
    cc.caretaker_count                     AS henkilökuntaa_läsnä,
    (
    	SELECT SUM(caretaker_count)
    	FROM caretaker_counts
    	WHERE unit_id = d.id
    		AND date = cc.date
    ) 									   AS kasvatusvastuullisten_lkm_yksikössä,
    (
        SELECT count(*)
        FROM daycare_group_placement dgp
        WHERE dgp.daycare_group_id = dg.id
            AND dgp.start_date <= cc.date
            AND cc.date <= dgp.end_date
    )                                      AS ryhmän_lapsimäärä
FROM daycare_group dg
    JOIN daycare d on dg.daycare_id = d.id
    JOIN care_area ca on ca.id = d.care_area_id
    LEFT JOIN caretaker_counts cc on cc.group_id = dg.id
    LEFT JOIN daycare_caretaker dc ON dg.id = dc.group_id
        AND dc.start_date <= cc.date
        AND (dc.end_date >= cc.date or dc.end_date is null)
    LEFT JOIN staff_attendance_aggregate saa ON dg.id = saa.group_id
WHERE (:date_val::DATE - interval '3 months' <= d.closing_date OR d.closing_date is null)
    AND (:date_val::DATE - interval '3 months' <= dg.end_date OR dg.end_date is null)
GROUP by
    cc.date,
    d.name,
    d.id,
    dg.name,
    dg.id,
    dc.amount,
    cc.caretaker_count
ORDER by dg.name, cc.date;
