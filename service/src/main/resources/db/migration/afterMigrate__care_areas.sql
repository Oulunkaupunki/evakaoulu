-- SPDX-FileCopyrightText: 2021 City of Oulu
--
-- SPDX-License-Identifier: LGPL-2.1-or-later
INSERT INTO care_area
  (name, area_code, sub_cost_center, short_name)
VALUES
  ('Haukipudas', NULL, NULL, 'haukipudas'),
  ('Jääli', NULL, NULL, 'jaali'),
  ('Kaakkuri', NULL, NULL, 'kaakkuri'),
  ('Karjasilta-Höyhtyä', NULL, NULL, 'karjasilta-hoyhtya'),
  ('Kastelli', NULL, NULL, 'kastelli'),
  ('Kaukovainio', NULL, NULL, 'kaukovainio'),
  ('Kello-Kiviniemi', NULL, NULL, 'kello-kiviniemi'),
  ('Keskusta', NULL, NULL, 'keskusta'),
  ('Kiiminki', NULL, NULL, 'kiiminki'),
  ('Korvensuora', NULL, NULL, 'korvensuora'),
  ('Maikkula', NULL, NULL, 'maikkula'),
  ('Myllyoja', NULL, NULL, 'myllyoja'),
  ('Oulunsalo', NULL, NULL, 'oulunsalo'),
  ('Patenniemi-Rajakylä', NULL, NULL, 'patenniemi-rajakyla'),
  ('Ritaharju-Kuivasjärvi-Pöllökangas', NULL, NULL, 'ritaharju-kuivasjarvi-pollokangas'),
  ('Tuira-Toppila', NULL, NULL, 'tuira-toppila'),
  ('Yli-Ii', NULL, NULL, 'yli-ii'),
  ('Yli-Kiiminki', NULL, NULL, 'yli-kiiminki')
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