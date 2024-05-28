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

@file:OptIn(ExperimentalCompilerApi::class)

package net.skyscanner.backpack.ksp

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.kspSourcesDir
import com.tschuchort.compiletesting.symbolProcessorProviders
import org.intellij.lang.annotations.Language
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.Assert.assertEquals

internal fun testKsp(
    @Language("kotlin") vararg sourceFile: String,
    evaluate: KotlinCompilation.Result.(String) -> Unit,
) {
    val compilation = KotlinCompilation().apply {
        sources = sourceFile.mapIndexed { index, it -> SourceFile.kotlin("file$index.kt", it) }
        symbolProcessorProviders = listOf(BackpackSymbolProcessorProvider())
        inheritClassPath = true
        verbose = false
    }
    val result = compilation.compile()
    require(result.exitCode == KotlinCompilation.ExitCode.OK) { result.messages }
    val generatedStories = compilation.kspSourcesDir.walkTopDown().first { it.name == "KspGeneratedStories.kt" }
    evaluate(result, generatedStories.readText())
}

internal fun testKsp(
    @Language("kotlin") source: String,
    @Language("kotlin") expected: String,
) =
    testKsp(source.trimIndent()) {
        assertEquals(expected.trimIndent(), it.trimIndent())
    }
