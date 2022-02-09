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

package net.skyscanner.backpack.text

import android.content.Context
import android.graphics.Paint
import android.view.ContextThemeWrapper
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.TestActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import net.skyscanner.backpack.test.R as TestR

@RunWith(AndroidJUnit4::class)
class BpkTextTest {
  private val testString: String = "Test"
  private lateinit var context: Context

  @get:Rule
  internal var activityRule: ActivityTestRule<TestActivity> =
    ActivityTestRule(TestActivity::class.java)

  @Before
  fun setUp() {
    context = activityRule.activity
  }

  @Test
  fun default() {
    val text = BpkText(context).apply {
      text = testString
    }

    val textSizePx = text.textSize
    val textSizeSp = textSizePx / context.resources.displayMetrics.scaledDensity

    val expectedAttributes = context.obtainStyledAttributes(R.style.bpkTextBodyDefault, R.styleable.BpkTextStyle)
    val expectedTextSizeSp = expectedAttributes.getDimensionPixelOffset(R.styleable.BpkTextStyle_android_textSize, 0) /
      context.resources.displayMetrics.scaledDensity

    Assert.assertEquals(expectedTextSizeSp, textSizeSp)
    Assert.assertEquals(testString, text.text)
  }

  @Test
  fun applyTo_TextView() {
    val font = BpkText.getFont(context, BpkText.TextStyle.BodyDefault)
    val subject = TextView(context)
    subject.text = "Foo"

    font.applyTo(subject)

    Assert.assertEquals(font.typeface, subject.typeface)
    Assert.assertEquals(font.letterSpacing ?: 0f, subject.letterSpacing)
    Assert.assertEquals(font.fontSize.toFloat(), subject.textSize)
  }

  @Test
  fun applyTo_TextView_withCustomFont() {
    val withCustomFont = ContextThemeWrapper(context, TestR.style.TestTextCustomFont)

    val font = BpkText.getFont(withCustomFont, BpkText.TextStyle.BodyDefault)
    val subject = TextView(withCustomFont)
    subject.text = "Foo"

    font.applyTo(subject)

    Assert.assertEquals(font.typeface, subject.typeface)
    Assert.assertEquals(0f, subject.letterSpacing)
    Assert.assertEquals(font.fontSize.toFloat(), subject.textSize)
  }

  @Test
  fun applyTo_Paint() {
    val font = BpkText.getFont(context, BpkText.TextStyle.BodyDefault)
    val subject = Paint()
    font.applyTo(subject)

    Assert.assertEquals(font.typeface, subject.typeface)
    Assert.assertEquals(font.letterSpacing ?: 0f, subject.letterSpacing)
    Assert.assertEquals(font.fontSize.toFloat(), subject.textSize)
  }

  @Test
  fun applyTo_Paint_withCustomFont() {
    val withCustomFont = ContextThemeWrapper(context, TestR.style.TestTextCustomFont)

    val font = BpkText.getFont(withCustomFont, BpkText.TextStyle.BodyDefault)
    val subject = Paint()
    font.applyTo(subject)

    Assert.assertEquals(font.typeface, subject.typeface)
    Assert.assertEquals(0f, subject.letterSpacing)
    Assert.assertEquals(font.fontSize.toFloat(), subject.textSize)
  }
}
