// SPDX-FileCopyrightText: 2023-2025 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu

import fi.espoo.evaka.BucketEnv
import fi.espoo.evaka.ScheduledJobsEnv
import fi.espoo.evaka.application.ApplicationStatus
import fi.espoo.evaka.espoo.DefaultPasswordSpecification
import fi.espoo.evaka.holidayperiod.QuestionnaireType
import fi.espoo.evaka.invoicing.domain.PaymentIntegrationClient
import fi.espoo.evaka.invoicing.service.DefaultInvoiceGenerationLogic
import fi.espoo.evaka.logging.defaultAccessLoggingValve
import fi.espoo.evaka.mealintegration.DefaultMealTypeMapper
import fi.espoo.evaka.mealintegration.MealTypeMapper
import fi.espoo.evaka.shared.ArchiveProcessConfig
import fi.espoo.evaka.shared.ArchiveProcessType
import fi.espoo.evaka.shared.FeatureConfig
import fi.espoo.evaka.shared.async.AsyncJobRunner
import fi.espoo.evaka.shared.auth.PasswordConstraints
import fi.espoo.evaka.shared.auth.PasswordSpecification
import fi.espoo.evaka.shared.auth.UserRole
import fi.espoo.evaka.shared.security.actionrule.ActionRuleMapping
import fi.espoo.evaka.titania.TitaniaEmployeeIdConverter
import fi.ouka.evakaoulu.dw.DWExportClient
import fi.ouka.evakaoulu.dw.DWExportJob
import fi.ouka.evakaoulu.dw.FileDWExportClient
import fi.ouka.evakaoulu.invoice.service.SftpConnector
import fi.ouka.evakaoulu.invoice.service.SftpSender
import fi.ouka.evakaoulu.payment.service.OuluPaymentIntegrationClient
import fi.ouka.evakaoulu.payment.service.ProEPaymentGenerator
import fi.ouka.evakaoulu.security.EvakaOuluActionRuleMapping
import io.opentelemetry.api.trace.Tracer
import org.jdbi.v3.core.Jdbi
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3AsyncClient
import java.time.MonthDay

@Configuration
@Import(EvakaOuluAsyncJobRegistration::class)
class EVakaOuluConfig {
    @Bean
    fun featureConfig(): FeatureConfig =
        FeatureConfig(
            valueDecisionCapacityFactorEnabled = false,
            // (7*24) - 3 = 165
            citizenReservationThresholdHours = 165,
            dailyFeeDivisorOperationalDaysOverride = null,
            freeSickLeaveOnContractDays = false,
            alwaysUseDaycareFinanceDecisionHandler = false,
            freeAbsenceGivesADailyRefund = true,
            paymentNumberSeriesStart = 1,
            serviceWorkerMessageAccountName = "Oulun kaupunki",
            applyPlacementUnitFromDecision = false,
            unplannedAbsencesAreContractSurplusDays = false,
            maxContractDaySurplusThreshold = 13,
            useContractDaysAsDailyFeeDivisor = false,
            assistanceDecisionMakerRoles = setOf(UserRole.DIRECTOR, UserRole.SPECIAL_EDUCATION_TEACHER),
            preschoolAssistanceDecisionMakerRoles = setOf(UserRole.DIRECTOR, UserRole.SPECIAL_EDUCATION_TEACHER),
            requestedStartUpperLimit = 7,
            preferredStartRelativeApplicationDueDate = true,
            postOffice = "OULU",
            municipalMessageAccountName = "Oulun kaupunki",
            fiveYearsOldDaycareEnabled = false,
            financeMessageAccountName =
                "Varhaiskasvatuksen asiakasmaksut - Early childhood education fees",
            archiveMetadataOrganization = "Oulun kaupungin varhaiskasvatus",
            archiveMetadataConfigs = { type: ArchiveProcessType, year: Int ->
                when (type) {
                    ArchiveProcessType.APPLICATION_DAYCARE ->
                        ArchiveProcessConfig(
                            processDefinitionNumber = "12.06.01",
                            archiveDurationMonths = 10 * 12,
                        )

                    ArchiveProcessType.APPLICATION_PRESCHOOL ->
                        ArchiveProcessConfig(
                            processDefinitionNumber = "12.06.01",
                            archiveDurationMonths = 10 * 12,
                        )

                    ArchiveProcessType.APPLICATION_CLUB ->
                        ArchiveProcessConfig(
                            processDefinitionNumber = "12.06.01",
                            archiveDurationMonths = 10 * 12,
                        )

                    ArchiveProcessType.ASSISTANCE_NEED_DECISION_DAYCARE ->
                        ArchiveProcessConfig(
                            processDefinitionNumber = "12.06.01",
                            archiveDurationMonths = 120 * 12,
                        )

                    ArchiveProcessType.ASSISTANCE_NEED_DECISION_PRESCHOOL ->
                        ArchiveProcessConfig(
                            processDefinitionNumber = "12.06.01",
                            archiveDurationMonths = 120 * 12,
                        )

                    ArchiveProcessType.FEE_DECISION ->
                        ArchiveProcessConfig(
                            processDefinitionNumber = "12.06.01",
                            archiveDurationMonths = 192,
                        )

                    ArchiveProcessType.VOUCHER_VALUE_DECISION ->
                        ArchiveProcessConfig(
                            processDefinitionNumber = "12.06.01",
                            archiveDurationMonths = 192,
                        )
                }
            },
            placementToolApplicationStatus = ApplicationStatus.WAITING_DECISION,
            holidayQuestionnaireType = QuestionnaireType.OPEN_RANGES,
            minimumInvoiceAmount = 800,
            nekkuMealDeductionFactor = 0.9,
            daycarePlacementPlanEndMonthDay = MonthDay.of(8, 15),
        )

    @Bean
    fun actionRuleMapping(): ActionRuleMapping = EvakaOuluActionRuleMapping()

    @Bean
    fun invoiceGenerationLogicChooser() = DefaultInvoiceGenerationLogic // TODO: implement

    @Bean
    fun paymentIntegrationClient(
        evakaProperties: EvakaOuluProperties,
        paymentGenerator: ProEPaymentGenerator,
        sftpConnector: SftpConnector,
    ): PaymentIntegrationClient {
        val sftpSender = SftpSender(evakaProperties.intimePayments, sftpConnector)
        return OuluPaymentIntegrationClient(paymentGenerator, sftpSender)
    }

    @Bean
    fun tomcatCustomizer(env: Environment) =
        WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
            it.addContextValves(defaultAccessLoggingValve(env))
        }

    @Bean
    fun titaniaEmployeeIdConverter(): TitaniaEmployeeIdConverter =
        object : TitaniaEmployeeIdConverter {
            override fun fromTitania(employeeId: String): String = employeeId.trimStart('0')
        }

    @Bean
    fun mealTypeMapper(): MealTypeMapper = DefaultMealTypeMapper

    @Bean
    @Profile("production")
    fun productionS3AsyncClient(
        bucketEnv: BucketEnv,
        credentialsProvider: AwsCredentialsProvider,
    ): S3AsyncClient =
        S3AsyncClient
            .crtBuilder()
            .credentialsProvider(credentialsProvider)
            .build()

    @Bean
    @Profile("local")
    fun localS3AsyncClient(
        bucketEnv: BucketEnv,
        credentialsProvider: AwsCredentialsProvider,
    ): S3AsyncClient =
        S3AsyncClient
            .crtBuilder()
            .region(Region.EU_WEST_1)
            .credentialsProvider(credentialsProvider)
            .build()

    @Bean
    fun fileDWExportClient(
        asyncClient: S3AsyncClient,
        sftpConnector: SftpConnector,
        properties: EvakaOuluProperties,
    ): DWExportClient = FileDWExportClient(asyncClient, SftpSender(properties.dwExport.sftp, sftpConnector), properties)

    @Bean
    fun evakaOuluAsyncJobRunner(
        jdbi: Jdbi,
        tracer: Tracer,
        env: Environment,
    ): AsyncJobRunner<EvakaOuluAsyncJob> = AsyncJobRunner(EvakaOuluAsyncJob::class, listOf(EvakaOuluAsyncJob.pool), jdbi, tracer)

    @Bean
    fun evakaOuluDWJob(dwExportClient: DWExportClient) = DWExportJob(dwExportClient)

    @Bean
    fun evakaOuluScheduledJobEnv(env: Environment): ScheduledJobsEnv<EvakaOuluScheduledJob> =
        ScheduledJobsEnv.fromEnvironment(
            EvakaOuluScheduledJob.entries.associateWith { it.defaultSettings },
            "oulu.job",
            env,
        )

    @Bean
    fun evakaOuluScheduledJobs(
        evakaOuluRunner: AsyncJobRunner<EvakaOuluAsyncJob>,
        env: ScheduledJobsEnv<EvakaOuluScheduledJob>,
    ): EvakaOuluScheduledJobs = EvakaOuluScheduledJobs(evakaOuluRunner, env)

    @Bean
    fun passwordSpecification(): PasswordSpecification =
        DefaultPasswordSpecification(
            PasswordConstraints.UNCONSTRAINED.copy(
                minLength = 14,
                minLowers = 1,
                minUppers = 1,
                minDigits = 1,
                minSymbols = 1,
            ),
        )
}
