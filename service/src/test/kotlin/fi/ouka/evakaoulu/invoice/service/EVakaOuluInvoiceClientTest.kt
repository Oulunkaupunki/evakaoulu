package fi.ouka.evakaoulu.invoice.service

import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import fi.ouka.evakaoulu.IntimeProperties
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

internal class EVakaOuluInvoiceClientTest {

    val invoiceDetailed = mock<InvoiceDetailed>()
    val invoiceSender = mock<InvoiceSender>()

    @Test
    fun `should send generated invoices`() {
        val properties = IntimeProperties("", "", "", "")
        val eVakaOuluInvoiceClient = EVakaOuluInvoiceClient(properties, invoiceSender)
        val invoiceList = listOf(invoiceDetailed)

        
        eVakaOuluInvoiceClient.send(invoiceList)

        verify(invoiceSender).send()
    }
}