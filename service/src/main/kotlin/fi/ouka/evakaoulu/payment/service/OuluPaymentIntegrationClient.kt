package fi.ouka.evakaoulu.payment.service

import com.jcraft.jsch.SftpException
import fi.espoo.evaka.invoicing.domain.Payment
import fi.espoo.evaka.invoicing.domain.PaymentIntegrationClient
import fi.espoo.evaka.shared.db.Database
import fi.ouka.evakaoulu.invoice.service.SftpSender
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class OuluPaymentIntegrationClient(
    private val paymentGenerator: ProEPaymentGenerator,
    private val sftpSender: SftpSender
) : PaymentIntegrationClient {

    override fun send(payments: List<Payment>, tx: Database.Read): PaymentIntegrationClient.SendResult {
        var failedList: MutableList<Payment> = mutableListOf()

        logger.info { "OuluPaymentIntegrationClient.send() called with ${payments.size} payments" }
        val generatorResult = paymentGenerator.generatePayments(payments)
        var successList = generatorResult.sendResult.succeeded
        failedList.addAll(generatorResult.sendResult.failed)

        if (!successList.isEmpty()) {
            try {
                sftpSender.send(generatorResult.paymentString)
                logger.info { "Successfully sent ${successList.size} payments" }
            } catch (e: SftpException) {
                logger.error { "Failed to send ${successList.size} payments" }
                failedList.addAll(successList)
                successList = listOf()
            }
        }

        logger.info { "OuluPaymentIntegrationClient.send() returning" }
        return PaymentIntegrationClient.SendResult(successList, failedList)
    }
}
