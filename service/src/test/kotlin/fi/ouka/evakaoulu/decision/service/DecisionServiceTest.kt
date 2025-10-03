// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.decision.service

import fi.espoo.evaka.application.ServiceNeed
import fi.espoo.evaka.application.ServiceNeedOption
import fi.espoo.evaka.assistanceneed.decision.AssistanceLevel
import fi.espoo.evaka.assistanceneed.decision.AssistanceNeedDecision
import fi.espoo.evaka.assistanceneed.decision.AssistanceNeedDecisionChild
import fi.espoo.evaka.assistanceneed.decision.AssistanceNeedDecisionEmployee
import fi.espoo.evaka.assistanceneed.decision.AssistanceNeedDecisionMaker
import fi.espoo.evaka.assistanceneed.decision.AssistanceNeedDecisionStatus
import fi.espoo.evaka.assistanceneed.decision.ServiceOptions
import fi.espoo.evaka.assistanceneed.decision.StructuralMotivationOptions
import fi.espoo.evaka.assistanceneed.decision.UnitInfo
import fi.espoo.evaka.assistanceneed.preschooldecision.AssistanceNeedPreschoolDecision
import fi.espoo.evaka.assistanceneed.preschooldecision.AssistanceNeedPreschoolDecisionChild
import fi.espoo.evaka.assistanceneed.preschooldecision.AssistanceNeedPreschoolDecisionForm
import fi.espoo.evaka.assistanceneed.preschooldecision.AssistanceNeedPreschoolDecisionGuardian
import fi.espoo.evaka.assistanceneed.preschooldecision.AssistanceNeedPreschoolDecisionType
import fi.espoo.evaka.daycare.UnitManager
import fi.espoo.evaka.daycare.domain.ProviderType
import fi.espoo.evaka.decision.Decision
import fi.espoo.evaka.decision.DecisionSendAddress
import fi.espoo.evaka.decision.DecisionStatus
import fi.espoo.evaka.decision.DecisionType
import fi.espoo.evaka.decision.DecisionUnit
import fi.espoo.evaka.decision.createDecisionPdf
import fi.espoo.evaka.identity.ExternalIdentifier
import fi.espoo.evaka.pdfgen.Page
import fi.espoo.evaka.pdfgen.PdfGenerator
import fi.espoo.evaka.pdfgen.Template
import fi.espoo.evaka.pis.service.PersonDTO
import fi.espoo.evaka.setting.SettingType
import fi.espoo.evaka.shared.ApplicationId
import fi.espoo.evaka.shared.AssistanceNeedDecisionId
import fi.espoo.evaka.shared.AssistanceNeedPreschoolDecisionGuardianId
import fi.espoo.evaka.shared.AssistanceNeedPreschoolDecisionId
import fi.espoo.evaka.shared.ChildId
import fi.espoo.evaka.shared.DaycareId
import fi.espoo.evaka.shared.DecisionId
import fi.espoo.evaka.shared.EmployeeId
import fi.espoo.evaka.shared.PersonId
import fi.espoo.evaka.shared.ServiceNeedOptionId
import fi.espoo.evaka.shared.config.PDFConfig
import fi.espoo.evaka.shared.domain.DateRange
import fi.espoo.evaka.shared.domain.OfficialLanguage
import fi.espoo.evaka.shared.template.ITemplateProvider
import fi.ouka.evakaoulu.template.config.TemplateConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.thymeleaf.context.Context
import java.io.FileOutputStream
import java.nio.file.Paths
import java.time.LocalDate
import java.util.Locale
import java.util.UUID

private val settings =
    mapOf(
        SettingType.DECISION_MAKER_NAME to "Paula Palvelupäällikkö",
        SettingType.DECISION_MAKER_TITLE to "Asiakaspalvelupäällikkö",
    )

@Tag("PDFGenerationTest")
class DecisionServiceTest {
    private lateinit var templateProvider: ITemplateProvider
    private lateinit var pdfService: PdfGenerator

    @BeforeEach
    fun setup() {
        templateProvider = TemplateConfiguration().templateProvider()
        pdfService = PdfGenerator(templateProvider, PDFConfig.templateEngine())
    }

    @ParameterizedTest
    @EnumSource(
        value = DecisionType::class,
        names = [],
        mode = EnumSource.Mode.EXCLUDE,
    )
    fun createDecisionPdf(decisionType: DecisionType) {
        val bytes =
            createDecisionPdf(
                templateProvider,
                pdfService,
                settings,
                validDecision(decisionType, validDecisionUnit(ProviderType.MUNICIPAL)),
                child = validChild(),
                isTransferApplication = false,
                serviceNeed =
                    when (decisionType) {
                        DecisionType.CLUB -> null
                        DecisionType.PRESCHOOL -> null
                        DecisionType.PREPARATORY_EDUCATION -> null
                        else ->
                            ServiceNeed(
                                startTime = "08:00",
                                endTime = "16:00",
                                shiftCare = false,
                                partTime = false,
                                ServiceNeedOption(
                                    ServiceNeedOptionId(UUID.randomUUID()),
                                    "Palveluntarve 1",
                                    "Palveluntarve 1",
                                    "Palveluntarve 1",
                                    null,
                                ),
                            )
                    },
                lang = OfficialLanguage.FI,
                unitManager = UnitManager("Päivi Päiväkodinjohtaja", "paivi.paivakodinjohtaja@example.com", "0451231234"),
                preschoolManager = UnitManager("Päivi Päiväkodinjohtaja", "paivi.paivakodinjohtaja@example.com", "0451231234"),
            )

        val filepath = "${Paths.get("build").toAbsolutePath()}/reports/DecisionServiceTest-$decisionType.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun decisionPdfCreationShouldSucceedWhenServiceNeedOptionIsNull() {
        // my kind of assertion
        assertDoesNotThrow {
            val bytes =
                createDecisionPdf(
                    templateProvider,
                    pdfService,
                    settings,
                    validDecision(DecisionType.PREPARATORY_EDUCATION, validDecisionUnit(ProviderType.MUNICIPAL)),
                    child = validChild(),
                    isTransferApplication = false,
                    serviceNeed =
                        ServiceNeed(
                            startTime = "08:00",
                            endTime = "16:00",
                            shiftCare = false,
                            partTime = false,
                            // this is null!!!
                            serviceNeedOption = null,
                        ),
                    lang = OfficialLanguage.FI,
                    unitManager = UnitManager("Päivi Päiväkodinjohtaja", "paivi.paivakodinjohtaja@example.com", "0451231234"),
                    preschoolManager = UnitManager("Päivi Päiväkodinjohtaja", "paivi.paivakodinjohtaja@example.com", "0451231234"),
                )

            val filepath = "${Paths.get("build").toAbsolutePath()}/reports/DecisionServiceTest-PREPARATORY_EDUCATION-NO-OPTIONS.pdf"
            FileOutputStream(filepath).use { it.write(bytes) }
        }
    }

    @Test
    fun createDecisionPdfWithoutSettings() {
        val bytes =
            createDecisionPdf(
                templateProvider,
                pdfService,
                mapOf(),
                validDecision(DecisionType.DAYCARE, validDecisionUnit(ProviderType.MUNICIPAL)),
                child = validChild(),
                isTransferApplication = false,
                serviceNeed =
                    ServiceNeed(
                        startTime = "08:00",
                        endTime = "16:00",
                        shiftCare = false,
                        partTime = false,
                        ServiceNeedOption(
                            ServiceNeedOptionId(UUID.randomUUID()),
                            "Palveluntarve 1",
                            "Palveluntarve 1",
                            "Palveluntarve 1",
                            null,
                        ),
                    ),
                lang = OfficialLanguage.FI,
                unitManager = UnitManager("Päivi Päiväkodinjohtaja", "paivi.paivakodinjohtaja@example.com", "0451231234"),
                preschoolManager = UnitManager("Päivi Päiväkodinjohtaja", "paivi.paivakodinjohtaja@example.com", "0451231234"),
            )

        val filepath = "${Paths.get("build").toAbsolutePath()}/reports/DecisionServiceTest-DAYCARE-without-settings.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun createDaycareTransferDecisionPdf() {
        val bytes =
            createDecisionPdf(
                templateProvider,
                pdfService,
                settings,
                validDecision(DecisionType.DAYCARE, validDecisionUnit(ProviderType.MUNICIPAL)),
                child = validChild(),
                isTransferApplication = true,
                serviceNeed =
                    ServiceNeed(
                        startTime = "08:00",
                        endTime = "16:00",
                        shiftCare = false,
                        partTime = false,
                        ServiceNeedOption(
                            ServiceNeedOptionId(UUID.randomUUID()),
                            "Palveluntarve 1",
                            "Palveluntarve 1",
                            "Palveluntarve 1",
                            null,
                        ),
                    ),
                lang = OfficialLanguage.FI,
                unitManager = UnitManager("Päivi Päiväkodinjohtaja", "paivi.paivakodinjohtaja@example.com", "0451231234"),
                preschoolManager = UnitManager("Päivi Päiväkodinjohtaja", "paivi.paivakodinjohtaja@example.com", "0451231234"),
            )

        val filepath = "${Paths.get("build").toAbsolutePath()}/reports/DecisionServiceTest-DAYCARE-transfer.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun createDaycareVoucherDecisionPdf() {
        val bytes =
            createDecisionPdf(
                templateProvider,
                pdfService,
                settings,
                validDecision(DecisionType.DAYCARE, validDecisionUnit(ProviderType.PRIVATE_SERVICE_VOUCHER)),
                child = validChild(),
                isTransferApplication = false,
                serviceNeed =
                    ServiceNeed(
                        startTime = "08:00",
                        endTime = "16:00",
                        shiftCare = false,
                        partTime = false,
                        ServiceNeedOption(
                            ServiceNeedOptionId(UUID.randomUUID()),
                            "Palveluntarve 1",
                            "Palveluntarve 1",
                            "Palveluntarve 1",
                            null,
                        ),
                    ),
                lang = OfficialLanguage.FI,
                unitManager = UnitManager("Päivi Päiväkodinjohtaja", "paivi.paivakodinjohtaja@example.com", "0451231234"),
                preschoolManager = UnitManager("Päivi Päiväkodinjohtaja", "paivi.paivakodinjohtaja@example.com", "0451231234"),
            )

        val filepath = "${Paths.get("build").toAbsolutePath()}/reports/DecisionServiceTest-DAYCARE-voucher.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun createRestrictedDetailsEnabledPdf() {
        val bytes =
            createDecisionPdf(
                templateProvider,
                pdfService,
                settings,
                validDecision(DecisionType.DAYCARE, validDecisionUnit(ProviderType.MUNICIPAL)),
                child = validChild(true),
                isTransferApplication = false,
                serviceNeed =
                    ServiceNeed(
                        startTime = "08:00",
                        endTime = "16:00",
                        shiftCare = false,
                        partTime = false,
                        ServiceNeedOption(
                            ServiceNeedOptionId(UUID.randomUUID()),
                            "Palveluntarve 1",
                            "Palveluntarve 1",
                            "Palveluntarve 1",
                            null,
                        ),
                    ),
                lang = OfficialLanguage.FI,
                unitManager = UnitManager("Päivi Päiväkodinjohtaja", "paivi.paivakodinjohtaja@example.com", "0451231234"),
                preschoolManager = UnitManager("Päivi Päiväkodinjohtaja", "paivi.paivakodinjohtaja@example.com", "0451231234"),
            )

        val filepath =
            "${Paths.get("build").toAbsolutePath()}/reports/DecisionServiceTest-DAYCARE-restricted-details-enabled.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun generateAssistanceNeedPdf() {
        val decision = validAssistanceNeedDecision

        val bytes = generateAssistanceNeedPdf(decision, pdfService, templateProvider)

        val filepath = "${Paths.get("build").toAbsolutePath()}/reports/DecisionServiceTest-assistance-need-decision.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }

    @Test
    fun generateAssistanceNeedPreschoolPdf() {
        val decision = validAssistanceNeedPreSchoolDecision

        val bytes = generateAssistanceNeedPreschoolPdf(decision, pdfService, templateProvider)

        val filepath = "${Paths.get("build").toAbsolutePath()}/reports/DecisionServiceTest-preschool-assistance-need-decision.pdf"
        FileOutputStream(filepath).use { it.write(bytes) }
    }
}

private fun validDecision(
    type: DecisionType,
    decisionUnit: DecisionUnit,
) = Decision(
    DecisionId(UUID.randomUUID()),
    createdBy = "Matti Käsittelijä",
    type,
    startDate = LocalDate.now(),
    endDate = LocalDate.now().plusMonths(3),
    decisionUnit,
    applicationId = ApplicationId(UUID.randomUUID()),
    childId = ChildId(UUID.randomUUID()),
    childName = "Matti",
    documentKey = null,
    decisionNumber = 12345,
    sentDate = LocalDate.now(),
    DecisionStatus.ACCEPTED,
    requestedStartDate = null,
    resolved = null,
    resolvedByName = null,
    documentContainsContactInfo = false,
    archivedAt = null,
)

private fun validDecisionUnit(providerType: ProviderType) =
    DecisionUnit(
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
        providerType,
    )

private fun validGuardian(restrictedDetailsEnabled: Boolean = false) =
    PersonDTO(
        PersonId(UUID.randomUUID()),
        duplicateOf = null,
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
        restrictedDetailsEnabled = restrictedDetailsEnabled,
        municipalityOfResidence = "Oulu",
    )

private fun validChild(restrictedDetailsEnabled: Boolean = false) =
    PersonDTO(
        PersonId(UUID.randomUUID()),
        duplicateOf = null,
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
        restrictedDetailsEnabled = restrictedDetailsEnabled,
        municipalityOfResidence = "Tampere",
    )

private val validAssistanceNeedDecision =
    AssistanceNeedDecision(
        id = AssistanceNeedDecisionId(UUID.randomUUID()),
        decisionNumber = 125632424,
        child =
            AssistanceNeedDecisionChild(
                id = ChildId(UUID.randomUUID()),
                name = "Matti Meikäläinen",
                dateOfBirth = LocalDate.of(2020, 1, 5),
            ),
        validityPeriod = DateRange(LocalDate.of(2022, 8, 2), LocalDate.of(2022, 12, 31)),
        status = AssistanceNeedDecisionStatus.ACCEPTED,
        language = OfficialLanguage.FI,
        decisionMade = LocalDate.of(2022, 7, 1),
        sentForDecision = LocalDate.of(2022, 5, 12),
        selectedUnit =
            UnitInfo(
                id = DaycareId(UUID.randomUUID()),
                name = "Amurin päiväkoti",
                streetAddress = "Amurinpolku 1",
                postalCode = "33100",
                postOffice = "Tampere",
            ),
        preparedBy1 =
            AssistanceNeedDecisionEmployee(
                EmployeeId(UUID.randomUUID()),
                "JOHTAJA",
                "JORMA PERTTILÄ",
                "0401234567",
            ),
        preparedBy2 = null,
        decisionMaker =
            AssistanceNeedDecisionMaker(
                employeeId = EmployeeId(UUID.randomUUID()),
                title = "Asiakaspalvelupäällikkö",
                name = "Paula Palvelupäällikkö",
            ),
        pedagogicalMotivation = null,
        structuralMotivationOptions =
            StructuralMotivationOptions(
                smallerGroup = false,
                specialGroup = false,
                smallGroup = false,
                groupAssistant = false,
                childAssistant = false,
                additionalStaff = false,
            ),
        structuralMotivationDescription = null,
        careMotivation = null,
        serviceOptions =
            ServiceOptions(
                consultationSpecialEd = false,
                partTimeSpecialEd = false,
                fullTimeSpecialEd = false,
                interpretationAndAssistanceServices = false,
                specialAides = false,
            ),
        servicesMotivation = null,
        expertResponsibilities = null,
        guardiansHeardOn = null,
        guardianInfo = emptySet(),
        viewOfGuardians = null,
        otherRepresentativeHeard = false,
        otherRepresentativeDetails = null,
        assistanceLevels = setOf(AssistanceLevel.ENHANCED_ASSISTANCE),
        motivationForDecision = null,
        hasDocument = false,
        annulmentReason = "",
        endDateNotKnown = false,
        processId = null,
    )

private val validAssistanceNeedPreSchoolDecision =
    AssistanceNeedPreschoolDecision(
        id = AssistanceNeedPreschoolDecisionId(UUID.randomUUID()),
        decisionNumber = 125632424,
        child =
            AssistanceNeedPreschoolDecisionChild(
                id = ChildId(UUID.randomUUID()),
                name = "Matti Meikäläinen",
                dateOfBirth = LocalDate.of(2020, 1, 5),
            ),
        status = AssistanceNeedDecisionStatus.ACCEPTED,
        decisionMade = LocalDate.of(2022, 7, 1),
        decisionMakerHasOpened = false,
        annulmentReason = "Tyhjä syy",
        hasDocument = false,
        sentForDecision = LocalDate.of(2022, 5, 12),
        unitName = "Amurin päiväkoti",
        unitStreetAddress = "Amurinpolku 1",
        unitPostalCode = "33100",
        unitPostOffice = "Tampere",
        preparer1Name = "JOHTAJA JORMA PERTTILÄ",
        preparer2Name = "Paula Palvelupäällikkö",
        decisionMakerName = "Pate Päättäjä",
        processId = null,
        form =
            AssistanceNeedPreschoolDecisionForm(
                language = OfficialLanguage.FI,
                type = AssistanceNeedPreschoolDecisionType.NEW,
                validFrom = LocalDate.now(),
                validTo = null,
                extendedCompulsoryEducation = true,
                extendedCompulsoryEducationInfo = "Jotai infoo",
                grantedAssistanceService = true,
                grantedInterpretationService = false,
                grantedAssistiveDevices = true,
                grantedServicesBasis = "Juttuja",
                selectedUnit = DaycareId(UUID.randomUUID()),
                primaryGroup = "Eskarilaiset",
                decisionBasis = "Hyvä syy",
                basisDocumentPedagogicalReport = true,
                basisDocumentPsychologistStatement = true,
                basisDocumentSocialReport = false,
                basisDocumentDoctorStatement = false,
                basisDocumentOtherOrMissing = false,
                basisDocumentOtherOrMissingInfo = "",
                basisDocumentsInfo = "Juttu homma",
                guardiansHeardOn = LocalDate.now(),
                guardianInfo =
                    setOf(
                        AssistanceNeedPreschoolDecisionGuardian(
                            AssistanceNeedPreschoolDecisionGuardianId(UUID.randomUUID()),
                            PersonId(UUID.randomUUID()),
                            name = "Matti Iskä Möttönen",
                            isHeard = true,
                            details = "",
                        ),
                    ),
                otherRepresentativeHeard = false,
                otherRepresentativeDetails = "",
                viewOfGuardians = "Olemme täysin samaa mieltä tuen tarpeesta",
                preparer1EmployeeId = EmployeeId(UUID.randomUUID()),
                preparer1Title = "Valmistelija 1",
                preparer1PhoneNumber = "358 40 1234567",
                preparer2EmployeeId = EmployeeId(UUID.randomUUID()),
                preparer2Title = "Valmistelija 2",
                preparer2PhoneNumber = "358 40 1234587",
                decisionMakerEmployeeId = EmployeeId(UUID.randomUUID()),
                decisionMakerTitle = "Päättäjä",
                basisDocumentDoctorStatementDate = LocalDate.of(2022, 7, 1),
                basisDocumentPedagogicalReportDate = LocalDate.of(2022, 7, 1),
                basisDocumentPsychologistStatementDate = LocalDate.of(2022, 7, 1),
                basisDocumentSocialReportDate = LocalDate.of(2022, 7, 1),
            ),
    )

private fun validAddress() = DecisionSendAddress("Kotikatu", "90100", "Oulu", "", "", "")

fun generateAssistanceNeedPdf(
    decision: AssistanceNeedDecision,
    pdfService: PdfGenerator,
    templateProvider: ITemplateProvider,
): ByteArray =
    pdfService.render(
        Page(
            Template(templateProvider.getAssistanceNeedDecisionPath()),
            Context().apply {
                locale = Locale.Builder().setLanguage(decision.language.name.lowercase()).build()
                setVariable("decision", decision)
                setVariable("sentDate", LocalDate.now())
            },
        ),
    )

fun generateAssistanceNeedPreschoolPdf(
    decision: AssistanceNeedPreschoolDecision,
    pdfService: PdfGenerator,
    templateProvider: ITemplateProvider,
): ByteArray =
    pdfService.render(
        Page(
            Template(templateProvider.getAssistanceNeedPreschoolDecisionPath()),
            Context().apply {
                setVariable("decision", decision)
                setVariable("sentDate", LocalDate.now())
            },
        ),
    )
