plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.ktlint.gradle)
    id("com.gorylenko.gradle-git-properties") version "2.5.3"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(libs.versions.java.get())
    }
}

repositories {
    mavenCentral()
    maven("https://build.shibboleth.net/maven/releases") {
        content {
            includeGroup("net.shibboleth")
            includeGroup("net.shibboleth.utilities")
            includeGroup("org.opensaml")
        }
    }
}

dependencies {
    implementation(platform("evaka:evaka-bom"))
    testImplementation(platform("evaka:evaka-bom"))

    implementation("evaka:evaka-service")

    implementation("ch.qos.logback.access:logback-access-tomcat")
    implementation("io.github.oshai:kotlin-logging-jvm")
    implementation("net.logstash.logback:logstash-logback-encoder")
    implementation("org.thymeleaf:thymeleaf")

    implementation("org.springframework.boot:spring-boot-tomcat")
    implementation("org.springframework.boot:spring-boot-webservices")
    implementation("org.springframework.boot:spring-boot-jdbc")

    implementation("com.github.kittinunf.fuel:fuel")
    implementation("org.jdbi:jdbi3-core")
    implementation("org.jdbi:jdbi3-kotlin")
    implementation("org.jdbi:jdbi3-json")
    implementation("com.github.mwiede:jsch")
    implementation("software.amazon.awssdk:s3")
    implementation("software.amazon.awssdk:http-auth-aws-crt")
    implementation("org.unbescape:unbescape:1.1.6.RELEASE")
    implementation("io.opentelemetry:opentelemetry-api")
    implementation("com.github.kagkarlsson:db-scheduler")

    implementation("org.jetbrains.kotlin:kotlin-reflect:")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("com.vaadin.external.google", "android-json")
    }

    testImplementation("org.junit-pioneer:junit-pioneer:2.3.0") // for CartesianProductTest
    testImplementation("org.mockito.kotlin:mockito-kotlin")
    testImplementation("org.springframework.boot:spring-boot-starter-webservices-test")
    testImplementation("org.wiremock.integrations:wiremock-spring-boot:4.0.8")
    testImplementation("com.auth0:java-jwt")

    developmentOnly(platform("evaka:evaka-bom"))
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}

springBoot {
    mainClass.set("fi.ouka.evakaoulu.EVakaOuluMainKt")
}

kotlin {
    compilerOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

allprojects {
    tasks.register("resolveDependencies") {
        description = "Resolves all dependencies"
        doLast {
            configurations
                .matching {
                    it.isCanBeResolved &&
                        // ignore configurations that fetch sources (e.g. Java source code)
                        !it.name.endsWith("dependencySources", ignoreCase = true)
                }.map {
                    val files = it.resolve()
                    it.name to files.size
                }.groupBy({ (_, count) -> count }) { (name, _) -> name }
                .forEach { (count, names) ->
                    println(
                        "Resolved $count dependency files for configurations: ${names.joinToString(", ")}",
                    )
                }
        }
    }
}

tasks {
    test {
        useJUnitPlatform()
        systemProperty("spring.profiles.active", "integration-test,evakaoulu")
    }
    bootRun {
        systemProperty("spring.profiles.active", "local,evakaoulu-local")
    }
}

tasks.register<Test>("pdfGenerationTest") {
    useJUnitPlatform {
        includeTags("PDFGenerationTest")
    }
}

gitProperties {
    dotGitDirectory =
        project.rootProject.layout.projectDirectory
            .dir("../.git")
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    version.set(
        libs.versions.ktlint
            .asProvider()
            .get(),
    )
}
