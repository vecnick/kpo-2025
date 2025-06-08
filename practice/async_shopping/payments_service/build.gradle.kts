plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "payments"
version = "1.0.0"

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
	maven("https://jitpack.io") // для java-object-diff
}

dependencies {
	// для работы с kafka
	implementation("org.springframework.kafka:spring-kafka")
	
	// для Pair
	implementation ("org.apache.commons:commons-lang3:3.12.0")

	// сериализация класса в json формат
	implementation("com.fasterxml.jackson.core:jackson-databind")
	
	// для работы с бд
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("org.postgresql:postgresql")
	
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation ("org.assertj:assertj-core:3.24.2")
	implementation ("org.springframework.boot:spring-boot-starter-web")
	implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	implementation ("jakarta.validation:jakarta.validation-api:3.0.2")
	implementation("org.apache.commons:commons-lang3:3.12.0")
	implementation("de.danielbechler:java-object-diff:0.93.1")
	implementation("org.springframework.boot:spring-boot-starter")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named<Jar>("jar") {
    enabled = false
}
