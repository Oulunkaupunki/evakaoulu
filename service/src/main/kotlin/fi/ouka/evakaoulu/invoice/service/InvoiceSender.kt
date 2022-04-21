// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.SftpException
import fi.ouka.evakaoulu.IntimeProperties
import java.time.Instant
import java.time.format.DateTimeFormatter


class InvoiceSender(val intimeProperties: IntimeProperties, val sftpConnector: SftpConnector) {
    @Throws(SftpException::class)
    fun send(proEInvoice: String) {
        sftpConnector.connect(intimeProperties.address, intimeProperties.username, intimeProperties.password)
        val filepath = "${intimeProperties.path}-${
            DateTimeFormatter.ofPattern("yyyy-MM-dd").format(Instant.now())}"
        sftpConnector.send(filepath, proEInvoice)

        sftpConnector.disconnect()
    }
}
