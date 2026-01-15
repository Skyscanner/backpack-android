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
    id("backpack.android-library")
    alias(libs.plugins.compose.compiler)
    id("backpack.publishing")
}

backpackPublishing {
    artifactId = "backpack-compose"
}

android {
    namespace = "net.skyscanner.backpack.compose"
    buildFeatures {
        compose = true
        resValues = false
    }
    packaging {
        resources.excludes.add("**/attach_hotspot_windows.dll")
        resources.excludes.add("META-INF/licenses/**")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

dependencies {
    // Compose BOM
    val composeBom = platform(libs.compose.bom)
    api(composeBom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    debugImplementation(composeBom)

    // Module-specific API dependencies
    api(libs.compose.ui)
    api(libs.compose.foundation)
    api(libs.compose.uiToolingPreview)
    api(libs.compose.uiUtil)
    api(libs.google.maps)
    api(libs.google.mapsCompose)

    // Module-specific implementation dependencies
    implementation(libs.compose.material3)
    implementation(libs.androidx.lifecycleViewmodel)
    implementation(libs.androidx.lifecycleViewmodelKtx)
    implementation(libs.androidx.coreKts)

    // Module-specific test dependencies
    androidTestImplementation(libs.test.compose)
    debugImplementation(libs.compose.uiTooling)
    debugImplementation(libs.compose.uiToolingTestManifest)

    implementation(project(":backpack-common"))

    // Detekt rules
    detektPlugins(libs.detektRules.compose)
    detektPlugins(libs.detektRules.formatting)
    detektPlugins(libs.detektRules.libraries)
}

apply(from = "tokens.gradle.kts")
apply(from = "components.gradle.kts")

apply(from = "$rootDir/android-configuration-check.gradle.kts")
