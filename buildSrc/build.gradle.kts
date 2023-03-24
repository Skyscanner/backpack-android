repositories {
    mavenCentral()
    google()
}

plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(gradleApi())
    implementation(libs.android.gradlePlugin)
    implementation(libs.android.sdkCommon)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.square.kotlinPoet)
    implementation(libs.google.guava)
    implementation(libs.protocol.gson)

    implementation(platform(libs.http4k.bom))
    implementation(libs.http4k.core)
    implementation(libs.http4k.serverUnderflow)
    implementation(libs.http4k.clientApache)
    implementation(libs.protocol.dadb)
}
