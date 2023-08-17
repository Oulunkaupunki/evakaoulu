SET TIMEZONE = 'Europe/Helsinki';
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
        SELECT distinct absence_type
        FROM absence
        WHERE child_id = p.id
            AND absence.date = :date_val::DATE
    )                          AS poissaolon_syy
FROM person p
    JOIN placement pl ON pl.child_id = p.id
        AND pl.start_date <= :date_val::DATE
        AND pl.end_date >= :date_val::DATE
    JOIN daycare d ON pl.unit_id = d.id
    JOIN care_area ca ON d.care_area_id = ca.id
    JOIN daycare_group_placement dgp ON pl.id = dgp.daycare_placement_id
        AND dgp.start_date <= :date_val::DATE
        AND dgp.end_date >= :date_val::DATE
    JOIN daycare_group dg ON d.id = dg.daycare_id
        AND dgp.daycare_group_id = dg.id
    LEFT JOIN backup_care bc ON p.id = bc.child_id
        AND bc.start_date <= :date_val::DATE
        AND bc.end_date >= :date_val::DATE
    LEFT JOIN daycare bu ON bu.id = bc.unit_id
    LEFT JOIN daycare_group bg ON bg.id = bc.group_id
    LEFT JOIN service_need sn ON pl.id = sn.placement_id
        AND sn.start_date <= :date_val::DATE
        AND sn.end_date >= :date_val::DATE
    JOIN service_need_option sno ON sno.id = sn.option_id
    LEFT JOIN assistance_need an ON p.id = an.child_id
        AND an.start_date <= :date_val::DATE
        AND an.end_date >= :date_val::DATE
    LEFT JOIN assistance_need_voucher_coefficient anvc ON p.id = anvc.child_id
        AND lower(anvc.validity_period) <= :date_val::DATE
        AND upper(anvc.validity_period) >= :date_val::DATE;