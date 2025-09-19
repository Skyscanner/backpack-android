11  /**
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

plugins {
    id("com.android.library")
    id("kotlin-android")
}

extra["artifactId"] = "backpack-android"

apply(from = "$rootDir/gradle-maven-push.gradle")
apply(from = "$rootDir/dokka.gradle")
apply(from = "$rootDir/android-configuration.gradle")

android {
    namespace = "net.skyscanner.backpack"
}

dependencies {
    api(libs.google.material)
    api(libs.androidx.constraintLayout)
    api(libs.androidx.cardView)
    api(libs.google.maps)
    implementation(libs.androidx.swiperefreshLayout)
    implementation(libs.androidx.coreKts)
    androidTestImplementation(libs.test.junitAndroid)
    androidTestImplementation(libs.test.junitKtx)
    androidTestImplementation(libs.test.espressoCore)
    androidTestImplementation(libs.test.espressoContrib)
    androidTestImplementation(libs.test.rules)
    androidTestImplementation(libs.test.mockitoKotlin)
    androidTestImplementation(libs.test.mockitoAndroid)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.coroutines)

    api(project(":backpack-common"))
    lintPublish(project(":backpack-lint"))
}

apply(from = "tokens.gradle.kts")

apply(from = "$rootDir/android-configuration-check.gradle")
