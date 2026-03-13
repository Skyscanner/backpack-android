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
import com.android.tools.lint.checks.infrastructure.TestMode
import org.junit.Test

@Suppress("UnstableApiUsage")
class BpkTokensCopyDetectorTest {

    @Test
    fun `detects copy on BpkTheme colors corePrimary`() {
        val code = kotlin(
            """
            package test
            import net.skyscanner.backpack.compose.theme.BpkTheme

            fun test() {
                val customColor = BpkTheme.colors.corePrimary.copy(alpha = 0.3f)
            }
            """,
        ).indented()

        lint().files(code, bpkThemeStubWithMoreColors())
            .allowMissingSdk()
            .issues(BpkTokensCopyDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectContains("Do not use .copy() to modify design tokens")
    }

    @Test
    fun `detects copy on BpkTheme colors with coreAccent`() {
        val code = kotlin(
            """
            package test
            import net.skyscanner.backpack.compose.theme.BpkTheme

            fun test() {
                val customColor = BpkTheme.colors.coreAccent.copy(alpha = 0.5f)
            }
            """,
        ).indented()

        lint().files(code, bpkThemeStub())
            .allowMissingSdk()
            .issues(BpkTokensCopyDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectContains("Do not use .copy() to modify design tokens")
    }

    @Test
    fun `detects copy on BpkTheme colors with textPrimary`() {
        val code = kotlin(
            """
            package test
            import net.skyscanner.backpack.compose.theme.BpkTheme

            fun test() {
                val customColor = BpkTheme.colors.textPrimary.copy(alpha = 0.8f)
            }
            """,
        ).indented()

        lint().files(code, bpkThemeStub())
            .allowMissingSdk()
            .issues(BpkTokensCopyDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectContains("Do not use .copy() to modify design tokens")
    }

    @Test
    fun `detects copy on BpkTheme colors with any color name`() {
        val code = kotlin(
            """
            package test
            import net.skyscanner.backpack.compose.theme.BpkTheme

            fun test() {
                val customColor = BpkTheme.colors.statusWarningFill.copy(alpha = 0.5f)
            }
            """,
        ).indented()

        lint().files(code, bpkThemeStubWithMoreColors())
            .allowMissingSdk()
            .issues(BpkTokensCopyDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectContains("Do not use .copy() to modify design tokens")
    }

    @Test
    fun `detects copy on BpkTheme typography`() {
        val code = kotlin(
            """
            package test
            import net.skyscanner.backpack.compose.theme.BpkTheme

            fun test() {
                val customTypo = BpkTheme.typography.heading1.copy(fontWeight = "Bold")
            }
            """,
        ).indented()

        lint().files(code, bpkThemeStub())
            .allowMissingSdk()
            .issues(BpkTokensCopyDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectContains("Do not use .copy() to modify design tokens")
    }

    @Test
    fun `detects copy on imported colors reference`() {
        val code = kotlin(
            """
            package test
            import net.skyscanner.backpack.compose.theme.BpkTheme.colors

            fun test() {
                val customColor = colors.coreAccent.copy(alpha = 0.5f)
            }
            """,
        ).indented()

        lint().files(code, bpkThemeStub())
            .allowMissingSdk()
            .issues(BpkTokensCopyDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectContains("Do not use .copy() to modify design tokens")
    }

    @Test
    fun `detects copy on imported typography reference`() {
        val code = kotlin(
            """
            package test
            import net.skyscanner.backpack.compose.theme.BpkTheme.typography

            fun test() {
                val customTypo = typography.heading1.copy(fontWeight = "Bold")
            }
            """,
        ).indented()

        lint().files(code, bpkThemeStub())
            .allowMissingSdk()
            .issues(BpkTokensCopyDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectContains("Do not use .copy() to modify design tokens")
    }

    @Test
    fun `allows copy on non-token objects`() {
        val code = kotlin(
            """
            package test

            data class MyColor(val red: Int, val green: Int, val blue: Int)

            fun test() {
                val original = MyColor(255, 0, 0)
                val modified = original.copy(green = 128)
            }
            """,
        ).indented()

        lint().files(code)
            .allowMissingSdk()
            .allowCompilationErrors()
            .issues(BpkTokensCopyDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectClean()
    }

    @Test
    fun `allows copy on unrelated colors variable`() {
        val code = kotlin(
            """
            package test

            data class MyColors(val primary: Int, val secondary: Int)

            fun test() {
                val mycolors = MyColors(0xFF0000, 0x00FF00)
                val modified = mycolors.copy(secondary = 0x0000FF)
            }
            """,
        ).indented()

        lint().files(code)
            .allowMissingSdk()
            .allowCompilationErrors()
            .issues(BpkTokensCopyDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectClean()
    }

    @Test
    fun `detects copy on BpkTheme getColors()`() {
        val code = kotlin(
            """
            package test
            import net.skyscanner.backpack.compose.theme.BpkTheme

            fun test() {
                val customColor = BpkTheme.getColors().coreAccent.copy(alpha = 0.5f)
            }
            """,
        ).indented()

        lint().files(code, bpkThemeStub())
            .allowMissingSdk()
            .issues(BpkTokensCopyDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectContains("Do not use .copy() to modify design tokens")
    }

    @Test
    fun `detects copy on BpkTheme getTypography()`() {
        val code = kotlin(
            """
            package test
            import net.skyscanner.backpack.compose.theme.BpkTheme

            fun test() {
                val customTypo = BpkTheme.getTypography().heading1.copy(fontWeight = "Bold")
            }
            """,
        ).indented()

        lint().files(code, bpkThemeStub())
            .allowMissingSdk()
            .issues(BpkTokensCopyDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectContains("Do not use .copy() to modify design tokens")
    }

    private fun bpkThemeStub() = kotlin(
        """
        package net.skyscanner.backpack.compose.theme

        object BpkTheme {
            val colors = BpkColors()
            val typography = BpkTypography()

            fun getColors(): BpkColors = colors
            fun getTypography(): BpkTypography = typography
        }

        data class BpkColors(
            val coreAccent: BpkColor = BpkColor(),
            val textPrimary: BpkColor = BpkColor(),
        )

        data class BpkColor(val value: Long = 0L) {
            fun copy(
                red: Float = 0f,
                green: Float = 0f,
                blue: Float = 0f,
                alpha: Float = 1f,
            ): BpkColor = this
        }

        data class BpkTypography(
            val heading1: BpkTextStyle = BpkTextStyle(),
            val bodyDefault: BpkTextStyle = BpkTextStyle(),
        )

        data class BpkTextStyle(val fontSize: Int = 16) {
            fun copy(
                fontSize: Int = this.fontSize,
                fontWeight: String? = null,
            ): BpkTextStyle = this
        }
        """,
    ).indented()

    private fun bpkThemeStubWithMoreColors() = kotlin(
        """
        package net.skyscanner.backpack.compose.theme

        object BpkTheme {
            val colors = BpkColors()
            val typography = BpkTypography()

            fun getColors(): BpkColors = colors
            fun getTypography(): BpkTypography = typography
        }

        data class BpkColors(
            val coreAccent: BpkColor = BpkColor(),
            val corePrimary: BpkColor = BpkColor(),
            val textPrimary: BpkColor = BpkColor(),
            val statusWarningFill: BpkColor = BpkColor(),
            val statusSuccessSpot: BpkColor = BpkColor(),
            val surfaceHighlight: BpkColor = BpkColor(),
        )

        data class BpkColor(val value: Long = 0L) {
            fun copy(
                red: Float = 0f,
                green: Float = 0f,
                blue: Float = 0f,
                alpha: Float = 1f,
            ): BpkColor = this
        }

        data class BpkTypography(
            val heading1: BpkTextStyle = BpkTextStyle(),
            val bodyDefault: BpkTextStyle = BpkTextStyle(),
        )

        data class BpkTextStyle(val fontSize: Int = 16) {
            fun copy(
                fontSize: Int = this.fontSize,
                fontWeight: String? = null,
            ): BpkTextStyle = this
        }
        """,
    ).indented()
}
