package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import java.io.ByteArrayInputStream

class SftpConnector(val jsch: JSch) {

    lateinit var channelSftp: ChannelSftp
    fun connect(address:String, username:String, password:String): Unit {

        jsch.setKnownHosts("/Users/john/.ssh/known_hosts")
        val jschSession: Session = jsch.getSession(username, address)
        jschSession.setPassword(password)
        jschSession.connect()

        channelSftp = jschSession.openChannel("sftp") as ChannelSftp
        channelSftp.connect()
    }

    fun send(filePath:String, proEInvoice:String) : Unit {
        channelSftp.put(ByteArrayInputStream(proEInvoice.toByteArray()), filePath)
    }

    fun disconnect(){
        channelSftp.disconnect()
    }

}