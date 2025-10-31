// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.SftpException
import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import fi.espoo.evaka.invoicing.integration.InvoiceIntegrationClient
import fi.espoo.evaka.shared.domain.EvakaClock
import fi.espoo.evaka.shared.domain.RealEvakaClock
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.format.DateTimeFormatter

private val logger = KotlinLogging.logger {}

class EVakaOuluInvoiceClient(
    private val sftpSender: SftpSender,
    private val invoiceGenerator: ProEInvoiceGenerator,
) : InvoiceIntegrationClient {
    override fun send(invoices: List<InvoiceDetailed>): InvoiceIntegrationClient.SendResult = sendWithClock(invoices, RealEvakaClock())

    fun sendWithClock(
        invoices: List<InvoiceDetailed>,
        clock: EvakaClock,
    ): InvoiceIntegrationClient.SendResult {
        val failedList = mutableListOf<InvoiceDetailed>()

        val generatorResult = invoiceGenerator.generateInvoice(invoices)
        val proEinvoices = generatorResult.invoiceString
        var successList = generatorResult.sendResult.succeeded
        var manuallySentList = generatorResult.sendResult.manuallySent

        if (successList.isNotEmpty()) {
            try {
                val fileName = "proe-" + clock.now().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) + ".txt"
                sftpSender.send(proEinvoices, fileName)
                logger.info { "Successfully sent ${successList.size} invoices and created ${manuallySentList.size} manual invoice" }
            } catch (e: SftpException) {
                failedList.addAll(successList)
                failedList.addAll(manuallySentList)
                successList = listOf()
                manuallySentList = listOf()
                logger.error { "Failed to send ${failedList.size} invoices" }
            }
        }

        return InvoiceIntegrationClient.SendResult(successList, failedList, manuallySentList)
    }
}

interface StringInvoiceGenerator {
    data class InvoiceGeneratorResult(
        val sendResult: InvoiceIntegrationClient.SendResult = InvoiceIntegrationClient.SendResult(),
        val invoiceString: String = "",
    )

    fun generateInvoice(invoices: List<InvoiceDetailed>): InvoiceGeneratorResult
}
