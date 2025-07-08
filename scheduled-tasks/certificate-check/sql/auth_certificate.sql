-- SPDX-FileCopyrightText: 2023-2025 City of Oulu
--
-- SPDX-License-Identifier: LGPL-2.1-or-later

SELECT value
FROm client_attributes
WHERE name = 'saml.signing.certificate'
    AND client_id = (
        SELECT id
        FROM client
        WHERE client_id = :client_val
        AND protocol = 'saml'
    )
