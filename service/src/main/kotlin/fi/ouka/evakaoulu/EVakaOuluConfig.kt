package fi.ouka.evakaoulu

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
        daycareApplicationServiceNeedOptionsEnabled = true,
        citizenReservationThresholdHours = 6 * 24, // Tue 00:00
        dailyFeeDivisorOperationalDaysOverride = null,
        freeSickLeaveOnContractDays = true
    )

    @Bean
    fun actionRuleMapping(): ActionRuleMapping = EvakaOuluActionRuleMapping()

}
