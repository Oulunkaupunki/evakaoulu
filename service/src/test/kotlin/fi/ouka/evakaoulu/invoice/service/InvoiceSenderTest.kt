// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import fi.ouka.evakaoulu.EvakaOuluProperties
import fi.ouka.evakaoulu.IntimeProperties
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*

internal class InvoiceSenderTest {

    @Test
    fun `should send invoice`() {
        val path = "/some/path"
        val intimeProperties = IntimeProperties("", path, "", "")
        val proEInvoice = "one"
        val sftpConnector = mock<SftpConnector>()
        val invoiceSender = InvoiceSender(
            EvakaOuluProperties(intimeProperties), sftpConnector
        )

        invoiceSender.send(proEInvoice)

        verify(sftpConnector).connect(intimeProperties.address, intimeProperties.username, intimeProperties.password)
        val fileNamePattern = """$path/proe-\d{8}-\d{6}.txt"""
        verify(sftpConnector).send(
            argThat { filePath -> filePath.matches(Regex(fileNamePattern)) },
            eq("one")
        )
        verify(sftpConnector).disconnect()
    }
}