WITH application_infos AS (
    SELECT
        now() AT TIME ZONE 'Europe/Helsinki'    AS tiedoston_ajopaiva,
        ap.id as hakemuksen_id,
        ap.created_at as hakemus_luotu,
        ap.modified_at as hakemusta_paivitetty,
        ap.type as tyyppi,
        ap.status as tilanne,
        ap.origin as alkupera,
        ap.transferapplication as siirtohakemus,
        ap.child_id as lapsen_id,
        pe.date_of_birth as syntymaaika,
        jsonb_array_elements_text(ap.document->'apply'->'preferredUnits') AS yksikot,
        ap.document->'preferredStartDate' AS haluttu_aloituspaiva
    FROM application ap, person pe
    WHERE :date_val::DATE - INTERVAL '3 months' <= ap.created_at
    AND ap.child_id = pe.id
ORDER BY ap.created_at DESC)
SELECT hakemuksen_id, hakemus_luotu, hakemusta_paivitetty, tyyppi, tilanne, alkupera, siirtohakemus, lapsen_id, syntymaaika, yksikot, haluttu_aloituspaiva, dg.name as yksikko_nimi, dg.care_area_id as alue_id, ca.name as alue_nimi
FROM application_infos, daycare dg, care_area ca
WHERE dg.id IN (application_infos.yksikot::uuid)
  AND dg.care_area_id = ca.id;