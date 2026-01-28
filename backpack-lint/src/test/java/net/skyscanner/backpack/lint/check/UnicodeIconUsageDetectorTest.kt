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
class UnicodeIconUsageDetectorTest {

    @Test
    fun `detects star symbols and suggests BpkStarRating`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.material3.Text

            fun test() {
                Text(text = "★★★★☆")
            }
            """,
        ).indented()

        lint().files(code, textStub())
            .allowMissingSdk()
            .issues(UnicodeIconUsageDetector.ISSUE)
            .run()
            .expectErrorCount(1)
            .expectContains("BpkStarRating")
    }

    @Test
    fun `detects arrow symbol and suggests BpkIcon ArrowRight`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.material3.Text

            fun test() {
                Text(text = "→")
            }
            """,
        ).indented()

        lint().files(code, textStub())
            .allowMissingSdk()
            .issues(UnicodeIconUsageDetector.ISSUE)
            .run()
            .expectErrorCount(1)
            .expectContains("BpkIcon.ArrowRight")
    }

    @Test
    fun `detects checkmark and suggests BpkIcon Tick`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.material3.Text

            fun test() {
                Text(text = "✓")
            }
            """,
        ).indented()

        lint().files(code, textStub())
            .allowMissingSdk()
            .issues(UnicodeIconUsageDetector.ISSUE)
            .run()
            .expectErrorCount(1)
            .expectContains("BpkIcon.Tick")
    }

    @Test
    fun `detects star in mixed content`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.material3.Text

            fun test() {
                Text(text = "4.5 ★")
            }
            """,
        ).indented()

        lint().files(code, textStub())
            .allowMissingSdk()
            .issues(UnicodeIconUsageDetector.ISSUE)
            .run()
            .expectErrorCount(1)
            .expectContains("BpkStarRating")
    }

    @Test
    fun `detects heart and suggests BpkSaveButton`() {
        val code = kotlin(
            """
            package test
            import net.skyscanner.backpack.compose.text.BpkText

            fun test() {
                BpkText(text = "♥")
            }
            """,
        ).indented()

        lint().files(code, bpkTextStub())
            .allowMissingSdk()
            .issues(UnicodeIconUsageDetector.ISSUE)
            .run()
            .expectErrorCount(1)
            .expectContains("BpkSaveButton")
    }

    @Test
    fun `allows normal text`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.material3.Text

            fun test() {
                Text(text = "Hello")
            }
            """,
        ).indented()

        lint().files(code, textStub())
            .allowMissingSdk()
            .issues(UnicodeIconUsageDetector.ISSUE)
            .run()
            .expectClean()
    }

    @Test
    fun `detects flight symbol`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.material3.Text

            fun test() {
                Text(text = "✈")
            }
            """,
        ).indented()

        lint().files(code, textStub())
            .allowMissingSdk()
            .issues(UnicodeIconUsageDetector.ISSUE)
            .run()
            .expectErrorCount(1)
            .expectContains("BpkIcon.Flight")
    }

    @Test
    fun `detects cross symbol and suggests BpkIcon Close`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.material3.Text

            fun test() {
                Text(text = "✗")
            }
            """,
        ).indented()

        lint().files(code, textStub())
            .allowMissingSdk()
            .issues(UnicodeIconUsageDetector.ISSUE)
            .run()
            .expectErrorCount(1)
            .expectContains("BpkIcon.Close")
    }

    @Test
    fun `detects multiple different symbols in one string`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.material3.Text

            fun test() {
                Text(text = "✓ → ★")
            }
            """,
        ).indented()

        lint().files(code, textStub())
            .allowMissingSdk()
            .issues(UnicodeIconUsageDetector.ISSUE)
            .run()
            .expectErrorCount(1)
            .expectContains("BpkIcon.Tick")
            .expectContains("BpkIcon.ArrowRight")
            .expectContains("BpkStarRating")
    }

    @Test
    fun `detects emoji search icon`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.material3.Text

            fun test() {
                Text(text = "\uD83D\uDD0D")
            }
            """,
        ).indented()

        lint().files(code, textStub())
            .allowMissingSdk()
            .issues(UnicodeIconUsageDetector.ISSUE)
            .run()
            .expectErrorCount(1)
            .expectContains("BpkIcon.Search")
    }

    @Test
    fun `detects emoji car icon`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.material3.Text

            fun test() {
                Text(text = "\uD83D\uDE97")
            }
            """,
        ).indented()

        lint().files(code, textStub())
            .allowMissingSdk()
            .issues(UnicodeIconUsageDetector.ISSUE)
            .run()
            .expectErrorCount(1)
            .expectContains("BpkIcon.Cars")
    }

    @Test
    fun `detects via material compose Text`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.material.Text

            fun test() {
                Text(text = "★")
            }
            """,
        ).indented()

        lint().files(code, materialTextStub())
            .allowMissingSdk()
            .issues(UnicodeIconUsageDetector.ISSUE)
            .run()
            .expectErrorCount(1)
            .expectContains("BpkStarRating")
    }

    @Test
    fun `allows text with dollar sign`() {
        val code = kotlin(
            """
            package test
            import androidx.compose.material3.Text

            fun test() {
                Text(text = "Price: 100")
            }
            """,
        ).indented()

        lint().files(code, textStub())
            .allowMissingSdk()
            .issues(UnicodeIconUsageDetector.ISSUE)
            .run()
            .expectClean()
    }

    private fun textStub() = kotlin(
        """
        package androidx.compose.material3

        fun Text(text: String) {}
        """,
    ).indented()

    private fun materialTextStub() = kotlin(
        """
        package androidx.compose.material

        fun Text(text: String) {}
        """,
    ).indented()

    private fun bpkTextStub() = kotlin(
        """
        package net.skyscanner.backpack.compose.text

        fun BpkText(text: String) {}
        """,
    ).indented()
}
