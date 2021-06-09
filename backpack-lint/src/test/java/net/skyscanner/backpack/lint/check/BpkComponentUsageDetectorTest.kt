/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

@Suppress("UnstableApiUsage")
class BpkComponentUsageDetectorTest {

  @Test
  fun `warning when extending native component`() {
    lint()
      .files(
        kotlin(
          """import android.widget.Button
import android.content.Context

class CustomButton(context: Context) : Button(context)"""
        )
      )
      .issues(BpkComponentUsageDetector.ISSUE)
      .run()
      .expectWarningCount(1)
      .expect(
        """
src/CustomButton.kt:4: Warning: Backpack component available for android.widget.Button. Use net.skyscanner.backpack.button.BpkButton instead. More info at https://backpack.github.io/components/button [BpkComponentUsage]
class CustomButton(context: Context) : Button(context)
      ~~~~~~~~~~~~
0 errors, 1 warnings
      """
      )
  }

  @Test
  fun `warning when instantiating native component`() {
    lint()
      .files(
        kotlin(
          """import android.widget.Button

class View(context: Context) {
  private val button = Button(context)
}"""
        )
      )
      .issues(BpkComponentUsageDetector.ISSUE)
      .run()
      .expectWarningCount(1)
      .expect(
        """
src/View.kt:4: Warning: Backpack component available for android.widget.Button. Use net.skyscanner.backpack.button.BpkButton instead. More info at https://backpack.github.io/components/button [BpkComponentUsage]
  private val button = Button(context)
                       ~~~~~~~~~~~~~~~
0 errors, 1 warnings
      """
      )
  }

  @Test
  fun `warning when using native component in xml`() {
    lint()
      .files(
        xml(
          "res/layout/native_button.xml",
          """<?xml version="1.0" encoding="utf-8"?>
<Button xmlns:android="http://schemas.android.com/apk/res/android"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content" />"""
        )
      )
      .issues(BpkComponentUsageDetector.ISSUE)
      .run()
      .expectWarningCount(1)
      .expect(
        """
res/layout/native_button.xml:2: Warning: Backpack component available for Button. Use net.skyscanner.backpack.button.BpkButton instead. More info at https://backpack.github.io/components/button [BpkComponentUsage]
<Button xmlns:android="http://schemas.android.com/apk/res/android"
^
0 errors, 1 warnings
      """
      )
  }

  @Test
  fun `warning when using static method of native component`() {
    lint()
      .files(
        kotlin(
          """import android.widget.Toast
import android.content.Context

class View(private val context: Context) {
  fun showToast() {
    Toast.makeText(context, "Toast!", Toast.LENGTH_SHORT)
  }
}"""
        )
      )
      .issues(BpkComponentUsageDetector.ISSUE)
      .run()
      .expectWarningCount(1)
      .expect(
        """
src/View.kt:6: Warning: Backpack component available for android.widget.Toast. Use net.skyscanner.backpack.toast.BpkToast instead. More info at https://backpack.github.io/components/toast [BpkComponentUsage]
    Toast.makeText(context, "Toast!", Toast.LENGTH_SHORT)
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
0 errors, 1 warnings
      """
      )
  }

  @Test
  fun `clean when extending bpk component`() {
    lint()
      .files(
        kotlin(
          """import net.skyscanner.backpack.button.BpkButton
import android.content.Context

class CustomButton(context: Context) : BpkButton(context)"""
        )
      )
      .issues(BpkComponentUsageDetector.ISSUE)
      .run()
      .expectClean()
  }

  @Test
  fun `clean when instantiating bpk component`() {
    lint()
      .files(
        kotlin(
          """import net.skyscanner.backpack.button.BpkButton

class View(context: Context) {
private val button = BpkButton(context)
}"""
        )
      )
      .issues(BpkComponentUsageDetector.ISSUE)
      .run()
      .expectClean()
  }

  @Test
  fun `clean when using bpk component in xml`() {
    lint()
      .files(
        xml(
          "res/layout/backpack_button.xml",
          """<?xml version="1.0" encoding="utf-8"?>
<net.skyscanner.backpack.BpkButton xmlns:android="http://schemas.android.com/apk/res/android"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content" />"""
        )
      )
      .issues(BpkComponentUsageDetector.ISSUE)
      .run()
      .expectClean()
  }

  @Test
  fun `clean when using static method of bpk component`() {
    lint()
      .files(
        kotlin(
          """import net.skyscanner.backpack.toast.BpkToast
import android.content.Context

class View(private val context: Context) {
  fun showToast() {
    BpkToast.makeText(context, "Toast!", BpkToast.LENGTH_SHORT)
  }
}"""
        )
      )
      .issues(BpkComponentUsageDetector.ISSUE)
      .run()
      .expectClean()
  }
}
