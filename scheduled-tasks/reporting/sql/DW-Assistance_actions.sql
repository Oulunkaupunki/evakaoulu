-- SPDX-FileCopyrightText: 2023-2025 City of Oulu
--
-- SPDX-License-Identifier: LGPL-2.1-or-later

SELECT
    now() AT TIME ZONE 'Europe/Helsinki'    	AS pvm,
    ac.child_id                                	AS lapsen_id,
    aao.name_fi                                 AS tukitoimi,
    ac.other_action                             AS muu_tukitoimi,
    ac.start_date                               AS aloitus_pvm,
    ac.end_date                                 AS loppu_pvm,
    aao.category                                AS tuen_tyyppi
FROM assistance_action ac
    LEFT JOIN assistance_action_option_ref aaor ON aaor.action_id = ac.id
    LEFT JOIN assistance_action_option aao ON aao.id = aaor.option_id
WHERE :date_val::DATE - INTERVAL '3 months' <= ac.end_date;