plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.google.protobuf") version "0.9.4"
}

group = "storing"
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
	// для работы с бд
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("org.postgresql:postgresql")
		
	// для обработки расширения файла
	implementation("commons-io:commons-io:2.15.1")
	
	// для работы с grpc
	implementation("io.grpc:grpc-netty-shaded:1.61.0")
	implementation ("io.grpc:grpc-protobuf:1.61.0")
	implementation ("io.grpc:grpc-stub:1.61.0")
	implementation ("net.devh:grpc-server-spring-boot-starter:2.15.0.RELEASE")
	implementation ("net.devh:grpc-client-spring-boot-starter:2.15.0.RELEASE")

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

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.1"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.61.0"
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



tasks.withType<org.springframework.boot.gradle.tasks.run.BootRun> {
    mainClass.set("storing.Main")
}

