SELECT
    now() AT TIME ZONE 'Europe/Helsinki'               AS aikaleima,
    decision_number                                    AS päätos_tuesta,
    child_id                                           AS lapsen_id,
    lower(validity_period)                             AS tuen_alkupvm,
    upper(validity_period)                             AS tuen_loppupvm,
    structural_motivation_opt_smaller_group            AS pienennetty_ryhmä,
    structural_motivation_opt_special_group            AS erityisryhmä,
    structural_motivation_opt_small_group              AS pienryhmä,
    structural_motivation_opt_group_assistant          AS ryhmäkohtainen_avustaja,
    structural_motivation_opt_child_assistant          AS lapsikohtainen_avustaja,
    structural_motivation_opt_additional_staff         AS henkilöresurssien_lisäys,
    service_opt_consultation_special_ed                AS veon_antama_konsultaatio,
    service_opt_part_time_special_ed                   AS veon_osa_aikainen_opetus,
    service_opt_full_time_special_ed                   AS veon_kokoaikainen_opetus,
    service_opt_interpretation_and_assistance_services AS tulkitsemis_ja_avustamispalvelut,
    service_opt_special_aides                          AS apuvälineet,
    assistance_levels                                  AS tuen_taso
FROM assistance_need_decision
WHERE (current_date AT TIME ZONE 'Europe/Helsinki')::date - INTERVAL '3 months' <= upper(validity_period)
