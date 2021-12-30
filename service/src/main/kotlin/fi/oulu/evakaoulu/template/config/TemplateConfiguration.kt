// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.oulu.evakaoulu.template.config

import fi.espoo.evaka.shared.template.ITemplateProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TemplateConfiguration {

    @Bean
    fun templateProvider(): ITemplateProvider = EVakaOuluTemplateProvider()

}

internal class EVakaOuluTemplateProvider : ITemplateProvider {
    override fun getFeeDecisionPath(): String = "oulu/fee-decision/decision"
    override fun getVoucherValueDecisionPath(): String = "oulu/fee-decision/voucher-value-decision"
    override fun getClubDecisionPath(): String = "oulu/club/decision"
    override fun getDaycareVoucherDecisionPath(): String = "oulu/daycare/voucher/decision"
    override fun getDaycareTransferDecisionPath(): String = "oulu/daycare/decision"
    override fun getDaycareDecisionPath(): String = "oulu/daycare/decision"

    override fun getPreschoolDecisionPath(): String =
        throw UnsupportedOperationException("Preschool decision is not supported")

    override fun getPreparatoryDecisionPath(): String =
        throw UnsupportedOperationException("Preparatory decision is not supported")
}
