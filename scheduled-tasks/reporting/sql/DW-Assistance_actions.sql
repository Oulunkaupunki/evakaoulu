SELECT
    now() AT TIME ZONE 'Europe/Helsinki'    	AS pvm,
    ac.child_id                                	AS lapsen_id,
    array(
        SELECT name_fi
        FROM assistance_action_option
        WHERE id IN (
            SELECT option_id
            FROM assistance_action_option_ref
            WHERE action_id = ac.id
        )
    )                                    		AS tukitoimi,
    ac.other_action								as muu_tukitoimi,
    ac.start_date 		                    	AS aloitus_pvm,
    ac.end_date 		                    	AS loppu_pvm
FROM assistance_action ac
WHERE :date_val::DATE - INTERVAL '3 years' <= ac.end_date;