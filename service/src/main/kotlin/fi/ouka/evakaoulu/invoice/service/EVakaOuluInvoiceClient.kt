// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import fi.espoo.evaka.invoicing.integration.InvoiceIntegrationClient
import fi.ouka.evakaoulu.IntimeProperties


class EVakaOuluInvoiceClient(
    private val properties: IntimeProperties, private val invoiceSender: InvoiceSender
) : InvoiceIntegrationClient {
    override fun send(invoices: List<InvoiceDetailed>): InvoiceIntegrationClient.SendResult {

        // fake generated invoices
        invoices.forEach{invoiceSender.send("")}


        return InvoiceIntegrationClient.SendResult(emptyList(), emptyList(), emptyList())
    }

}

interface MockInvoiceGenerator{
    fun generateInvoice(invoice : InvoiceDetailed): String

}