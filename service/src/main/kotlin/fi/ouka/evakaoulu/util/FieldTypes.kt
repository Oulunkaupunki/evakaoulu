package fi.ouka.evakaoulu.util

enum class FieldType {
    ALPHANUMERIC,
    NUMERIC,

    // we need a specific monetary type because they are prescaled by 100, so they include two decimals
    MONETARY,
}
