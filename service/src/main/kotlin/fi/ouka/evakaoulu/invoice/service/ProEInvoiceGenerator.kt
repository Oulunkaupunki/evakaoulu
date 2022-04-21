// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import org.springframework.stereotype.Component

@Component
class ProEInvoiceGenerator : StringInvoiceGenerator {
    override fun generateInvoice(invoice: InvoiceDetailed): String {
        return ""
    }
}