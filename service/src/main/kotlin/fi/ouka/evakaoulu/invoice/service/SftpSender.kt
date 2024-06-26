// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.SftpException
import fi.ouka.evakaoulu.SftpProperties
import java.text.SimpleDateFormat
import java.util.Date

class SftpSender(val sftpProperties: SftpProperties, val sftpConnector: SftpConnector) {
    @Throws(SftpException::class)
    fun send(content: String) {
        val path = sftpProperties.path
        val fileName = SimpleDateFormat("'proe-'yyyyMMdd-hhmmss'.txt'").format(Date())
        val filepath = "$path/$fileName"

        try {
            sftpConnector.connect(sftpProperties.address, sftpProperties.username, sftpProperties.password)

            sftpConnector.send(filepath, content)
        } catch (e: Exception) {
            throw e
        } finally {
            sftpConnector.disconnect()
        }
    }
}
