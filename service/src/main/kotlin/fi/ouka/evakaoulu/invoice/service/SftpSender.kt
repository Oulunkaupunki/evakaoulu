// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.SftpException
import fi.ouka.evakaoulu.SftpProperties

class SftpSender(val sftpProperties: SftpProperties, val sftpConnector: SftpConnector) {
    @Throws(SftpException::class)
    fun send(
        content: String,
        fileName: String,
    ) {
        val path = sftpProperties.path
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
