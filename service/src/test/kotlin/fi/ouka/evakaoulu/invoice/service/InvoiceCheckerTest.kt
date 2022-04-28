package fi.ouka.evakaoulu.invoice.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class InvoiceCheckerTest {
    @Test
    fun `should return true for Invoices with restricted head of family details`() {
        val invoice = validInvoice().copy(headOfFamily = personWithRestrictedDetails())
        val invoiceChecker = InvoiceChecker()

        assert(invoiceChecker.shouldSendManually(invoice) == true)
    }

    @Test
    fun `should return false for Invoices without restricted head of family details`() {
        val invoice = validInvoice()
        val invoiceChecker = InvoiceChecker()

        assert(invoiceChecker.shouldSendManually(invoice) == false)
    }
}

