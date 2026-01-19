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

import com.android.build.gradle.internal.tasks.ManagedDeviceInstrumentationTestTask
import java.util.Properties

plugins {
    id("backpack.android-app")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.roborazzi)
}

val properties = Properties()
properties.putAll(System.getenv())
if (rootProject.file("local.properties").exists()) {
    properties.load(rootProject.file("local.properties").inputStream())
}

android {
    namespace = "net.skyscanner.backpack.demo"

    defaultConfig {
        applicationId = "net.skyscanner.backpack"
        versionCode = 1
        versionName = "1.0.0"

        manifestPlaceholders["MAPS_API_KEY"] = properties.getProperty("MAPS_API_KEY", "")
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
        create("screenshots") {
            dimension = "version"
            versionNameSuffix = "-screenshots"
            testInstrumentationRunnerArguments["notClass"] = "net.skyscanner.backpack.*"
            testInstrumentationRunnerArguments["class"] = "net.skyscanner.backpack.docs.GenerateScreenshots"
        }
    }

    sourceSets {
        getByName("screenshots") {
            java.srcDirs("src/internal/java")
            res.srcDirs("src/internal/res")
        }
    }

    testOptions {
        unitTests {
            all {
                it.systemProperty("variant", System.getProperty("variant") ?: "default")
                it.systemProperty("robolectric.pixelCopyRenderMode", "hardware")
            }
        }
        managedDevices {
            devices {
                create("Docs", com.android.build.api.dsl.ManagedVirtualDevice::class) {
                    device = "Pixel"
                    apiLevel = 35
                    systemImageSource = "aosp"
                }
            }
        }
    }
}

// Screenshots server setup
val screenshotServer = net.skyscanner.backpack.screenshots.ScreenshotTestsServer(rootProject.file("docs"))

tasks.register("startScreenshotsServer") {
    doFirst {
        screenshotServer.start()
    }
    finalizedBy("stopScreenshotsServer")
}

tasks.register("stopScreenshotsServer") {
    doLast {
        screenshotServer.close()
    }
}

tasks.withType<ManagedDeviceInstrumentationTestTask>().configureEach {
    outputs.upToDateWhen { device.get().name != "Docs" }
}

tasks.register("recordScreenshots") {
    mustRunAfter("startScreenshotsServer")
    dependsOn("startScreenshotsServer", "DocsScreenshotsDebugAndroidTest")
    finalizedBy("stopScreenshotsServer")
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

    // Detekt rules
    detektPlugins(libs.detektRules.compose)
    detektPlugins(libs.detektRules.formatting)
    detektPlugins(libs.detektRules.libraries)
}

