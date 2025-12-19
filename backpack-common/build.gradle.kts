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

plugins {
    id("com.android.library")
    id("kotlin-android")
}

extra["artifactId"] = "backpack-common"

apply(from = "$rootDir/gradle-maven-push.gradle.kts")
apply(from = "$rootDir/android-configuration.gradle.kts")

android {
    namespace = "net.skyscanner.backpack.common"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        freeCompilerArgs.add("-Xopt-in=net.skyscanner.backpack.util.ExperimentalBackpackApi")
        freeCompilerArgs.add("-Xopt-in=net.skyscanner.backpack.util.InternalBackpackApi")
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

apply(from = "$rootDir/android-configuration-check.gradle.kts")
