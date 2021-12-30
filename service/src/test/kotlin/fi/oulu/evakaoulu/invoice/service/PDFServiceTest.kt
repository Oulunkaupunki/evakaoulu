// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.oulu.evakaoulu.invoice.service

import fi.espoo.evaka.invoicing.domain.DecisionIncome
import fi.espoo.evaka.invoicing.domain.FeeAlteration
import fi.espoo.evaka.invoicing.domain.FeeAlterationWithEffect
import fi.espoo.evaka.invoicing.domain.FeeDecisionChildDetailed
import fi.espoo.evaka.invoicing.domain.FeeDecisionDetailed
import fi.espoo.evaka.invoicing.domain.FeeDecisionStatus
import fi.espoo.evaka.invoicing.domain.FeeDecisionThresholds
import fi.espoo.evaka.invoicing.domain.FeeDecisionType
import fi.espoo.evaka.invoicing.domain.IncomeEffect
import fi.espoo.evaka.invoicing.domain.PersonData
import fi.espoo.evaka.invoicing.domain.UnitData
import fi.espoo.evaka.invoicing.domain.VoucherValueDecisionDetailed
import fi.espoo.evaka.invoicing.domain.VoucherValueDecisionPlacementDetailed
import fi.espoo.evaka.invoicing.domain.VoucherValueDecisionServiceNeed
import fi.espoo.evaka.invoicing.domain.VoucherValueDecisionStatus
import fi.espoo.evaka.invoicing.domain.VoucherValueDecisionType
import fi.espoo.evaka.invoicing.service.DocumentLang
import fi.espoo.evaka.invoicing.service.FeeDecisionPdfData
import fi.espoo.evaka.invoicing.service.PDFService
import fi.espoo.evaka.invoicing.service.Page
import fi.espoo.evaka.invoicing.service.Template
import fi.espoo.evaka.invoicing.service.VoucherValueDecisionPdfData
import fi.espoo.evaka.placement.PlacementType
import fi.espoo.evaka.shared.AreaId
import fi.espoo.evaka.shared.DaycareId
import fi.espoo.evaka.shared.FeeDecisionId
import fi.espoo.evaka.shared.VoucherValueDecisionId
import fi.espoo.evaka.shared.domain.DateRange
import fi.oulu.evakaoulu.AbstractIntegrationTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.springframework.beans.factory.annotation.Autowired
import org.thymeleaf.context.Context
import java.io.FileOutputStream
import java.math.BigDecimal
import java.nio.file.Paths
import java.time.Instant
import java.time.LocalDate
import java.util.UUID

private val reportsPath: String = "${Paths.get("build").toAbsolutePath()}/reports"

internal class PDFServiceTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var pdfService: PDFService

    @Test
    fun render() {
        pdfService.render(Page(Template("test"), Context()))
    }

    @Test
    fun generateFeeDecisionPdf() {
        val decision = validFeeDecision()

        val bytes = pdfService.generateFeeDecisionPdf(FeeDecisionPdfData(decision, "fi"))

        val filepath = "$reportsPath/PDFServiceTest-fee-decision.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun generateFeeDecisionPdfWithIncome() {
        val decision = validFeeDecision().copy(headOfFamilyIncome = testDecisionIncome)

        val bytes = pdfService.generateFeeDecisionPdf(FeeDecisionPdfData(decision, "fi"))

        val filepath = "$reportsPath/PDFServiceTest-fee-decision-head-of-family-income.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @ParameterizedTest
    @EnumSource(FeeDecisionType::class)
    fun generateFeeDecisionPdfType(decisionType: FeeDecisionType) {
        val decision = validFeeDecision().copy(decisionType = decisionType)

        val bytes = pdfService.generateFeeDecisionPdf(FeeDecisionPdfData(decision, "fi"))

        val filepath = "$reportsPath/PDFServiceTest-fee-decision-type-$decisionType.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun generateFeeDecisionPdfPartner() {
        val decision = validFeeDecision().copy(
            partner = PersonData.Detailed(
                UUID.randomUUID(), LocalDate.of(1980, 6, 14), null,
                "Mikko", "Meikäläinen",
                "140680-9239", "", "", "",
                "", null, "", null, restrictedDetailsEnabled = false
            ),
            isElementaryFamily = true
        )

        val bytes = pdfService.generateFeeDecisionPdf(FeeDecisionPdfData(decision, "fi"))

        val filepath = "$reportsPath/PDFServiceTest-fee-decision-partner.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun generateFeeDecisionPdfValidTo() {
        val validFrom = LocalDate.now()
        val validTo = validFrom.plusYears(1)
        val decision = validFeeDecision().copy(validDuring = DateRange(validFrom, validTo))

        val bytes = pdfService.generateFeeDecisionPdf(FeeDecisionPdfData(decision, "fi"))

        val filepath = "$reportsPath/PDFServiceTest-fee-decision-valid-to.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun generateFeeDecisionPdfEmptyAddress() {
        val decision = validFeeDecision().copy(
            headOfFamily = PersonData.Detailed(
                UUID.randomUUID(), LocalDate.of(1982, 3, 31), null,
                "Maija", "Meikäläinen",
                "310382-956D", "", "", "",
                "", null, "", null, restrictedDetailsEnabled = false
            )
        )

        val bytes = pdfService.generateFeeDecisionPdf(FeeDecisionPdfData(decision, "fi"))

        val filepath = "$reportsPath/PDFServiceTest-fee-decision-empty-address.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun generateVoucherValueDecisionPdf() {
        val bytes = pdfService.generateVoucherValueDecisionPdf(validVoucherValueDecisionPdfData())

        val filepath = "$reportsPath/PDFServiceTest-voucher-value-decision.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun generateVoucherValueDecisionPdfWithIncome() {
        val pdfData = validVoucherValueDecisionPdfData()
        val bytes = pdfService.generateVoucherValueDecisionPdf(pdfData.copy(decision = pdfData.decision.copy(headOfFamilyIncome = testDecisionIncome)))

        val filepath = "$reportsPath/PDFServiceTest-voucher-value-decision-head-of-family-income.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun generateVoucherValueDecisionPdfPartner() {
        val original = validVoucherValueDecisionPdfData()
        val modified = original.copy(
            original.decision.copy(
                partner = PersonData.Detailed(
                    UUID.randomUUID(), LocalDate.of(1980, 6, 14), null,
                    "Mikko", "Meikäläinen",
                    "140680-9239", "", "", "",
                    "", null, "", null, restrictedDetailsEnabled = false
                ),
                isElementaryFamily = true
            )
        )

        val bytes = pdfService.generateVoucherValueDecisionPdf(modified)

        val filepath = "$reportsPath/PDFServiceTest-voucher-value-decision-partner.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun generateVoucherValueDecisionPdfValidTo() {
        val validTo = LocalDate.now().plusYears(1)
        val bytes = pdfService.generateVoucherValueDecisionPdf(validVoucherValueDecisionPdfData(validTo))

        val filepath = "$reportsPath/PDFServiceTest-voucher-value-decision-valid-to.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun generateVoucherValueDecisionPdfEmptyAddress() {
        val bytes = pdfService.generateVoucherValueDecisionPdf(
            validVoucherValueDecisionPdfData(
                headOfFamily = PersonData.Detailed(
                    UUID.randomUUID(), LocalDate.of(1982, 3, 31), null,
                    "Maija", "Meikäläinen",
                    "310382-956D", "", "", "",
                    "", null, "", null, restrictedDetailsEnabled = false
                )
            )
        )

        val filepath = "$reportsPath/PDFServiceTest-voucher-value-decision-empty-address.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun generateReliefAcceptedVoucherValueDecisionPdfValidTo() {
        val validTo = LocalDate.now().plusYears(1)
        val bytes = pdfService.generateVoucherValueDecisionPdf(
            validVoucherValueDecisionPdfData(
                validTo,
                voucherValueDecisionType = VoucherValueDecisionType.RELIEF_ACCEPTED,
                feeAlterations = listOf(
                    FeeAlterationWithEffect(FeeAlteration.Type.RELIEF, 50, false, -10800)
                )
            )
        )

        val filepath = "$reportsPath/PDFServiceTest-relief-accepted-voucher-value-decision-valid-to.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun generateReliefPartlyAcceptedVoucherValueDecisionPdfValidTo() {
        val validTo = LocalDate.now().plusYears(1)
        val bytes = pdfService.generateVoucherValueDecisionPdf(
            validVoucherValueDecisionPdfData(
                validTo,
                voucherValueDecisionType = VoucherValueDecisionType.RELIEF_PARTLY_ACCEPTED,
                feeAlterations = listOf(
                    FeeAlterationWithEffect(FeeAlteration.Type.RELIEF, 50, false, -100)
                )
            )
        )

        val filepath = "$reportsPath/PDFServiceTest-relief-partly-accepted-voucher-value-decision-valid-to.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun generateReliefRejectedVoucherValueDecisionPdfValidTo() {
        val validTo = LocalDate.now().plusYears(1)
        val bytes = pdfService.generateVoucherValueDecisionPdf(validVoucherValueDecisionPdfData(validTo, voucherValueDecisionType = VoucherValueDecisionType.RELIEF_REJECTED))

        val filepath = "$reportsPath/PDFServiceTest-relief-rejected-voucher-value-decision-valid-to.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

}

private val testDecisionIncome = DecisionIncome(
    effect = IncomeEffect.INCOME,
    data = mapOf("MAIN_INCOME" to 314100),
    totalIncome = 314100,
    totalExpenses = 0,
    total = 314100,
    validFrom = LocalDate.of(2000, 1, 1),
    validTo = null
)

private fun validFeeDecision() = FeeDecisionDetailed(
    FeeDecisionId(UUID.randomUUID()),
    children = listOf(
        FeeDecisionChildDetailed(
            child = PersonData.Detailed(
                UUID.randomUUID(), LocalDate.of(2018, 1, 1), null,
                "Matti", "Meikäläinen",
                null, "", "", "",
                "", null, "", null, restrictedDetailsEnabled = false
            ),
            placementType = PlacementType.DAYCARE,
            placementUnit = UnitData.Detailed(
                DaycareId(UUID.randomUUID()),
                name = "Yksikkö 1",
                areaId = AreaId(UUID.randomUUID()),
                areaName = "Alue 1",
                language = "fi"
            ),
            serviceNeedFeeCoefficient = BigDecimal.ONE,
            serviceNeedDescriptionFi = "Palveluntarve 1",
            serviceNeedDescriptionSv = "Palveluntarve 1 (sv)",
            serviceNeedMissing = false,
            baseFee = 1,
            siblingDiscount = 1,
            fee = 1,
            feeAlterations = listOf(
                FeeAlterationWithEffect(FeeAlteration.Type.RELIEF, 50, false, -10800),
            ),
            finalFee = 1
        )
    ),
    validDuring = DateRange(LocalDate.now(), null),
    FeeDecisionStatus.WAITING_FOR_SENDING,
    decisionNumber = null,
    FeeDecisionType.NORMAL,
    headOfFamily = PersonData.Detailed(
        UUID.randomUUID(), LocalDate.of(1982, 3, 31), null,
        "Maija", "Meikäläinen",
        "310382-956D", "Meikäläisenkuja 6 B 7", "33730", "TAMPERE",
        "", null, "", null, restrictedDetailsEnabled = false
    ),
    partner = null,
    headOfFamilyIncome = null,
    partnerIncome = null,
    familySize = 1,
    FeeDecisionThresholds(
        minIncomeThreshold = 1,
        maxIncomeThreshold = 2,
        incomeMultiplier = BigDecimal.ONE,
        maxFee = 1,
        minFee = 1,
    ),
    documentKey = null,
    approvedBy = PersonData.WithName(UUID.randomUUID(), "Markus", "Maksusihteeri"),
    approvedAt = Instant.now(),
    sentAt = null,
    financeDecisionHandlerFirstName = null,
    financeDecisionHandlerLastName = null
)

private fun validVoucherValueDecisionPdfData(
    validTo: LocalDate? = null,
    headOfFamily: PersonData.Detailed = PersonData.Detailed(
        UUID.randomUUID(), LocalDate.of(1982, 3, 31), null,
        "Maija", "Meikäläinen",
        "310382-956D", "Meikäläisenkuja 6 B 7", "33730", "TAMPERE",
        "", null, "", null, restrictedDetailsEnabled = false
    ),
    voucherValueDecisionType: VoucherValueDecisionType = VoucherValueDecisionType.NORMAL,
    feeAlterations: List<FeeAlterationWithEffect> = emptyList()
): VoucherValueDecisionPdfData {
    return VoucherValueDecisionPdfData(
        VoucherValueDecisionDetailed(
            VoucherValueDecisionId(UUID.randomUUID()),
            LocalDate.now(),
            validTo,
            VoucherValueDecisionStatus.WAITING_FOR_SENDING,
            decisionNumber = null,
            decisionType = voucherValueDecisionType,
            headOfFamily = headOfFamily,
            partner = null,
            headOfFamilyIncome = null,
            partnerIncome = null,
            familySize = 1,
            FeeDecisionThresholds(
                minIncomeThreshold = 1,
                maxIncomeThreshold = 2,
                incomeMultiplier = BigDecimal.ONE,
                maxFee = 1,
                minFee = 1,
            ),
            PersonData.Detailed(
                UUID.randomUUID(), LocalDate.of(2018, 1, 1), null,
                "Matti", "Meikäläinen",
                null, "", "", "",
                "", null, "", null, restrictedDetailsEnabled = false
            ),
            VoucherValueDecisionPlacementDetailed(
                UnitData.Detailed(
                    DaycareId(UUID.randomUUID()),
                    name = "Vuoreksen kerho",
                    areaId = AreaId(UUID.randomUUID()),
                    areaName = "Etelä",
                    language = "fi"
                ),
                type = PlacementType.DAYCARE
            ),
            VoucherValueDecisionServiceNeed(
                feeCoefficient = BigDecimal.ONE,
                voucherValueCoefficient = BigDecimal.ONE,
                feeDescriptionFi = "eka",
                feeDescriptionSv = "toka",
                voucherValueDescriptionFi = "kolmas",
                voucherValueDescriptionSv = "neljäs"
            ),
            baseCoPayment = 1,
            siblingDiscount = 1,
            coPayment = 1,
            feeAlterations,
            finalCoPayment = 1,
            baseValue = 1,
            childAge = 1,
            ageCoefficient = BigDecimal.ONE,
            capacityFactor = BigDecimal.ONE,
            voucherValue = 1,
            documentKey = null,
            approvedBy = PersonData.WithName(UUID.randomUUID(), "Markus", "Maksusihteeri"),
            approvedAt = Instant.now(),
            sentAt = null,
            created = Instant.now(),
            financeDecisionHandlerFirstName = null,
            financeDecisionHandlerLastName = null
        ),
        DocumentLang.fi
    )
}
