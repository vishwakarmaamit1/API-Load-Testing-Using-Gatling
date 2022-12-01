import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    id("io.gatling.gradle") version "3.8.4"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.gatling.highcharts:gatling-charts-highcharts:3.8.4")
    implementation("io.gatling:gatling-core:3.8.4")
    implementation("io.gatling:gatling-app:3.8.4")
    implementation("io.gatling:gatling-recorder:3.8.4")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")





}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}