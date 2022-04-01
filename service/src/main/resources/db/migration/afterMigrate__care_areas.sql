-- SPDX-FileCopyrightText: 2021 City of Oulu
--
-- SPDX-License-Identifier: LGPL-2.1-or-later
INSERT INTO care_area
  (id, name, area_code, sub_cost_center, short_name)
VALUES
  ('185be5a8-7aae-11ec-a67f-1b71d8cde045', 'Haukipudas', NULL, NULL, 'haukipudas'),
  ('702ee5c6-7cf4-11ec-bba2-2f668cb82533', 'Jääli', NULL, NULL, 'jaali'),
  ('702eeaa8-7cf4-11ec-bba3-a78313db20f3', 'Kaakkuri', NULL, NULL, 'kaakkuri'),
  ('702eeba2-7cf4-11ec-bba4-83599b5cc8e7', 'Karjasilta-Höyhtyä', NULL, NULL, 'karjasilta-hoyhtya'),
  ('702eec56-7cf4-11ec-bba5-53f47c29a967', 'Kastelli', NULL, NULL, 'kastelli'),
  ('702eed14-7cf4-11ec-bba6-234a90ea63ed', 'Kaukovainio', NULL, NULL, 'kaukovainio'),
  ('702eedd2-7cf4-11ec-bba7-57c86e4d2cff', 'Kello-Kiviniemi', NULL, NULL, 'kello-kiviniemi'),
  ('702eefe4-7cf4-11ec-bba8-3b19dc2689d0', 'Keskusta', NULL, NULL, 'keskusta'),
  ('702ef0ca-7cf4-11ec-bba9-cb4d2a65ed63', 'Kiiminki', NULL, NULL, 'kiiminki'),
  ('702ef16a-7cf4-11ec-bbaa-3f499df4448f', 'Korvensuora', NULL, NULL, 'korvensuora'),
  ('702ef214-7cf4-11ec-bbab-0718978ec9f6', 'Maikkula', NULL, NULL, 'maikkula'),
  ('702ef2b4-7cf4-11ec-bbac-e721a45f2de2', 'Myllyoja', NULL, NULL, 'myllyoja'),
  ('702ef7a0-7cf4-11ec-bbad-7f5b7a1e36f4', 'Oulunsalo', NULL, NULL, 'oulunsalo'),
  ('185be62a-7aae-11ec-a680-2b5d5379b31e', 'Patenniemi-Rajakylä', NULL, NULL, 'patenniemi-rajakyla'),
  ('185be10c-7aae-11ec-a67e-4bbf8c64d06c', 'Ritaharju-Kuivasjärvi-Pöllökangas', NULL, NULL, 'ritaharju-kuivasjarvi-pollokangas'),
  ('702f0718-7cf4-11ec-bbb0-13642fbc4623', 'Tuira-Toppila', NULL, NULL, 'tuira-toppila'),
  ('702f1316-7cf4-11ec-bbb1-ebe6fde98277', 'Yli-Ii', NULL, NULL, 'yli-ii'),
  ('702f142e-7cf4-11ec-bbb2-ab78be67da3a', 'Ylikiiminki', NULL, NULL, 'ylikiiminki')
ON CONFLICT (id) DO
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