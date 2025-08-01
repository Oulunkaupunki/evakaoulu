// SPDX-FileCopyrightText: 2023-2025 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import fi.ouka.evakaoulu.util.FieldType

enum class InvoiceFieldName {
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
    DETAIL_ROW_CODE,
    PRODUCT_NAME,
    PRICE_SIGN,
    UNIT_PRICE,
    UNIT,
    AMOUNT_SIGN,
    AMOUNT,
    VAT_CODE,
    VAT_ACCOUNT,
    BRUTTO_NETTO,
    DEBIT_ACCOUNTING,
    CREDIT_ACCOUNTING,
    ROW_SUM,
}

class InvoiceField(
    val field: InvoiceFieldName,
    val fieldType: FieldType,
    val start: Int,
    val length: Int,
    val decimals: Int = 0,
)

val headerRowFields =
    listOf(
        InvoiceField(InvoiceFieldName.INVOICE_IDENTIFIER, FieldType.ALPHANUMERIC, 1, 11),
        InvoiceField(InvoiceFieldName.HEADER_ROW_CODE, FieldType.ALPHANUMERIC, 12, 1),
        InvoiceField(InvoiceFieldName.CLIENT_GROUP, FieldType.ALPHANUMERIC, 13, 2),
        InvoiceField(InvoiceFieldName.CLIENT_NAME1, FieldType.ALPHANUMERIC, 15, 50),
        InvoiceField(InvoiceFieldName.CLIENT_NAME2, FieldType.ALPHANUMERIC, 65, 50),
        InvoiceField(InvoiceFieldName.STREET_ADDRESS, FieldType.ALPHANUMERIC, 115, 30),
        InvoiceField(InvoiceFieldName.POSTAL_ADDRESS, FieldType.ALPHANUMERIC, 145, 30),
        InvoiceField(InvoiceFieldName.PHONE_NUMBER, FieldType.ALPHANUMERIC, 175, 15),
        InvoiceField(InvoiceFieldName.FAX_NUMBER, FieldType.ALPHANUMERIC, 190, 15),
        InvoiceField(InvoiceFieldName.CLIENT_CONTACT, FieldType.ALPHANUMERIC, 205, 30),
        InvoiceField(InvoiceFieldName.CLIENT_BANK, FieldType.ALPHANUMERIC, 235, 30),
        InvoiceField(InvoiceFieldName.CLIENT_BANK_ACCOUNT, FieldType.ALPHANUMERIC, 265, 15),
        InvoiceField(InvoiceFieldName.CLIENT_TYPE, FieldType.ALPHANUMERIC, 280, 1),
        InvoiceField(InvoiceFieldName.LANGUAGE_CODE, FieldType.ALPHANUMERIC, 281, 1),
        InvoiceField(InvoiceFieldName.REMINDER_CODE, FieldType.ALPHANUMERIC, 282, 1),
        InvoiceField(InvoiceFieldName.PAYMENT_METHOD, FieldType.ALPHANUMERIC, 283, 1),
        InvoiceField(InvoiceFieldName.PAYMENT_DEFAULT_CODE, FieldType.ALPHANUMERIC, 284, 1),
        InvoiceField(InvoiceFieldName.PRINTING_METHOD, FieldType.ALPHANUMERIC, 285, 1),
        InvoiceField(InvoiceFieldName.INVOICE_DATE, FieldType.ALPHANUMERIC, 286, 8),
        InvoiceField(InvoiceFieldName.DUE_DATE, FieldType.ALPHANUMERIC, 294, 8),
        InvoiceField(InvoiceFieldName.ACCOUNTING_DATE, FieldType.ALPHANUMERIC, 302, 8),
        InvoiceField(InvoiceFieldName.INCLUDED_LATE_PAYMENT_INTEREST, FieldType.NUMERIC, 310, 6, 2),
        // CHECK THIS - the spec says the field is 10 characters, but the
        // next field starts at 330!
        InvoiceField(InvoiceFieldName.CREDIT_NOTE_INVOICE_NUMBER, FieldType.ALPHANUMERIC, 318, 12),
        InvoiceField(InvoiceFieldName.INVOICE_NUMBER, FieldType.ALPHANUMERIC, 330, 8),
        InvoiceField(InvoiceFieldName.REFERENCE_NUMBER, FieldType.ALPHANUMERIC, 338, 20),
        InvoiceField(InvoiceFieldName.PAYMENT_TYPE, FieldType.ALPHANUMERIC, 358, 1),
        InvoiceField(InvoiceFieldName.PARTNER_CODE, FieldType.ALPHANUMERIC, 359, 10),
        InvoiceField(InvoiceFieldName.CURRENCY, FieldType.ALPHANUMERIC, 369, 3),
        InvoiceField(InvoiceFieldName.INVOICE_TYPE, FieldType.ALPHANUMERIC, 372, 2),
        InvoiceField(InvoiceFieldName.INVOICING_UNIT, FieldType.ALPHANUMERIC, 374, 3),
        InvoiceField(InvoiceFieldName.DESCRIPTION, FieldType.ALPHANUMERIC, 377, 30),
        InvoiceField(InvoiceFieldName.SECURITY_DENIAL, FieldType.ALPHANUMERIC, 407, 1),
        InvoiceField(InvoiceFieldName.CONTRACT_NUMBER, FieldType.ALPHANUMERIC, 408, 40),
        InvoiceField(InvoiceFieldName.ORDER_NUMBER, FieldType.ALPHANUMERIC, 448, 40),
        InvoiceField(InvoiceFieldName.ADDRESS2, FieldType.ALPHANUMERIC, 488, 30),
        InvoiceField(InvoiceFieldName.COUNTRY, FieldType.ALPHANUMERIC, 518, 30),
        InvoiceField(InvoiceFieldName.SSN, FieldType.ALPHANUMERIC, 548, 11),
        InvoiceField(InvoiceFieldName.LATE_PAYMENT_INTEREST, FieldType.ALPHANUMERIC, 559, 6),
        InvoiceField(InvoiceFieldName.VAT_IDENTIFIER, FieldType.ALPHANUMERIC, 565, 35),
        InvoiceField(InvoiceFieldName.DELIVERY_DATE, FieldType.ALPHANUMERIC, 600, 8),
        InvoiceField(InvoiceFieldName.OVT_IDENTIFIER, FieldType.ALPHANUMERIC, 608, 35),
        InvoiceField(InvoiceFieldName.PAYMENT_TERM, FieldType.ALPHANUMERIC, 643, 10),
        InvoiceField(InvoiceFieldName.RF_REFERENCE, FieldType.ALPHANUMERIC, 643, 10),
    )

var codebtorRowFields =
    listOf(
        InvoiceField(InvoiceFieldName.INVOICE_IDENTIFIER, FieldType.ALPHANUMERIC, 1, 11),
        InvoiceField(InvoiceFieldName.CODEBTOR_ROW_CODE, FieldType.ALPHANUMERIC, 12, 1),
        InvoiceField(InvoiceFieldName.CODEBTOR_IDENTIFIER, FieldType.ALPHANUMERIC, 13, 11),
        InvoiceField(InvoiceFieldName.CODEBTOR_NAME, FieldType.ALPHANUMERIC, 24, 50),
        InvoiceField(InvoiceFieldName.CODEBTOR_STREET_ADDRESS, FieldType.ALPHANUMERIC, 74, 30),
        InvoiceField(InvoiceFieldName.CODEBTOR_POSTAL_ADDRESS, FieldType.ALPHANUMERIC, 104, 30),
        InvoiceField(InvoiceFieldName.CODEBTOR_PHONE_NUMBER, FieldType.ALPHANUMERIC, 134, 15),
        InvoiceField(InvoiceFieldName.NOT_USED, FieldType.ALPHANUMERIC, 149, 90),
        InvoiceField(InvoiceFieldName.CODEBTOR_LANGUAGE_CODE, FieldType.ALPHANUMERIC, 239, 1),
        InvoiceField(InvoiceFieldName.NOT_USED, FieldType.ALPHANUMERIC, 240, 1),
        InvoiceField(InvoiceFieldName.CODEBTOR_PARTNER_CODE, FieldType.ALPHANUMERIC, 241, 10),
        InvoiceField(InvoiceFieldName.NOT_USED, FieldType.ALPHANUMERIC, 251, 1),
        InvoiceField(InvoiceFieldName.CODEBTOR_ADDRESS2, FieldType.ALPHANUMERIC, 252, 30),
        InvoiceField(InvoiceFieldName.CODEBTOR_COUNTRY, FieldType.ALPHANUMERIC, 282, 30),
        InvoiceField(InvoiceFieldName.CODEBTOR_NAME2, FieldType.ALPHANUMERIC, 312, 50),
        InvoiceField(InvoiceFieldName.CODEBTOR_SSN, FieldType.ALPHANUMERIC, 362, 11),
        InvoiceField(InvoiceFieldName.CODEBTOR_VAT_IDENTIFIER, FieldType.ALPHANUMERIC, 373, 35),
        InvoiceField(InvoiceFieldName.CODEBTOR_OVT_IDENTIFIER, FieldType.ALPHANUMERIC, 408, 35),
    )

var childHeaderRowFields =
    listOf(
        InvoiceField(InvoiceFieldName.INVOICE_IDENTIFIER, FieldType.ALPHANUMERIC, 1, 11),
        InvoiceField(InvoiceFieldName.TEXT_ROW_CODE, FieldType.ALPHANUMERIC, 12, 1),
        InvoiceField(InvoiceFieldName.CHILD_NAME, FieldType.ALPHANUMERIC, 13, 78),
        InvoiceField(InvoiceFieldName.INVOICE_ROW_HEADER, FieldType.ALPHANUMERIC, 30, 91),
        InvoiceField(InvoiceFieldName.CONSTANT_TEXT_IDENTIFIER, FieldType.ALPHANUMERIC, 121, 10),
    )

var rowHeaderRowFields =
    listOf(
        InvoiceField(InvoiceFieldName.INVOICE_IDENTIFIER, FieldType.ALPHANUMERIC, 1, 11),
        InvoiceField(InvoiceFieldName.TEXT_ROW_CODE, FieldType.ALPHANUMERIC, 12, 1),
        InvoiceField(InvoiceFieldName.TIME_PERIOD, FieldType.ALPHANUMERIC, 13, 78),
        InvoiceField(InvoiceFieldName.INVOICE_ROW_HEADER, FieldType.ALPHANUMERIC, 30, 91),
        InvoiceField(InvoiceFieldName.CONSTANT_TEXT_IDENTIFIER, FieldType.ALPHANUMERIC, 121, 10),
    )

var detailRowFields =
    listOf(
        InvoiceField(InvoiceFieldName.INVOICE_IDENTIFIER, FieldType.ALPHANUMERIC, 1, 11),
        InvoiceField(InvoiceFieldName.DETAIL_ROW_CODE, FieldType.ALPHANUMERIC, 12, 1),
        InvoiceField(InvoiceFieldName.PRODUCT_NAME, FieldType.ALPHANUMERIC, 13, 40),
        InvoiceField(InvoiceFieldName.PRICE_SIGN, FieldType.ALPHANUMERIC, 53, 1),
        InvoiceField(InvoiceFieldName.UNIT_PRICE, FieldType.MONETARY, 54, 8, 4),
        InvoiceField(InvoiceFieldName.UNIT, FieldType.ALPHANUMERIC, 66, 3),
        InvoiceField(InvoiceFieldName.AMOUNT_SIGN, FieldType.ALPHANUMERIC, 69, 1),
        InvoiceField(InvoiceFieldName.AMOUNT, FieldType.NUMERIC, 70, 8, 4),
        InvoiceField(InvoiceFieldName.VAT_CODE, FieldType.ALPHANUMERIC, 82, 2),
        InvoiceField(InvoiceFieldName.VAT_ACCOUNT, FieldType.ALPHANUMERIC, 84, 60),
        InvoiceField(InvoiceFieldName.BRUTTO_NETTO, FieldType.ALPHANUMERIC, 144, 1),
        InvoiceField(InvoiceFieldName.DEBIT_ACCOUNTING, FieldType.ALPHANUMERIC, 145, 60),
        InvoiceField(InvoiceFieldName.CREDIT_ACCOUNTING, FieldType.ALPHANUMERIC, 205, 60),
        // format says that ROW_SUM is a numeric field, but the example file has this
        // empty which is not possible the way we implemented numeric fields
        InvoiceField(InvoiceFieldName.ROW_SUM, FieldType.ALPHANUMERIC, 265, 11),
    )
