plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":mongodb"))
    implementation(project(":amqp:producer"))
    implementation(project(":filesystem"))
    implementation(project(":aws"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
}

tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    val envFile = file(".env")
    if (envFile.exists()) {
        envFile.readLines()
            .filter { it.contains("=") }
            .forEach {
                val (key, value) = it.split("=", limit = 2)
                environment(key, value)
            }
    }
}
