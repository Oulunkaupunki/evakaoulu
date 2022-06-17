package fi.ouka.evakaoulu.invoice.service

import fi.espoo.evaka.daycare.CareType
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
        whenever(invoiceDateProvider.previousMonth()).thenReturn("04.2022")
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

        val restrictedInvoice = validInvoice().copy(headOfFamily = personWithRestrictedDetails())
        val invoiceWithoutSsn = validInvoice().copy(headOfFamily = personWithoutSSN())
        val invoiceList = listOf(restrictedInvoice, invoiceWithoutSsn)

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
        invoiceData.setAlphanumericValue(InvoiceField.CLIENT_NAME1, "Jokunen Jaska")
        invoiceData.setNumericValue(InvoiceField.INCLUDED_LATE_PAYMENT_INTEREST, 42)

        val result = proEInvoiceGenerator.generateRow(format, invoiceData)

        assertEquals(result, "121212A121AJokunen Jaska                 00004200\n")
    }

    @Test
    fun `should check that invoice format is a proper one also with invoice function number`() {

        val invoice = validInvoice()
        val longNamedInvoice = validInvoice().copy(headOfFamily = personWithLongName())
        val invoiceList = listOf(invoice, longNamedInvoice)

        val generationResult = proEInvoiceGenerator.generateInvoice(invoiceList)

        var correctInvoice = object {}.javaClass.getResource("/invoice-client/CorrectProEInvoice.txt")?.readText()

        assertEquals(correctInvoice, generationResult.invoiceString)
    }

}