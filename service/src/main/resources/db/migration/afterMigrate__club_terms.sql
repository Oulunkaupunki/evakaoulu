-- SPDX-FileCopyrightText: 2021 City of Oulu
--
-- SPDX-License-Identifier: LGPL-2.1-or-later

INSERT INTO club_term
    (id, term, application_period, term_breaks)
VALUES
    ('4df642ba-ac98-11eb-9ca8-2ff9d825b593', '[2021-08-16,2022-05-31]', '[2021-01-01,2021-08-16]', '{}'),
    ('4df67de8-ac98-11eb-9ca9-93954620f29f', '[2022-08-15,2023-05-31]', '[2022-01-01,2022-08-15]', '{}'),
    ('a0394e4a-c3e4-11ed-8aa6-075487fa65ad', '[2023-08-14,2024-05-31]', '[2023-01-01,2023-08-15]', datemultirange('[2023-10-23,2023-10-27]', '[2023-12-06,2023-12-06]', '[2023-12-23,2024-01-07]','[2024-03-04,2024-03-08]','[2024-03-29,2024-04-01]','[2024-05-01,2024-05-01]','[2024-05-09,2024-05-10]'))
ON CONFLICT (id) DO
UPDATE SET
    term = EXCLUDED.term,
    application_period = EXCLUDED.application_period,
    term_breaks = EXCLUDED.term_breaks
WHERE
    club_term.term <> EXCLUDED.term OR
    club_term.application_period <> EXCLUDED.application_period OR
    club_term.term_breaks <> EXCLUDED.term_breaks;
