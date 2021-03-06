-- SPDX-FileCopyrightText: 2021 City of Oulu
--
-- SPDX-License-Identifier: LGPL-2.1-or-later

INSERT INTO voucher_value
    (id, validity, base_value, base_value_age_under_three)
VALUES
    ('084314dc-ed7f-4725-92f2-5c220bb4bb7e', daterange('2000-01-01', NULL, '[]'), 82500, 126600)
ON CONFLICT (id) DO
UPDATE SET
    validity = EXCLUDED.validity,
    base_value = EXCLUDED.base_value,
    base_value_age_under_three = EXCLUDED.base_value_age_under_three
WHERE
    voucher_value.validity <> EXCLUDED.validity OR
    voucher_value.base_value <> EXCLUDED.base_value OR
    voucher_value.base_value_age_under_three <> EXCLUDED.base_value_age_under_three;
