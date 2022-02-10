// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import fi.espoo.evaka.invoicing.integration.InvoiceIntegrationClient
import fi.ouka.evakaoulu.InvoiceProperties
import org.springframework.ws.client.core.WebServiceTemplate

class EVakaOuluInvoiceClient(
    private val webServiceTemplate: WebServiceTemplate, private val properties: InvoiceProperties
) : InvoiceIntegrationClient {
    override fun send(invoices: List<InvoiceDetailed>): InvoiceIntegrationClient.SendResult {
        TODO("Not yet implemented")
    }
}