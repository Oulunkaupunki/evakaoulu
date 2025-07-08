// SPDX-FileCopyrightText: 2023-2025 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.payment.service

import fi.ouka.evakaoulu.util.FinanceDateProvider
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class ProEPaymentGeneratorTest {
    @Test
    fun `should not generate payment rows from payments with a negative amount`() {
        val proEPaymentGenerator = ProEPaymentGenerator(PaymentChecker(), FinanceDateProvider(), BicMapper())

        val validPayment = validPayment()
        val otherPaymentUnit = validPaymentUnit().copy(providerId = "OTHERPROVIDERID")
        val negativePayment = validPayment().copy(unit = otherPaymentUnit, amount = -10000)

        val result = proEPaymentGenerator.generatePayments(listOf(validPayment, negativePayment))

        assert(!result.paymentString.contains("OTHERPROVIDERID"))
    }

    @Test
    fun `should not generate payment rows from payments with a zero amount`() {
        val proEPaymentGenerator = ProEPaymentGenerator(PaymentChecker(), FinanceDateProvider(), BicMapper())

        val validPayment = validPayment()
        val otherPaymentUnit = validPaymentUnit().copy(providerId = "OTHERPROVIDERID")
        val negativePayment = validPayment().copy(unit = otherPaymentUnit, amount = 0)

        val result = proEPaymentGenerator.generatePayments(listOf(validPayment, negativePayment))

        assert(!result.paymentString.contains("OTHERPROVIDERID"))
    }

    @Test
    fun `should include payments with negative amounts in the success list`() {
        val proEPaymentGenerator = ProEPaymentGenerator(PaymentChecker(), FinanceDateProvider(), BicMapper())

        val validPayment = validPayment()
        val otherPaymentUnit = validPaymentUnit().copy(providerId = "OTHERPROVIDERID")
        val negativePayment = validPayment().copy(unit = otherPaymentUnit, amount = -10000)

        val result = proEPaymentGenerator.generatePayments(listOf(validPayment, negativePayment))

        assert(result.sendResult.succeeded.containsAll(listOf(validPayment, negativePayment)))
    }

    @Test
    fun `should include payments with zero amounts in the success list`() {
        val proEPaymentGenerator = ProEPaymentGenerator(PaymentChecker(), FinanceDateProvider(), BicMapper())

        val validPayment = validPayment()
        val otherPaymentUnit = validPaymentUnit().copy(providerId = "OTHERPROVIDERID")
        val negativePayment = validPayment().copy(unit = otherPaymentUnit, amount = 0)

        val result = proEPaymentGenerator.generatePayments(listOf(validPayment, negativePayment))

        assert(result.sendResult.succeeded.containsAll(listOf(validPayment, negativePayment)))
    }

    @Disabled
    @Test
    fun `should check that payment format is a proper one also with invoice function number`() {
        val proEPaymentGenerator = ProEPaymentGenerator(PaymentChecker(), FinanceDateProvider(), BicMapper())
        val validPayment = validPayment()
        val otherPaymentUnit = validPaymentUnit().copy(providerId = "OTHERPROVIDERID")
        val otherPayment = validPayment().copy(unit = otherPaymentUnit)
        val payments = listOf(validPayment, otherPayment)

        val generationResult = proEPaymentGenerator.generatePayments(payments)

        var correctPayments = object {}.javaClass.getResource("/payment-client/CorrectProEPayments.txt")?.readText()

        Assertions.assertEquals(correctPayments, generationResult.paymentString)
    }
}
