plugins {
    kotlin("jvm") version "1.7.21"
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(project(":kommon"))
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "18"
}
