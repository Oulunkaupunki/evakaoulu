-- SPDX-FileCopyrightText: 2021 City of Oulu
--
-- SPDX-License-Identifier: LGPL-2.1-or-later
INSERT INTO care_area
  (name, area_code, sub_cost_center, short_name)
VALUES
  ('Haukipudas', NULL, NULL, 'Haukipudas'),
  ('Jääli', NULL, NULL, 'Jääli'),
  ('Kaakkuri', NULL, NULL, 'Kaakkuri'),
  ('Karjasilta-Höyhtyä', NULL, NULL, 'Karjasilta-Höyhtyä'),
  ('Kastelli', NULL, NULL, 'Kastelli'),
  ('Kaukovainio', NULL, NULL, 'Kaukovainio'),
  ('Kello-Kiviniemi', NULL, NULL, 'Kello-Kiviniemi'),
  ('Keskusta', NULL, NULL, 'Keskusta'),
  ('Kiiminki', NULL, NULL, 'Kiiminki'),
  ('Korvensuora', NULL, NULL, 'Korvensuora'),
  ('Maikkula', NULL, NULL, 'Maikkula'),
  ('Myllyoja', NULL, NULL, 'Myllyoja'),
  ('Oulunsalo', NULL, NULL, 'Oulunsalo'),
  ('Patenniemi-Rajakylä', NULL, NULL, 'Patenniemi-Rajakylä'),
  ('Ritaharju-Kuivasjärvi-Pöllökangas', NULL, NULL, 'Ritaharju-Kuivasjärvi-Pöllökangas'),
  ('Tuira-Toppila', NULL, NULL, 'Tuira-Toppila'),
  ('Yli-Ii', NULL, NULL, 'Yli-Ii'),
  ('Yli-Kiiminki', NULL, NULL, 'Yli-Kiiminki')
ON CONFLICT (name) DO
UPDATE SET
  name = EXCLUDED.name,
  area_code = EXCLUDED.area_code,
  sub_cost_center = EXCLUDED.sub_cost_center,
  short_name = EXCLUDED.short_name
WHERE
  care_area.name <> EXCLUDED.name OR
  care_area.area_code <> EXCLUDED.area_code OR
  care_area.sub_cost_center <> EXCLUDED.sub_cost_center OR
  care_area.short_name <> EXCLUDED.short_name;