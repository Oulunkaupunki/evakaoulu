// SPDX-FileCopyrightText: 2023-2025 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.util

import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Component
class FinanceDateProvider(
    val currentDate: LocalDate = LocalDate.now(),
) {
    fun currentDate(): String {
        val invoiceIdFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        return currentDate.format(invoiceIdFormatter)
    }

    fun previousMonth(): String {
        val previousMonth = currentDate.minusMonths(1)
        val titleFormatter = DateTimeFormatter.ofPattern("MM.yyyy")
        return previousMonth.format(titleFormatter)
    }

    fun currentDateWithAbbreviatedYear(): String {
        val invoiceIdFormatter = DateTimeFormatter.ofPattern("yyMMdd")
        return currentDate.format(invoiceIdFormatter)
    }

    fun previousMonthLastDate(): String {
        val previousMonthlastDate = YearMonth.from(currentDate).minusMonths(1).atEndOfMonth()
        val invoiceIdFormatter = DateTimeFormatter.ofPattern("yyMMdd")
        return previousMonthlastDate.format(invoiceIdFormatter)
    }

    fun previousMonthYYMM(): String {
        val previousMonth = currentDate.minusMonths(1)
        val titleFormatter = DateTimeFormatter.ofPattern("yyMM")
        return previousMonth.format(titleFormatter)
    }
}
