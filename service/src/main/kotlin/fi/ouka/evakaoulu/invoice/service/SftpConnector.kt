package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream

@Component
class SftpConnector(val jsch: JSch) {
    var jschSession: Session? = null
    var channelSftp: ChannelSftp? = null

    @Throws(Exception::class)
    fun connect(
        address: String,
        username: String,
        password: String,
    ) {
        jschSession = jsch.getSession(username, address)
        jschSession?.setConfig("StrictHostKeyChecking", "no")
        jschSession?.setPassword(password)
        jschSession?.connect()

        channelSftp = jschSession?.openChannel("sftp") as ChannelSftp
        channelSftp?.connect()
    }

    @Throws(Exception::class)
    fun send(
        filePath: String,
        proEInvoice: String,
    ) {
        channelSftp?.put(ByteArrayInputStream(proEInvoice.toByteArray(Charsets.ISO_8859_1)), filePath)
    }

    fun disconnect() {
        channelSftp?.disconnect()
        jschSession?.disconnect()
    }
}
