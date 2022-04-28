package fi.ouka.evakaoulu.invoice.service

import fi.espoo.evaka.invoicing.domain.InvoiceDetailed
import fi.espoo.evaka.invoicing.domain.InvoiceRowDetailed
import fi.espoo.evaka.invoicing.domain.InvoiceStatus
import fi.espoo.evaka.invoicing.domain.PersonDetailed
import fi.espoo.evaka.invoicing.service.ProductKey
import fi.espoo.evaka.shared.*
import java.time.LocalDate
import java.util.*

fun validInvoice(): InvoiceDetailed {
    val headOfFamily = validPerson()
    val invoiceRow1 = InvoiceRowDetailed(
            InvoiceRowId(UUID.randomUUID()), PersonDetailed(
            PersonId(UUID.randomUUID()), LocalDate.of(2018, 1, 1), null,
            "Matti", "Meikäläinen",
            null, "", "", "",
            "", null, "", null, restrictedDetailsEnabled = false
    ), 1, 24300,
            LocalDate.of(2021, 1, 1),
            LocalDate.of(2021, 1, 31),
            ProductKey("DAYCARE"), DaycareId(UUID.randomUUID()), "131885", null, null, "kuvaus1",
            correctionId = null,
            note = null
    )
    val invoiceRow2 = InvoiceRowDetailed(
            InvoiceRowId(UUID.randomUUID()), PersonDetailed(
            PersonId(UUID.randomUUID()), LocalDate.of(2015, 11, 26), null,
            "Maiju", "Meikäläinen",
            null, "", "", "",
            "", null, "", null, restrictedDetailsEnabled = false
    ), 1, 48200,
            LocalDate.of(2021, 1, 1),
            LocalDate.of(2021, 1, 31),
            ProductKey("PRESCHOOL_WITH_DAYCARE"), DaycareId(UUID.randomUUID()), "284823", null, null, "kuvaus2",
            correctionId = null,
            note = null
    )
    return InvoiceDetailed(
            (InvoiceId(UUID.randomUUID())),
            InvoiceStatus.WAITING_FOR_SENDING,
            LocalDate.now(),
            LocalDate.now(),
            LocalDate.of(2021, 3, 6),
            LocalDate.of(2021, 2, 4),
            null,
            AreaId(UUID.randomUUID()),
            headOfFamily,
            null,
            listOf(invoiceRow1, invoiceRow2),
            null,
            null,
            null
    )
}

fun validPerson() = PersonDetailed(
        PersonId(UUID.randomUUID()), LocalDate.of(1982, 3, 31), null,
        "Matti", "Meikäläinen",
        "310382-956D", "Meikäläisenkuja 6 B 7", "90100", "OULU",
        "", null, "", null, restrictedDetailsEnabled = false
)

fun personWithoutSSN() = PersonDetailed(
        PersonId(UUID.randomUUID()), LocalDate.of(1982, 3, 31), null,
        "Maija", "Meikäläinen",
        null, "Meikäläisenkuja 6 B 7", "90100", "OULU",
        "", null, "", null, restrictedDetailsEnabled = false
)

fun personWithRestrictedDetails() = PersonDetailed(
        PersonId(UUID.randomUUID()), LocalDate.of(1982, 3, 31), null,
        "Mysteeri", "Meikäläinen",
        "280691-943Z", "Todistajansuojeluohjelmankatu 9", "45600", "OULU",
        "", null, "", null, restrictedDetailsEnabled = true
)
