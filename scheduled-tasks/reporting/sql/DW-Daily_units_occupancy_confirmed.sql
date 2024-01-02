WITH
caretaker_counts AS (
    SELECT 
        a.id                                    AS area_id, 
        a.name                                  AS area_name, 
        u.id                                    AS unit_id, 
        u.name                                  AS unit_name, 
        :date_val::DATE                         AS date,
        COALESCE(
            SUM(c.amount),
            0.0
        ) AS caretaker_count
    FROM daycare_group g
        JOIN daycare u ON g.daycare_id = u.id
            AND daterange(g.start_date, g.end_date, '[]') @> :date_val::DATE
        JOIN care_area a ON a.id = u.care_area_id
        LEFT JOIN daycare_caretaker c ON g.id = c.group_id 
            AND daterange(c.start_date, c.end_date, '[]') @> :date_val::DATE
        LEFT JOIN holiday h ON :date_val::DATE = h.date
            AND NOT u.operation_days @> ARRAY[1, 2, 3, 4, 5, 6, 7]
    WHERE date_part('isodow', :date_val::DATE) = ANY(u.operation_days)
        AND h.date IS NULL
        AND daterange(u.opening_date, u.closing_date, '[]') @> :date_val::DATE
    GROUP BY a.id, u.id
),
placements AS (
    SELECT
        p.id AS placement_id,
        p.child_id,
        p.unit_id,
        p.type,
        u.type && array['FAMILY', 'GROUP_FAMILY']::care_types[] AS family_unit_placement,
        daterange(p.start_date, p.end_date, '[]') AS period
    FROM placement p
        JOIN daycare u ON p.unit_id = u.id
    WHERE daterange(p.start_date, p.end_date, '[]') @> :date_val::DATE
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
placements_on_date AS (
    SELECT
        cc.date,
        cc.caretaker_count                          AS caretaker_count,
        pl.placement_id                             AS placement_id,
        pl.child_id                                 AS child_id,
        pl.unit_id                                  AS unit_id,
        CASE
            WHEN af.capacity_factor IS NOT NULL
                THEN af.capacity_factor
            ELSE 1
        END                                         AS assistance_coefficient,
        CASE
            WHEN pl.family_unit_placement = TRUE
                THEN 1.75
            WHEN cc.date < (p.date_of_birth + interval '3 year')::DATE
                THEN (
                    CASE
                        WHEN sno.occupancy_coefficient_under_3y IS NOT NULL
                            THEN sno.occupancy_coefficient_under_3y
                        ELSE dsc.occupancy_coefficient_under_3y
                    END
                )
            ELSE (
                CASE
                    WHEN sno.occupancy_coefficient IS NOT NULL
                        THEN sno.occupancy_coefficient
                    ELSE dsc.occupancy_coefficient
                END
            )
        END                                         AS service_need_coefficient
    from caretaker_counts cc
        JOIN (
            SELECT
                p.id AS placement_id,
                p.child_id,
                p.unit_id,
                p.type,
                u.type && array['FAMILY', 'GROUP_FAMILY']::care_types[] AS family_unit_placement,
                daterange(p.start_date, p.end_date, '[]') AS period
            FROM placement p
                JOIN daycare u ON p.unit_id = u.id
            WHERE daterange(p.start_date, p.end_date, '[]') @> :date_val::DATE
        ) pl ON pl.unit_id = cc.unit_id
        JOIN person p ON p.id = pl.child_id
        LEFT JOIN assistance_factor af ON af.child_id = pl.child_id
            AND af.valid_during @> cc.date
        LEFT JOIN service_need sn ON sn.placement_id = pl.placement_id
            AND daterange(sn.start_date, sn.end_date, '[]') @> cc.date
        left JOIN service_need_option sno ON sn.option_id = sno.id
        JOIN default_sn_coefficients dsc ON dsc.valid_placement_type = pl.type
)
SELECT
    pod.date                                                       AS pvm,
    pod.unit_id                                                    AS toimintayksikkö_id,
    d.name                                                         AS toimintayksikkö,
    pod.caretaker_count                                            AS kasvattajien_lkm,
    COUNT(pod.placement_id)                                        AS sijoituksien_lkm,
    SUM(pod.assistance_coefficient * pod.service_need_coefficient) AS täyttöaste_summa,
    CASE
        WHEN pod.caretaker_count IS NULL OR pod.caretaker_count = 0
            THEN null
        ELSE (SUM(pod.assistance_coefficient * pod.service_need_coefficient) / (pod.caretaker_count * 7)) * 100
    END                                                                AS täyttöaste_prosentteina
from placements_on_date pod
    JOIN daycare d ON d.id = pod.unit_id
group by
    pod.date, pod.unit_id, d.name, pod.caretaker_count;