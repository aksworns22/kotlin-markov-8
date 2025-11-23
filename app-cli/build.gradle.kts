plugins {
    kotlin("jvm") version "2.2.0"
    id("application")
}

group = "com.github.aksworns22"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass = "cli.ApplicationKt"
}


dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")
    implementation(project(":core"))
    testImplementation(testFixtures(project(":core")))
}

tasks.test {
    useJUnitPlatform()
}
