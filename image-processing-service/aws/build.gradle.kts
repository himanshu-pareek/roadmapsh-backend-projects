plugins {
    id("java")
}

group = "dev.javarush.roadmapsh.image_processing.aws"
version = "0.0.1"

val springCloudAwsVersion="3.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    // S3
    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:${springCloudAwsVersion}"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}