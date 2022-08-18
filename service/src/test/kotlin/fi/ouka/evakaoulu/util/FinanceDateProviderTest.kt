package fi.ouka.evakaoulu.invoice.service

import fi.ouka.evakaoulu.util.FinanceDateProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FinanceDateProviderTest {

    @Test
    fun `should return date with correct format`() {
        val financeDateProvider = FinanceDateProvider()
        val invoiceIdFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val expectedDate = LocalDate.now().format(invoiceIdFormatter)

        val actualDate = financeDateProvider.currentDate()

        assertThat(actualDate).isEqualTo(expectedDate)

    }

    @Test
    fun `should return date with correct abbreviated format`() {
        val financeDateProvider = FinanceDateProvider()
        val invoiceIdFormatter = DateTimeFormatter.ofPattern("yyMMdd")
        val expectedDate = LocalDate.now().format(invoiceIdFormatter)

        val actualDate = financeDateProvider.currentDateWithAbbreviatedYear()

        assertThat(actualDate).isEqualTo(expectedDate)

    }
}