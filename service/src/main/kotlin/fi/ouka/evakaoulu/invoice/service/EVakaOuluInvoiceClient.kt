// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import com.jcraft.jsch.SftpException
import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import fi.espoo.evaka.invoicing.integration.InvoiceIntegrationClient
import fi.ouka.evakaoulu.IntimeProperties


class EVakaOuluInvoiceClient(
    private val properties: IntimeProperties,
    private val invoiceSender: InvoiceSender,
    private val proEInvoiceGenerator: ProEInvoiceGenerator
) : InvoiceIntegrationClient {
    override fun send(invoices: List<InvoiceDetailed>): InvoiceIntegrationClient.SendResult {
        val successList = mutableListOf<InvoiceDetailed>()
        val failedList = mutableListOf<InvoiceDetailed>()
        var invoice = ""

        invoices.forEach {
            invoice += proEInvoiceGenerator.generateInvoice(it)
            successList.add(it)
        }

        try {
            invoiceSender.send(invoice)
        } catch (e: SftpException){
            failedList.addAll(successList)
            successList.clear()
        }

        return InvoiceIntegrationClient.SendResult(successList, failedList, emptyList())
    }

}

interface ProEInvoiceGenerator {
    fun generateInvoice(invoice: InvoiceDetailed): String

}