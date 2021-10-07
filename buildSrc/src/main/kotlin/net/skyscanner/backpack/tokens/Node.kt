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
