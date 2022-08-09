package fi.ouka.evakaoulu.payment.service

import com.jcraft.jsch.SftpException
import fi.espoo.evaka.invoicing.domain.Payment
import fi.espoo.evaka.invoicing.domain.PaymentIntegrationClient
import fi.ouka.evakaoulu.invoice.service.SftpSender
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class OuluPaymentIntegrationClient(
    private val paymentGenerator: ProEPaymentGenerator,
    private val sftpSender: SftpSender
): PaymentIntegrationClient {

    override fun send(payments: List<Payment>): PaymentIntegrationClient.SendResult {

        var failedList: MutableList<Payment> = mutableListOf()

        val generatorResult = paymentGenerator.generatePayments(payments)
        var successList = generatorResult.sendResult.succeeded
        failedList.addAll(generatorResult.sendResult.failed)

        try {
            sftpSender.send(generatorResult.paymentString)
        }
        catch (e: SftpException) {
            logger.error { "Failed to send ${successList.size} invoices" }
            failedList.addAll(successList)
            successList = listOf()
        }

        return PaymentIntegrationClient.SendResult(successList, failedList)
    }
}