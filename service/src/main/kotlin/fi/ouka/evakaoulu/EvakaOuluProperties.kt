// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * All evakaoulu-specific configuration properties.
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "evakaoulu", ignoreUnknownFields = false)
data class EvakaOuluProperties(
    val intimeInvoices: IntimeProperties,
    val intimePayments: IntimeProperties,
)

data class IntimeProperties(
    val address: String,
    val path: String,
    val username: String,
    val password: String
)
