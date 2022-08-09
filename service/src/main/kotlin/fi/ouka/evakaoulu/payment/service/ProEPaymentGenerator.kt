package fi.ouka.evakaoulu.payment.service

import fi.espoo.evaka.invoicing.domain.Payment
import fi.espoo.evaka.invoicing.domain.PaymentIntegrationClient
import org.springframework.stereotype.Component

@Component
class ProEPaymentGenerator {

    data class Result(
        val sendResult: PaymentIntegrationClient.SendResult = PaymentIntegrationClient.SendResult(),
        val paymentString: String = ""
    )

    fun generatePayments(payments: List<Payment> ): Result {
        return Result(PaymentIntegrationClient.SendResult(), "")
    }
}
