SELECT
    now() AT TIME ZONE 'Europe/Helsinki'    AS pvm,
    child_id                                AS lapsen_id,
    type                                    AS muu_toimi,
    lower(valid_during)                     AS aloitus_pvm,
    upper(valid_during)                     AS loppu_pvm
FROM other_assistance_measure
WHERE :date_val::DATE - INTERVAL '3 months' <= upper(valid_during);