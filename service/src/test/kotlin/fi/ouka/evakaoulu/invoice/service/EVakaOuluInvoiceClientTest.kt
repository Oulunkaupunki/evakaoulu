// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later
package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.SftpException
import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import fi.espoo.evaka.invoicing.domain.InvoiceRowDetailed
import fi.espoo.evaka.invoicing.domain.InvoiceStatus
import fi.espoo.evaka.invoicing.domain.PersonDetailed
import fi.espoo.evaka.invoicing.integration.InvoiceIntegrationClient
import fi.espoo.evaka.invoicing.service.ProductKey
import fi.espoo.evaka.shared.*
import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension
import java.time.LocalDate
import java.util.*

@ExtendWith(OutputCaptureExtension::class)
internal class EVakaOuluInvoiceClientTest {


    val invoiceGenerator = mock<ProEInvoiceGenerator>()
    val invoiceSender = mock<InvoiceSender>()
    val eVakaOuluInvoiceClient = EVakaOuluInvoiceClient(invoiceSender, invoiceGenerator)

    @Test
    fun `should send generated invoices`() {
        val validInvoice = validInvoice()
        val invoiceList = listOf(validInvoice)
        val proEInvoice1 = ""
        val invoiceGeneratorResult = StringInvoiceGenerator.InvoiceGeneratorResult(InvoiceIntegrationClient.SendResult(), proEInvoice1)
        whenever(invoiceGenerator.generateInvoice(invoiceList)).thenReturn(invoiceGeneratorResult)

        eVakaOuluInvoiceClient.send(invoiceList)

        verify(invoiceGenerator).generateInvoice(invoiceList)
        verify(invoiceSender).send(proEInvoice1)
    }

    @Test
    fun `should return successfully sent invoices in success list`() {
        val validInvoice = validInvoice()
        val invoiceList = listOf(validInvoice)
        val proEInvoice1 = ""
        val invoiceGeneratorResult = StringInvoiceGenerator.InvoiceGeneratorResult(InvoiceIntegrationClient.SendResult(invoiceList, listOf(), listOf()), proEInvoice1)
        whenever(invoiceGenerator.generateInvoice(invoiceList)).thenReturn(invoiceGeneratorResult)

        val sendResult = eVakaOuluInvoiceClient.send(invoiceList)

        assertThat(sendResult.succeeded).hasSize(1)
    }

    @Test
    fun `should return unsuccessfully sent invoices in failed list`() {
        val validInvoice = validInvoice()
        val invoiceWithoutSSN = validInvoice().copy(headOfFamily = personWithoutSSN())
        val invoiceList = listOf(validInvoice, invoiceWithoutSSN)
        val proEInvoice1 = ""
        val invoiceGeneratorResult = StringInvoiceGenerator.InvoiceGeneratorResult(InvoiceIntegrationClient.SendResult(invoiceList, listOf(), listOf()), proEInvoice1)
        whenever(invoiceGenerator.generateInvoice(invoiceList)).thenReturn(invoiceGeneratorResult)
        whenever(invoiceSender.send(proEInvoice1)).thenThrow(SftpException::class.java)

        val sendResult = eVakaOuluInvoiceClient.send(invoiceList)

        assertThat(sendResult.failed).hasSize(2)
    }

    @Test
    fun `should send more invoices at once`() {
        val validInvoice = validInvoice()
        val invoiceList = listOf(validInvoice, validInvoice)
        val proEInvoice1 = "xx"
        whenever(invoiceGenerator.generateInvoice(invoiceList)).thenReturn(
            StringInvoiceGenerator.InvoiceGeneratorResult(InvoiceIntegrationClient.SendResult(invoiceList, listOf(), listOf()), "xx")
        )
        val sendResult = eVakaOuluInvoiceClient.send(invoiceList)

        assertThat(sendResult.succeeded).hasSize(2)
        verify(invoiceSender).send(proEInvoice1)
    }

    @Test
    fun `should send manually invoices when customer has no SSN`() {
        val validInvoice = validInvoice()
        val invoiceWithoutSSN = validInvoice().copy(headOfFamily = personWithoutSSN())
        val invoiceList = listOf(validInvoice, invoiceWithoutSSN)
        val proEInvoice1 = "x"
        whenever(invoiceGenerator.generateInvoice(invoiceList)).thenReturn(
                StringInvoiceGenerator.InvoiceGeneratorResult(InvoiceIntegrationClient.SendResult(listOf(validInvoice), listOf(), listOf(invoiceWithoutSSN)), "x")
        )
        val sendResult = eVakaOuluInvoiceClient.send(invoiceList)

        assertThat(sendResult.succeeded).hasSize(1)
        assertThat(sendResult.manuallySent).hasSize(1)
        verify(invoiceSender).send(proEInvoice1)
    }

    @Test
    fun `should log successful invoices`(output: CapturedOutput) {
        val validInvoice = validInvoice()
        val invoiceWithoutSSN = validInvoice().copy(headOfFamily = personWithoutSSN())
        val invoiceList = listOf(validInvoice, invoiceWithoutSSN)
        whenever(invoiceGenerator.generateInvoice(invoiceList)).thenReturn(
                StringInvoiceGenerator.InvoiceGeneratorResult(InvoiceIntegrationClient.SendResult(listOf(validInvoice), listOf(), listOf(invoiceWithoutSSN)), "x")
        )

        eVakaOuluInvoiceClient.send(invoiceList)

        assertThat(output).contains("Successfully sent 1 invoices and created 1 manual invoice")
    }

    @Test
    fun `should log failed invoice sends`(output: CapturedOutput) {
        val validInvoice = validInvoice()
        val invoiceWithoutSSN = validInvoice().copy(headOfFamily = personWithoutSSN())
        val invoiceList = listOf(validInvoice, invoiceWithoutSSN)
        val proEInvoice1 = ""
        whenever(invoiceGenerator.generateInvoice(invoiceList)).thenReturn(
                StringInvoiceGenerator.InvoiceGeneratorResult(InvoiceIntegrationClient.SendResult(invoiceList, listOf(), listOf()), proEInvoice1)
        )

        whenever(invoiceSender.send(proEInvoice1)).thenThrow(SftpException::class.java)
        eVakaOuluInvoiceClient.send(invoiceList)

        assertThat(output).contains("Failed to send 2 invoices")
    }
}