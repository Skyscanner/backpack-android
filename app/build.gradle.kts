/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Properties

plugins {
    alias(libs.plugins.compose.compiler)
    id("com.android.application")
    kotlin("android")
    alias(libs.plugins.ksp)
    alias(libs.plugins.roborazzi)
}

apply(from = "screenshots.gradle.kts")

val properties = Properties()
properties.putAll(System.getenv())
if (rootProject.file("local.properties").exists()) {
    properties.load(rootProject.file("local.properties").inputStream())
}

android {
    namespace = "net.skyscanner.backpack.demo"
    compileSdk = 36

    defaultConfig {
        applicationId = "net.skyscanner.backpack"
        minSdk = 28
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        manifestPlaceholders["MAPS_API_KEY"] = properties.getProperty("MAPS_API_KEY", "")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    signingConfigs {
        create("release") {
            storeFile = rootProject.file("backpack.android.demo.key")
            storePassword = properties.getProperty("KEYSTORE_PASSWORD")
            keyAlias = properties.getProperty("KEYSTORE_ALIAS")
            keyPassword = properties.getProperty("KEYSTORE_ALIAS_PASSWORD")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }

    flavorDimensions += "version"
    productFlavors {
        create("oss") {
            dimension = "version"
        }
        create("internal") {
            dimension = "version"
        }
    }

    testOptions {
        animationsDisabled = true
        unitTests {
            isIncludeAndroidResources = true
            all {
                it.systemProperty("variant", System.getProperty("variant") ?: "default")
                it.systemProperty("robolectric.pixelCopyRenderMode", "hardware")
            }
        }
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources.excludes.add("**/attach_hotspot_windows.dll")
        resources.excludes.add("META-INF/licenses/**")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
        freeCompilerArgs.add("-opt-in=net.skyscanner.backpack.util.InternalBackpackApi")
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(libs.androidx.lifecycleKtx)
    implementation(libs.androidx.lifecycleRuntimeCompose)
    implementation(libs.compose.activity)
    implementation(libs.kotlin.reflection)
    implementation(libs.destinations.core)
    testImplementation(libs.test.junit)
    testImplementation(libs.roborazzi.compose)
    testImplementation(libs.roborazzi.core)
    testImplementation(libs.roborazzi.junit)
    testImplementation(libs.test.compose)
    testImplementation(libs.robolectric)
    debugImplementation(libs.compose.uiToolingTestManifest)
    androidTestImplementation(libs.compose.foundation)
    androidTestImplementation(libs.test.coreKtx)
    androidTestImplementation(libs.test.junitKtx)
    androidTestImplementation(libs.test.junitAndroid)
    androidTestImplementation(libs.test.espressoCore)
    androidTestImplementation(libs.test.espressoContrib)
    androidTestImplementation(libs.test.rules)
    androidTestImplementation(libs.test.coroutines)
    androidTestImplementation(libs.test.compose)
    androidTestImplementation(libs.ktor.clientCore)
    androidTestImplementation(libs.ktor.clientCio)
    implementation(project(":Backpack"))
    implementation(project(":backpack-compose"))
    implementation(project(":meta:annotations"))
    ksp(project(":meta:processor"))
    ksp(libs.destinations.ksp)

    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    debugImplementation(composeBom)
    androidTestImplementation(composeBom)
}

