// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.SftpException
import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import fi.espoo.evaka.invoicing.integration.InvoiceIntegrationClient
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

enum class InvoiceField {
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
}

enum class FieldType {
     ALPHANUMERIC,
}

class Field(val field: InvoiceField, val fieldType: FieldType, val start: Int, val length: Int)

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
)

class InvoiceData {
    val alphanumericValues: MutableMap<InvoiceField, String> = mutableMapOf()

    fun setAlphanumericValue(field: InvoiceField, value: String) { alphanumericValues.put(field, value) }
    fun getAlphanumericValue(field: InvoiceField): String? { return alphanumericValues.get(field)}
}

fun gatherInvoiceData(invoiceDetailed: InvoiceDetailed): InvoiceData {
    var invoiceData = InvoiceData()

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