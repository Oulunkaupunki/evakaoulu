package fi.ouka.evakaoulu

import fi.espoo.evaka.BucketEnv
import fi.espoo.evaka.invoicing.domain.PaymentIntegrationClient
import fi.espoo.evaka.invoicing.service.DefaultInvoiceGenerationLogic
import fi.espoo.evaka.logging.defaultAccessLoggingValve
import fi.espoo.evaka.s3.DocumentService
import fi.espoo.evaka.shared.FeatureConfig
import fi.espoo.evaka.shared.auth.UserRole
import fi.espoo.evaka.shared.security.actionrule.ActionRuleMapping
import fi.ouka.evakaoulu.invoice.service.SftpConnector
import fi.ouka.evakaoulu.invoice.service.SftpSender
import fi.ouka.evakaoulu.payment.service.OuluPaymentIntegrationClient
import fi.ouka.evakaoulu.payment.service.ProEPaymentGenerator
import fi.ouka.evakaoulu.security.EvakaOuluActionRuleMapping
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.presigner.S3Presigner

@Configuration
class EVakaOuluConfig {

    @Bean
    fun featureConfig(): FeatureConfig = FeatureConfig(
        valueDecisionCapacityFactorEnabled = false,
        daycareApplicationServiceNeedOptionsEnabled = true,
        citizenReservationThresholdHours = 7 * 24,
        dailyFeeDivisorOperationalDaysOverride = null,
        freeSickLeaveOnContractDays = true,
        alwaysUseDaycareFinanceDecisionHandler = false,
        freeAbsenceGivesADailyRefund = true,
        invoiceNumberSeriesStart = 1,
        paymentNumberSeriesStart = 1,
        serviceWorkerMessageAccountName = "Oulun kaupunki",
        applyPlacementUnitFromDecision = false,
        unplannedAbsencesAreContractSurplusDays = false,
        maxContractDaySurplusThreshold = 13,
        useContractDaysAsDailyFeeDivisor = false,
        enabledChildConsentTypes = setOf(),
        curriculumDocumentPermissionToShareRequired = true,
        assistanceDecisionMakerRoles = setOf(UserRole.DIRECTOR, UserRole.SPECIAL_EDUCATION_TEACHER),
        preschoolAssistanceDecisionMakerRoles = setOf(UserRole.DIRECTOR, UserRole.SPECIAL_EDUCATION_TEACHER),
        requestedStartUpperLimit = 7,
        partialAbsenceThresholdsEnabled = false,
        preferredStartRelativeApplicationDueDate = false,
        postOffice = "OULU",
        municipalMessageAccountName = "Oulun kaupunki"
    )

    @Bean
    fun actionRuleMapping(): ActionRuleMapping = EvakaOuluActionRuleMapping()

    @Bean
    fun invoiceGenerationLogicChooser() = DefaultInvoiceGenerationLogic // TODO: implement

    @Bean
    fun documentService(s3Client: S3Client, s3Presigner: S3Presigner, env: BucketEnv): DocumentService =
        DocumentService(s3Client, s3Presigner, env.proxyThroughNginx)

    @Bean
    fun paymentIntegrationClient(evakaProperties: EvakaOuluProperties, paymentGenerator: ProEPaymentGenerator, sftpConnector: SftpConnector): PaymentIntegrationClient {
        val sftpSender = SftpSender(evakaProperties.intimePayments, sftpConnector)
        return OuluPaymentIntegrationClient(paymentGenerator, sftpSender)
    }

    @Bean
    fun tomcatCustomizer(env: Environment) =
        WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
            it.addContextValves(defaultAccessLoggingValve(env))
        }
}
