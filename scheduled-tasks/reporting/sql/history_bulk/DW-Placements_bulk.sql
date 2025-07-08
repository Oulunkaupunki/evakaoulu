-- SPDX-FileCopyrightText: 2023-2025 City of Oulu
--
-- SPDX-License-Identifier: LGPL-2.1-or-later

SELECT
    now() AT TIME ZONE 'Europe/Helsinki' AS aikaleima,
    p.id                                 AS lapsen_id,
    d.name                               AS toimintayksikkö,
    d.id                                 AS toimintayksikkö_id,
    pl.start_date                        AS sijoituksen_alkupvm,
    pl.end_date                          AS sijoituksen_loppupvm,
    dg.name                              AS ryhmä,
    dg.id                                AS ryhmä_id,
    dg.start_date                        AS ryhmän_alkupvm,
    dg.end_date                          AS ryhmän_loppupvm,
    sno.name_fi                          AS palveluntarve,
    sno.id                               AS palveluntarve_id,
    sn.start_date                        AS palveluntarpeen_alkupvm,
    sn.end_date                          AS palveluntarpeen_loppupvm
FROM person p
    JOIN placement pl ON pl.child_id = p.id
        AND pl.start_date <= :date_val::DATE
        AND pl.end_date >= :date_val::DATE
    JOIN daycare d ON pl.unit_id = d.id
    JOIN daycare_group_placement dgp ON pl.id = dgp.daycare_placement_id
        AND dgp.start_date <= :date_val::DATE
        AND dgp.end_date >= :date_val::DATE
    JOIN daycare_group dg ON d.id = dg.daycare_id
        AND dgp.daycare_group_id = dg.id
    LEFT JOIN service_need sn ON pl.id = sn.placement_id
        AND sn.start_date <= :date_val::DATE
        AND sn.end_date >= :date_val::DATE
    LEFT JOIN service_need_option sno ON sn.option_id = sno.id
WHERE :date_val::DATE <= pl.end_date;
