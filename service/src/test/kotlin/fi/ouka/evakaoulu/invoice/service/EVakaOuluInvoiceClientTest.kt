package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.SftpException
import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class EVakaOuluInvoiceClientTest {

    val invoiceDetailed = mock<InvoiceDetailed>()
    val invoiceGenerator = mock<ProEInvoiceGenerator>()
    val invoiceSender = mock<InvoiceSender>()
    val eVakaOuluInvoiceClient = EVakaOuluInvoiceClient(invoiceSender, invoiceGenerator)

    @Test
    fun `should send generated invoices`() {
        val invoiceList = listOf(invoiceDetailed)
        val proEInvoice1 = ""
        whenever(invoiceGenerator.generateInvoice(invoiceDetailed)).thenReturn(proEInvoice1)

        eVakaOuluInvoiceClient.send(invoiceList)

        verify(invoiceGenerator).generateInvoice(invoiceDetailed)
        verify(invoiceSender).send(proEInvoice1)
    }

    @Test
    fun `should return successfully sent invoices in success list`() {
        val invoiceList = listOf(invoiceDetailed)
        val proEInvoice1 = ""
        whenever(invoiceGenerator.generateInvoice(invoiceDetailed)).thenReturn(proEInvoice1)

        val sendResult = eVakaOuluInvoiceClient.send(invoiceList)

        assertThat(sendResult.succeeded).hasSize(1)
    }

    @Test
    fun `should return unsuccessfully sent invoices in failed list`() {
        val invoiceList = listOf(invoiceDetailed, invoiceDetailed)
        val proEInvoice1 = ""
        whenever(invoiceGenerator.generateInvoice(invoiceDetailed)).thenReturn(proEInvoice1)
        whenever(invoiceSender.send(proEInvoice1)).thenThrow(SftpException::class.java)

        val sendResult = eVakaOuluInvoiceClient.send(invoiceList)

        assertThat(sendResult.failed).hasSize(2)
    }

    @Test
    fun `should send more invoices at once`() {
        val invoiceList = listOf(invoiceDetailed, invoiceDetailed)
        val proEInvoice1 = "xx"
        whenever(invoiceGenerator.generateInvoice(invoiceDetailed)).thenReturn(
            "x"
        )
        val sendResult = eVakaOuluInvoiceClient.send(invoiceList)

        assertThat(sendResult.succeeded).hasSize(2)
        verify(invoiceSender).send(proEInvoice1)
    }



}