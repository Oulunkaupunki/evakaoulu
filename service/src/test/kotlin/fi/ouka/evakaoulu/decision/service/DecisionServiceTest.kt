// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.decision.service

import fi.espoo.evaka.application.ServiceNeed
import fi.espoo.evaka.application.ServiceNeedOption
import fi.espoo.evaka.daycare.domain.ProviderType
import fi.espoo.evaka.daycare.service.DaycareManager
import fi.espoo.evaka.decision.Decision
import fi.espoo.evaka.decision.DecisionStatus
import fi.espoo.evaka.decision.DecisionType
import fi.espoo.evaka.decision.DecisionUnit
import fi.espoo.evaka.decision.createDecisionPdf
import fi.espoo.evaka.identity.ExternalIdentifier
import fi.espoo.evaka.pis.service.PersonDTO
import fi.espoo.evaka.setting.SettingType
import fi.espoo.evaka.shared.ApplicationId
import fi.espoo.evaka.shared.ChildId
import fi.espoo.evaka.shared.DaycareId
import fi.espoo.evaka.shared.DecisionId
import fi.espoo.evaka.shared.PersonId
import fi.espoo.evaka.shared.ServiceNeedOptionId
import fi.espoo.evaka.shared.config.PDFConfig
import fi.espoo.evaka.shared.message.IMessageProvider
import fi.espoo.evaka.shared.template.ITemplateProvider
import fi.espoo.voltti.pdfgen.PDFService
import fi.ouka.evakaoulu.message.config.MessageConfiguration
import fi.ouka.evakaoulu.template.config.TemplateConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import java.io.FileOutputStream
import java.nio.file.Paths
import java.time.LocalDate
import java.util.UUID

private val settings = mapOf(
    SettingType.DECISION_MAKER_NAME to "Paula Palvelupäällikkö",
    SettingType.DECISION_MAKER_TITLE to "Asiakaspalvelupäällikkö"
)

@Tag("PDFGenerationTest")
class DecisionServiceTest {
    private lateinit var messageProvider: IMessageProvider
    private lateinit var templateProvider: ITemplateProvider
    private lateinit var pdfService: PDFService

    @BeforeEach
    fun setup() {
        messageProvider = MessageConfiguration().messageProvider()
        templateProvider = TemplateConfiguration().templateProvider()
        pdfService = PDFService(PDFConfig.templateEngine())
    }

    @ParameterizedTest
    @EnumSource(
        value = DecisionType::class,
        names = [],
        mode = EnumSource.Mode.EXCLUDE
    )
    fun createDecisionPdf(decisionType: DecisionType) {
        val bytes = createDecisionPdf(
            messageProvider,
            templateProvider,
            pdfService,
            settings,
            validDecision(decisionType, validDecisionUnit(ProviderType.MUNICIPAL)),
            guardian = validGuardian(),
            child = validChild(),
            isTransferApplication = false,
            serviceNeed = when (decisionType) {
                DecisionType.CLUB -> null
                DecisionType.PRESCHOOL -> null
                DecisionType.PREPARATORY_EDUCATION -> null
                else -> ServiceNeed(
                    startTime = "08:00",
                    endTime = "16:00",
                    shiftCare = false,
                    partTime = false,
                    ServiceNeedOption(
                        ServiceNeedOptionId(UUID.randomUUID()),
                        "Palveluntarve 1",
                        "Palveluntarve 1",
                        "Palveluntarve 1"
                    )
                )
            },
            lang = "fi",
            DaycareManager("Päivi Päiväkodinjohtaja", "paivi.paivakodinjohtaja@example.com", "0451231234")
        )

        val filepath = "${Paths.get("build").toAbsolutePath()}/reports/DecisionServiceTest-$decisionType.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun createDecisionPdfWithoutSettings() {
        val bytes = createDecisionPdf(
            messageProvider,
            templateProvider,
            pdfService,
            mapOf(),
            validDecision(DecisionType.DAYCARE, validDecisionUnit(ProviderType.MUNICIPAL)),
            guardian = validGuardian(),
            child = validChild(),
            isTransferApplication = false,
            serviceNeed = ServiceNeed(
                startTime = "08:00",
                endTime = "16:00",
                shiftCare = false,
                partTime = false,
                ServiceNeedOption(
                    ServiceNeedOptionId(UUID.randomUUID()), "Palveluntarve 1", "Palveluntarve 1", "Palveluntarve 1"
                )
            ),
            lang = "fi",
            DaycareManager("Päivi Päiväkodinjohtaja", "paivi.paivakodinjohtaja@example.com", "0451231234")
        )

        val filepath = "${Paths.get("build").toAbsolutePath()}/reports/DecisionServiceTest-DAYCARE-without-settings.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun createDaycareTransferDecisionPdf() {
        val bytes = createDecisionPdf(
            messageProvider,
            templateProvider,
            pdfService,
            settings,
            validDecision(DecisionType.DAYCARE, validDecisionUnit(ProviderType.MUNICIPAL)),
            guardian = validGuardian(),
            child = validChild(),
            isTransferApplication = true,
            serviceNeed = ServiceNeed(
                startTime = "08:00",
                endTime = "16:00",
                shiftCare = false,
                partTime = false,
                ServiceNeedOption(
                    ServiceNeedOptionId(UUID.randomUUID()),
                    "Palveluntarve 1",
                    "Palveluntarve 1",
                    "Palveluntarve 1"
                )
            ),
            lang = "fi",
            DaycareManager("Päivi Päiväkodinjohtaja", "paivi.paivakodinjohtaja@example.com", "0451231234")
        )

        val filepath = "${Paths.get("build").toAbsolutePath()}/reports/DecisionServiceTest-DAYCARE-transfer.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun createDaycareVoucherDecisionPdf() {
        val bytes = createDecisionPdf(
            messageProvider,
            templateProvider,
            pdfService,
            settings,
            validDecision(DecisionType.DAYCARE, validDecisionUnit(ProviderType.PRIVATE_SERVICE_VOUCHER)),
            guardian = validGuardian(),
            child = validChild(),
            isTransferApplication = false,
            serviceNeed = ServiceNeed(
                startTime = "08:00",
                endTime = "16:00",
                shiftCare = false,
                partTime = false,
                ServiceNeedOption(
                    ServiceNeedOptionId(UUID.randomUUID()),
                    "Palveluntarve 1",
                    "Palveluntarve 1",
                    "Palveluntarve 1"
                )
            ),
            lang = "fi",
            DaycareManager("Päivi Päiväkodinjohtaja", "paivi.paivakodinjohtaja@example.com", "0451231234")
        )

        val filepath = "${Paths.get("build").toAbsolutePath()}/reports/DecisionServiceTest-DAYCARE-voucher.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun createRestrictedDetailsEnabledPdf() {
        val bytes = createDecisionPdf(
            messageProvider,
            templateProvider,
            pdfService,
            settings,
            validDecision(DecisionType.DAYCARE, validDecisionUnit(ProviderType.MUNICIPAL)),
            guardian = validGuardian(true),
            child = validChild(true),
            isTransferApplication = false,
            serviceNeed = ServiceNeed(
                startTime = "08:00",
                endTime = "16:00",
                shiftCare = false,
                partTime = false,
                ServiceNeedOption(
                    ServiceNeedOptionId(UUID.randomUUID()),
                    "Palveluntarve 1",
                    "Palveluntarve 1",
                    "Palveluntarve 1"
                )
            ),
            lang = "fi",
            DaycareManager("Päivi Päiväkodinjohtaja", "paivi.paivakodinjohtaja@example.com", "0451231234")
        )

        val filepath =
            "${Paths.get("build").toAbsolutePath()}/reports/DecisionServiceTest-DAYCARE-restricted-details-enabled.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

}

private fun validDecision(type: DecisionType, decisionUnit: DecisionUnit) = Decision(
    DecisionId(UUID.randomUUID()),
    createdBy = "Päivi Päiväkodinjohtaja",
    type,
    startDate = LocalDate.now(),
    endDate = LocalDate.now().plusMonths(3),
    decisionUnit,
    applicationId = ApplicationId(UUID.randomUUID()),
    childId = ChildId(UUID.randomUUID()),
    childName = "Matti",
    documentKey = null,
    otherGuardianDocumentKey = null,
    decisionNumber = 12345,
    sentDate = LocalDate.now(),
    DecisionStatus.ACCEPTED,
    requestedStartDate = null,
    resolved = null
)

private fun validDecisionUnit(providerType: ProviderType) = DecisionUnit(
    DaycareId(UUID.randomUUID()),
    name = "Vuoreksen kerho",
    daycareDecisionName = "Vuoreksen kerho",
    preschoolDecisionName = "Vuoreksen kerho",
    manager = null,
    streetAddress = "Anunpolku 15",
    postalCode = "90100",
    postOffice = "Oulu",
    phone = "+35850 1234564",
    decisionHandler = "Ainolan kerho",
    decisionHandlerAddress = "Toritie 2, 90100 Oulu",
    providerType
)

private fun validGuardian(restrictedDetailsEnabled: Boolean = false) = PersonDTO(
    PersonId(UUID.randomUUID()),
    ExternalIdentifier.SSN.getInstance("070682-924A"),
    ssnAddingDisabled = false,
    firstName = "Oili",
    lastName = "Oululainen",
    preferredName = "Oili",
    email = null,
    phone = "",
    backupPhone = "",
    language = null,
    dateOfBirth = LocalDate.of(1982, 6, 7),
    streetAddress = "Oulunkatu 123",
    postalCode = "90100",
    postOffice = "Oulu",
    residenceCode = "",
    restrictedDetailsEnabled = restrictedDetailsEnabled
)

private fun validChild(restrictedDetailsEnabled: Boolean = false) = PersonDTO(
    PersonId(UUID.randomUUID()),
    ExternalIdentifier.SSN.getInstance("010115A9532"),
    ssnAddingDisabled = false,
    firstName = "Matti",
    lastName = "Meikäläinen",
    preferredName = "Matti",
    email = null,
    phone = "",
    backupPhone = "",
    language = null,
    dateOfBirth = LocalDate.of(2015, 1, 1),
    streetAddress = "Kokinpellonraitti 3",
    postalCode = "33870",
    postOffice = "Tampere",
    residenceCode = "",
    restrictedDetailsEnabled = restrictedDetailsEnabled
)
