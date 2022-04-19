package fi.ouka.evakaoulu.invoice.service

import fi.ouka.evakaoulu.IntimeProperties
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

internal class InvoiceSenderTest {

    @Test
    fun `should send all invoices`() {
        val intimeProperties = IntimeProperties("", "", "", "")
        val sftpConnector = mock<SftpConnector>()
        val invoiceSender = InvoiceSender(intimeProperties, sftpConnector)
        val proEInvoices = listOf("one", "two")

        invoiceSender.send(proEInvoices)

        verify(sftpConnector).connect(intimeProperties.address, intimeProperties.username, intimeProperties.password)
        verify(sftpConnector).send(intimeProperties.path, "one")
        verify(sftpConnector).send(intimeProperties.path, "two")
        verify(sftpConnector).disconnect()
    }
}