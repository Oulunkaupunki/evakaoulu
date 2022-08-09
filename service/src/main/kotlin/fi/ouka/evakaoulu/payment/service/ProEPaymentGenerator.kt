package fi.ouka.evakaoulu.payment.service

import fi.espoo.evaka.invoicing.domain.Payment
import org.springframework.stereotype.Component

@Component
class ProEPaymentGenerator {

    fun generatePayments(payments: List<Payment> ): String {
        return ""
    }
}
