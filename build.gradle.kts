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
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.roborazzi) apply false
}

apply(from = "publish-root.gradle.kts")

extra["group"] = "net.skyscanner.backpack"

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        config.setFrom(files("$rootDir/.detekt.yml", "$rootDir/.detekt-compose.yml"))
        buildUponDefaultConfig = true
        source.setFrom(files("src", "$rootDir/buildSrc/src"))
    }
}

tasks.register<Copy>("installGitHooks") {
    from(File(rootProject.rootDir, "hooks/pre-commit"))
    into(File(rootProject.rootDir, ".git/hooks"))
    filePermissions {
        unix("rwxrwxr-x")
    }
}

tasks.register<Exec>("installAiLabels") {
    description = "Install ai-labels for AI tool detection in commits"
    group = "setup"

    workingDir = rootProject.rootDir
    commandLine("bash", "hooks/install-ai-labels.sh")
    isIgnoreExitValue = true
    val stdoutStream = java.io.ByteArrayOutputStream()
    val stderrStream = java.io.ByteArrayOutputStream()
    standardOutput = stdoutStream
    errorOutput = stderrStream

    doLast {
        val stdout = stdoutStream.toString().trim()
        val stderr = stderrStream.toString().trim()
        if (stdout.isNotEmpty()) logger.lifecycle(stdout)
        if (stderr.isNotEmpty()) logger.error(stderr)
    }
}

project(":app").afterEvaluate {
    tasks.named("preBuild").configure {
        dependsOn(rootProject.tasks.named("installGitHooks"), rootProject.tasks.named("installAiLabels"))
    }
}

