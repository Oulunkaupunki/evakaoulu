package fi.ouka.evakaoulu.invoice.service

import fi.ouka.evakaoulu.IntimeProperties

class InvoiceSender(val intimeProperties: IntimeProperties, val sftpConnector: SftpConnector) {
    fun send(proEInvoices: List<String>) {
        sftpConnector.connect(intimeProperties.address, intimeProperties.username, intimeProperties.password)
        proEInvoices.forEach{sftpConnector.send(intimeProperties.path, it)}
        sftpConnector.disconnect()
    }
}
