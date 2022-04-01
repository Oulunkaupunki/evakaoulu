// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import fi.espoo.evaka.invoicing.integration.InvoiceIntegrationClient
import fi.ouka.evakaoulu.IntimeProperties
import org.apache.tomcat.jni.User.username




class EVakaOuluInvoiceClient(
    private val properties: IntimeProperties
) : InvoiceIntegrationClient {
    override fun send(invoices: List<InvoiceDetailed>): InvoiceIntegrationClient.SendResult {
//        TODO("Not yet implemented")
        val channelSftp: ChannelSftp = setupJsch()
        channelSftp.connect()
        val localFile = "src/main/resources/sample.txt"
        val remoteDir = properties.address
        channelSftp.put(localFile, remoteDir + "jschFile.txt")
        channelSftp.exit()
    }

//    @Throws(JSchException::class)
    private fun setupJsch(): ChannelSftp? {
        val jsch = JSch()
        jsch.setKnownHosts("/Users/john/.ssh/known_hosts")
        val jschSession: Session = jsch.getSession(properties.username, properties.address)
        jschSession.setPassword(properties.password)
        jschSession.connect()
        return jschSession.openChannel("sftp") as ChannelSftp
    }


}