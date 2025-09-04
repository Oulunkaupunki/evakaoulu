package fi.ouka.evakaoulu.dw

enum class DwQuery(
    val queryName: String,
    val query: DwQueries.CsvQuery,
) {
    ABSENCE("absences", DwQueries.getAbsences),
    APPLICATION_INFO("application_info", DwQueries.getApplicationInfos),
    ASSISTANCE_ACTION("assistance_actions_", DwQueries.getAssistanceActions),
    ASSISTANCE_NEED_DECISION("assistance_need_decisions_", DwQueries.getAssistanceNeedDecisions),
    DAILY_INFO("daily_info_", DwQueries.getDailyInfos),
    DAILY_ATTENDANCE("daily_units_and_groups_", DwQueries.getDailyUnitsAndGroupsAttendances),
    DAILY_OCCUPANCY_CONFIRMED("daily_units_occupancies_confirmed_", DwQueries.getDailyUnitsOccupanciesConfirmed),
    DAILY_OCCUPANCY_REALIZED("daily_units_occupancies_realized_", DwQueries.getDailyUnitsOccupanciesRealized),
    DAYCARE_ASSISTANCE("daycare_assistances_", DwQueries.getDaycareAssistances),
    FEE_DECISION("fee_decisions_", DwQueries.getFeeDecisions),
    OTHER_ASSISTANCE_MEASURE("other_assistance_measures_", DwQueries.getOtherAssistanceMeasures),
    PLACEMENT("placements_", DwQueries.getPlacements),
    PRESCHOOL_ASSISTANCE("preschool_assistances_", DwQueries.getPreschoolAssistances),
    UNIT_GROUP("units_and_groups_", DwQueries.getUnitsAndGroups),
    VOUCHER_VALUE_DECISION("voucher_value_decisions_", DwQueries.getVoucherValueDecisions),
}
