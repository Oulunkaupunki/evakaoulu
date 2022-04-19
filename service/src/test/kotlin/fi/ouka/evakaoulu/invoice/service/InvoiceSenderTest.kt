package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import fi.ouka.evakaoulu.IntimeProperties
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.ByteArrayInputStream

internal class InvoiceSenderTest {
    @Test
    fun `should send a invoice`() {
        val intimeProperties = IntimeProperties("", "", "", "")
        val sftpConnector = mock<SftpConnector>()
        val invoiceSender = InvoiceSender(intimeProperties, sftpConnector)

        val proEInvoices = listOf("Hauskaa", "Tosiaan")
        invoiceSender.send(proEInvoices)

        verify(sftpConnector).connect(intimeProperties.address, intimeProperties.username, intimeProperties.password)
//        proEInvoices.forEach{
//            verify(sftpConnector).send(intimeProperties.path, any() )
//        }
        verify(sftpConnector).disconnect()
    }
}