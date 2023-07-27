repositories {
    mavenCentral()
    google()
}

plugins {
    `kotlin-dsl`
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
