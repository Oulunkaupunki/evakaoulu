import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    id("org.unbroken-dome.xjc") version "2.0.0"
    id("com.gorylenko.gradle-git-properties") version "2.4.2"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

java.sourceCompatibility = JavaVersion.VERSION_21

object Version {
    const val OPEN_TRACING = "0.33.0"
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

    implementation("io.github.microutils:kotlin-logging-jvm")
    implementation("ch.qos.logback.access:tomcat")
    implementation("net.logstash.logback:logstash-logback-encoder")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")

    implementation("org.apache.httpcomponents:httpclient:4.5.14") {
        exclude("commons-logging", "commons-logging")
    }
    implementation("com.github.kittinunf.fuel:fuel")
    implementation("org.jdbi:jdbi3-core")
    implementation("com.jcraft:jsch:0.1.55")

    implementation("software.amazon.awssdk:s3")

    implementation("org.unbescape:unbescape:1.1.6.RELEASE")

    api("io.opentracing:opentracing-api:${Version.OPEN_TRACING}")
    api("io.opentracing:opentracing-util:${Version.OPEN_TRACING}")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("com.vaadin.external.google", "android-json")
    }

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit-pioneer:junit-pioneer:2.2.0") // for CartesianProductTest
    testImplementation("org.mockito.kotlin:mockito-kotlin")
    testImplementation("org.springframework.ws:spring-ws-test")
    testImplementation(platform("org.springframework.cloud:spring-cloud-dependencies:2023.0.3"))
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
    testImplementation(platform("org.testcontainers:testcontainers-bom:1.20.1"))
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.reflections:reflections:0.10.2")
    testImplementation("software.amazon.awssdk:s3")
    testImplementation("com.auth0:java-jwt")
    testImplementation("org.thymeleaf:thymeleaf")
}

springBoot {
    mainClass.set("fi.ouka.evakaoulu.EVakaOuluMainKt")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = JvmTarget.fromTarget(libs.versions.java.get())
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
