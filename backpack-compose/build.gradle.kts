/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

import com.squareup.kotlinpoet.ClassName
import net.skyscanner.backpack.tokens.BpkColor
import net.skyscanner.backpack.tokens.BpkDimension
import net.skyscanner.backpack.tokens.BpkFormat
import net.skyscanner.backpack.tokens.BpkIcon
import net.skyscanner.backpack.tokens.BpkOutput
import net.skyscanner.backpack.tokens.BpkTextStyle
import net.skyscanner.backpack.tokens.BpkTextUnit
import net.skyscanner.backpack.tokens.androidFileOf
import net.skyscanner.backpack.tokens.nodeFileOf
import net.skyscanner.backpack.tokens.parseAs
import net.skyscanner.backpack.tokens.readAs
import net.skyscanner.backpack.tokens.saveTo
import net.skyscanner.backpack.tokens.transformTo
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.figma.code.connect)
    id("com.android.library")
    id("kotlin-android")
}

extra["artifactId"] = "backpack-compose"

apply(from = "$rootDir/gradle-maven-push.gradle")
apply(from = "$rootDir/android-configuration.gradle")

android {
    namespace = "net.skyscanner.backpack.compose"
    buildFeatures {
        compose = true
        resValues = false
    }
    packagingOptions {
        exclude("**/attach_hotspot_windows.dll")
        exclude("META-INF/licenses/**")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

dependencies {
    api(libs.compose.ui)
    api(libs.compose.foundation)
    api(libs.compose.uiToolingPreview)
    api(libs.compose.uiUtil)
    api(libs.google.maps)
    api(libs.google.mapsCompose)

    implementation(libs.compose.material3)
    implementation(libs.androidx.lifecycleViewmodel)
    implementation(libs.androidx.lifecycleViewmodelKtx)
    implementation(libs.androidx.coreKts)
    implementation(libs.figma.code.connect)
    androidTestImplementation(libs.test.junitAndroid)
    androidTestImplementation(libs.test.rules)
    androidTestImplementation(libs.test.mockitoKotlin)
    androidTestImplementation(libs.test.mockitoAndroid)
    androidTestImplementation(libs.test.compose)

    debugImplementation(libs.compose.uiTooling)
    debugImplementation(libs.compose.uiToolingTestManifest)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.coroutines)

    lintPublish(project(":backpack-lint"))
    implementation(project(":backpack-common"))

    val composeBom = platform(libs.compose.bom)
    api(composeBom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    debugImplementation(composeBom)
}

apply(from = "tokens.gradle.kts")
apply(from = "components.gradle.kts")

apply(from = "$rootDir/android-configuration-check.gradle")
