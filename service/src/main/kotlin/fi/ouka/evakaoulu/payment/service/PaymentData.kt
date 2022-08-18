package fi.ouka.evakaoulu.payment.service

import fi.ouka.evakaoulu.invoice.service.InvoiceFieldName
import fi.ouka.evakaoulu.util.DataMapper
import fi.ouka.evakaoulu.util.FieldType

// NB, I have no idea what many of these things would properly be in English...
enum class PaymentFieldName {
    INTIME_COMPANY_ID,
    PROVIDER_ID,
    INVOICE_ID,
    INVOICE_ACCEPTANCE,
    VOUCHER_TYPE,
    VOUCHER_NUMBER,
    VOUCHER_DATE,
    INVOICE_TYPE,
    ACCOUNT_SUGGESTION,
    PERIOD,
}

class PaymentField(val field: PaymentFieldName, val fieldType: FieldType, val start: Int, val length: Int, val decimals: Int = 0)

val headerRowFields = listOf(
    PaymentField(PaymentFieldName.INTIME_COMPANY_ID, FieldType.ALPHANUMERIC, 1, 2),
    PaymentField(PaymentFieldName.PROVIDER_ID, FieldType.ALPHANUMERIC, 3, 8),
    PaymentField(PaymentFieldName.INVOICE_ID, FieldType.ALPHANUMERIC, 11, 12),
    PaymentField(PaymentFieldName.INVOICE_ACCEPTANCE, FieldType.NUMERIC, 23, 1),
    PaymentField(PaymentFieldName.VOUCHER_TYPE, FieldType.NUMERIC, 24, 2),
    PaymentField(PaymentFieldName.VOUCHER_NUMBER, FieldType.ALPHANUMERIC, 26, 8),
    PaymentField(PaymentFieldName.VOUCHER_DATE, FieldType.ALPHANUMERIC, 34, 6),
    PaymentField(PaymentFieldName.INVOICE_TYPE, FieldType.NUMERIC, 40, 1),
    PaymentField(PaymentFieldName.ACCOUNT_SUGGESTION, FieldType.ALPHANUMERIC, 41, 1),
    PaymentField(PaymentFieldName.PERIOD, FieldType.ALPHANUMERIC, 42, 4),
)

typealias PaymentData = DataMapper<PaymentFieldName>