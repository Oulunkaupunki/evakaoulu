package fi.ouka.evakaoulu.util

import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class FinanceDateProvider {
    fun currentDate() : String {
        val invoiceIdFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        return LocalDate.now().format(invoiceIdFormatter)
    }

    fun previousMonth(): String {
        val previousMonth = LocalDate.now().minusMonths(1)
        val titleFormatter = DateTimeFormatter.ofPattern("MM.yyyy")
        return previousMonth.format(titleFormatter)
    }

    fun currentDateWithAbbreviatedYear(): String {
        val invoiceIdFormatter = DateTimeFormatter.ofPattern("yyMMdd")
        return LocalDate.now().format(invoiceIdFormatter)
    }

    fun previousMonthYYMM() : String {
        val previousMonth = LocalDate.now().minusMonths(1)
        val titleFormatter = DateTimeFormatter.ofPattern("yyMM")
        return previousMonth.format(titleFormatter)
    }

}