plugins {
    kotlin("jvm") version "1.7.21"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}
