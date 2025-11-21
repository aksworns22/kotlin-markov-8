plugins {
    id("java")
}

group = "com.github.aksworns22"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.junit.vintage:junit-vintage-engine:6.1.0-M1")
    implementation("org.assertj:assertj-swing:3.17.1")
    implementation(project(":core"))
    testImplementation(testFixtures(project(":core")))
}
