// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu

import mu.KotlinLogging
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
        TransactionAutoConfiguration::class
    ]
)
@ConfigurationPropertiesScan(basePackages = ["fi.ouka.evakaoulu"])
class EvakaOuluMain

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {

    val profiles = mutableListOf("evakaoulu")

    System.getenv("VOLTTI_ENV")?.let { envString ->
        when (envString) {
            "dev", "test" -> profiles.add("enables_dev_api")
            else -> {
            }
        }
    }

    logger.info("Parsed profiles: {}", profiles.toTypedArray())
    SpringApplicationBuilder()
        .sources(EvakaOuluMain::class.java)
        .profiles(*profiles.toTypedArray())
        .run(*args)
}