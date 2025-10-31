// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later
package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.SftpException
import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import fi.espoo.evaka.invoicing.integration.InvoiceIntegrationClient
import fi.espoo.evaka.shared.domain.HelsinkiDateTime
import fi.espoo.evaka.shared.domain.MockEvakaClock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.boot.test.system.CapturedOutput
import org.springframework.boot.test.system.OutputCaptureExtension
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@ExtendWith(OutputCaptureExtension::class)
internal class EVakaOuluInvoiceClientTest {
    val invoiceGenerator = mock<ProEInvoiceGenerator>()
    val sftpSender = mock<SftpSender>()
    val eVakaOuluInvoiceClient = EVakaOuluInvoiceClient(sftpSender, invoiceGenerator)
    val mockClock = MockEvakaClock(HelsinkiDateTime.of(LocalDate.of(2022, 10, 12), LocalTime.of(13, 34, 56)))
    val fileName: String = "proe-" + mockClock.now().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) + ".txt"

    @Test
    fun `should pass invoices to the invoice generator`() {
        val validInvoice = validInvoice()
        val invoiceList = listOf(validInvoice)
        val proEInvoice1 = ""
        val invoiceGeneratorResult =
            StringInvoiceGenerator.InvoiceGeneratorResult(InvoiceIntegrationClient.SendResult(), proEInvoice1)
        whenever(invoiceGenerator.generateInvoice(invoiceList)).thenReturn(invoiceGeneratorResult)

        eVakaOuluInvoiceClient.send(invoiceList)

        verify(invoiceGenerator).generateInvoice(invoiceList)
    }

    @Test
    fun `should send generated invoices`() {
        val validInvoice = validInvoice()
        val invoiceList = listOf(validInvoice)
        val proEInvoice1 = ""
        val invoiceGeneratorResult =
            StringInvoiceGenerator.InvoiceGeneratorResult(
                InvoiceIntegrationClient.SendResult(
                    invoiceList,
                    listOf(),
                    listOf(),
                ),
                proEInvoice1,
            )
        whenever(invoiceGenerator.generateInvoice(invoiceList)).thenReturn(invoiceGeneratorResult)

        eVakaOuluInvoiceClient.sendWithClock(invoiceList, mockClock)

        verify(sftpSender).send(proEInvoice1, fileName)
    }

    @Test
    fun `should not send anything if there are no sendable invoices`() {
        val invoiceList = listOf<InvoiceDetailed>()
        val invoiceGeneratorResult = StringInvoiceGenerator.InvoiceGeneratorResult(InvoiceIntegrationClient.SendResult(), "")
        whenever(invoiceGenerator.generateInvoice(invoiceList)).thenReturn(invoiceGeneratorResult)

        eVakaOuluInvoiceClient.sendWithClock(invoiceList, mockClock)

        verify(sftpSender, never()).send("", fileName)
    }

    @Test
    fun `should return successfully sent invoices in success list`() {
        val validInvoice = validInvoice()
        val invoiceList = listOf(validInvoice)
        val proEInvoice1 = ""
        val invoiceGeneratorResult =
            StringInvoiceGenerator.InvoiceGeneratorResult(
                InvoiceIntegrationClient.SendResult(
                    invoiceList,
                    listOf(),
                    listOf(),
                ),
                proEInvoice1,
            )
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
        val invoiceGeneratorResult =
            StringInvoiceGenerator.InvoiceGeneratorResult(
                InvoiceIntegrationClient.SendResult(
                    invoiceList,
                    listOf(),
                    listOf(),
                ),
                proEInvoice1,
            )
        whenever(invoiceGenerator.generateInvoice(invoiceList)).thenReturn(invoiceGeneratorResult)
        whenever(sftpSender.send(proEInvoice1, fileName)).thenThrow(SftpException::class.java)

        val sendResult = eVakaOuluInvoiceClient.sendWithClock(invoiceList, mockClock)

        assertThat(sendResult.failed).hasSize(2)
    }

    @Test
    fun `should send more invoices at once`() {
        val validInvoice = validInvoice()
        val invoiceList = listOf(validInvoice, validInvoice)
        val proEInvoice1 = "xx"
        whenever(invoiceGenerator.generateInvoice(invoiceList)).thenReturn(
            StringInvoiceGenerator.InvoiceGeneratorResult(
                InvoiceIntegrationClient.SendResult(
                    invoiceList,
                    listOf(),
                    listOf(),
                ),
                "xx",
            ),
        )
        val sendResult = eVakaOuluInvoiceClient.sendWithClock(invoiceList, mockClock)

        assertThat(sendResult.succeeded).hasSize(2)
        verify(sftpSender).send(proEInvoice1, fileName)
    }

    @Test
    fun `should send manually invoices which invoice generator determined to be manually sent`() {
        val validInvoice = validInvoice()
        val invoiceWithRestrictedDetails = validInvoice().copy(headOfFamily = personWithRestrictedDetails())
        val invoiceList = listOf(validInvoice, invoiceWithRestrictedDetails)
        val proEInvoice1 = "x"
        whenever(invoiceGenerator.generateInvoice(invoiceList)).thenReturn(
            StringInvoiceGenerator.InvoiceGeneratorResult(
                InvoiceIntegrationClient.SendResult(
                    listOf(validInvoice),
                    listOf(),
                    listOf(invoiceWithRestrictedDetails),
                ),
                "x",
            ),
        )
        val sendResult = eVakaOuluInvoiceClient.sendWithClock(invoiceList, mockClock)

        assertThat(sendResult.succeeded).hasSize(1)
        assertThat(sendResult.manuallySent).hasSize(1)
        verify(sftpSender).send(proEInvoice1, fileName)
    }

    @Test
    fun `should log successful invoices`(output: CapturedOutput) {
        val validInvoice = validInvoice()
        val invoiceWithoutSSN = validInvoice().copy(headOfFamily = personWithoutSSN())
        val invoiceList = listOf(validInvoice, invoiceWithoutSSN)
        whenever(invoiceGenerator.generateInvoice(invoiceList)).thenReturn(
            StringInvoiceGenerator.InvoiceGeneratorResult(
                InvoiceIntegrationClient.SendResult(
                    listOf(validInvoice),
                    listOf(),
                    listOf(invoiceWithoutSSN),
                ),
                "x",
            ),
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
            StringInvoiceGenerator.InvoiceGeneratorResult(
                InvoiceIntegrationClient.SendResult(
                    invoiceList,
                    listOf(),
                    listOf(),
                ),
                proEInvoice1,
            ),
        )

        whenever(sftpSender.send(proEInvoice1, fileName)).thenThrow(SftpException::class.java)
        eVakaOuluInvoiceClient.sendWithClock(invoiceList, mockClock)

        assertThat(output).contains("Failed to send 2 invoices")
    }

    @Test
    fun `should format the sent file name correctly`() {
        val validInvoice = validInvoice()
        val invoiceList = listOf(validInvoice)
        val proEInvoice1 = "xx"
        whenever(invoiceGenerator.generateInvoice(invoiceList)).thenReturn(
            StringInvoiceGenerator.InvoiceGeneratorResult(
                InvoiceIntegrationClient.SendResult(
                    invoiceList,
                    listOf(),
                    listOf(),
                ),
                "xx",
            ),
        )
        val sendResult = eVakaOuluInvoiceClient.sendWithClock(invoiceList, mockClock)

        verify(sftpSender).send(proEInvoice1, "proe-20221012-133456.txt")
    }
}
