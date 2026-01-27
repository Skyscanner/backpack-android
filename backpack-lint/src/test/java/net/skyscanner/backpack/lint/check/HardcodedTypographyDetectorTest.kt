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
    fun `detects hardcoded sp value`() {
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
            .expectContains("BpkTypography")
            .expectContains("consult with design")
    }

    @Test
    fun `shows all typography styles in error message`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.unit.sp

            fun test() {
                val size = 14.sp
            }
            """,
        ).indented()

        lint().files(code, spStub())
            .allowMissingSdk()
            .issues(HardcodedTypographyDetector.ISSUE)
            .run()
            .expectContains("BpkTypography.hero1")
            .expectContains("BpkTypography.bodyDefault")
            .expectContains("BpkTypography.footnote")
            .expectContains("BpkTypography.caption")
    }

    @Test
    fun `detects TextStyle creation with fontSize`() {
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

        lint().files(code, textStyleStub(), spStub())
            .allowMissingSdk()
            .issues(HardcodedTypographyDetector.ISSUE)
            .run()
            .expectContains("BpkTypography")
    }

    private fun spStub() = kotlin(
        """
        package androidx.compose.ui.unit

        class TextUnit
        val Int.sp: TextUnit get() = TextUnit()
        """,
    ).indented()

    private fun textStyleStub() = kotlin(
        """
        package androidx.compose.ui.text
        import androidx.compose.ui.unit.TextUnit

        class TextStyle(val fontSize: TextUnit? = null)
        """,
    ).indented()
}
