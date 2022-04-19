package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import fi.ouka.evakaoulu.IntimeProperties
import java.io.ByteArrayInputStream

class InvoiceSender(val intimeProperties: IntimeProperties, val sftpConnector: SftpConnector) {
    fun send(proEInvoices: List<String>): Unit {
        print(proEInvoices)
        sftpConnector.connect(intimeProperties.address, intimeProperties.username, intimeProperties.password)
        proEInvoices.forEach{sftpConnector.send(intimeProperties.path, ByteArrayInputStream(it.toByteArray()))}
        sftpConnector.disconnect()
    }


}
