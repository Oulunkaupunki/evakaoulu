-- SPDX-FileCopyrightText: 2023-2025 City of Oulu
--
-- SPDX-License-Identifier: LGPL-2.1-or-later

SELECT
    now() AT TIME ZONE 'Europe/Helsinki'    AS aikaleima,
    fd.decision_number                      AS maksupäätöksen_numero,
    fd.id                                   AS maksupäätös_id,
    lower(fd.valid_during)                  AS alkupvm,
    upper(fd.valid_during)                  AS loppupvm,
    fd.decision_type                        AS huojennustyyppi,
    fd.family_size                          AS perhekoko,
    fd.total_fee                            AS kokonaismaksu,
    fd.status                               AS tila,
    fdc.child_id                            AS lapsi_id,
    fdc.final_fee                           AS lapsikohtainen_maksu,
    fdc.placement_type                      AS toimintamuoto,
    ca.name                                 AS palvelualue,
    ca.id                                   AS palvelualue_id,
    d.name                                  AS toimipaikka,
    d.id                                    AS toimipaikka_id,
    d.dw_cost_center                        AS kustannuspaikka
FROM fee_decision fd
    JOIN fee_decision_child fdc on fd.id = fdc.fee_decision_id
    JOIN daycare d ON fdc.placement_unit_id = d.id
    JOIN care_area ca ON d.care_area_id = ca.id
WHERE fd.decision_number IS NOT NULL -- ei tuoda effican päätöksiä
    AND :date_val::DATE - INTERVAL '3 months' <= upper(fd.valid_during);
