package fi.ouka.evakaoulu.invoice.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class InvoiceDateProviderTest {

    @Test
    fun `should return date with correct format`() {
        val invoiceDateProvider = InvoiceDateProvider()
        val invoiceIdFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val expectedDate = LocalDate.now().format(invoiceIdFormatter)

        val actualDate = invoiceDateProvider.currentDate()

        assertThat(actualDate).isEqualTo(expectedDate)

    }
}