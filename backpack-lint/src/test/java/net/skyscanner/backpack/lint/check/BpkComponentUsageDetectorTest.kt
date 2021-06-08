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
src/CustomButton.kt:4: Warning: Backpack component available for android.widget.Button. Use net.skyscanner.backpack.BpkButton instead [BpkComponentUsage]
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
src/View.kt:4: Warning: Backpack component available for android.widget.Button. Use net.skyscanner.backpack.BpkButton instead [BpkComponentUsage]
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
res/layout/native_button.xml:2: Warning: Backpack component available for Button. Use net.skyscanner.backpack.BpkButton instead [BpkComponentUsage]
<Button xmlns:android="http://schemas.android.com/apk/res/android"
^
0 errors, 1 warnings
      """
      )
  }

  @Test
  fun `clean when extending bpk component`() {
    lint()
      .files(
        kotlin(
          """import net.skyscanner.backpack.BpkButton
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
          """import net.skyscanner.backpack.BpkButton

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
}
