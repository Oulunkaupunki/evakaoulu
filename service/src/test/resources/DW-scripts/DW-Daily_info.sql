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
    sa.count                   AS henkilökuntaa_läsnä,
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
    abo.name_fi                AS tuentarve,
    anvc.coefficient           AS tuentarpeen_kerroin,
    an.capacity_factor         AS lapsen_kapasiteetti,
    a.id IS NOT NULL           AS poissaolo,
    a.absence_type             AS poissaolon_syy
FROM person p
    JOIN placement pl ON pl.child_id = p.id AND pl.start_date <= current_date AND pl.end_date >= current_date
    JOIN daycare d ON pl.unit_id = d.id
    JOIN care_area ca ON d.care_area_id = ca.id
    JOIN daycare_group_placement dgp ON pl.id = dgp.daycare_placement_id
    JOIN daycare_group dg ON d.id = dg.daycare_id AND dgp.daycare_group_id = dg.id
    JOIN daycare_caretaker dc ON dg.id = dc.group_id
        AND dc.start_date <= current_date
        AND (dc.end_date >= current_date or dc.end_date is null)
    left JOIN staff_attendance sa ON dg.id = sa.group_id AND sa.date = current_date
    left JOIN backup_care bc ON p.id = bc.child_id
        AND bc.start_date <= current_date
        AND bc.end_date >= current_date
    left JOIN daycare bu ON bu.id = bc.unit_id
    left JOIN daycare_group bg ON bg.id = bc.group_id
    left JOIN service_need sn ON pl.id = sn.placement_id
        AND sn.start_date <= current_date
        AND sn.end_date >= current_date
    JOIN service_need_option sno ON sno.id = sn.option_id
    left JOIN assistance_need an ON p.id = an.child_id
        AND an.start_date <= current_date
        AND an.end_date >= current_date
    left JOIN assistance_basis_option_ref abor ON an.id = abor.need_id
    left JOIN assistance_basis_option abo ON abor.option_id = abo.id
    left JOIN assistance_need_voucher_coefficient anvc ON p.id = anvc.child_id
        AND lower(anvc.validity_period) <= current_date
        AND upper(anvc.validity_period) >= current_date
    left JOIN absence a ON p.id = a.child_id AND a.date = current_date
WHERE pl.start_date <= current_date AND pl.end_date >= current_date
