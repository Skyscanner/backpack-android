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

import net.skyscanner.backpack.tokens.BpkColor
import net.skyscanner.backpack.tokens.BpkDimension
import net.skyscanner.backpack.tokens.BpkDuration
import net.skyscanner.backpack.tokens.BpkFormat
import net.skyscanner.backpack.tokens.BpkIcon
import net.skyscanner.backpack.tokens.BpkOutput
import net.skyscanner.backpack.tokens.BpkTextStyle
import net.skyscanner.backpack.tokens.BpkTextUnit
import net.skyscanner.backpack.tokens.nodeFileOf
import net.skyscanner.backpack.tokens.parseAs
import net.skyscanner.backpack.tokens.readAs
import net.skyscanner.backpack.tokens.saveTo
import net.skyscanner.backpack.tokens.transformTo

plugins {
    id("com.android.library")
    id("kotlin-android")
}

extra["artifactId"] = "backpack-common"

apply(from = "$rootDir/gradle-maven-push.gradle")
apply(from = "$rootDir/android-configuration.gradle")

android {
    namespace = "net.skyscanner.backpack.common"
    kotlinOptions {
        freeCompilerArgs += "-Xopt-in=net.skyscanner.backpack.util.ExperimentalBackpackApi"
        freeCompilerArgs += "-Xopt-in=net.skyscanner.backpack.util.InternalBackpackApi"
    }
}

dependencies {
    val composeBom = platform(libs.compose.bom)
    api(composeBom)
    implementation(composeBom)

    api(libs.compose.ui)
    testImplementation(libs.test.junit)
    implementation(libs.compose.runtime)
    androidTestImplementation(libs.test.espressoCore)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.junitAndroid)
    androidTestImplementation(libs.test.coroutines)
}

apply(from = "tokens.gradle.kts")


apply(from = "$rootDir/android-configuration-check.gradle")
