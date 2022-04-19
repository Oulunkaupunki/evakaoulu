package fi.ouka.evakaoulu.invoice.service

import fi.ouka.evakaoulu.IntimeProperties
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.io.ByteArrayInputStream

internal class InvoiceSenderTest {
    @Test
    fun `should send a invoice`() {
        val intimeProperties = IntimeProperties("", "", "", "")
        val sftpConnector = mock<SftpConnector>()
        val invoiceSender = InvoiceSender(intimeProperties, sftpConnector)
        val proEInvoices = listOf("one", "two")

        invoiceSender.send(proEInvoices)

        verify(sftpConnector).connect(intimeProperties.address, intimeProperties.username, intimeProperties.password)
        verify(sftpConnector, times(2)).send(eq(intimeProperties.path), any<ByteArrayInputStream>())
        verify(sftpConnector).disconnect()
    }
}