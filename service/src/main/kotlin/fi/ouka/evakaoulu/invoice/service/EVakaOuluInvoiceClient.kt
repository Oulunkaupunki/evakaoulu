// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.SftpException
import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import fi.espoo.evaka.invoicing.integration.InvoiceIntegrationClient
import fi.ouka.evakaoulu.IntimeProperties
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

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

class InvoiceData {
    val alphanumericValues: MutableMap<InvoiceField, String> = mutableMapOf()
    val numericValues: MutableMap<InvoiceField, Int> = mutableMapOf()

    fun setAlphanumericValue(field: InvoiceField, value: String) { alphanumericValues.put(field, value) }
    fun getAlphanumericValue(field: InvoiceField): String? { return alphanumericValues.get(field)}
    fun setNumericValue(field: InvoiceField, value: Int) { numericValues.put(field, value) }
    fun getNumericValue(field: InvoiceField): Int? { return numericValues.get(field)}
}

fun gatherInvoiceData(invoiceDetailed: InvoiceDetailed): InvoiceData {
    var invoiceData = InvoiceData()

    var invoiceDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

    invoiceData.setAlphanumericValue(InvoiceField.NOT_USED, "")

    // what will be the invoice ID if the headOfFamily has no ssn?
    invoiceData.setAlphanumericValue(InvoiceField.INVOICE_IDENTIFIER, invoiceDetailed.headOfFamily.ssn ?: "")
    invoiceData.setAlphanumericValue(InvoiceField.HEADER_ROW_CODE, "L")
    invoiceData.setAlphanumericValue(InvoiceField.CLIENT_GROUP, "10")
    invoiceData.setAlphanumericValue(InvoiceField.CLIENT_NAME1, invoiceDetailed.headOfFamily.firstName + " " + invoiceDetailed.headOfFamily.lastName)
    invoiceData.setAlphanumericValue(InvoiceField.CLIENT_NAME2, "")
    invoiceData.setAlphanumericValue(InvoiceField.STREET_ADDRESS, invoiceDetailed.headOfFamily.streetAddress)
    invoiceData.setAlphanumericValue(InvoiceField.POSTAL_ADDRESS, invoiceDetailed.headOfFamily.postalCode + " " + invoiceDetailed.headOfFamily.postOffice)
    invoiceData.setAlphanumericValue(InvoiceField.PHONE_NUMBER, invoiceDetailed.headOfFamily.phone)
    invoiceData.setAlphanumericValue(InvoiceField.FAX_NUMBER, "")
    invoiceData.setAlphanumericValue(InvoiceField.CLIENT_CONTACT, "")
    invoiceData.setAlphanumericValue(InvoiceField.CLIENT_BANK, "")
    // 0 = external client
    invoiceData.setAlphanumericValue(InvoiceField.CLIENT_TYPE, "0")
    // 1 = Finnish
    invoiceData.setAlphanumericValue(InvoiceField.LANGUAGE_CODE, "1")
    invoiceData.setAlphanumericValue(InvoiceField.REMINDER_CODE, "")
    // N = normal invoicing
    invoiceData.setAlphanumericValue(InvoiceField.PAYMENT_METHOD, "N")
    // 0 = no payments defaulted
    invoiceData.setAlphanumericValue(InvoiceField.PAYMENT_DEFAULT_CODE, "0")
    // K = print a normal invoice
    invoiceData.setAlphanumericValue(InvoiceField.PRINTING_METHOD, "K")
    invoiceData.setAlphanumericValue(InvoiceField.INVOICE_DATE, invoiceDetailed.invoiceDate.format(invoiceDateFormatter))
    invoiceData.setAlphanumericValue(InvoiceField.DUE_DATE, invoiceDetailed.dueDate.format(invoiceDateFormatter))
    invoiceData.setAlphanumericValue(InvoiceField.ACCOUNTING_DATE, invoiceDetailed.sentAt?.toLocalDateTime()?.format(invoiceDateFormatter) ?: LocalDate.now().format(invoiceDateFormatter))
    invoiceData.setNumericValue(InvoiceField.INCLUDED_LATE_PAYMENT_INTEREST, 0)
    invoiceData.setAlphanumericValue(InvoiceField.CREDIT_NOTE_INVOICE_NUMBER, "")
    invoiceData.setAlphanumericValue(InvoiceField.INVOICE_NUMBER, invoiceDetailed.number.toString())
    invoiceData.setAlphanumericValue(InvoiceField.REFERENCE_NUMBER, "")
    // N = normal
    invoiceData.setAlphanumericValue(InvoiceField.PAYMENT_TYPE, "N")
    invoiceData.setAlphanumericValue(InvoiceField.PARTNER_CODE, "N")
    invoiceData.setAlphanumericValue(InvoiceField.CURRENCY, "")
    invoiceData.setAlphanumericValue(InvoiceField.INVOICE_TYPE, "")
    invoiceData.setAlphanumericValue(InvoiceField.INVOICING_UNIT, "000")
    // what should we put here?
    invoiceData.setAlphanumericValue(InvoiceField.DESCRIPTION, "Selite tähän!")
    invoiceData.setAlphanumericValue(InvoiceField.SECURITY_DENIAL, "")
    invoiceData.setAlphanumericValue(InvoiceField.CONTRACT_NUMBER, "")
    invoiceData.setAlphanumericValue(InvoiceField.ORDER_NUMBER, "")
    invoiceData.setAlphanumericValue(InvoiceField.ADDRESS2, "")
    invoiceData.setAlphanumericValue(InvoiceField.COUNTRY, "")
    invoiceData.setAlphanumericValue(InvoiceField.SSN, "")
    invoiceData.setAlphanumericValue(InvoiceField.LATE_PAYMENT_INTEREST, "")
    invoiceData.setAlphanumericValue(InvoiceField.VAT_IDENTIFIER, "")
    invoiceData.setAlphanumericValue(InvoiceField.DELIVERY_DATE, "")
    invoiceData.setAlphanumericValue(InvoiceField.OVT_IDENTIFIER, "")
    invoiceData.setAlphanumericValue(InvoiceField.PAYMENT_TERM, "")
    invoiceData.setAlphanumericValue(InvoiceField.RF_REFERENCE, "")

    invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_ROW_CODE, "Y")

    val codebtor = invoiceDetailed.codebtor
    if (codebtor != null) {
        invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_IDENTIFIER, codebtor.ssn ?: "")
        invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_NAME, codebtor.firstName + " " + codebtor.lastName)
        invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_STREET_ADDRESS, codebtor.streetAddress)
        invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_POSTAL_ADDRESS, codebtor.postalCode + " " + codebtor.postOffice)
        invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_PHONE_NUMBER, codebtor.phone)
    }
    invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_LANGUAGE_CODE, "1")
    invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_ADDRESS2, "")
    invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_COUNTRY, "")
    invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_NAME2, "")
    invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_VAT_IDENTIFIER, "")
    invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_OVT_IDENTIFIER, "")

    return invoiceData
}

fun formatInvoice(invoiceData: InvoiceData): String {

    var result = ""

    var iter = headerRowFields.iterator()
    while (iter.hasNext()) {
        val fieldInfo = iter.next()
	if (fieldInfo.fieldType == FieldType.ALPHANUMERIC) {
	    var value = invoiceData.getAlphanumericValue(fieldInfo.field) ?: ""
	    result = result + value.padEnd(fieldInfo.length)
	}
	else if (fieldInfo.fieldType == FieldType.NUMERIC) {
	     var value = invoiceData.getNumericValue(fieldInfo.field) ?: 0
	     var stringValue = value.toString().padStart(fieldInfo.length, '0')
	     // all Evaka values seem to be Int so we can just pad
	     // the decimal part with the correct number of zeroes
	     result = result + stringValue.padEnd(fieldInfo.length + fieldInfo.decimals, '0')
	}
    }

    return result
}

class EVakaOuluInvoiceClient(
    private val invoiceSender: InvoiceSender,
    private val invoiceGenerator: ProEInvoiceGenerator
) : InvoiceIntegrationClient {
    override fun send(invoices: List<InvoiceDetailed>): InvoiceIntegrationClient.SendResult {

	var iter = invoices.iterator()
	while (iter.hasNext()) {
	    var invoiceData = gatherInvoiceData(iter.next())
	    var invoiceString = formatInvoice(invoiceData)
	    println(invoiceString)
	}

        val successList = mutableListOf<InvoiceDetailed>()
        val failedList = mutableListOf<InvoiceDetailed>()
        val manuallySentList = mutableListOf<InvoiceDetailed>()
        var proEinvoices = ""

        val (withSSN, withoutSSN) = invoices.partition { invoice -> invoice.headOfFamily.ssn != null }

        withSSN.forEach {
            proEinvoices += invoiceGenerator.generateInvoice(it)
            successList.add(it)
        }

        try {
            invoiceSender.send(proEinvoices)
            manuallySentList.addAll(withoutSSN)
            logger.info { "Successfully sent ${successList.size} invoices and created ${manuallySentList.size} manual invoice" }
        } catch (e: SftpException){
            failedList.addAll(successList)
            failedList.addAll(withoutSSN)
            successList.clear()
            logger.error { "Failed to send ${failedList.size} invoices" }
        }

        return InvoiceIntegrationClient.SendResult(successList, failedList, manuallySentList)
    }

}

interface StringInvoiceGenerator {
    fun generateInvoice(invoice: InvoiceDetailed): String

}