// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.SftpException
import fi.ouka.evakaoulu.EvakaOuluProperties
import fi.ouka.evakaoulu.IntimeProperties
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.format.DateTimeFormatter

@Component
class InvoiceSender(val properties: EvakaOuluProperties, val sftpConnector: SftpConnector) {
    @Throws(SftpException::class)
    fun send(proEInvoice: String) {
        val intimeProperties = properties.intime
        sftpConnector.connect(intimeProperties.address, intimeProperties.username, intimeProperties.password)

        val filepath = "${intimeProperties.path}-${System.currentTimeMillis()}.csv"

        sftpConnector.send(filepath, proEInvoice)

        sftpConnector.disconnect()
    }
}
