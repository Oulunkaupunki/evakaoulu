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
    INVOICE_DATE,
    DUE_DATE,
    INVOICE_SUM,
    INVOICE_1,
    CURRENCY,
    CASHBOX_DATE,
    CASHBOX_SUM,
    CASHBOX_MINUS,
    DEBT_ACCOUNT,
    SI_DEBT_ACCOUNT,
    KP_PURCHASE_ACCOUNT,
    SI_PURCHASE_ACCOUNT,
    KP_CASHBOX_ACCOUNT,
    SI_CASHBOX_ACCOUNT,
    KP_OTHER_ACCOUNT,
    SI_OTHER_ACCOUNT,
    SI_KERO_ACCOUNT,
    KP_KERO_ACCOUNT,
    STATS,
    CALC_IDENTIFIER,
    RESP_PERSON,
    FACTORING_NUMBER,
    MACHINE_REFERENCE_NUMBER,





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
    PaymentField(PaymentFieldName.INVOICE_DATE, FieldType.ALPHANUMERIC, 46, 6),
    PaymentField(PaymentFieldName.DUE_DATE, FieldType.ALPHANUMERIC, 52, 6),
    PaymentField(PaymentFieldName.INVOICE_SUM, FieldType.ALPHANUMERIC, 58, 12), // TODO Vesa check this out
    PaymentField(PaymentFieldName.INVOICE_1, FieldType.ALPHANUMERIC, 70, 12), // TODO Vesa check this out
    PaymentField(PaymentFieldName.CURRENCY, FieldType.ALPHANUMERIC, 82, 3),
    PaymentField(PaymentFieldName.CASHBOX_DATE, FieldType.ALPHANUMERIC, 85, 12),
    PaymentField(PaymentFieldName.CASHBOX_SUM, FieldType.ALPHANUMERIC, 97, 24),
    PaymentField(PaymentFieldName.CASHBOX_MINUS, FieldType.ALPHANUMERIC, 121, 24),
    PaymentField(PaymentFieldName.DEBT_ACCOUNT, FieldType.ALPHANUMERIC, 145, 8),
    PaymentField(PaymentFieldName.SI_DEBT_ACCOUNT, FieldType.ALPHANUMERIC, 153, 8),
    PaymentField(PaymentFieldName.KP_PURCHASE_ACCOUNT, FieldType.NUMERIC, 161, 8),
    PaymentField(PaymentFieldName.SI_PURCHASE_ACCOUNT, FieldType.NUMERIC, 169, 8),
    PaymentField(PaymentFieldName.KP_CASHBOX_ACCOUNT, FieldType.NUMERIC, 177, 8),
    PaymentField(PaymentFieldName.SI_CASHBOX_ACCOUNT, FieldType.NUMERIC, 185, 8),
    PaymentField(PaymentFieldName.KP_OTHER_ACCOUNT, FieldType.NUMERIC, 193, 8),
    PaymentField(PaymentFieldName.SI_OTHER_ACCOUNT, FieldType.NUMERIC, 201, 8),
    PaymentField(PaymentFieldName.SI_KERO_ACCOUNT, FieldType.NUMERIC, 209, 8),
    PaymentField(PaymentFieldName.KP_KERO_ACCOUNT, FieldType.NUMERIC, 217, 8),
    PaymentField(PaymentFieldName.STATS, FieldType.ALPHANUMERIC, 225, 12),
    PaymentField(PaymentFieldName.CALC_IDENTIFIER, FieldType.ALPHANUMERIC, 237, 30),
    PaymentField(PaymentFieldName.RESP_PERSON, FieldType.ALPHANUMERIC, 267, 8),
    PaymentField(PaymentFieldName.FACTORING_NUMBER, FieldType.NUMERIC, 275, 8),
    PaymentField(PaymentFieldName.MACHINE_REFERENCE_NUMBER, FieldType.ALPHANUMERIC, 283, 20),






    )

typealias PaymentData = DataMapper<PaymentFieldName>