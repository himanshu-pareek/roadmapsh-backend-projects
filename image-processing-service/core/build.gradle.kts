dependencies {
    // Core dependencies will be added here
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-parameters")
}
