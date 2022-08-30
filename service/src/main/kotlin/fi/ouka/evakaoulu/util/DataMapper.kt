package fi.ouka.evakaoulu.util

class DataMapper<EnumClass> {
    val alphanumericValues: MutableMap<EnumClass, String> = mutableMapOf()
    val numericValues: MutableMap<EnumClass, Int> = mutableMapOf()
    var rowMap: Map<String, List<DataMapper<EnumClass>>> = mapOf()

    fun setAlphanumericValue(field: EnumClass, value: String) { alphanumericValues.put(field, value) }
    fun getAlphanumericValue(field: EnumClass): String? { return alphanumericValues.get(field)}
    fun setNumericValue(field: EnumClass, value: Int) { numericValues.put(field, value) }
    fun getNumericValue(field: EnumClass): Int? { return numericValues.get(field)}

    fun setChildRowMap(childMap: Map<String, List<DataMapper<EnumClass>>>) { rowMap = childMap }
    fun getChildRowMap(): Map<String, List<DataMapper<EnumClass>>> { return rowMap }
}

enum class FieldType {
    ALPHANUMERIC,
    NUMERIC,
    // we need a specific monetary type because they are prescaled by 100, so they include two decimals
    MONETARY,
}
