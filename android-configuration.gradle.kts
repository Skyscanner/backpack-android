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

import com.android.build.gradle.LibraryExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

val libs = the<LibrariesForLibs>()

configure<LibraryExtension> {
    compileSdk = 36

    defaultConfig {
        minSdk = 28
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    lint {
        lintConfig = file("$rootDir/lint.xml")
        baseline = file("$projectDir/lint-baseline.xml")
        checkReleaseBuilds = false // we're already running lint separately
        warningsAsErrors = true
        disable += "UnusedResources" // we're exposing resources for consumers
        targetSdk = 35
    }

    buildFeatures {
        buildConfig = false
    }
}

configure<KotlinProjectExtension> {
    jvmToolchain(17)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
        freeCompilerArgs.add("-opt-in=net.skyscanner.backpack.util.InternalBackpackApi")
    }
}

dependencies {
    "api"(libs.androidx.annotations)
    "api"(libs.kotlin.stdlib)
    "api"(libs.kotlin.coroutines)
    "api"(libs.androidx.appCompat)
}

