package fi.ouka.evakaoulu.payment.service

import fi.espoo.evaka.invoicing.domain.Payment
import fi.espoo.evaka.invoicing.domain.PaymentIntegrationClient
import org.springframework.stereotype.Component

@Component
class ProEPaymentGenerator(private val paymentChecker: PaymentChecker) {

    data class Result(
        val sendResult: PaymentIntegrationClient.SendResult = PaymentIntegrationClient.SendResult(),
        val paymentString: String = ""
    )

    /*
    fun gatherPaymentData(payment: Payment): PaymentData {

    }

    fun formatPayment(paymentData): String {
        return ""
    }
     */

    fun generatePayments(payments: List<Payment> ): Result {
        var successList = mutableListOf<Payment>()
        var failedList = mutableListOf<Payment>()

        val (failed, succeeded) = payments.partition { payment -> paymentChecker.shouldFail(payment) }
        failedList.addAll(failed)

        var paymentString = ""
        succeeded.forEach {
            // val paymentData = gatherPaymentData(it)
            // paymentString += formatPayment(paymentData)
            successList.add(it)
        }

        return Result(PaymentIntegrationClient.SendResult(successList, failedList), paymentString)
    }
}
