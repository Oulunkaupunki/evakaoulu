// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import fi.espoo.evaka.invoicing.integration.InvoiceIntegrationClient
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class ProEInvoiceGenerator(private val invoiceChecker: InvoiceChecker) : StringInvoiceGenerator {

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

    fun generateRow(fields: List<Field>, invoiceData: InvoiceData): String {
        var result = ""

        fields.forEach{
            if (it.fieldType == FieldType.ALPHANUMERIC) {
                var value = invoiceData.getAlphanumericValue(it.field) ?: ""
                result = result + value.padEnd(it.length)
            }
            else if (it.fieldType == FieldType.NUMERIC) {
                var value = invoiceData.getNumericValue(it.field) ?: 0
                var stringValue = value.toString().padStart(it.length, '0')
                // all Evaka values seem to be Int so we can just pad
                // the decimal part with the correct number of zeroes
                result = result + stringValue.padEnd(it.length + it.decimals, '0')
            }
        }

        result = result + "\n"

        return result
    }

    fun formatInvoice(invoiceData: InvoiceData): String {

        var result = generateRow(headerRowFields, invoiceData)

        if (invoiceData.getAlphanumericValue(InvoiceField.CODEBTOR_IDENTIFIER) != "")
            result += generateRow(codebtorRowFields, invoiceData)

        return result
    }

    override fun generateInvoice(invoices: List<InvoiceDetailed>): StringInvoiceGenerator.InvoiceGeneratorResult {
        var invoiceString = ""

        var successList = mutableListOf<InvoiceDetailed>()
        var failedList = mutableListOf<InvoiceDetailed>()
        var manuallySentList = mutableListOf<InvoiceDetailed>()

        val (manuallySent, succeeded) = invoices.partition { invoice -> invoiceChecker.shouldSendManually(invoice) }
        manuallySentList.addAll(manuallySent)

        succeeded.forEach {
            var invoiceData = gatherInvoiceData(it)
            invoiceString += formatInvoice(invoiceData)
            successList.add(it)
        }

        return StringInvoiceGenerator.InvoiceGeneratorResult(InvoiceIntegrationClient.SendResult(successList, failedList, manuallySentList), invoiceString)
    }
}