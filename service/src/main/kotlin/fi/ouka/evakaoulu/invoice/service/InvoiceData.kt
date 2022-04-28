package fi.ouka.evakaoulu.invoice.service

enum class InvoiceField {
    NOT_USED,
    INVOICE_IDENTIFIER,
    HEADER_ROW_CODE,
    CLIENT_GROUP,
    CLIENT_NAME1,
    CLIENT_NAME2,
    STREET_ADDRESS,
    POSTAL_ADDRESS,
    PHONE_NUMBER,
    FAX_NUMBER,
    CLIENT_CONTACT,
    CLIENT_BANK,
    CLIENT_BANK_ACCOUNT,
    CLIENT_TYPE,
    LANGUAGE_CODE,
    REMINDER_CODE,
    PAYMENT_METHOD,
    PAYMENT_DEFAULT_CODE,
    PRINTING_METHOD,
    INVOICE_DATE,
    DUE_DATE,
    ACCOUNTING_DATE,
    INCLUDED_LATE_PAYMENT_INTEREST,
    CREDIT_NOTE_INVOICE_NUMBER,
    INVOICE_NUMBER,
    REFERENCE_NUMBER,
    PAYMENT_TYPE,
    PARTNER_CODE,
    CURRENCY,
    INVOICE_TYPE,
    INVOICING_UNIT,
    DESCRIPTION,
    SECURITY_DENIAL,
    CONTRACT_NUMBER,
    ORDER_NUMBER,
    ADDRESS2,
    COUNTRY,
    SSN,
    LATE_PAYMENT_INTEREST,
    VAT_IDENTIFIER,
    DELIVERY_DATE,
    OVT_IDENTIFIER,
    PAYMENT_TERM,
    RF_REFERENCE,
    CODEBTOR_ROW_CODE,
    CODEBTOR_IDENTIFIER,
    CODEBTOR_NAME,
    CODEBTOR_STREET_ADDRESS,
    CODEBTOR_POSTAL_ADDRESS,
    CODEBTOR_PHONE_NUMBER,
    CODEBTOR_LANGUAGE_CODE,
    CODEBTOR_PARTNER_CODE,
    CODEBTOR_ADDRESS2,
    CODEBTOR_COUNTRY,
    CODEBTOR_NAME2,
    CODEBTOR_SSN,
    CODEBTOR_VAT_IDENTIFIER,
    CODEBTOR_OVT_IDENTIFIER,
    TEXT_ROW_CODE,
    CHILD_NAME,
    TIME_PERIOD,
    INVOICE_ROW_HEADER,
    CONSTANT_TEXT_IDENTIFIER,
}

enum class FieldType {
    ALPHANUMERIC,
    NUMERIC,
}

class Field(val field: InvoiceField, val fieldType: FieldType, val start: Int, val length: Int, val decimals: Int = 0)

val headerRowFields = listOf(
        Field(InvoiceField.INVOICE_IDENTIFIER, FieldType.ALPHANUMERIC, 1, 11),
        Field(InvoiceField.HEADER_ROW_CODE, FieldType.ALPHANUMERIC, 12, 1),
        Field(InvoiceField.CLIENT_GROUP, FieldType.ALPHANUMERIC, 13, 2),
        Field(InvoiceField.CLIENT_NAME1, FieldType.ALPHANUMERIC, 15, 50),
        Field(InvoiceField.CLIENT_NAME2, FieldType.ALPHANUMERIC, 65, 50),
        Field(InvoiceField.STREET_ADDRESS, FieldType.ALPHANUMERIC, 115, 30),
        Field(InvoiceField.POSTAL_ADDRESS, FieldType.ALPHANUMERIC, 145, 30),
        Field(InvoiceField.PHONE_NUMBER, FieldType.ALPHANUMERIC, 175, 15),
        Field(InvoiceField.FAX_NUMBER, FieldType.ALPHANUMERIC, 190, 15),
        Field(InvoiceField.CLIENT_CONTACT, FieldType.ALPHANUMERIC, 205, 30),
        Field(InvoiceField.CLIENT_BANK, FieldType.ALPHANUMERIC, 235, 30),
        Field(InvoiceField.CLIENT_BANK_ACCOUNT, FieldType.ALPHANUMERIC, 265, 15),
        Field(InvoiceField.CLIENT_TYPE, FieldType.ALPHANUMERIC, 280, 1),
        Field(InvoiceField.LANGUAGE_CODE, FieldType.ALPHANUMERIC, 281, 1),
        Field(InvoiceField.REMINDER_CODE, FieldType.ALPHANUMERIC, 282, 1),
        Field(InvoiceField.PAYMENT_METHOD, FieldType.ALPHANUMERIC, 283, 1),
        Field(InvoiceField.PAYMENT_DEFAULT_CODE, FieldType.ALPHANUMERIC, 284, 1),
        Field(InvoiceField.PRINTING_METHOD, FieldType.ALPHANUMERIC, 285, 1),
        Field(InvoiceField.INVOICE_DATE, FieldType.ALPHANUMERIC, 286, 8),
        Field(InvoiceField.DUE_DATE, FieldType.ALPHANUMERIC, 294, 8),
        Field(InvoiceField.ACCOUNTING_DATE, FieldType.ALPHANUMERIC, 302, 8),
        Field(InvoiceField.INCLUDED_LATE_PAYMENT_INTEREST, FieldType.NUMERIC, 310, 6, 2),
        // CHECK THIS - the spec says the field is 10 characters, but the
        // next field starts at 330!
        Field(InvoiceField.CREDIT_NOTE_INVOICE_NUMBER, FieldType.ALPHANUMERIC, 318, 12),
        Field(InvoiceField.INVOICE_NUMBER, FieldType.ALPHANUMERIC, 330, 8),
        Field(InvoiceField.REFERENCE_NUMBER, FieldType.ALPHANUMERIC, 338, 20),
        Field(InvoiceField.PAYMENT_TYPE, FieldType.ALPHANUMERIC, 358, 1),
        Field(InvoiceField.PARTNER_CODE, FieldType.ALPHANUMERIC, 359, 10),
        Field(InvoiceField.CURRENCY, FieldType.ALPHANUMERIC, 369, 3),
        Field(InvoiceField.INVOICE_TYPE, FieldType.ALPHANUMERIC, 372, 2),
        Field(InvoiceField.INVOICING_UNIT, FieldType.ALPHANUMERIC, 374, 3),
        Field(InvoiceField.DESCRIPTION, FieldType.ALPHANUMERIC, 377, 30),
        Field(InvoiceField.SECURITY_DENIAL, FieldType.ALPHANUMERIC, 407, 1),
        Field(InvoiceField.CONTRACT_NUMBER, FieldType.ALPHANUMERIC, 408, 40),
        Field(InvoiceField.ORDER_NUMBER, FieldType.ALPHANUMERIC, 448, 40),
        Field(InvoiceField.ADDRESS2, FieldType.ALPHANUMERIC, 488, 30),
        Field(InvoiceField.COUNTRY, FieldType.ALPHANUMERIC, 518, 30),
        Field(InvoiceField.SSN, FieldType.ALPHANUMERIC, 548, 11),
        Field(InvoiceField.LATE_PAYMENT_INTEREST, FieldType.ALPHANUMERIC, 559, 6),
        Field(InvoiceField.VAT_IDENTIFIER, FieldType.ALPHANUMERIC, 565, 35),
        Field(InvoiceField.DELIVERY_DATE, FieldType.ALPHANUMERIC, 600, 8),
        Field(InvoiceField.OVT_IDENTIFIER, FieldType.ALPHANUMERIC, 608, 35),
        Field(InvoiceField.PAYMENT_TERM, FieldType.ALPHANUMERIC, 643, 10),
        Field(InvoiceField.RF_REFERENCE, FieldType.ALPHANUMERIC, 643, 10),
)

var codebtorRowFields = listOf(
        Field(InvoiceField.INVOICE_IDENTIFIER, FieldType.ALPHANUMERIC, 1, 11),
        Field(InvoiceField.CODEBTOR_ROW_CODE, FieldType.ALPHANUMERIC, 12, 1),
        Field(InvoiceField.CODEBTOR_IDENTIFIER, FieldType.ALPHANUMERIC, 13, 11),
        Field(InvoiceField.CODEBTOR_NAME, FieldType.ALPHANUMERIC, 24, 50),
        Field(InvoiceField.CODEBTOR_STREET_ADDRESS, FieldType.ALPHANUMERIC, 74, 30),
        Field(InvoiceField.CODEBTOR_POSTAL_ADDRESS, FieldType.ALPHANUMERIC, 104, 30),
        Field(InvoiceField.CODEBTOR_PHONE_NUMBER, FieldType.ALPHANUMERIC, 134, 15),
        Field(InvoiceField.NOT_USED, FieldType.ALPHANUMERIC, 149, 90),
        Field(InvoiceField.CODEBTOR_LANGUAGE_CODE, FieldType.ALPHANUMERIC, 239, 1),
        Field(InvoiceField.NOT_USED, FieldType.ALPHANUMERIC, 240, 1),
        Field(InvoiceField.CODEBTOR_PARTNER_CODE, FieldType.ALPHANUMERIC, 241, 10),
        Field(InvoiceField.NOT_USED, FieldType.ALPHANUMERIC, 251, 1),
        Field(InvoiceField.CODEBTOR_ADDRESS2, FieldType.ALPHANUMERIC, 252, 30),
        Field(InvoiceField.CODEBTOR_COUNTRY, FieldType.ALPHANUMERIC, 282, 30),
        Field(InvoiceField.CODEBTOR_NAME2, FieldType.ALPHANUMERIC, 312, 50),
        Field(InvoiceField.CODEBTOR_SSN, FieldType.ALPHANUMERIC, 362, 11),
        Field(InvoiceField.CODEBTOR_VAT_IDENTIFIER, FieldType.ALPHANUMERIC, 373, 35),
        Field(InvoiceField.CODEBTOR_OVT_IDENTIFIER, FieldType.ALPHANUMERIC, 408, 35),
)

var childHeaderRowFields = listOf(
        Field(InvoiceField.INVOICE_IDENTIFIER, FieldType.ALPHANUMERIC, 1, 11),
        Field(InvoiceField.TEXT_ROW_CODE, FieldType.ALPHANUMERIC, 12, 1),
        Field(InvoiceField.CHILD_NAME, FieldType.ALPHANUMERIC, 13, 78),
        Field(InvoiceField.INVOICE_ROW_HEADER, FieldType.ALPHANUMERIC, 30, 91),
        Field(InvoiceField.CONSTANT_TEXT_IDENTIFIER, FieldType.ALPHANUMERIC, 121, 10),
)

var rowHeaderRowFields = listOf(
        Field(InvoiceField.INVOICE_IDENTIFIER, FieldType.ALPHANUMERIC, 1, 11),
        Field(InvoiceField.TEXT_ROW_CODE, FieldType.ALPHANUMERIC, 12, 1),
        Field(InvoiceField.TIME_PERIOD, FieldType.ALPHANUMERIC, 13, 78),
        Field(InvoiceField.INVOICE_ROW_HEADER, FieldType.ALPHANUMERIC, 30, 91),
        Field(InvoiceField.CONSTANT_TEXT_IDENTIFIER, FieldType.ALPHANUMERIC, 121, 10),
)

class InvoiceData {
    val alphanumericValues: MutableMap<InvoiceField, String> = mutableMapOf()
    val numericValues: MutableMap<InvoiceField, Int> = mutableMapOf()
    var rowMap: Map<String, List<InvoiceData>> = mapOf()

    fun setAlphanumericValue(field: InvoiceField, value: String) { alphanumericValues.put(field, value) }
    fun getAlphanumericValue(field: InvoiceField): String? { return alphanumericValues.get(field)}
    fun setNumericValue(field: InvoiceField, value: Int) { numericValues.put(field, value) }
    fun getNumericValue(field: InvoiceField): Int? { return numericValues.get(field)}

    fun setChildRowMap(childMap: Map<String, List<InvoiceData>>) { rowMap = childMap }
    fun getChildRowMap(): Map<String, List<InvoiceData>> { return rowMap }
}
