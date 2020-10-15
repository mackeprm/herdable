import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    application
}
group = "de.mackeprm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.processing:core:3.3.7")
    testImplementation("io.kotest:kotest-runner-junit5:4.3.0")
    testImplementation("io.kotest:kotest-assertions-core:4.3.0")
}

tasks.withType<KotlinCompile>() {
    //kotlinOptions.jvmTarget = "13"
}

application {
    mainClassName = "MainKt"
}