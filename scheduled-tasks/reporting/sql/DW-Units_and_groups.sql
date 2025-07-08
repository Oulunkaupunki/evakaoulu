-- SPDX-FileCopyrightText: 2023-2025 City of Oulu
--
-- SPDX-License-Identifier: LGPL-2.1-or-later

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
    dg.name                                AS ryhmä,
    dg.id                                  AS ryhmä_id,
    dg.start_date                          AS ryhmän_alkupvm,
    dg.end_date                            AS ryhmän_loppupvm
FROM daycare_group dg
    JOIN daycare d on dg.daycare_id = d.id
    JOIN care_area ca on ca.id = d.care_area_id
    LEFT JOIN daycare_caretaker dc ON dg.id = dc.group_id
        AND dc.start_date <= :date_val::DATE
        AND (dc.end_date >= :date_val::DATE or dc.end_date is null)
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
    dc.amount
ORDER BY d.name;