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
class HardcodedTypographyDetectorTest {

    @Test
    fun `detects hardcoded sp value defaults to Normal weight`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.unit.sp

            fun test() {
                val size = 16.sp
            }
            """,
        ).indented()

        lint().files(code, spStub())
            .allowMissingSdk()
            .issues(HardcodedTypographyDetector.ISSUE)
            .run()
            .expectErrorCount(1)
            .expectContains("BpkTheme.typography.bodyDefault")
    }

    @Test
    fun `suggests specific token for unique font size and weight`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.unit.sp

            fun test() {
                val size = 12.sp
            }
            """,
        ).indented()

        lint().files(code, spStub())
            .allowMissingSdk()
            .issues(HardcodedTypographyDetector.ISSUE)
            .run()
            .expectErrorCount(1)
            .expectContains("Use BpkTheme.typography.caption instead of 12.sp")
    }

    @Test
    fun `shows all available styles for unmapped sp value`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.unit.sp

            fun test() {
                val size = 18.sp
            }
            """,
        ).indented()

        lint().files(code, spStub())
            .allowMissingSdk()
            .issues(HardcodedTypographyDetector.ISSUE)
            .run()
            .expectErrorCount(1)
            .expectContains("BpkTheme.typography")
            .expectContains("12sp/Normal")
            .expectContains("16sp/Bold")
    }

    @Test
    fun `detects TextStyle with Bold fontWeight and highlights entire call`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.text.TextStyle
            import androidx.compose.ui.text.font.FontWeight
            import androidx.compose.ui.unit.sp

            fun test() {
                val style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            """,
        ).indented()

        lint().files(code, textStyleStub(), fontWeightStub(), spStub())
            .allowMissingSdk()
            .issues(HardcodedTypographyDetector.ISSUE)
            .testModes(com.android.tools.lint.checks.infrastructure.TestMode.DEFAULT)
            .run()
            .expectErrorCount(1)
            .expectContains("instead of TextStyle")
            .expectContains("BpkTheme.typography.heading5")
            .expectContains("BpkTheme.typography.label1")
            // Verify the entire TextStyle call is underlined, not just 16.sp
            .expectContains("TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)")
            .expectContains("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    }

    @Test
    fun `detects TextStyle with Normal fontWeight and highlights entire call`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.text.TextStyle
            import androidx.compose.ui.text.font.FontWeight
            import androidx.compose.ui.unit.sp

            fun test() {
                val style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal)
            }
            """,
        ).indented()

        lint().files(code, textStyleStub(), fontWeightStub(), spStub())
            .allowMissingSdk()
            .issues(HardcodedTypographyDetector.ISSUE)
            .testModes(com.android.tools.lint.checks.infrastructure.TestMode.DEFAULT)
            .run()
            .expectErrorCount(1)
            .expectContains("instead of TextStyle")
            .expectContains("BpkTheme.typography.bodyDefault")
            // Verify the entire TextStyle call is underlined, not just 16.sp
            .expectContains("TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal)")
            .expectContains("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    }

    @Test
    fun `detects TextStyle without fontWeight defaults to Normal and highlights entire call`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.text.TextStyle
            import androidx.compose.ui.unit.sp

            fun test() {
                val style = TextStyle(fontSize = 16.sp)
            }
            """,
        ).indented()

        lint().files(code, simpleTextStyleStub(), spStub())
            .allowMissingSdk()
            .issues(HardcodedTypographyDetector.ISSUE)
            .testModes(com.android.tools.lint.checks.infrastructure.TestMode.DEFAULT)
            .run()
            .expectErrorCount(1)
            .expectContains("instead of TextStyle")
            .expectContains("BpkTheme.typography.bodyDefault")
            // Verify the entire TextStyle call is underlined, not just 16.sp
            .expectContains("TextStyle(fontSize = 16.sp)")
            .expectContains("~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    }

    @Test
    fun `allows variable sp usage`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.unit.sp

            val FONT_SIZE = 16
            fun test() {
                val size = FONT_SIZE.sp
            }
            """,
        ).indented()

        lint().files(code, spStub())
            .allowMissingSdk()
            .issues(HardcodedTypographyDetector.ISSUE)
            .run()
            .expectClean()
    }

    @Test
    fun `allows BpkTheme typography usage`() {
        val code = kotlin(
            """
            package test
            import net.skyscanner.backpack.compose.theme.BpkTheme

            fun test() {
                val style = BpkTheme.typography.heading5
            }
            """,
        ).indented()

        lint().files(code, bpkThemeStub())
            .allowMissingSdk()
            .issues(HardcodedTypographyDetector.ISSUE)
            .run()
            .expectClean()
    }

    @Test
    fun `allows TextStyle without hardcoded fontSize`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.text.TextStyle
            import androidx.compose.ui.text.font.FontWeight

            fun test() {
                val style = TextStyle(fontWeight = FontWeight.Bold)
            }
            """,
        ).indented()

        lint().files(code, textStyleWithoutFontSizeStub(), fontWeightStub())
            .allowMissingSdk()
            .issues(HardcodedTypographyDetector.ISSUE)
            .run()
            .expectClean()
    }

    private fun spStub() = kotlin(
        """
        package androidx.compose.ui.unit

        class TextUnit
        val Int.sp: TextUnit get() = TextUnit()
        """,
    ).indented()

    private fun simpleTextStyleStub() = kotlin(
        """
        package androidx.compose.ui.text
        import androidx.compose.ui.unit.TextUnit

        class TextStyle(val fontSize: TextUnit? = null)
        """,
    ).indented()

    private fun textStyleStub() = kotlin(
        """
        package androidx.compose.ui.text
        import androidx.compose.ui.unit.TextUnit
        import androidx.compose.ui.text.font.FontWeight

        class TextStyle(
            val fontSize: TextUnit? = null,
            val fontWeight: FontWeight? = null,
        )
        """,
    ).indented()

    private fun fontWeightStub() = kotlin(
        """
        package androidx.compose.ui.text.font

        class FontWeight {
            companion object {
                val Thin = FontWeight()
                val Normal = FontWeight()
                val Bold = FontWeight()
                val Black = FontWeight()
            }
        }
        """,
    ).indented()

    private fun bpkThemeStub() = kotlin(
        """
        package net.skyscanner.backpack.compose.theme

        object BpkTheme {
            val typography = BpkTypography()
        }

        class BpkTypography {
            val heading5 = Any()
            val bodyDefault = Any()
        }
        """,
    ).indented()

    private fun textStyleWithoutFontSizeStub() = kotlin(
        """
        package androidx.compose.ui.text
        import androidx.compose.ui.text.font.FontWeight

        class TextStyle(val fontWeight: FontWeight? = null)
        """,
    ).indented()
}
