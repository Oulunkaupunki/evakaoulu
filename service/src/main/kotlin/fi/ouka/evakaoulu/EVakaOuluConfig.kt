package fi.ouka.evakaoulu

import fi.espoo.evaka.invoicing.service.DefaultInvoiceGenerationLogic
import fi.espoo.evaka.shared.FeatureConfig
import fi.espoo.evaka.shared.security.actionrule.ActionRuleMapping
import fi.ouka.evakaoulu.security.EvakaOuluActionRuleMapping
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EVakaOuluConfig {

    @Bean
    fun featureConfig(): FeatureConfig = FeatureConfig(
        valueDecisionCapacityFactorEnabled = true,
        daycareApplicationServiceNeedOptionsEnabled = false,
        citizenReservationThresholdHours = 6 * 24, // Tue 00:00
        dailyFeeDivisorOperationalDaysOverride = null,
        freeSickLeaveOnContractDays = true,
        alwaysUseDaycareFinanceDecisionHandler = true
    )

    @Bean
    fun actionRuleMapping(): ActionRuleMapping = EvakaOuluActionRuleMapping()

    @Bean
    fun invoiceGenerationLogicChooser() = DefaultInvoiceGenerationLogic // TODO: implement

}
