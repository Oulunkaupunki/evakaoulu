SELECT
    now() AT TIME ZONE 'Europe/Helsinki'    AS pvm,
    child_id                                AS lapsen_id,
    level                                   AS tuentarve_esiopetuksessa,
    lower(valid_during)                     AS aloitus_pvm,
    upper(valid_during)                     AS loppu_pvm
FROM preschool_assistance
WHERE :date_val::DATE - INTERVAL '3 months' <= upper(valid_during);