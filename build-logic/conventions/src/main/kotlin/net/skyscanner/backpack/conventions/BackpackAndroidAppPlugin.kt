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

package net.skyscanner.backpack.conventions

import SdkVersions
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.internal.lint.AndroidLintTask
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class BackpackAndroidAppPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            plugins.apply("com.android.application")

            extensions.configure<ApplicationExtension> {
                compileSdk = SdkVersions.COMPILE_SDK

                defaultConfig {
                    minSdk = SdkVersions.MIN_SDK
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                testOptions {
                    animationsDisabled = true
                    unitTests {
                        isIncludeAndroidResources = true
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

            extensions.configure<KotlinAndroidProjectExtension> {
                jvmToolchain(17)
            }

            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                    freeCompilerArgs.addAll(
                        "-opt-in=kotlin.RequiresOptIn",
                        "-opt-in=net.skyscanner.backpack.util.InternalBackpackApi",
                    )
                }
            }

            // Workaround for K2 FIR lint analysis crash when analyzing Kotlin build scripts
            tasks.withType<AndroidLintTask>().configureEach {
                if (name.contains("Analyze")) {
                    enabled = false
                }
            }
        }
    }
}
