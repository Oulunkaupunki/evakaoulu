SELECT
    now()                      AS pvm,
    p.id                       AS lapsen_id,
    p.social_security_number   AS henkilöturvatunnus,
    p.date_of_birth            AS syntymäaika,
    p.language                 AS kieli,
    p.street_address           AS postiosoite,
    p.postal_code              AS postinumero,
    p.post_office              AS postitoimipaikka,
    p.nationalities            AS kansalaisuudet,
    p.language                 AS kieli,
    pl.type                    AS sijoitustyyppi,
    pl.unit_id                 AS sijoitusyksikkö_id,
    d.name                     AS yksikön_nimi,
    d.care_area_id             AS palvelualue_id,
    ca.name                    AS palvelualue,
    d.type                     AS toimintamuoto,
    d.provider_type            AS järjestämistapa,
    d.dw_cost_center           AS kustannuspaikka,
    dg.id                      AS sijoitusryhmä_id,
    dg.name                    AS sijoitusryhmä,
    dc.amount                  AS henkilökuntaa_ryhmässä,
    sum(
        CASE
            WHEN sar.arrived IS NOT NULL
                THEN ROUND(
                    EXTRACT(
                        EPOCH FROM (
                            LEAST(sar.departed, timezone('Europe/Helsinki', (current_date + 1)::date::timestamp)) - GREATEST(sar.arrived, timezone('Europe/Helsinki', current_date::timestamp))
                        )
                    ) / 3600 / 7.75 * sar.occupancy_coefficient / 7,
                    4
                )
            ELSE sa.count
        END
    )                          AS henkilökuntaa_läsnä,
    bc.unit_id                 AS varahoitoyksikkö_id,
    bu.name                    AS varahoitoyksikkö,
    bc.group_id                AS varahoitoryhmä_id,
    bg.name                    AS varahoitoryhmä,
    sn.id IS NOT NULL          AS palveluntarve_merkitty,
    sno.name_fi                AS palveluntarve,
    sno.id                     AS palveluntarve_id,
    sno.part_day               AS osapäiväinen,
    sno.part_week              AS osaviikkoinen,
    sn.shift_care              AS vuorohoito,
    sno.daycare_hours_per_week AS tunteja_viikossa,
    array(
        SELECT name_fi
        FROM assistance_basis_option
        WHERE id IN (
            SELECT option_id
            FROM assistance_basis_option_ref
            WHERE need_id = an.id
        )
    )                          AS tuentarve,
    anvc.coefficient           AS tuentarpeen_kerroin,
    an.capacity_factor         AS lapsen_kapasiteetti,
    array(
        SELECT absence_type
        FROM absence
        WHERE child_id = p.id
            AND a.date = current_date
    )                          AS poissaolon_syy
FROM person p
    JOIN placement pl ON pl.child_id = p.id
        AND pl.start_date <= current_date
        AND pl.end_date >= current_date
    JOIN daycare d ON pl.unit_id = d.id
    JOIN care_area ca ON d.care_area_id = ca.id
    JOIN daycare_group_placement dgp ON pl.id = dgp.daycare_placement_id
        AND dgp.start_date <= current_date
        AND dgp.end_date >= current_date
    JOIN daycare_group dg ON d.id = dg.daycare_id
        AND dgp.daycare_group_id = dg.id
    JOIN daycare_caretaker dc ON dg.id = dc.group_id
        AND dc.start_date <= current_date
        AND (dc.end_date >= current_date or dc.end_date is null)
    LEFT JOIN staff_attendance sa ON dg.id = sa.group_id
        AND sa.date = current_date
    LEFT JOIN (
        SELECT arrived, departed, occupancy_coefficient
        FROM staff_attendance_realtime
        WHERE departed IS NOT NULL
            AND type NOT IN ('OTHER_WORK', 'TRAINING')
            AND (date(arrived) = current_date OR date(departed) = current_date)
        UNION ALL
        SELECT arrived, departed, occupancy_coefficient
        FROM staff_attendance_external
        WHERE departed IS NOT NULL
            AND (date(arrived) = current_date OR date(departed) = current_date)
    ) sar ON dc.group_id = dg.id
    LEFT JOIN backup_care bc ON p.id = bc.child_id
        AND bc.start_date <= current_date
        AND bc.end_date >= current_date
    LEFT JOIN daycare bu ON bu.id = bc.unit_id
    LEFT JOIN daycare_group bg ON bg.id = bc.group_id
    LEFT JOIN service_need sn ON pl.id = sn.placement_id
        AND sn.start_date <= current_date
        AND sn.end_date >= current_date
    JOIN service_need_option sno ON sno.id = sn.option_id
    LEFT JOIN assistance_need an ON p.id = an.child_id
        AND an.start_date <= current_date
        AND an.end_date >= current_date
    LEFT JOIN assistance_need_voucher_coefficient anvc ON p.id = anvc.child_id
        AND lower(anvc.validity_period) <= current_date
        AND upper(anvc.validity_period) >= current_date
    LEFT JOIN absence a ON p.id = a.child_id
        AND a.date = current_date
GROUP BY
    p.id,
    p.social_security_number,
    p.date_of_birth,
    p.language,
    p.street_address,
    p.postal_code,
    p.post_office,
    p.nationalities,
    p.language,
    pl.type,
    pl.unit_id,
    d.name,
    d.care_area_id,
    ca.name,
    d.type,
    d.provider_type,
    d.dw_cost_center,
    dg.id,
    dg.name,
    dc.amount,
    bc.unit_id,
    bu.name,
    bc.group_id,
    bg.name,
    sn.id,
    sno.name_fi,
    sno.id,
    sno.part_day,
    sno.part_week,
    sn.shift_care,
    sno.daycare_hours_per_week,
    anvc.coefficient,
    an.id,
    an.capacity_factor,
    a.date
