

plugins {
    java
    jacoco
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "hw2"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // implementation("org.springframework.boot:spring-boot-starter")
    // // implementation("org.springframework.boot:spring-boot-starter-web")
    // implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")
    // implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    // implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    // runtimeOnly("org.postgresql:postgresql")
    // compileOnly("org.projectlombok:lombok")

    // annotationProcessor("org.projectlombok:lombok")
    // testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Spring Boot starters
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator") // Optional but recommended

    // Spring Cloud Gateway (REPLACES spring-boot-starter-web)
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")

    // Service Discovery (Eureka) - Optional
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    // OpenAPI Docs
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0") // Use latest stable

    // Database
    runtimeOnly("org.postgresql:postgresql")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.1") // Match Spring Boot 3.2.x
    }
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}