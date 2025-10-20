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

// Component list generation
tasks.register("generateComponentList") {
    group = "compose"
    description = "Generate list of public Compose components"

    val composeSourceDir = file("src/main/kotlin/net/skyscanner/backpack/compose")
    val outputFile = file("src/main/res/raw/compose_components.txt")

    inputs.dir(composeSourceDir)
    outputs.file(outputFile)

    doLast {
        val pattern = Regex("""@Composable\s+fun\s+(Bpk[A-Za-z0-9_]*)\s*(?:<[^>]*>)?\s*\(""")
        val excludePaths = listOf("/internal/", "Internal", "/theme/", "/tokens/", "/util/")
        val components = mutableSetOf<String>()

        fileTree(composeSourceDir) {
            include("**/*.kt")
        }.forEach { file ->
            if (excludePaths.none { file.path.contains(it) }) {
                val content = file.readText()
                if (!content.contains("internal fun")) {
                    pattern.findAll(content).forEach { match ->
                        val name = match.groupValues[1]
                        if (!name.contains("Internal") && !name.endsWith("Impl")) {
                            components.add(name)
                        }
                    }
                }
            }
        }

        outputFile.parentFile.mkdirs()
        outputFile.writeText(components.sorted().joinToString("\n") + "\n")
        logger.lifecycle("Generated component list: ${components.size} components")
    }
}

// Hook into preBuild
tasks.named("preBuild").configure {
    dependsOn("generateComponentList")
}
