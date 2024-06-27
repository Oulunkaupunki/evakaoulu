// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@SpringBootApplication(
    scanBasePackages = ["fi.ouka.evakaoulu", "fi.espoo.evaka"],
    exclude = [
        DataSourceAutoConfiguration::class,
        FlywayAutoConfiguration::class,
        SecurityAutoConfiguration::class,
        SecurityFilterAutoConfiguration::class,
        TransactionAutoConfiguration::class,
    ],
)
@ConfigurationPropertiesScan(basePackages = ["fi.ouka.evakaoulu"])
class EvakaOuluMain

fun main(args: Array<String>) {
    SpringApplicationBuilder()
        .sources(EvakaOuluMain::class.java)
        .profiles("evakaoulu")
        .run(*args)
}
