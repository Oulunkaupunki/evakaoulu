package fi.ouka.evakaoulu.payment.service

import fi.espoo.evaka.invoicing.domain.Payment
import org.springframework.stereotype.Component

@Component
class PaymentChecker {
    fun shouldFail(payment: Payment): Boolean {
        if (payment.unit.iban == null) return true
        if (payment.unit.businessId == null) return true
        if (payment.unit.providerId == null) return true
        if (payment.amount <= 0) return true
        return false
    }
}