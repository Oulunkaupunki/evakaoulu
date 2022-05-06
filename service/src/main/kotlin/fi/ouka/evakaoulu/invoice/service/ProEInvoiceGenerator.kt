// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import fi.espoo.evaka.invoicing.integration.InvoiceIntegrationClient
import fi.ouka.evakaoulu.invoice.config.Product
import org.springframework.stereotype.Component
import java.lang.Math.abs
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class ProEInvoiceGenerator(private val invoiceChecker: InvoiceChecker, val invoiceDateProvider: InvoiceDateProvider) : StringInvoiceGenerator {

    var invoiceIdWithoutSsn: Long = 0

    fun generateInvoiceTitle(): String {
        val previousMonth = LocalDate.now().minusMonths(1)
        val titleFormatter = DateTimeFormatter.ofPattern("MM.yyyy")
        return "Varhaiskasvatus " + previousMonth.format(titleFormatter)
    }
    fun gatherInvoiceData(invoiceDetailed: InvoiceDetailed): InvoiceData {
        var invoiceData = InvoiceData()

        var invoiceDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

        invoiceData.setAlphanumericValue(InvoiceField.NOT_USED, "")

        val headOfFamilySsn = invoiceDetailed.headOfFamily.ssn
        if (headOfFamilySsn != null)
            invoiceData.setAlphanumericValue(InvoiceField.INVOICE_IDENTIFIER, headOfFamilySsn)
        else {
            invoiceData.setAlphanumericValue(InvoiceField.INVOICE_IDENTIFIER, invoiceIdWithoutSsn.toString())
        }
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
        invoiceData.setAlphanumericValue(InvoiceField.DESCRIPTION, generateInvoiceTitle())
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
        else {
            invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_IDENTIFIER, "")
            invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_NAME, "")
            invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_STREET_ADDRESS, "")
            invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_POSTAL_ADDRESS, "")
            invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_PHONE_NUMBER, "")
        }
        invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_LANGUAGE_CODE, "1")
        invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_ADDRESS2, "")
        invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_COUNTRY, "")
        invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_NAME2, "")
        invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_VAT_IDENTIFIER, "")
        invoiceData.setAlphanumericValue(InvoiceField.CODEBTOR_OVT_IDENTIFIER, "")

        val sortedRows = invoiceDetailed.rows.sortedBy { row -> row.child.firstName }

        val rowsPerChild: MutableMap<String, List<InvoiceData>> = mutableMapOf()
        var currentChild = ""
        var childRows: MutableList<InvoiceData> = mutableListOf()

        sortedRows.forEach {
            if (it.child.firstName != currentChild) {
                currentChild = it.child.firstName
                childRows = mutableListOf()
                rowsPerChild.put(currentChild, childRows)
            }

            val invoiceRowData = InvoiceData()

            if (headOfFamilySsn != null)
                invoiceRowData.setAlphanumericValue(InvoiceField.INVOICE_IDENTIFIER, headOfFamilySsn)
            else {
                invoiceRowData.setAlphanumericValue(InvoiceField.INVOICE_IDENTIFIER, invoiceIdWithoutSsn.toString())
            }
            invoiceRowData.setAlphanumericValue(InvoiceField.TEXT_ROW_CODE, "3")
            invoiceRowData.setAlphanumericValue(InvoiceField.CHILD_NAME, it.child.firstName + " " + it.child.lastName)
            val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            invoiceRowData.setAlphanumericValue(InvoiceField.TIME_PERIOD, it.periodStart.format(dateFormatter) + " - " + it.periodEnd.format(dateFormatter))
            invoiceRowData.setAlphanumericValue(InvoiceField.INVOICE_ROW_HEADER, "")
            invoiceRowData.setAlphanumericValue(InvoiceField.CONSTANT_TEXT_IDENTIFIER, "")

            invoiceRowData.setAlphanumericValue(InvoiceField.DETAIL_ROW_CODE, "1")
            invoiceRowData.setAlphanumericValue(InvoiceField.PRODUCT_NAME, Product.valueOf(it.product.value).nameFi)
            // sign of unitPrice is moved to a separate field - empty value is interpreted as a plus sign
            invoiceRowData.setAlphanumericValue(InvoiceField.PRICE_SIGN, if (it.unitPrice < 0) "-" else "")
            invoiceRowData.setNumericValue(InvoiceField.UNIT_PRICE, abs(it.unitPrice))
            invoiceRowData.setAlphanumericValue(InvoiceField.UNIT, "kpl")
            // empty value is interpreted as a plus sign
            invoiceRowData.setAlphanumericValue(InvoiceField.AMOUNT_SIGN, "")
            invoiceRowData.setNumericValue(InvoiceField.AMOUNT, it.amount)
            invoiceRowData.setAlphanumericValue(InvoiceField.VAT_CODE, "00")
            invoiceRowData.setAlphanumericValue(InvoiceField.VAT_ACCOUNT, "")
            // format description says "value of this field has not been used", example file has "0" here
            invoiceRowData.setAlphanumericValue(InvoiceField.BRUTTO_NETTO, "0")
            invoiceRowData.setAlphanumericValue(InvoiceField.DEBIT_ACCOUNTING, "")
            invoiceRowData.setAlphanumericValue(InvoiceField.CREDIT_ACCOUNTING, "3271 1101170      2627")

            childRows.add(invoiceRowData)
        }

        invoiceData.setChildRowMap(rowsPerChild)

        invoiceIdWithoutSsn += 1

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
            else if (it.fieldType == FieldType.MONETARY) {
                var value = invoiceData.getNumericValue(it.field) ?: 0
                // if the value if non-zero it has been multiplied by 100 to already contain two decimals
                val decimals = if (value == 0) it.decimals else it.decimals - 2
                val length = if (value == 0) it.length else it.length + 2
                var stringValue = value.toString().padStart(length, '0')
                result = result + stringValue.padEnd(length + decimals, '0')
            }
        }

        result = result + "\n"

        return result
    }

    fun formatInvoice(invoiceData: InvoiceData): String {

        var result = generateRow(headerRowFields, invoiceData)

        if (invoiceData.getAlphanumericValue(InvoiceField.CODEBTOR_IDENTIFIER) != "")
            result += generateRow(codebtorRowFields, invoiceData)

        var rowsPerChild = invoiceData.getChildRowMap()
        rowsPerChild.forEach {
            result += generateRow(childHeaderRowFields, it.value.get(0))
            it.value.forEach {
                result += generateRow(rowHeaderRowFields, it)
                result += generateRow(detailRowFields, it)
            }
        }

        return result
    }

    override fun generateInvoice(invoices: List<InvoiceDetailed>): StringInvoiceGenerator.InvoiceGeneratorResult {
        var invoiceString = ""
        invoiceIdWithoutSsn = (invoiceDateProvider.currentDate() + "001").toLong()
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