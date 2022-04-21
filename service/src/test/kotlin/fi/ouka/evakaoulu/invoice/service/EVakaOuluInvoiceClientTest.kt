package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.SftpException
import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import fi.ouka.evakaoulu.IntimeProperties
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class EVakaOuluInvoiceClientTest {

    val invoiceDetailed = mock<InvoiceDetailed>()
    val proEInvoiceGenerator = mock<ProEInvoiceGenerator>()
    val sftpConnector = mock<SftpConnector>()
    val properties = IntimeProperties("", "", "", "")
    val eVakaOuluInvoiceClient = EVakaOuluInvoiceClient(properties, proEInvoiceGenerator, sftpConnector)

    @Test
    fun `should send generated invoices`() {
        val invoiceList = listOf(invoiceDetailed)
        val proEInvoice1 = ""
        whenever(proEInvoiceGenerator.generateInvoice(invoiceDetailed)).thenReturn(proEInvoice1)

        eVakaOuluInvoiceClient.send(invoiceList)

        verify(proEInvoiceGenerator).generateInvoice(invoiceDetailed)
        verify(invoiceSender).send(proEInvoice1)
    }

    @Test
    fun `should return successfully sent invoices in success list`() {
        val invoiceList = listOf(invoiceDetailed)
        val proEInvoice1 = ""
        whenever(proEInvoiceGenerator.generateInvoice(invoiceDetailed)).thenReturn(proEInvoice1)

        val sendResult = eVakaOuluInvoiceClient.send(invoiceList)

        assertThat(sendResult.succeeded).hasSize(1)

    }

    @Test
    fun `should return unsuccessfully sent invoices in failed list`() {
        val invoiceList = listOf(invoiceDetailed)
        val proEInvoice1 = ""
        whenever(proEInvoiceGenerator.generateInvoice(invoiceDetailed)).thenReturn(proEInvoice1)
        whenever(invoiceSender.send(proEInvoice1)).thenThrow(SftpException::class.java)

        val sendResult = eVakaOuluInvoiceClient.send(invoiceList)

        assertThat(sendResult.failed).hasSize(1)
    }
}