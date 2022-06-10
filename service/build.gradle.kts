plugins {
    id("org.jetbrains.kotlin.jvm") version TrevakaServiceDeps.kotlin
    id("org.jetbrains.kotlin.plugin.spring") version TrevakaServiceDeps.kotlin
    id("org.springframework.boot") version TrevakaServiceDeps.springBoot
    id("org.unbroken-dome.xjc") version TrevakaServiceDeps.xjc
}

repositories {
    mavenCentral()
}


dependencies {
    implementation(platform(":evaka-bom"))
    testImplementation(platform(":evaka-bom"))

    implementation(":evaka-service")

    implementation("io.github.microutils:kotlin-logging-jvm")
    implementation("dev.akkinoc.spring.boot:logback-access-spring-boot-starter")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")

    implementation("javax.xml.bind:jaxb-api")
    implementation("org.apache.httpcomponents:httpclient")
    implementation("com.github.kittinunf.fuel:fuel")
    implementation("org.jdbi:jdbi3-core")
    implementation("com.jcraft:jsch:0.1.55")

    implementation("software.amazon.awssdk:s3")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude( "com.vaadin.external.google", "android-json")
    }

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit-pioneer:junit-pioneer:${TrevakaServiceDeps.junitPioneer}") // for CartesianProductTest
    testImplementation("org.mockito.kotlin:mockito-kotlin")
    testImplementation("org.springframework.ws:spring-ws-test")
    testImplementation(platform("org.springframework.cloud:spring-cloud-dependencies:${TrevakaServiceDeps.springCloud}"))
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner")
    testImplementation(platform("org.testcontainers:testcontainers-bom:1.16.2"))
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.reflections:reflections:${TrevakaServiceDeps.reflections}")
    testImplementation("software.amazon.awssdk:s3")
    testImplementation("com.auth0:java-jwt")
    testImplementation("org.thymeleaf:thymeleaf")
}

springBoot {
    mainClass.set("fi.ouka.evakaoulu.EVakaOuluMainKt")
}

allprojects {
    tasks.withType<JavaCompile> {
        sourceCompatibility = TrevakaServiceDeps.java
        targetCompatibility = TrevakaServiceDeps.java
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = TrevakaServiceDeps.java
        allWarningsAsErrors = name != "compileIntegrationTestKotlin"
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

tasks.register<Test>("pdfGenerationTest"){
    useJUnitPlatform {
        includeTags("PDFGenerationTest")
    }
}

