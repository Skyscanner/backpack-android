repositories {
    mavenCentral()
    google()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

plugins {
    `kotlin-dsl`
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(gradleApi())
    implementation(libs.plugin.android)
    implementation(libs.android.sdkCommon)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.square.kotlinPoet)
    implementation(libs.google.guava)
    implementation(libs.protocol.gson)

    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    implementation(libs.ktor.clientCore)
    implementation(libs.ktor.clientCio)
    implementation(libs.protocol.dadb)
}
