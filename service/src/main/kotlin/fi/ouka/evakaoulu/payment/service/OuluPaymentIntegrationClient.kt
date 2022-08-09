package fi.ouka.evakaoulu.payment.service

import fi.espoo.evaka.invoicing.domain.Payment
import fi.espoo.evaka.invoicing.domain.PaymentIntegrationClient
import fi.ouka.evakaoulu.invoice.service.SftpSender

class OuluPaymentIntegrationClient(
    private val paymentGenerator: ProEPaymentGenerator,
    private val sftpSender: SftpSender
): PaymentIntegrationClient {

    override fun send(payments: List<Payment>): PaymentIntegrationClient.SendResult {

        val generatorResult = paymentGenerator.generatePayments(payments)

        sftpSender.send(generatorResult)

        return PaymentIntegrationClient.SendResult()
    }
}