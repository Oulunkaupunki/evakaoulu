SELECT
    now() AT TIME ZONE 'Europe/Helsinki'    AS aikaleima,
    vvd.decision_number                     AS arvopäätöksen_numero,
    vvd.valid_from                          AS alkupvm,
    vvd.valid_to                            AS loppupvm,
    vvd.decision_type                       AS huojennustyyppi,
    vvd.family_size                         AS perhekoko,
    vvd.voucher_value                       AS palvelusetelin_arvo,
    vvd.final_co_payment                    AS omavastuuosuus,
    vvd.child_id                            AS lapsen_id,
    vvd.placement_type                      AS toimintamuoto,
    ca.name                                 AS palvelualue,
    ca.id                                   AS palvelualue_id,
    d.name                                  AS toimipaikka,
    d.id                                    AS toimipaikka_id
FROM voucher_value_decision vvd
    JOIN daycare d ON vvd.placement_unit_id = d.id
    JOIN care_area ca ON d.care_area_id = ca.id
WHERE vvd.status = 'SENT'
    AND vvd.decision_number IS NOT NULL -- ei tuoda effican päätöksiä
    AND :date_val::DATE - INTERVAL '3 months' <= vvd.valid_to;
