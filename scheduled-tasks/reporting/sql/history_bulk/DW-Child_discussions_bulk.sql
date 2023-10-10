SELECT
    now() AT TIME ZONE 'Europe/Helsinki'    AS aikaleima,
    id,
    child_id 		                        AS lapsen_id,
    created AT TIME ZONE 'Europe/Helsinki'  AS luonti_aika,
    offered_date 	                        AS tarjottu_pvm,
    held_date 		                        AS pidetty_pvm,
    counseling_date                         AS neuvonpito_pvm
FROM child_discussion
WHERE :date_val::DATE <= date(created);