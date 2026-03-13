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
class HardcodedBorderRadiusDetectorTest {

    @Test
    fun `detects hardcoded corner radius with exact token match`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.foundation.shape.RoundedCornerShape
            import androidx.compose.ui.unit.dp

            fun test() = RoundedCornerShape(8.dp)
            """,
        ).indented()

        lint().files(code, roundedCornerShapeStub(), dpStub())
            .allowMissingSdk()
            .issues(HardcodedBorderRadiusDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectErrorCount(1)
            .expectContains("BpkBorderRadius.Sm")
    }

    @Test
    fun `shows available tokens when border radius value not found`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.foundation.shape.RoundedCornerShape
            import androidx.compose.ui.unit.dp

            fun test() = RoundedCornerShape(20.dp)
            """,
        ).indented()

        lint().files(code, roundedCornerShapeStub(), dpStub())
            .allowMissingSdk()
            .issues(HardcodedBorderRadiusDetector.ISSUE)
            .testModes(TestMode.DEFAULT)
            .run()
            .expectContains("Available tokens")
            .expectContains("BpkBorderRadius")
    }

    @Test
    fun `allows variable dp usage in corner shape`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.foundation.shape.RoundedCornerShape
            import androidx.compose.ui.unit.dp

            private val CORNER_RADIUS = 8
            fun test() = RoundedCornerShape(CORNER_RADIUS.dp)
            """,
        ).indented()

        lint().files(code, roundedCornerShapeStub(), dpStub())
            .allowMissingSdk()
            .issues(HardcodedBorderRadiusDetector.ISSUE)
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
            .issues(HardcodedBorderRadiusDetector.ISSUE)
            .run()
            .expectClean()
    }

    private fun roundedCornerShapeStub() = kotlin(
        """
        package androidx.compose.foundation.shape
        import androidx.compose.ui.unit.Dp

        fun RoundedCornerShape(size: Dp): Any = Any()
        """,
    ).indented()

    private fun modifierStub() = kotlin(
        """
        package androidx.compose.ui
        import androidx.compose.ui.unit.Dp

        interface Modifier {
            companion object : Modifier
            fun padding(value: Dp): Modifier
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
