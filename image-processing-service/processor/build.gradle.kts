plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":filesystem"))
    implementation(project(":mongodb"))
    implementation(project(":amqp:consumer"))
    implementation(project(":opencv"))
    // https://mvnrepository.com/artifact/org.springframework/spring-context
    implementation("org.springframework.boot:spring-boot-starter")
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
