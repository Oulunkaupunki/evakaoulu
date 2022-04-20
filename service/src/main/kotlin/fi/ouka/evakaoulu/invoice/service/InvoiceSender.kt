package fi.ouka.evakaoulu.invoice.service

import fi.ouka.evakaoulu.IntimeProperties
import org.springframework.stereotype.Component

@Component
class InvoiceSender(val intimeProperties: IntimeProperties, val sftpConnector: SftpConnector) {
    fun send(proEInvoice: String) {
        sftpConnector.connect(intimeProperties.address, intimeProperties.username, intimeProperties.password)

        sftpConnector.send(intimeProperties.path, proEInvoice)

        sftpConnector.disconnect()
    }
}
