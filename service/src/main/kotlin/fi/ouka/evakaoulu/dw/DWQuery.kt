package fi.ouka.evakaoulu.dw

enum class DWQuery(
    val queryName: String,
    val query: DWQueries.CsvQuery,
) {
    ABSENCE("absences", DWQueries.getAbsences),
    APPLICATION_INFO("applicationInfos", DWQueries.getApplicationInfos),
    ASSISTANCE_ACTION("assistanceActions", DWQueries.getAssistanceActions),
    ASSISTANCE_NEED_DECISION("assistanceNeedDecisions", DWQueries.getAssistanceNeedDecisions),
    DAILY_INFO("dailyInfos", DWQueries.getDailyInfos),
    DAILY_ATTENDANCE("dailyUnitsAndGroupsAttendances", DWQueries.getDailyUnitsAndGroupsAttendances),
    DAILY_OCCUPANCY_CONFIRMED("dailyUnitsOccupanciesConfirmed", DWQueries.getDailyUnitsOccupanciesConfirmed),
    DAILY_OCCUPANCY_REALIZED("dailyUnitsOccupanciesRealized", DWQueries.getDailyUnitsOccupanciesRealized),
    DAYCARE_ASSISTANCE("daycareAssistances", DWQueries.getDaycareAssistances),
    FEE_DECISION("feeDecisions", DWQueries.getFeeDecisions),
    OTHER_ASSISTANCE_MEASURE("otherAssistanceMeasures", DWQueries.getOtherAssistanceMeasures),
    PLACEMENT("placements", DWQueries.getPlacements),
    PRESCHOOL_ASSISTANCE("preschoolAssistances", DWQueries.getPreschoolAssistances),
    UNIT_GROUP("unitsAndGroups", DWQueries.getUnitsAndGroups),
    VOUCHER_VALUE_DECISION("voucherValueDecisions", DWQueries.getVoucherValueDecisions),
}
