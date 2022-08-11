package fi.ouka.evakaoulu

import fi.espoo.evaka.invoicing.service.DefaultInvoiceGenerationLogic
import fi.espoo.evaka.shared.FeatureConfig
import fi.espoo.evaka.shared.security.actionrule.ActionRuleMapping
import fi.ouka.evakaoulu.security.EvakaOuluActionRuleMapping
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import fi.espoo.evaka.s3.DocumentService
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import fi.espoo.evaka.BucketEnv
import fi.espoo.evaka.invoicing.domain.PaymentIntegrationClient
import fi.ouka.evakaoulu.invoice.service.SftpConnector
import fi.ouka.evakaoulu.invoice.service.SftpSender
import fi.ouka.evakaoulu.payment.service.ProEPaymentGenerator
import fi.ouka.evakaoulu.payment.service.OuluPaymentIntegrationClient

@Configuration
class EVakaOuluConfig {

    @Bean
    fun featureConfig(): FeatureConfig = FeatureConfig(
        valueDecisionCapacityFactorEnabled = true,
        daycareApplicationServiceNeedOptionsEnabled = true,
        citizenReservationThresholdHours = 6 * 24, // Tue 00:00
        dailyFeeDivisorOperationalDaysOverride = 20,
        freeSickLeaveOnContractDays = true,
        alwaysUseDaycareFinanceDecisionHandler = true,
        freeAbsenceGivesADailyRefund = true,
        invoiceNumberSeriesStart = 1,
        paymentNumberSeriesStart = 1
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
        val sftpSender = SftpSender(evakaProperties.intimepayments, sftpConnector)
        return OuluPaymentIntegrationClient(paymentGenerator, sftpSender)
    }
}
