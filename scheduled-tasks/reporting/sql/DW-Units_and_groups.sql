WITH staff_attendance_aggregate AS (
    SELECT group_id, arrived, departed, occupancy_coefficient
    FROM staff_attendance_realtime
    WHERE departed IS NOT NULL
        AND type = ANY('{PRESENT,OVERTIME,JUSTIFIED_CHANGE}'::staff_attendance_type[])
        AND (:date_val::DATE = DATE(arrived) OR :date_val::DATE = DATE(departed))
    UNION ALL
    SELECT group_id, arrived, departed, occupancy_coefficient
    FROM staff_attendance_external
    WHERE departed IS NOT NULL
        AND (:date_val::DATE = DATE(arrived) OR :date_val::DATE = DATE(departed))
),
caretaker_counts AS (
    SELECT
        u.id                           AS unit_id,
        g.id						   AS group_id,
        COALESCE(
            SUM(
                CASE
                    WHEN saa.arrived IS NOT NULL
                        THEN ROUND(EXTRACT(EPOCH FROM (
                            LEAST(saa.departed, timezone('Europe/Helsinki', (:date_val::DATE + 1)::DATE::TIMESTAMP)) -
                            GREATEST(saa.arrived, timezone('Europe/Helsinki', :date_val::DATE::TIMESTAMP))
                        )) / 3600 / 7.65 * saa.occupancy_coefficient / 7, 4)
                    ELSE s.count
                END
            ), 0.0
        )                               AS caretaker_count
    FROM daycare_group g
    JOIN daycare u ON g.daycare_id = u.id
         AND daterange(g.start_date, g.end_date, '[]') @> :date_val::DATE
    LEFT JOIN staff_attendance_aggregate saa ON g.id = saa.group_id
    LEFT JOIN staff_attendance s ON g.id = s.group_id
        AND :date_val::DATE = s.date
    LEFT JOIN holiday h ON :date_val::DATE = h.date
        AND NOT u.operation_days @> ARRAY[1, 2, 3, 4, 5, 6, 7]
    WHERE date_part('isodow', :date_val::DATE) = ANY(u.operation_days)
        AND h.date IS NULL
        AND daterange(u.opening_date, u.closing_date, '[]') @> :date_val::DATE
    GROUP BY u.id, g.id
)
SELECT
    now() AT TIME ZONE 'Europe/Helsinki'   AS aikaleima,
    d.name                                 AS toimintayksikkö,
    d.id                                   AS toimintayksikkö_id,
    d.opening_date                         AS toimintayksikön_alkupvm,
    d.closing_date                         AS toimintayksikön_loppupvm,
    d.type                                 AS toimintamuoto,
    d.provider_type                        AS järjestämistapa,
    d.street_address					   AS katuosoite,
    d.postal_code						   AS postinumero,
    d.post_office						   AS postitoimipaikka,
    d.capacity                             AS toimintayksikön_laskennallinen_lapsimäärä,
    ca.name                                AS palvelualue,
    ca.id                                  AS palvelualue_id,
    d.dw_cost_center                       AS dw_kustannuspaikka,
    (
        SELECT count(*)
        FROM placement p
        WHERE p.unit_id = d.id
            AND p.start_date <= :date_val::DATE
            AND :date_val::DATE <= p.end_date
    )                                      AS toimintayksikön_lapsimäärä,
    (
        SELECT count(*)
        FROM placement p
        WHERE p.unit_id = d.id
            AND p.start_date < date_trunc('month', :date_val::DATE)
            AND date_trunc('month', :date_val::DATE) < p.end_date --Edellisen kuun viimeisen päivän mukaan
    )                                      AS toimintayksikön_lapsimäärä_ed_kuun_lopussa,
    dg.name                                AS ryhmä,
    dg.id                                  AS ryhmä_id,
    dg.start_date                          AS ryhmän_alkupvm,
    dg.end_date                            AS ryhmän_loppupvm,
    dc.amount                              AS henkilökuntaa_ryhmässä,
    cc.caretaker_count                     AS henkilökuntaa_läsnä,
    (
    	SELECT SUM(caretaker_count)
    	FROM caretaker_counts
    	WHERE unit_id = d.id
    ) 									   AS kasvatusvastuullisten_lkm_yksikössä,
    (
        SELECT count(*)
        FROM daycare_group_placement dgp
        WHERE dgp.daycare_group_id = dg.id
            AND dgp.start_date <= :date_val::DATE
            AND :date_val::DATE <= dgp.end_date
    )                                      AS ryhmän_lapsimäärä,
    (
        SELECT count(*)
        FROM daycare_group_placement dgp
        WHERE dgp.daycare_group_id = dg.id
            AND dgp.start_date < date_trunc('month', :date_val::DATE)
            AND date_trunc('month', :date_val::DATE) < dgp.end_date --Edellisen kuun viimeisen päivän mukaan
    )                                      AS ryhmän_lapsimäärä_ed_kuun_lopussa
FROM daycare_group dg
    JOIN daycare d on dg.daycare_id = d.id
    JOIN care_area ca on ca.id = d.care_area_id
    LEFT JOIN daycare_caretaker dc ON dg.id = dc.group_id
        AND dc.start_date <= :date_val::DATE
        AND (dc.end_date >= :date_val::DATE or dc.end_date is null)
    LEFT JOIN staff_attendance_aggregate saa ON dg.id = saa.group_id
    LEFT JOIN caretaker_counts cc on cc.group_id = dg.id
WHERE (:date_val::DATE - interval '3 months' <= d.closing_date OR d.closing_date is null)
    AND (:date_val::DATE - interval '3 months' <= dg.end_date OR dg.end_date is null)
GROUP BY
    d.name,
    d.id,
    d.opening_date,
    d.closing_date,
    d.type,
    d.provider_type,
    ca.name,
    ca.id,
    d.dw_cost_center,
    dg.name,
    dg.id,
    dg.start_date,
    dg.end_date,
    dc.amount,
    cc.caretaker_count
ORDER BY d.name;