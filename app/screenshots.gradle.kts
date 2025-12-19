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

import com.android.build.api.dsl.ManagedVirtualDevice
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.android.build.gradle.internal.tasks.ManagedDeviceInstrumentationTestTask
import net.skyscanner.backpack.screenshots.ScreenshotTestsServer

configure<BaseAppModuleExtension> {
    productFlavors {
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
        animationsDisabled = true
        managedDevices {
            allDevices {
                create<ManagedVirtualDevice>("Docs") {
                    device = "Pixel"
                    apiLevel = 35
                    systemImageSource = "aosp"
                }
            }
        }
    }
}

val server = ScreenshotTestsServer(rootProject.file("docs"))

tasks.register("startScreenshotsServer") {
    doFirst {
        server.start()
    }
    finalizedBy("stopScreenshotsServer")
}

tasks.register("stopScreenshotsServer") {
    doLast {
        server.close()
    }
}

// disable gradle caching for recording screenshots
tasks.withType<ManagedDeviceInstrumentationTestTask> {
    outputs.upToDateWhen { device.get().name != "Docs" }
}

tasks.register("recordScreenshots") {
    mustRunAfter(
        "startScreenshotsServer",
    )

    dependsOn(
        "startScreenshotsServer",
        "DocsScreenshotsDebugAndroidTest",
    )
    finalizedBy("stopScreenshotsServer")
}
