// SPDX-FileCopyrightText: 2023-2025 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.invoice.service

import fi.ouka.evakaoulu.util.FinanceDateProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FinanceDateProviderTest {
    @Test
    fun `should default to today`() {
        val financeDateProvider = FinanceDateProvider()
        val invoiceIdFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val expectedDate = LocalDate.now().format(invoiceIdFormatter)

        val actualDate = financeDateProvider.currentDate()

        assertThat(actualDate).isEqualTo(expectedDate)
    }

    @Test
    fun `should obey the date the LocalDate parameter if given`() {
        val financeDateProvider = FinanceDateProvider(LocalDate.of(2025, 7, 14))
        val expectedDate = "20250714"

        val actualDate = financeDateProvider.currentDate()

        assertThat(actualDate).isEqualTo(expectedDate)
    }

    @Test
    fun `should return correct previous month`() {
        val financeDateProvider = FinanceDateProvider(LocalDate.of(2025, 7, 14))
        val expectedDate = "06.2025"

        val actualDate = financeDateProvider.previousMonth()

        assertThat(actualDate).isEqualTo(expectedDate)

        val anotherFinanceDateProvider = FinanceDateProvider(LocalDate.of(2025, 1, 14))
        val anotherExpectedDate = "12.2024"

        val anotherActualDate = anotherFinanceDateProvider.previousMonth()

        assertThat(anotherActualDate).isEqualTo(anotherExpectedDate)
    }

    @Test
    fun `should return date with correct abbreviated format`() {
        val financeDateProvider = FinanceDateProvider(LocalDate.of(2025, 7, 14))
        val expectedDate = "250714"

        val actualDate = financeDateProvider.currentDateWithAbbreviatedYear()

        assertThat(actualDate).isEqualTo(expectedDate)
    }

    @Test
    fun `should return correct last date of previous month`() {
        val financeDateProvider = FinanceDateProvider(LocalDate.of(2025, 7, 14))
        val expectedDate = "250630"

        val actualDate = financeDateProvider.previousMonthLastDate()

        assertThat(actualDate).isEqualTo(expectedDate)

        val anotherFinanceDateProvider = FinanceDateProvider(LocalDate.of(2024, 3, 7))
        val anotherExpectedDate = "240229"

        val anotherActualDate = anotherFinanceDateProvider.previousMonthLastDate()

        assertThat(anotherActualDate).isEqualTo(anotherExpectedDate)
    }

    @Test
    fun `should return correct previous month in YYMM format`() {
        val financeDateProvider = FinanceDateProvider(LocalDate.of(2025, 7, 14))
        val expectedDate = "2506"

        val actualDate = financeDateProvider.previousMonthYYMM()

        assertThat(actualDate).isEqualTo(expectedDate)
    }
}
