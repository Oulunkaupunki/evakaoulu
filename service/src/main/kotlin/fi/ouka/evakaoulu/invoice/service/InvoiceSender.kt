package fi.ouka.evakaoulu.invoice.service

import fi.ouka.evakaoulu.IntimeProperties
import java.io.ByteArrayInputStream

class InvoiceSender(val intimeProperties: IntimeProperties, val sftpConnector: SftpConnector) {
    fun send(proEInvoices: List<String>) {
        print(proEInvoices)
        sftpConnector.connect(intimeProperties.address, intimeProperties.username, intimeProperties.password)
        proEInvoices.forEach{sftpConnector.send(intimeProperties.path, ByteArrayInputStream(it.toByteArray()))}
        sftpConnector.disconnect()
    }
}
