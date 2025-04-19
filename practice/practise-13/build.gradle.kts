plugins {
	java
	checkstyle
	jacoco
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.liquibase.gradle") version "2.0.4"
	id("com.google.protobuf") version "0.9.4"
}

group = "hse"
version = "0.0.1-SNAPSHOT"

checkstyle {
	toolVersion = "10.13.0"
	isIgnoreFailures = false
	maxWarnings = 0
	maxErrors = 200
}

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
	implementation("org.springframework.boot:spring-boot-starter")

	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.liquibase:liquibase-core")
	liquibaseRuntime("org.liquibase:liquibase-core")
	liquibaseRuntime("org.liquibase.ext:liquibase-hibernate6:5.0.0")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation("org.springframework.boot:spring-boot-starter-aop")

	implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")

	// gRPC dependencies
	implementation("net.devh:grpc-server-spring-boot-starter:2.15.0.RELEASE")
	implementation("io.grpc:grpc-protobuf:1.54.0")
	implementation("io.grpc:grpc-stub:1.54.0")
	compileOnly("org.apache.tomcat:annotations-api:6.0.53")
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:3.22.0"
	}
	plugins {
		create("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:1.54.0"
		}
	}
	generateProtoTasks {
		all().forEach { task ->
			task.plugins {
				create("grpc")
			}
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
	dependsOn(tasks.test) // tests are required to run before generating the report
}

// Добавляем генерацию proto в исходные пути
sourceSets {
	main {
		java {
			srcDirs(
				"build/generated/source/proto/main/java",
				"build/generated/source/proto/main/grpc"
			)
		}
	}
}

tasks.named("compileJava").configure {
	dependsOn("generateProto")
}