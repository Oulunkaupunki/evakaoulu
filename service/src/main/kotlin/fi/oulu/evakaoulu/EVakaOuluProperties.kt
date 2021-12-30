// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.oulu.evakaoulu

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * All trevaka-specific configuration properties.
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "evakaoulu", ignoreUnknownFields = false)
data class EVakaOuluProperties(
    val ipaas: IpaasProperties,
    val invoice: InvoiceProperties,
)

data class IpaasProperties(
    val username: String,
    val password: String,
)

data class InvoiceProperties(
    val url: String,
    val paymentTerm: String = "V000",
    val salesOrganisation: String = "1312",
    val distributionChannel: String = "00",
    val division: String = "00",
    val salesOrderType: String = "ZPH",
    val interfaceID: String = "352",
    val plant: String = "1310"
)
