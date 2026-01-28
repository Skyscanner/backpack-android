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
class HardcodedPaddingDetectorTest {

    @Test
    fun `detects hardcoded padding with exact token match`() {
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
            .issues(HardcodedPaddingDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectErrorCount(1)
            .expectContains("BpkSpacing.Base")
    }

    @Test
    fun `shows available tokens when padding value not found`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.Modifier
            import androidx.compose.ui.unit.dp

            fun test() = Modifier.padding(20.dp)
            """,
        ).indented()

        lint().files(code, modifierStub(), dpStub())
            .allowMissingSdk()
            .issues(HardcodedPaddingDetector.ISSUE)
            .run()
            .expectContains("Available tokens")
            .expectContains("BpkSpacing.Sm")
            .expectContains("BpkSpacing.Md")
    }

    @Test
    fun `allows variable dp usage in padding`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.ui.Modifier
            import androidx.compose.ui.unit.dp

            private val PADDING = 16
            fun test() = Modifier.padding(PADDING.dp)
            """,
        ).indented()

        lint().files(code, modifierStub(), dpStub())
            .allowMissingSdk()
            .issues(HardcodedPaddingDetector.ISSUE)
            .run()
            .expectClean()
    }

    @Test
    fun `does not trigger on size methods`() {
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
            .issues(HardcodedPaddingDetector.ISSUE)
            .run()
            .expectClean()
    }

    private fun modifierStub() = kotlin(
        """
        package androidx.compose.ui
        import androidx.compose.ui.unit.Dp

        interface Modifier {
            companion object : Modifier
            fun padding(value: Dp): Modifier
            fun size(value: Dp): Modifier
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
