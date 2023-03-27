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

package net.skyscanner.backpack.ksp.writer

import net.skyscanner.backpack.ksp.ComponentDefinition
import net.skyscanner.backpack.ksp.SampleDefinition
import java.io.File
import java.lang.StringBuilder

fun writeListOfSamples(samples: Sequence<SampleDefinition>, output: String) {
    samples
        .groupBy { it.component }
        .forEach { component, samples ->
            writeFile(component, samples, output)
        }
}

fun writeFile(component: ComponentDefinition, samples: List<SampleDefinition>, output: String) {
    val folderName = component.name.replace(" ", "")
    val folder = File(output, folderName)
    if (!folder.exists()) {
        folder.mkdirs()
    }
    val file = File(folder, "README.md")
    if (!file.exists()) {
        file.createNewFile()
    }

    val sb = StringBuilder()
    samples.forEach {
        sb.append(it.kDocs)
        sb.appendLine()
        sb.append("```kotlin")
        sb.appendLine()
        sb.append(it.sourceCode)
        sb.appendLine()
        sb.append("```")
        sb.appendLine()
        sb.appendLine()
    }
    file.writeText(sb.toString())
}
