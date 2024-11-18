WITH
caretaker_counts AS (
    SELECT
        a.id                            AS area_id,
        a.name                          AS area_name,
        u.id                            AS unit_id,
        u.name                          AS unit_name,
        :date_val::DATE                 AS date,
        COALESCE(
            SUM(
                CASE
                    WHEN sar.arrived IS NOT null AND (:date_val::DATE = DATE(sar.arrived) OR :date_val::DATE = DATE(sar.departed))
                        THEN ROUND(EXTRACT(EPOCH FROM (
                            LEAST(sar.departed, timezone('Europe/Helsinki', (:date_val::DATE + 1)::DATE::TIMESTAMP)) - GREATEST(sar.arrived, timezone('Europe/Helsinki', :date_val::DATE::TIMESTAMP))
                        )) / 3600 / 7.65 * sar.occupancy_coefficient / 7, 4)
                    ELSE s.count
                END
            ),
            0.0
        )                               AS caretaker_count
    FROM daycare_group g
        JOIN daycare u ON g.daycare_id = u.id
            AND daterange(g.start_date, g.end_date, '[]') @> :date_val::DATE
        JOIN care_area a ON a.id = u.care_area_id
        LEFT JOIN (
            SELECT group_id, arrived, departed, occupancy_coefficient
            FROM staff_attendance_realtime
            WHERE arrived IS NOT null
                AND departed IS NOT NULL
                AND type = ANY('{PRESENT,OVERTIME,JUSTIFIED_CHANGE}'::staff_attendance_type[])
            UNION ALL
            SELECT group_id, arrived, departed, occupancy_coefficient
            FROM staff_attendance_external
            WHERE arrived IS NOT null
                AND departed IS NOT null
        ) sar ON g.id = sar.group_id
        LEFT JOIN staff_attendance s ON g.id = s.group_id
            AND :date_val::DATE = s.date
    WHERE date_part('isodow', :date_val::DATE) = ANY(u.operation_days)
        AND daterange(u.opening_date, u.closing_date, '[]') @> :date_val::DATE
    GROUP BY a.id, u.id
),
default_sn_coefficients AS (
    SELECT
        occupancy_coefficient,
        occupancy_coefficient_under_3y,
        realized_occupancy_coefficient,
        realized_occupancy_coefficient_under_3y,
        valid_placement_type
    FROM service_need_option
    WHERE default_option
),
placements_realized AS (
    SELECT
        bc.id                                                   AS placement_id,
        bc.child_id,
        bc.unit_id,
        pl.type,
        u.type && array['FAMILY', 'GROUP_FAMILY']::care_types[] AS family_unit_placement,
        daterange(bc.start_date, bc.end_date, '[]')             AS period
    FROM backup_care bc
        JOIN daycare u ON bc.unit_id = u.id
        JOIN placement pl ON bc.child_id = pl.child_id
            AND daterange(bc.start_date, bc.end_date, '[]') && daterange(pl.start_date, pl.end_date, '[]')
    WHERE daterange(bc.start_date, bc.end_date, '[]') @> :date_val::DATE
        AND NOT EXISTS (
            SELECT *
            FROM absence a
            WHERE a.child_id = pl.child_id
                AND :date_val::DATE = a.date
                AND array[a.category] && absence_categories(pl.type)
        )
    union
    SELECT
        pl.id                                                   AS placement_id,
        pl.child_id,
        pl.unit_id,
        pl.type,
        u.type && array['FAMILY', 'GROUP_FAMILY']::care_types[] AS family_unit_placement,
        daterange(pl.start_date, pl.end_date, '[]')             AS period
    FROM placement pl
        JOIN daycare u ON pl.unit_id = u.id
    where daterange(pl.start_date, pl.end_date, '[]') @> :date_val::DATE
        AND NOT EXISTS(
            SELECT *
            FROM backup_care bc
            WHERE daterange(bc.start_date, bc.end_date, '[]') @> :date_val::DATE
                AND daterange(bc.start_date, bc.end_date, '[]') && daterange(pl.start_date, pl.end_date, '[]')
                AND bc.child_id = pl.child_id
        )
        AND NOT EXISTS (
            SELECT *
            FROM absence a
            WHERE a.child_id = pl.child_id
                AND :date_val::DATE = a.date
                AND array[a.category] && absence_categories(pl.type)
        )
),
placements_on_date AS (
    SELECT
        cc.date,
        cc.caretaker_count                                  AS caretaker_count,
        pr.placement_id                                     AS placement_id,
        pr.child_id                                         AS child_id,
        pr.unit_id                                          AS unit_id,
        CASE
            WHEN af.capacity_factor IS NOT NULL
                THEN af.capacity_factor
            ELSE 1
        END AS assistance_coefficient,
        CASE
            WHEN pr.family_unit_placement = TRUE
                THEN 1.75
            WHEN cc.date < (p.date_of_birth + interval '3 year')::DATE
                THEN (
                    CASE
                        WHEN sno.realized_occupancy_coefficient_under_3y IS NOT NULL
                            THEN sno.realized_occupancy_coefficient_under_3y
                        ELSE dsc.realized_occupancy_coefficient_under_3y
                    END
                )
            ELSE (
                CASE
                    WHEN sno.realized_occupancy_coefficient IS NOT NULL
                        THEN sno.realized_occupancy_coefficient
                    ELSE dsc.realized_occupancy_coefficient
                END
            )
        END AS service_need_coefficient
    FROM caretaker_counts cc
        JOIN placements_realized pr ON pr.unit_id = cc.unit_id
        JOIN person p ON p.id = pr.child_id
        LEFT JOIN assistance_factor af ON af.child_id = pr.child_id
            AND af.valid_during @> cc.date
        LEFT JOIN service_need sn ON sn.placement_id = pr.placement_id
            AND daterange(sn.start_date, sn.end_date, '[]') @> cc.date
        LEFT JOIN service_need_option sno ON sn.option_id = sno.id
        JOIN default_sn_coefficients dsc ON dsc.valid_placement_type = pr.type
)
SELECT
    pod.date                                                        AS pvm,
    pod.unit_id                                                     AS toimintayksikkö_id,
    d.name                                                          AS toimintayksikkö,
    pod.caretaker_count                                             AS kasvattajien_lkm,
    count(pod.placement_id)                                         AS sijoituksien_lkm,
    SUM(pod.assistance_coefficient * pod.service_need_coefficient)  AS käyttöaste_summa,
    CASE
        WHEN pod.caretaker_count IS NULL OR pod.caretaker_count = 0
            THEN null
        ELSE (SUM(pod.assistance_coefficient * pod.service_need_coefficient) / (pod.caretaker_count * 7)) * 100
    END                                                             AS käyttöaste_prosentteina
FROM placements_on_date pod
         JOIN daycare d ON d.id = pod.unit_id
GROUP BY
    pod.date, pod.unit_id, d.name, pod.caretaker_count;
