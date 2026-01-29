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
class HardcodedSizeDetectorTest {

    @Test
    fun `detects hardcoded size and suggests named constant`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.Modifier
            import androidx.compose.ui.unit.dp

            fun test() = Modifier.size(200.dp)
            """,
        ).indented()

        lint().files(code, modifierStub(), dpStub())
            .allowMissingSdk()
            .issues(HardcodedSizeDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectWarningCount(1)
            .expectContains("Extract hardcoded size")
            .expectContains("private val ItemSize = 200.dp")
    }

    @Test
    fun `detects hardcoded height and suggests ItemHeight constant`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.Modifier
            import androidx.compose.ui.unit.dp

            fun test() = Modifier.height(100.dp)
            """,
        ).indented()

        lint().files(code, modifierStub(), dpStub())
            .allowMissingSdk()
            .issues(HardcodedSizeDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectWarningCount(1)
            .expectContains("private val ItemHeight = 100.dp")
    }

    @Test
    fun `detects hardcoded width and suggests ItemWidth constant`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.Modifier
            import androidx.compose.ui.unit.dp

            fun test() = Modifier.width(150.dp)
            """,
        ).indented()

        lint().files(code, modifierStub(), dpStub())
            .allowMissingSdk()
            .issues(HardcodedSizeDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectWarningCount(1)
            .expectContains("private val ItemWidth = 150.dp")
    }

    @Test
    fun `allows variable dp usage`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.Modifier
            import androidx.compose.ui.unit.dp

            private val IMAGE_HEIGHT = 200
            fun test() = Modifier.height(IMAGE_HEIGHT.dp)
            """,
        ).indented()

        lint().files(code, modifierStub(), dpStub())
            .allowMissingSdk()
            .issues(HardcodedSizeDetector.ISSUE)
            .run()
            .expectClean()
    }

    @Test
    fun `does not trigger on padding methods`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.Modifier
            import androidx.compose.ui.unit.dp

            fun test() = Modifier.padding(16.dp)
            """,
        ).indented()

        lint().files(code, modifierStub(), dpStub())
            .allowMissingSdk()
            .issues(HardcodedSizeDetector.ISSUE)
            .run()
            .expectClean()
    }

    @Test
    fun `provides fix suggestions in warning message`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.Modifier
            import androidx.compose.ui.unit.dp

            fun test() = Modifier.height(100.dp)
            """,
        ).indented()

        lint().files(code, modifierStub(), dpStub())
            .allowMissingSdk()
            .issues(HardcodedSizeDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectWarningCount(1)
            .expectContains("Extract hardcoded size to a named constant")
            .expectContains("private val ItemHeight = 100.dp")
            .expectContains("Modifier.height(ItemHeight)")
    }

    @Test
    fun `suggests reusing existing constant with same value`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.Modifier
            import androidx.compose.ui.unit.dp

            private val ItemHeight = 56.dp

            fun test() = Modifier.height(56.dp)
            """,
        ).indented()

        lint().files(code, modifierStub(), dpStub())
            .allowMissingSdk()
            .issues(HardcodedSizeDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectWarningCount(1)
            .expectContains("Existing constant(s) with same value: ItemHeight")
    }

    @Test
    fun `provides fix to reuse existing constant`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.Modifier
            import androidx.compose.ui.unit.dp

            private val RowHeight = 56.dp

            fun test() = Modifier.height(56.dp)
            """,
        ).indented()

        lint().files(code, modifierStub(), dpStub())
            .allowMissingSdk()
            .issues(HardcodedSizeDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectWarningCount(1)
            .expectContains("Hardcoded size detected")
            .expectContains("Existing constant(s) with same value: RowHeight")
            .expectContains("Use an existing constant or extract to a new one")
    }

    private fun modifierStub() = kotlin(
        """
        package androidx.compose.ui
        import androidx.compose.ui.unit.Dp

        interface Modifier {
            companion object : Modifier
            fun padding(value: Dp): Modifier
            fun size(value: Dp): Modifier
            fun width(value: Dp): Modifier
            fun height(value: Dp): Modifier
        }
        """,
    ).indented()

    private fun dpStub() = kotlin(
        """
        package androidx.compose.ui.unit

        class Dp
        val Int.dp: Dp get() = Dp()
        """,
    ).indented()
}
