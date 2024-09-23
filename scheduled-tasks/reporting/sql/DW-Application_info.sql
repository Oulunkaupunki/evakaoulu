SELECT
    now() AT TIME ZONE 'Europe/Helsinki'    AS tiedoston_ajopaiva,
    ap.id as hakemuksen_id,
    ap.created as hakemus_luotu,
    ap.type as tyyppi,
    ap.child_id as lapsen_id,
    pe.date_of_birth as syntymaaika,
    jsonb_array_elements_text(ap.document->'apply'->'preferredUnits') AS yksikot
FROM application ap, person pe
WHERE :date_val::DATE - INTERVAL '12 months' <= ap.created
AND ap.child_id = pe.id
ORDER BY ap.created DESC;