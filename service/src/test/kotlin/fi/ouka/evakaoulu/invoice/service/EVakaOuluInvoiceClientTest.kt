package fi.ouka.evakaoulu.invoice.service

import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import fi.ouka.evakaoulu.IntimeProperties
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class EVakaOuluInvoiceClientTest {

    val invoiceDetailed = mock<InvoiceDetailed>()
    val invoiceSender = mock<InvoiceSender>()
    val invoiceGenerator = mock<MockInvoiceGenerator>()

    @Test
    fun `should send generated invoices`() {
        val properties = IntimeProperties("", "", "", "")
        val eVakaOuluInvoiceClient = EVakaOuluInvoiceClient(properties, invoiceSender)
        val invoiceList = listOf(invoiceDetailed)
        val proEInvoice1 = ""
        whenever(invoiceGenerator.generateInvoice(invoiceDetailed)).thenReturn(proEInvoice1)

        eVakaOuluInvoiceClient.send(invoiceList)

        verify(invoiceGenerator).generateInvoice(invoiceDetailed)
        verify(invoiceSender).send(proEInvoice1)
    }
}