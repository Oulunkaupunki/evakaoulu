// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import fi.ouka.evakaoulu.IntimeProperties


class InvoiceSender(val intimeProperties: IntimeProperties, val sftpConnector: SftpConnector) {
    fun send(proEInvoice: String) {
        sftpConnector.connect(intimeProperties.address, intimeProperties.username, intimeProperties.password)

        sftpConnector.send(intimeProperties.path, proEInvoice)

        sftpConnector.disconnect()
    }
}
