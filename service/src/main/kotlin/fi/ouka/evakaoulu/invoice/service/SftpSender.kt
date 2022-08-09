// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.SftpException
import fi.ouka.evakaoulu.EvakaOuluProperties
import fi.ouka.evakaoulu.IntimeProperties
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.util.*

class SftpSender(val intimeProperties: IntimeProperties, val sftpConnector: SftpConnector) {
    @Throws(SftpException::class)
    fun send(proEInvoice: String) {
        val path = intimeProperties.path
        val fileName = SimpleDateFormat("'proe-'yyyyMMdd-hhmmss'.txt'").format(Date())
        val filepath = "$path/$fileName"

        sftpConnector.connect(intimeProperties.address, intimeProperties.username, intimeProperties.password)

        sftpConnector.send(filepath, proEInvoice)

        sftpConnector.disconnect()
    }
}
