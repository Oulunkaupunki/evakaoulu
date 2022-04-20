package fi.ouka.evakaoulu.invoice.service

import fi.ouka.evakaoulu.IntimeProperties
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.time.Instant

internal class InvoiceSenderTest {

    @Test
    fun `should send invoice`() {
        val intimeProperties = IntimeProperties("", "", "", "")
        val sftpConnector = mock<SftpConnector>()
        val invoiceSender = InvoiceSender(intimeProperties, sftpConnector)
        val proEInvoice = "one"
        val filepath = "${intimeProperties.path}-${Instant.now()}"

        invoiceSender.send(proEInvoice)

        verify(sftpConnector).connect(intimeProperties.address, intimeProperties.username, intimeProperties.password)
        verify(sftpConnector).send(filepath, "one")
        verify(sftpConnector).disconnect()
    }
}