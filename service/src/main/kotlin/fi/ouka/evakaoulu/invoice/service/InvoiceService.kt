package fi.ouka.evakaoulu.invoice.service

import fi.espoo.evaka.invoicing.data.*
import fi.espoo.evaka.invoicing.domain.Invoice
import fi.espoo.evaka.invoicing.domain.InvoiceStatus
import fi.espoo.evaka.invoicing.integration.InvoiceIntegrationClient
import fi.espoo.evaka.invoicing.service.*
import fi.espoo.evaka.shared.DaycareId
import fi.espoo.evaka.shared.InvoiceId
import fi.espoo.evaka.shared.InvoiceRowId
import fi.espoo.evaka.shared.auth.AuthenticatedUser
import fi.espoo.evaka.shared.db.Database
import fi.espoo.evaka.shared.domain.BadRequest
import fi.espoo.evaka.shared.domain.FiniteDateRange
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*

data class InvoiceDaycare(val id: DaycareId, val name: String, val costCenter: String?)

data class InvoiceCodes(
    val products: List<ProductWithName>,
    val units: List<InvoiceDaycare>
)

@Component("InvoiceService")
@Primary
class InvoiceService(
    private val integrationClient: InvoiceIntegrationClient,
    private val productProvider: InvoiceProductProvider
) {

    fun sendInvoices(tx: Database.Transaction, user: AuthenticatedUser, invoiceIds: List<InvoiceId>, invoiceDate: LocalDate?, dueDate: LocalDate?) {
        val invoices = tx.getInvoicesByIds(invoiceIds)
        if (invoices.isEmpty()) return

        val notDrafts = invoices.filterNot { it.status == InvoiceStatus.DRAFT }
        if (notDrafts.isNotEmpty()) {
            throw BadRequest("Some invoices were not drafts")
        }

        val maxInvoiceNumber = getMAxInvoiceId(tx)
        val updatedInvoices = invoices.mapIndexed { index, invoice ->
            invoice.copy(
                number = maxInvoiceNumber + index,
                invoiceDate = invoiceDate ?: invoice.invoiceDate,
                dueDate = dueDate ?: invoice.dueDate
            )
        }

        val sendResult = integrationClient.send(updatedInvoices)
        tx.setDraftsSent(sendResult.succeeded, user.evakaUserId)
        tx.setDraftsWaitingForManualSending(sendResult.manuallySent)
        tx.saveCostCenterFields(sendResult.succeeded.map { it.id } + sendResult.manuallySent.map { it.id })
        tx.markInvoicedCorrectionsAsComplete()
    }

    fun getMAxInvoiceId(tx: Database.Transaction) : Long {
        return OneMillionOrMore(tx.getMaxInvoiceNumber())
    }

    fun OneMillionOrMore(it: Long): Long {
        return if (it >= 1000000) {it + 1 } else {1000000}
    }

    fun updateInvoice(tx: Database.Transaction, uuid: InvoiceId, invoice: Invoice) {
        val original = tx.getInvoice(uuid)
            ?: throw BadRequest("No original found for invoice with given ID ($uuid)")

        val updated = when (original.status) {
            InvoiceStatus.DRAFT -> original.copy(
                rows = invoice.rows.map { row -> if (row.id == null) row.copy(id = InvoiceRowId(UUID.randomUUID())) else row }
            )
            else -> throw BadRequest("Only draft invoices can be updated")
        }

        tx.upsertInvoices(listOf(updated))
    }

    fun getInvoiceIds(tx: Database.Read, from: LocalDate, to: LocalDate, areas: List<String>): List<InvoiceId> {
        return tx.getInvoiceIdsByDates(FiniteDateRange(from, to), areas)
    }

    fun getInvoiceCodes(tx: Database.Read): InvoiceCodes {
        val units = tx.createQuery(
            """
        SELECT daycare.id, daycare.name, cost_center
        FROM daycare
        ORDER BY name
            """.trimIndent()
        ).mapTo(InvoiceDaycare::class.java).list()

        return InvoiceCodes(
            productProvider.products,
            units
        )
    }
}