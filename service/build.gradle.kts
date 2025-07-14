plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    id("org.unbroken-dome.xjc") version "2.0.0"
    id("com.gorylenko.gradle-git-properties") version "2.5.2"
    id("org.jlleitschuh.gradle.ktlint") version "12.3.0"
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

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")

    implementation("com.github.kittinunf.fuel:fuel")
    implementation("org.jdbi:jdbi3-core")
    implementation("com.github.mwiede:jsch")

    implementation("software.amazon.awssdk:s3")

    implementation("org.unbescape:unbescape:1.1.6.RELEASE")

    implementation("io.opentelemetry:opentelemetry-api")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("com.vaadin.external.google", "android-json")
    }

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit-pioneer:junit-pioneer:2.3.0") // for CartesianProductTest
    testImplementation("org.mockito.kotlin:mockito-kotlin")
    testImplementation("org.springframework.ws:spring-ws-test")
    testImplementation(platform("org.springframework.cloud:spring-cloud-dependencies:2025.0.0"))
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
    testImplementation(platform("org.testcontainers:testcontainers-bom:1.21.3"))
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.reflections:reflections:0.10.2")
    testImplementation("software.amazon.awssdk:s3")
    testImplementation("com.auth0:java-jwt")
    testImplementation("org.thymeleaf:thymeleaf")
}

springBoot {
    mainClass.set("fi.ouka.evakaoulu.EVakaOuluMainKt")
}

kotlin {
    compilerOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
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
    dotGitDirectory = project.rootProject.layout.projectDirectory.dir("../.git")
}
