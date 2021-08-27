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

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.java
import com.android.tools.lint.checks.infrastructure.TestFile
import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin
import com.android.tools.lint.checks.infrastructure.TestFiles.xml
import com.android.tools.lint.checks.infrastructure.TestLintResult
import com.android.tools.lint.checks.infrastructure.TestLintTask
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

class CustomButton(context: Context) : Button(context)"""
        ),
        button()
      )
      .runCheck()
      .expectWarningCount(1)
      .expect(
        """
src/CustomButton.kt:3: Warning: Backpack component available for android.widget.Button. Use net.skyscanner.backpack.button.BpkButton instead. More info at https://backpack.github.io/components/button [BpkComponentUsage]
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
        ),
        button()
      )
      .runCheck()
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
      .runCheck()
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

class View(private val context: Context) {
  fun showToast() {
    Toast.makeText(context, "Toast!", Toast.LENGTH_SHORT)
  }
}"""
        ),
        toast()
      )
      .runCheck()
      .expectWarningCount(1)
      .expect(
        """
src/View.kt:5: Warning: Backpack component available for android.widget.Toast. Use net.skyscanner.backpack.toast.BpkToast instead. More info at https://backpack.github.io/components/toast [BpkComponentUsage]
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

class CustomButton(context: Context) : BpkButton(context)"""
        ),
        bpkButton()
      )
      .runCheck()
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
        ),
        bpkButton()
      )
      .runCheck()
      .expectClean()
  }

  @Test
  fun `clean when using bpk component in xml`() {
    lint()
      .files(
        xml(
          "res/layout/backpack_button.xml",
          """<?xml version="1.0" encoding="utf-8"?>
<net.skyscanner.backpack.button.BpkButton xmlns:android="http://schemas.android.com/apk/res/android"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content" />"""
        )
      )
      .runCheck()
      .expectClean()
  }

  @Test
  fun `clean when using static method of bpk component`() {
    lint()
      .files(
        kotlin(
          """import net.skyscanner.backpack.toast.BpkToast

class View(private val context: Context) {
  fun showToast() {
    BpkToast.makeText(context, "Toast!", BpkToast.LENGTH_SHORT)
  }
}"""
        ),
        bpkToast()
      )
      .runCheck()
      .expectClean()
  }

  private fun TestLintTask.runCheck(): TestLintResult =
    issues(BpkComponentUsageDetector.ISSUE)
      .allowMissingSdk()
      .run()

  private fun button(): TestFile =
    kotlin(
      """package android.widget

class Button(context: Context) : View(context)"""
    )

  private fun bpkButton(): TestFile =
    kotlin(
      """package net.skyscanner.backpack.button

class BpkButton(context: Context) : View(context)"""
    )

  private fun toast(): TestFile =
    java(
      """package android.widget;

public class Toast {
  public static int LENGTH_SHORT = 1;
  public static void makeText(Context context, String text, Int length) {}
}"""
    )

  private fun bpkToast(): TestFile =
    java(
      """package net.skyscanner.backpack.toast;

public class BpkToast {
  public static int LENGTH_SHORT = 1;
  public static void makeText(Context context, String text, Int length) {}
}"""
    )
}
