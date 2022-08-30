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

package net.skyscanner.backpack.lint.check

import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin
import com.android.tools.lint.checks.infrastructure.TestFiles.xml
import com.android.tools.lint.checks.infrastructure.TestLintResult
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

@Suppress("UnstableApiUsage")
class BpkDeprecatedColorUsageDetectorTest {

  @Test
  fun `warning when using deprecated colour resource as xml attribute`() {
    lint()
      .files(
        xml(
          "res/layout/colour_attribute.xml",
          """<?xml version="1.0" encoding="utf-8"?>
<Button xmlns:android="http://schemas.android.com/apk/res/android"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:background="@color/bpkBackground"/>"""
        )
      )
      .runCheck()
      .expectWarningCount(1)
      .expect(
        """
res/layout/colour_attribute.xml:5: Warning: This colour is now deprecated. Please switch to the new semantic colours - see internal New Colours documentation. [BpkDeprecatedColorUsage]
  android:background="@color/bpkBackground"/>
                      ~~~~~~~~~~~~~~~~~~~~
0 errors, 1 warnings
      """
      )
  }

  @Test
  fun `warning when using deprecated colour resource as xml element`() {
    lint()
      .files(
        xml(
          "res/values/colors.xml",
          """<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="fancyColor">@color/bpkBackground</color>
</resources>"""
        )
      )
      .runCheck()
      .expectWarningCount(1)
      .expect(
        """
res/values/colors.xml:3: Warning: This colour is now deprecated. Please switch to the new semantic colours - see internal New Colours documentation. [BpkDeprecatedColorUsage]
    <color name="fancyColor">@color/bpkBackground</color>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
0 errors, 1 warnings
      """
      )
  }

  @Test
  fun `warning when using deprecated colour resource in code`() {
    lint()
      .files(
        kotlin(
          """class SomeClass(private val context: Context) {
    fun someFunction() {
        val color = context.getColor(R.color.bpkBackground)
    }
}"""
        )
      )
      .runCheck()
      .expectWarningCount(1)
      .expect(
        """
src/SomeClass.kt:3: Warning: This colour is now deprecated. Please switch to the new semantic colours - see internal New Colours documentation. [BpkDeprecatedColorUsage]
        val color = context.getColor(R.color.bpkBackground)
                                     ~~~~~~~~~~~~~~~~~~~~~
0 errors, 1 warnings
      """
      )
  }

  @Test
  fun `clean when using semantic colour as xml attribute`() {
    lint()
      .files(
        xml(
          "res/layout/colour_attribute.xml",
          """<?xml version="1.0" encoding="utf-8"?>
<Button xmlns:android="http://schemas.android.com/apk/res/android"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:background="@color/bpkCorePrimary"/>"""
        )
      )
      .runCheck()
      .expectClean()
  }

  @Test
  fun `clean when using semantic colour as xml element`() {
    lint()
      .files(
        xml(
          "res/values/colors.xml",
          """<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="fancyColor">@color/bpkCorePrimary</color>
</resources>"""
        )
      )
      .runCheck()
      .expectClean()
  }

  @Test
  fun `clean when using semantic colour in code`() {
    lint()
      .files(
        kotlin(
          """class SomeClass(private val context: Context) {
    fun someFunction() {
        val color = context.getColor(R.color.bpkCorePrimary)
    }
}"""
        )
      )
      .runCheck()
      .expectClean()
  }

  private fun TestLintTask.runCheck(): TestLintResult =
    issues(BpkDeprecatedColorUsageDetector.ISSUE)
      .allowMissingSdk()
      .run()
}
