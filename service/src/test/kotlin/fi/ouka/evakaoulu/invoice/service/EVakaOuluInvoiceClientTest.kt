// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later
package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.SftpException
import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import fi.espoo.evaka.invoicing.domain.InvoiceRowDetailed
import fi.espoo.evaka.invoicing.domain.InvoiceStatus
import fi.espoo.evaka.invoicing.domain.PersonDetailed
import fi.espoo.evaka.invoicing.service.ProductKey
import fi.espoo.evaka.shared.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate
import java.util.*

internal class EVakaOuluInvoiceClientTest {


    val invoiceGenerator = mock<ProEInvoiceGenerator>()
    val invoiceSender = mock<InvoiceSender>()
    val eVakaOuluInvoiceClient = EVakaOuluInvoiceClient(invoiceSender, invoiceGenerator)

    @Test
    fun `should send generated invoices`() {
        val validInvoice = validInvoice()
        val invoiceList = listOf(validInvoice)
        val proEInvoice1 = ""
        whenever(invoiceGenerator.generateInvoice(validInvoice)).thenReturn(proEInvoice1)

        eVakaOuluInvoiceClient.send(invoiceList)

        verify(invoiceGenerator).generateInvoice(validInvoice)
        verify(invoiceSender).send(proEInvoice1)
    }

    @Test
    fun `should return successfully sent invoices in success list`() {
        val validInvoice = validInvoice()
        val invoiceList = listOf(validInvoice)
        val proEInvoice1 = ""
        whenever(invoiceGenerator.generateInvoice(validInvoice)).thenReturn(proEInvoice1)

        val sendResult = eVakaOuluInvoiceClient.send(invoiceList)

        assertThat(sendResult.succeeded).hasSize(1)
    }

    @Test
    fun `should return unsuccessfully sent invoices in failed list`() {
        val validInvoice = validInvoice()
        val invoiceWithoutSSN = validInvoice().copy(headOfFamily = personWithoutSSN())
        val invoiceList = listOf(validInvoice, invoiceWithoutSSN)
        val proEInvoice1 = ""
        whenever(invoiceGenerator.generateInvoice(validInvoice)).thenReturn(proEInvoice1)
        whenever(invoiceSender.send(proEInvoice1)).thenThrow(SftpException::class.java)

        val sendResult = eVakaOuluInvoiceClient.send(invoiceList)

        assertThat(sendResult.failed).hasSize(2)
    }

    @Test
    fun `should send more invoices at once`() {
        val validInvoice = validInvoice()
        val invoiceList = listOf(validInvoice, validInvoice)
        val proEInvoice1 = "xx"
        whenever(invoiceGenerator.generateInvoice(validInvoice)).thenReturn(
            "x"
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
        whenever(invoiceGenerator.generateInvoice(validInvoice)).thenReturn(
            "x"
        )
        val sendResult = eVakaOuluInvoiceClient.send(invoiceList)

        assertThat(sendResult.succeeded).hasSize(1)
        assertThat(sendResult.manuallySent).hasSize(1)
        verify(invoiceSender).send(proEInvoice1)
    }


    fun validInvoice(): InvoiceDetailed {
        val headOfFamily = validPerson()
        val invoiceRow1 = InvoiceRowDetailed(
            InvoiceRowId(UUID.randomUUID()), PersonDetailed(
                PersonId(UUID.randomUUID()), LocalDate.of(2018, 1, 1), null,
                "Matti", "Meikäläinen",
                null, "", "", "",
                "", null, "", null, restrictedDetailsEnabled = false
            ), 1, 24300,
            LocalDate.of(2021, 1, 1),
            LocalDate.of(2021, 1, 31),
            ProductKey("DAYCARE"), DaycareId(UUID.randomUUID()), "131885", null, null, "kuvaus1",
            correctionId = null,
            note = null
        )
        val invoiceRow2 = InvoiceRowDetailed(
            InvoiceRowId(UUID.randomUUID()), PersonDetailed(
                PersonId(UUID.randomUUID()), LocalDate.of(2015, 11, 26), null,
                "Maiju", "Meikäläinen",
                null, "", "", "",
                "", null, "", null, restrictedDetailsEnabled = false
            ), 1, 48200,
            LocalDate.of(2021, 1, 1),
            LocalDate.of(2021, 1, 31),
            ProductKey("PRESCHOOL_WITH_DAYCARE"), DaycareId(UUID.randomUUID()), "284823", null, null, "kuvaus2",
            correctionId = null,
            note = null
        )
        return InvoiceDetailed(
            (InvoiceId(UUID.randomUUID())),
            InvoiceStatus.WAITING_FOR_SENDING,
            LocalDate.now(),
            LocalDate.now(),
            LocalDate.of(2021, 3, 6),
            LocalDate.of(2021, 2, 4),
            null,
            AreaId(UUID.randomUUID()),
            headOfFamily,
            null,
            listOf(invoiceRow1, invoiceRow2),
            null,
            null,
            null
        )
    }

    fun validPerson() = PersonDetailed(
        PersonId(UUID.randomUUID()), LocalDate.of(1982, 3, 31), null,
        "Matti", "Meikäläinen",
        "310382-956D", "Meikäläisenkuja 6 B 7", "90100", "OULU",
        "", null, "", null, restrictedDetailsEnabled = false
    )

    fun personWithoutSSN() = PersonDetailed(
        PersonId(UUID.randomUUID()), LocalDate.of(1982, 3, 31), null,
        "Maija", "Meikäläinen",
        null, "Meikäläisenkuja 6 B 7", "90100", "OULU",
        "", null, "", null, restrictedDetailsEnabled = false
    )


}