plugins {
	java
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "dev.javarush.roadmapsh-projects"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// Self-managed
	// https://mvnrepository.com/artifact/com.vladsch.flexmark/flexmark
	implementation("com.vladsch.flexmark:flexmark:0.64.8")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
