// This is a parent module for producer and consumer
subprojects {
    dependencies {
        implementation(project(":core"))
        implementation("org.springframework.boot:spring-boot-starter-amqp")
        // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
        implementation("com.fasterxml.jackson.core:jackson-databind:2.20.1")
    }
}
