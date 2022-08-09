package fi.ouka.evakaoulu.payment.service

import com.jcraft.jsch.SftpException
import fi.espoo.evaka.invoicing.domain.Payment
import fi.espoo.evaka.invoicing.domain.PaymentIntegrationClient
import fi.espoo.evaka.invoicing.domain.PaymentUnit
import fi.espoo.evaka.shared.DaycareId
import fi.ouka.evakaoulu.invoice.service.SftpSender
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

internal class OuluPaymentIntegrationClientTest {

    val paymentGenerator = mock<ProEPaymentGenerator>()
    val sftpSender = mock<SftpSender>()
    val paymentClient = OuluPaymentIntegrationClient(paymentGenerator, sftpSender)

    @Test
    fun `should pass payments to the payment generator`() {
        val validPayment = validPayment()
        val paymentList = listOf(validPayment)
        val proEPayment1 = ""
        val paymentGeneratorResult = ProEPaymentGenerator.Result(PaymentIntegrationClient.SendResult(), proEPayment1)
        whenever(paymentGenerator.generatePayments(paymentList)).thenReturn(paymentGeneratorResult)

        paymentClient.send(paymentList)

        verify(paymentGenerator).generatePayments(paymentList)
    }

    @Test
    fun `should send generated payments`() {
        val validPayment = validPayment()
        val paymentList = listOf(validPayment)
        val proEPayment1 = ""
        val paymentGeneratorResult = ProEPaymentGenerator.Result(PaymentIntegrationClient.SendResult(), proEPayment1)
        whenever(paymentGenerator.generatePayments(paymentList)).thenReturn(paymentGeneratorResult)

        paymentClient.send(paymentList)

        verify(sftpSender).send(proEPayment1)
    }

    @Test
    fun `should return successfully sent payments in success list`() {
        val validPayment = validPayment()
        val paymentList = listOf(validPayment)
        val proEPayment1 = ""
        val paymentGeneratorResult = ProEPaymentGenerator.Result(PaymentIntegrationClient.SendResult(listOf(validPayment),listOf()), proEPayment1)
        whenever(paymentGenerator.generatePayments(paymentList)).thenReturn(paymentGeneratorResult)

        val sendResult = paymentClient.send(paymentList)

        Assertions.assertThat(sendResult.succeeded).hasSize(1)
    }

    @Test
    fun `should return unsuccessfully generated payments in failed list`() {
        val validPayment = validPayment()
        val unitWithoutIban = PaymentUnit(DaycareId(UUID.randomUUID()), "No IBAN", "1234567-8", null, "PROVIDERID")
        val paymentWithoutIban = validPayment().copy(unit = unitWithoutIban)
        val paymentList = listOf(validPayment, paymentWithoutIban)
        val proEPayment1 = ""
        val paymentGeneratorResult = ProEPaymentGenerator.Result(PaymentIntegrationClient.SendResult(listOf(validPayment), listOf(paymentWithoutIban)), proEPayment1)
        whenever(paymentGenerator.generatePayments(paymentList)).thenReturn(paymentGeneratorResult)
        // whenever(sftpSender.send(proEPayment1)).thenThrow(SftpException::class.java)

        val sendResult = paymentClient.send(paymentList)

        Assertions.assertThat(sendResult.failed).hasSize(1)
    }

    @Test
    fun `should return unsuccessfully sent payments in failed list`() {
        val validPayment = validPayment()
        val paymentList = listOf(validPayment, validPayment)
        val proEPayment1 = ""
        val paymentGeneratorResult = ProEPaymentGenerator.Result(PaymentIntegrationClient.SendResult(paymentList, listOf()), proEPayment1)
        whenever(paymentGenerator.generatePayments(paymentList)).thenReturn(paymentGeneratorResult)
        whenever(sftpSender.send(proEPayment1)).thenThrow(SftpException::class.java)

        val sendResult = paymentClient.send(paymentList)

        Assertions.assertThat(sendResult.failed).hasSize(2)
    }
}