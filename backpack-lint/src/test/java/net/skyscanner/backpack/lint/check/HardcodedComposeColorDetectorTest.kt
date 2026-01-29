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

package net.skyscanner.backpack.lint.check

import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

@Suppress("UnstableApiUsage")
class HardcodedComposeColorDetectorTest {

    @Test
    fun `allows Color Unspecified`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.graphics.Color

            fun test() = Color.Unspecified
            """,
        ).indented()

        // Color.Unspecified should be allowed
        lint().files(code, colorStub())
            .allowMissingSdk()
            .issues(HardcodedComposeColorDetector.ISSUE)
            .run()
            .expect(
                """
                No warnings.
                """.trimIndent(),
            )
    }

    @Test
    fun `allows Color Transparent`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.graphics.Color

            fun test() = Color.Transparent
            """,
        ).indented()

        // Color.Transparent should be allowed
        lint().files(code, colorStub())
            .allowMissingSdk()
            .issues(HardcodedComposeColorDetector.ISSUE)
            .run()
            .expect(
                """
                No warnings.
                """.trimIndent(),
            )
    }

    @Test
    fun `detects Color Red`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.graphics.Color

            fun test() = Color.Red
            """,
        ).indented()

        lint().files(code, colorStub())
            .allowMissingSdk()
            .issues(HardcodedComposeColorDetector.ISSUE)
            .run()
            .expectContains("This color doesn't exist in Backpack")
    }

    @Test
    fun `detects hex color with single token match`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.graphics.Color

            fun test() = Color(0xFFEFF3F8)
            """,
        ).indented()

        lint().files(code, colorStub())
            .allowMissingSdk()
            .issues(HardcodedComposeColorDetector.ISSUE)
            .testModes(com.android.tools.lint.checks.infrastructure.TestMode.DEFAULT)
            .run()
            .expectErrorCount(1)
            .expectContains("BpkTheme.colors.canvasContrast")
    }

    @Test
    fun `detects hex color with multiple token matches`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.graphics.Color

            fun test() = Color(0xFF0062E3)
            """,
        ).indented()

        lint().files(code, colorStub())
            .allowMissingSdk()
            .issues(HardcodedComposeColorDetector.ISSUE)
            .run()
            .expectContains("BpkTheme.colors.coreAccent")
            .expectContains("BpkTheme.colors.textLink")
            .expectContains("All these tokens have the same color value")
    }

    @Test
    fun `shows all tokens when color not found`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.graphics.Color

            fun test() = Color(0xFFABCDEF)
            """,
        ).indented()

        lint().files(code, colorStub())
            .allowMissingSdk()
            .issues(HardcodedComposeColorDetector.ISSUE)
            .run()
            .expectContains("This color doesn't exist in Backpack")
            .expectContains("#backpack Slack channel")
    }

    @Test
    fun `allows dynamic color from variable`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.graphics.Color

            class PackageUiModel(val brandColor: String)

            fun String.toColorInt(): Long = 0L

            fun test(packageUiModel: PackageUiModel) = Color(packageUiModel.brandColor.toColorInt())
            """,
        ).indented()

        lint().files(code, colorStub())
            .allowMissingSdk()
            .issues(HardcodedComposeColorDetector.ISSUE)
            .run()
            .expectClean()
    }

    @Test
    fun `allows dynamic color from function call`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.graphics.Color

            fun getColorFromBackend(): Long = 0L

            fun test() = Color(getColorFromBackend())
            """,
        ).indented()

        lint().files(code, colorStub())
            .allowMissingSdk()
            .issues(HardcodedComposeColorDetector.ISSUE)
            .run()
            .expectClean()
    }

    @Test
    fun `allows dynamic color from variable reference`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.graphics.Color

            fun test(colorValue: Long) = Color(colorValue)
            """,
        ).indented()

        lint().files(code, colorStub())
            .allowMissingSdk()
            .issues(HardcodedComposeColorDetector.ISSUE)
            .run()
            .expectClean()
    }

    private fun colorStub() = kotlin(
        """
        package androidx.compose.ui.graphics

        class Color {
            constructor(value: Long)

            companion object {
                // Use external to avoid constructor call in stub
                @JvmField
                external val Unspecified: Color
                @JvmField
                external val Transparent: Color
                @JvmField
                val Red: Color
            }
        }
        """,
    ).indented()
}
