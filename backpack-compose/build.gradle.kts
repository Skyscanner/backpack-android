/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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
  kotlin("android")
}

ext {
  set("artifactId", "backpack-compose")
}

apply(from = "${rootProject.projectDir}/gradle-maven-push.gradle")
apply(from = "${rootProject.projectDir}/dokka.gradle")
apply(from = "${rootProject.projectDir}/android-configuration.gradle")

android {
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = rootProject.ext.get("compose_compiler_version") as String
  }

  packagingOptions {
    resources.excludes.add("**/attach_hotspot_windows.dll")
    resources.excludes.add("META-INF/licenses/**")
    resources.excludes.add("META-INF/AL2.0")
    resources.excludes.add("META-INF/LGPL2.1")
  }
  namespace = "net.skyscanner.backpack.compose"
}

dependencies {
  val lifecycleVersion = "2.5.1"
  implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycleVersion")
  implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
  api(rootProject.ext.get("composeUi")!!)
  api(rootProject.ext.get("composeMaterial")!!)
  api(rootProject.ext.get("composeUiToolingPreview")!!)

  implementation(rootProject.ext.get("ktx")!!)
  androidTestImplementation(rootProject.ext.get("junitAndroid")!!)
  androidTestImplementation(rootProject.ext.get("testRules")!!)
  androidTestImplementation(rootProject.ext.get("mockitoKotlin")!!)
  androidTestImplementation(rootProject.ext.get("mockitoAndroid")!!)
  androidTestImplementation(rootProject.ext.get("composeTestJUnit")!!)
  debugImplementation(rootProject.ext.get("composeUiTooling")!!)
  testImplementation(rootProject.ext.get("junit")!!)
  testImplementation(rootProject.ext.get("coroutinesTest")!!)

  lintPublish(project(":backpack-lint"))
  implementation(project(":backpack-common"))
}

apply(from = "${rootProject.projectDir}/android-configuration-check.gradle")
apply(from = "tokens.gradle.kts")
