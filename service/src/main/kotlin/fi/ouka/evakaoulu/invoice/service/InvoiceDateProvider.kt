package fi.ouka.evakaoulu.invoice.service

import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class InvoiceDateProvider {
    fun currentDate() : String {
        val invoiceIdFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        return LocalDate.now().format(invoiceIdFormatter)
    }

    fun previousMonth(): String {
        val previousMonth = LocalDate.now().minusMonths(1)
        val titleFormatter = DateTimeFormatter.ofPattern("MM.yyyy")
        return previousMonth.format(titleFormatter)
    }
}