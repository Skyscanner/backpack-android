/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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
package net.skyscanner.backpack.tokens

import org.gradle.api.Project
import java.io.File

fun Project.nodeFileOf(module: String, file: String) : Pipeline<File> =
  pipelineOf {
    val nodeModules = project.rootDir.resolve("node_modules/")
    if (!nodeModules.exists()) error("Run npm install first")


    val resolvedModule = nodeModules.resolve(module)
    if (!resolvedModule.exists()) error("Module $module is not found!")

    val resolvedFile = resolvedModule.resolve(file)
    if (!resolvedFile.exists()) error("File $file is not found in module $module")

    resolvedFile
  }

fun Project.androidFileOf(module: String, file: String) : Pipeline<File>  =
  pipelineOf {
    val resolvedModule = project.rootDir.resolve("$module/")

    if (!resolvedModule.exists() && !resolvedModule.isDirectory) error("Module $module is not found!")

    val resolvedFile = resolvedModule.resolve(file)
    if (!resolvedFile.exists()) error("File $file is not found in module $module")

    resolvedFile
  }
