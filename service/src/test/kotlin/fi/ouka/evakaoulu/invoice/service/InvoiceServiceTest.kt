package fi.ouka.evakaoulu.invoice.service

import fi.espoo.evaka.invoicing.integration.InvoiceIntegrationClient
import fi.espoo.evaka.invoicing.service.InvoiceProductProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock


class InvoiceServiceTest {

    @Test
    fun `should start invoice numbering from 1000000`() {
        val invoiceIntegrationClient = mock<InvoiceIntegrationClient>()
        val invoiceProductProvider = mock<InvoiceProductProvider>()


        val invoiceService =  InvoiceService(invoiceIntegrationClient, invoiceProductProvider)

        // when
        val maxInvoiceId = invoiceService.OneMillionOrMore(1)

        // then
        assertThat(maxInvoiceId).isEqualTo(1000000)
    }
}