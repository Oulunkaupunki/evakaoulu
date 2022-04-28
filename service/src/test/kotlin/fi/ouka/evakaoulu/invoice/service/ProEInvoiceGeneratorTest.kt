package fi.ouka.evakaoulu.invoice.service

import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ProEInvoiceGeneratorTest {

    @Test
    fun `should return successfully created invoices in success list`() {
        val proEInvoiceGenerator = ProEInvoiceGenerator(InvoiceChecker())
        val invoice = validInvoice()
        val invoiceList = listOf(invoice, invoice)

        val generationResult = proEInvoiceGenerator.generateInvoice(invoiceList)
        assertEquals(generationResult.sendResult.succeeded, invoiceList)
        assertEquals(generationResult.sendResult.manuallySent, listOf<InvoiceDetailed>())
        assertEquals(generationResult.sendResult.failed, listOf<InvoiceDetailed>())
    }

    @Test
    fun `should return manually sent invoices in manually list`() {
        val proEInvoiceGenerator = ProEInvoiceGenerator(InvoiceChecker())
        val invoice = validInvoice().copy(headOfFamily = personWithRestrictedDetails())
        val invoiceList = listOf(invoice, invoice)

        val generationResult = proEInvoiceGenerator.generateInvoice(invoiceList)
        assertEquals(generationResult.sendResult.succeeded, listOf<InvoiceDetailed>())
        assertEquals(generationResult.sendResult.manuallySent, invoiceList)
        assertEquals(generationResult.sendResult.failed, listOf<InvoiceDetailed>())
    }

    @Test
    fun `should format invoice rows according to data and formatting`() {
        val proEInvoiceGenerator = ProEInvoiceGenerator(InvoiceChecker())
        val format = listOf(
                Field(InvoiceField.INVOICE_IDENTIFIER, FieldType.ALPHANUMERIC, 1, 11),
                Field(InvoiceField.CLIENT_NAME1, FieldType.ALPHANUMERIC, 12, 30),
                Field(InvoiceField.INCLUDED_LATE_PAYMENT_INTEREST, FieldType.NUMERIC, 42, 6, 2)
        )
        val invoiceData = InvoiceData()

        invoiceData.setAlphanumericValue(InvoiceField.INVOICE_IDENTIFIER, "121212A121A")
        invoiceData.setAlphanumericValue(InvoiceField.CLIENT_NAME1, "Jaska Jokunen")
        invoiceData.setNumericValue(InvoiceField.INCLUDED_LATE_PAYMENT_INTEREST, 42)

        val result = proEInvoiceGenerator.generateRow(format, invoiceData)

        assertEquals(result, "121212A121AJaska Jokunen                 00004200\n")
    }
}