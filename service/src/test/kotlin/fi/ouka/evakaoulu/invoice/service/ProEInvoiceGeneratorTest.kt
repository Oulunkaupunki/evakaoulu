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
}