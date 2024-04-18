import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
     id("org.springframework.boot") version "3.2.4"
     id("io.spring.dependency-management") version "1.1.4"
     kotlin("jvm") version "1.9.23"
     kotlin("plugin.spring") version "1.9.23"
     kotlin("plugin.jpa") version "1.9.23"
}

noArg {
     annotation("javax.persistence.Entity")
     annotation("javax.persistence.MappedSuperclass")
     annotation("javax.persistence.Embeddable")
}

group = "net.bk24"
version = "0.0.1-SNAPSHOT"

java {
     sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
     mavenCentral()
}

dependencies {
     implementation("org.springframework.boot:spring-boot-starter-data-jpa")
     implementation("org.springframework.boot:spring-boot-starter-quartz")
     implementation("org.springframework.boot:spring-boot-starter-validation")
     implementation("org.springframework.boot:spring-boot-starter-webflux")
     implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
     implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
     implementation("org.jetbrains.kotlin:kotlin-reflect")
     implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
     testImplementation("org.springframework.boot:spring-boot-starter-test")
     testImplementation("io.projectreactor:reactor-test")
     runtimeOnly("com.mysql:mysql-connector-j")
     testImplementation("com.tngtech.archunit:archunit:1.3.0")
     runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.104.Final:osx-aarch_64")
     implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
}

tasks.withType<KotlinCompile> {
     kotlinOptions {
          freeCompilerArgs += "-Xjsr305=strict"
          jvmTarget = "17"
     }
}

tasks.withType<Test> {
     useJUnitPlatform()
}