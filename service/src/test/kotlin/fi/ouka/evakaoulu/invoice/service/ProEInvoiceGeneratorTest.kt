package fi.ouka.evakaoulu.invoice.service

import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


internal class ProEInvoiceGeneratorTest {

    val invoiceDateProvider = mock<InvoiceDateProvider>()
    val proEInvoiceGenerator = ProEInvoiceGenerator(InvoiceChecker(), invoiceDateProvider)

    @BeforeEach
    fun setup() {
        whenever(invoiceDateProvider.currentDate()).thenReturn("20220505")
    }

    @Test
    fun `should return successfully created invoices in success list`() {

        val invoice = validInvoice()
        val invoiceList = listOf(invoice, invoice)

        val generationResult = proEInvoiceGenerator.generateInvoice(invoiceList)
        assertEquals(generationResult.sendResult.succeeded, invoiceList)
        assertEquals(generationResult.sendResult.manuallySent, listOf<InvoiceDetailed>())
        assertEquals(generationResult.sendResult.failed, listOf<InvoiceDetailed>())
    }

    @Test
    fun `should return manually sent invoices in manually list`() {

        val invoice = validInvoice().copy(headOfFamily = personWithRestrictedDetails())
        val invoiceList = listOf(invoice, invoice)

        val generationResult = proEInvoiceGenerator.generateInvoice(invoiceList)
        assertEquals(generationResult.sendResult.succeeded, listOf<InvoiceDetailed>())
        assertEquals(generationResult.sendResult.manuallySent, invoiceList)
        assertEquals(generationResult.sendResult.failed, listOf<InvoiceDetailed>())
    }

    @Test
    fun `should format invoice rows according to data and formatting`() {

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

    @Test
    fun `should format invoice identifier for clients that has no SSN`() {

        val invoice = validInvoice().copy(headOfFamily = personWithoutSSN())
        val invoiceList = listOf(invoice, invoice)

        val generationResult = proEInvoiceGenerator.generateInvoice(invoiceList)

        var correctInvoice = object {}.javaClass.getResource("/invoice-client/CorrectProEInvoice.txt")?.readText()

        assertEquals(correctInvoice, generationResult.invoiceString)
    }
}