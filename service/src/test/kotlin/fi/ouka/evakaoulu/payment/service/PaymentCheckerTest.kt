package fi.ouka.evakaoulu.payment.service

import org.junit.jupiter.api.Test

internal class PaymentCheckerTest {
    @Test
    fun `should return true for Payments whose IBAN is null`() {
        val paymentChecker = PaymentChecker()
        val unitWithoutIban = validPaymentUnit().copy(iban = null)
        val paymentWithoutIban = validPayment().copy(unit = unitWithoutIban)

        assert(paymentChecker.shouldFail(paymentWithoutIban))
    }

    @Test
    fun `should return true for Payments whose business ID is null`() {
        val paymentChecker = PaymentChecker()
        val unitWithoutBusinessId = validPaymentUnit().copy(businessId = null)
        val paymentWithoutBusinessId = validPayment().copy(unit = unitWithoutBusinessId)

        assert(paymentChecker.shouldFail(paymentWithoutBusinessId))
    }

    @Test
    fun `should return true for Payments whose provider ID is null`() {
        val paymentChecker = PaymentChecker()
        val unitWithoutProviderId = validPaymentUnit().copy(providerId = null)
        val paymentWithoutProviderId = validPayment().copy(unit = unitWithoutProviderId)

        assert(paymentChecker.shouldFail(paymentWithoutProviderId))
    }

    @Test
    fun `should return true for Payments whose amount is negative`() {
        val paymentChecker = PaymentChecker()
        val paymentWithNegativeAmount = validPayment().copy(amount = -100050)

        assert(paymentChecker.shouldFail(paymentWithNegativeAmount))
    }

    @Test
    fun `should return true for Payments whose amount is zero`() {
        val paymentChecker = PaymentChecker()
        val paymentWithNegativeAmount = validPayment().copy(amount = 0)

        assert(paymentChecker.shouldFail(paymentWithNegativeAmount))
    }

    @Test
    fun `should return false for valid Payments`() {
        val paymentChecker = PaymentChecker()
        val validPayment = validPayment()

        assert(!paymentChecker.shouldFail(validPayment))
    }
}
