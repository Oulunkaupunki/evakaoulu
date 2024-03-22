SELECT value
FROm client_attributes
WHERE name = 'saml.signing.certificate'
    AND client_id = (
        SELECT id
        FROM client
        WHERE client_id = :client_val
        AND protocol = 'saml'
    )
