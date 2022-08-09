package fi.ouka.evakaoulu.payment.service

import fi.espoo.evaka.invoicing.domain.Payment
import fi.espoo.evaka.invoicing.integration.InvoiceIntegrationClient
import fi.ouka.evakaoulu.invoice.service.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

val paymentGenerator = mock<ProEPaymentGenerator>()
val sftpSender = mock<SftpSender>()
val paymentClient = OuluPaymentIntegrationClient(paymentGenerator, sftpSender)

internal class OuluPaymentIntegrationClientTest {

    @Test
    fun `should pass payments to the payment generator`() {
        val validPayment = validPayment()
        val paymentList = listOf(validPayment)
        val proEPayment1 = ""
        // val invoiceGeneratorResult = StringInvoiceGenerator.InvoiceGeneratorResult(InvoiceIntegrationClient.SendResult(), proEInvoice1)
        val paymentGeneratorResult = proEPayment1
        whenever(paymentGenerator.generatePayments(paymentList)).thenReturn(paymentGeneratorResult)

        paymentClient.send(paymentList)

        verify(paymentGenerator).generatePayments(paymentList)
    }

    @Test
    fun `should send generated payments`() {
        val validPayment = validPayment()
        val paymentList = listOf(validPayment)
        val proEPayment1 = ""
        val paymentGeneratorResult = proEPayment1
        whenever(paymentGenerator.generatePayments(paymentList)).thenReturn(paymentGeneratorResult)

        paymentClient.send(paymentList)

        verify(sftpSender).send(proEPayment1)
    }

}