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
        // NOTE: This regex attempts to match Compose component functions with possible annotations and modifiers.
        // It captures optional visibility modifiers and handles annotations before @Composable.
        // For full accuracy in complex edge cases, consider using a Kotlin parser.
        val pattern = Regex(
            """(?s)(?:@[A-Za-z0-9_]+[\s\r\n]*)*@[Cc]omposable[\s\r\n]*(?:(public|internal|private|protected)[\s\r\n]+)?fun[\s\r\n]+(Bpk[A-Za-z0-9_]*)[\s\r\n]*(?:<[^>]*>)?[\s\r\n]*\("""
        )
        val excludePaths = listOf("/internal/", "/theme/", "/tokens/", "/util/")
        val components = mutableSetOf<String>()

        // Regex to detect internal functions anywhere in the file
        val internalFunRegex = Regex("""\binternal\b[^\n]*\bfun\b""")

        fileTree(composeSourceDir) {
            include("**/*.kt")
        }.forEach { file ->
            if (excludePaths.none { file.path.contains(it) }) {
                val content = file.readText()
                // Skip files containing internal function declarations
                if (!internalFunRegex.containsMatchIn(content)) {
                    pattern.findAll(content).forEach { match ->
                        val visibility = match.groupValues[1]
                        val name = match.groupValues[2]
                        // Only include public functions (explicit or default)
                        if (visibility.isEmpty() || visibility == "public") {
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
