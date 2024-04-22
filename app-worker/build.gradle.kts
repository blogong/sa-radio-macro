tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("io.sentry.jvm.gradle") version "4.4.1"
}

sentry {
    // Generates a JVM (Java, Kotlin, etc.) source bundle and uploads your source code to Sentry.
    // This enables source context, allowing you to see your source
    // code as part of your stack traces in Sentry.
    includeSourceContext = true

    org = "bk-ub"
    projectName = "thunder-macro-worker"
    authToken =
        System.getenv("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")
}

dependencies {
    implementation(project(":app-common"))
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("io.asyncer:r2dbc-mysql:1.1.0")
    implementation("io.r2dbc:r2dbc-h2:1.0.0.RELEASE")
    implementation("org.springframework.cloud:spring-cloud-starter-aws-messaging:2.2.6.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.discord4j:discord4j-core:3.2.6")
    implementation("io.r2dbc:r2dbc-spi")
    implementation("io.sentry:sentry-spring-boot-starter-jakarta:7.8.0")
    implementation("io.sentry:sentry-spring-boot-starter:5.5.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
}
