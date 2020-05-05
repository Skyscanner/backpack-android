/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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
import android.graphics.Typeface
import android.view.ContextThemeWrapper
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import net.skyscanner.backpack.R
import net.skyscanner.backpack.util.BpkTheme
import net.skyscanner.backpack.util.ResourcesUtil
import net.skyscanner.backpack.util.unsafeLazy
import org.junit.Before
import net.skyscanner.backpack.test.R as TestR

@RunWith(AndroidJUnit4::class)
class BpkTextTest {
  private val testString: String = "Test"
  private lateinit var context: Context

  @Before
  fun setUp() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
    BpkTheme.applyDefaultsToContext(context)
  }

  @Test
  fun default() {
    val text = BpkText(context).apply {
      text = testString
    }

    val textSizePx = text.textSize
    val textSizeSp = textSizePx / context.resources.displayMetrics.scaledDensity

    val expectedAttributes = context.obtainStyledAttributes(R.style.bpkTextBase, R.styleable.BpkTextStyle)
    val expectedTextSizeSp = expectedAttributes.getDimensionPixelOffset(R.styleable.BpkTextStyle_android_textSize, 0) / context.resources.displayMetrics.scaledDensity

    Assert.assertEquals(expectedTextSizeSp, textSizeSp)
    Assert.assertEquals(testString, text.text)
  }

  @Test
  fun emphasized() {
    val text = BpkText(context).apply {
      text = testString
      weight = BpkText.Weight.EMPHASIZED
    }

    val textSizePx = text.textSize
    val textSizeSp = textSizePx / context.resources.displayMetrics.scaledDensity

    val expectedAttributes = context.obtainStyledAttributes(R.style.bpkTextBaseEmphasized, R.styleable.BpkTextStyle)
    val expectedTextSizeSp = expectedAttributes.getDimensionPixelOffset(R.styleable.BpkTextStyle_android_textSize, 0) / context.resources.displayMetrics.scaledDensity

    Assert.assertEquals(expectedTextSizeSp, textSizeSp)
    Assert.assertEquals(testString, text.text)
    Assert.assertEquals(BpkText.Weight.EMPHASIZED, text.weight)
  }

  @Test
  fun heavy() {
    val text = BpkText(context).apply {
      text = testString
      textStyle = BpkText.XL
      weight = BpkText.Weight.HEAVY
    }

    val textSizePx = text.textSize
    val textSizeSp = textSizePx / context.resources.displayMetrics.scaledDensity

    val expectedAttributes = context.obtainStyledAttributes(R.style.bpkTextXlHeavy, R.styleable.BpkTextStyle)
    val expectedTextSizeSp = expectedAttributes.getDimensionPixelOffset(R.styleable.BpkTextStyle_android_textSize, 0) / context.resources.displayMetrics.scaledDensity

    Assert.assertEquals(expectedTextSizeSp, textSizeSp)
    Assert.assertEquals(testString, text.text)
  }

  @Test(expected = IllegalStateException::class)
  fun throw_when_invalid_style() {
    BpkText(context).apply {
      textStyle = 99
    }
  }

  @Test(expected = IllegalStateException::class)
  fun throw_when_heavy_unsupported() {
    BpkText(context).apply {
      textStyle = BpkText.SM
      weight = BpkText.Weight.HEAVY
    }
  }

  private val textDefinitions by unsafeLazy {
    val fontsAccessor = { style: Int, weight: BpkText.Weight -> { context: Context -> BpkText.getFont(context, style, weight) } }
    arrayOf(
      arrayOf("bpkTextBase", "sans-serif", 16, -0.0125f, fontsAccessor(BpkText.BASE, BpkText.Weight.NORMAL)),
      arrayOf("bpkTextCaps", "sans-serif", 10, 0.04f, fontsAccessor(BpkText.CAPS, BpkText.Weight.NORMAL)),
      arrayOf("bpkTextLg", "sans-serif", 20, -0.02f, fontsAccessor(BpkText.LG, BpkText.Weight.NORMAL)),
      arrayOf("bpkTextSm", "sans-serif", 14, 0f, fontsAccessor(BpkText.SM, BpkText.Weight.NORMAL)),
      arrayOf("bpkTextXl", "sans-serif", 24, -0.024999999999999998f, fontsAccessor(BpkText.XL, BpkText.Weight.NORMAL)),
      arrayOf("bpkTextXs", "sans-serif", 12, 0f, fontsAccessor(BpkText.XS, BpkText.Weight.NORMAL)),
      arrayOf("bpkTextXxl", "sans-serif", 30, -0.02666666666666667f, fontsAccessor(BpkText.XXL, BpkText.Weight.NORMAL)),
      arrayOf("bpkTextXxxl", "sans-serif", 36, -0.027777777777777776f, fontsAccessor(BpkText.XXXL, BpkText.Weight.NORMAL)),
      arrayOf("bpkTextBaseEmphasized", "sans-serif-medium", 16, -0.0125f, fontsAccessor(BpkText.BASE, BpkText.Weight.EMPHASIZED)),
      arrayOf("bpkTextCapsEmphasized", "sans-serif-medium", 10, 0.04f, fontsAccessor(BpkText.CAPS, BpkText.Weight.EMPHASIZED)),
      arrayOf("bpkTextLgEmphasized", "sans-serif-medium", 20, -0.02f, fontsAccessor(BpkText.LG, BpkText.Weight.EMPHASIZED)),
      arrayOf("bpkTextSmEmphasized", "sans-serif-medium", 14, 0f, fontsAccessor(BpkText.SM, BpkText.Weight.EMPHASIZED)),
      arrayOf("bpkTextXlEmphasized", "sans-serif-medium", 24, -0.024999999999999998f, fontsAccessor(BpkText.XL, BpkText.Weight.EMPHASIZED)),
      arrayOf("bpkTextXsEmphasized", "sans-serif-medium", 12, 0f, fontsAccessor(BpkText.XS, BpkText.Weight.EMPHASIZED)),
      arrayOf("bpkTextXxlEmphasized", "sans-serif-medium", 30, -0.02666666666666667f, fontsAccessor(BpkText.XXL, BpkText.Weight.EMPHASIZED)),
      arrayOf("bpkTextXxxlEmphasized", "sans-serif-medium", 36, -0.027777777777777776f, fontsAccessor(BpkText.XXXL, BpkText.Weight.EMPHASIZED)),
      arrayOf("bpkTextXlHeavy", "sans-serif-black", 24, -0.024999999999999998f, fontsAccessor(BpkText.XL, BpkText.Weight.HEAVY)),
      arrayOf("bpkTextXxlHeavy", "sans-serif-black", 30, -0.02666666666666667f, fontsAccessor(BpkText.XXL, BpkText.Weight.HEAVY)),
      arrayOf("bpkTextXxxlHeavy", "sans-serif-black", 36, -0.027777777777777776f, fontsAccessor(BpkText.XXXL, BpkText.Weight.HEAVY))
    )
  }

  @Test
  fun getFont() {
    textDefinitions.forEach { test ->
      val message = test[0] as String
      val fontFamily = test[1] as String
      val fontSize = test[2] as Int
      val letterSpacing = test[3] as Float
      val getFont = test[4] as (context: Context) -> BpkText.FontDefinition

      val font = getFont.invoke(context)

      Assert.assertEquals(message, Typeface.create(fontFamily, Typeface.NORMAL), font.typeface)
      Assert.assertEquals(message, font.fontSize, ResourcesUtil.dpToPx(fontSize, context))
      Assert.assertEquals(message, font.letterSpacing, letterSpacing)
    }
  }

  @Test
  fun getFont_withCustomFont() {
    val withCustomFont = ContextThemeWrapper(context, TestR.style.TestTextCustomFont)

    textDefinitions.forEach { test ->
      val message = test[0] as String
      val fontSize = test[2] as Int
      val getFont = test[4] as (context: Context) -> BpkText.FontDefinition

      val font = getFont.invoke(withCustomFont)

      val expectedFont = when {
        message.contains("Emphasized") ->
          Typeface.create("cursive", Typeface.NORMAL)
        message.contains("Heavy") ->
          Typeface.create("casual", Typeface.NORMAL)
        else ->
          ResourcesCompat.getFont(withCustomFont, TestR.font.shadows_into_light)
      }

      Assert.assertEquals(message, expectedFont, font.typeface)
      Assert.assertEquals(message, font.fontSize, ResourcesUtil.dpToPx(fontSize, context))
      Assert.assertEquals(message, font.letterSpacing, null)
    }
  }

  @Test
  fun applyTo_TextView() {
    val font = BpkText.getFont(context, BpkText.BASE)
    val subject = TextView(context)
    subject.text = "Foo"

    font.applyTo(subject)

    Assert.assertEquals(font.typeface, subject.typeface)
    Assert.assertEquals(font.letterSpacing, subject.letterSpacing)
    Assert.assertEquals(font.fontSize.toFloat(), subject.textSize)
  }

  @Test
  fun applyTo_TextView_withCustomFont() {
    val withCustomFont = ContextThemeWrapper(context, TestR.style.TestTextCustomFont)

    val font = BpkText.getFont(withCustomFont, BpkText.BASE)
    val subject = TextView(withCustomFont)
    subject.text = "Foo"

    font.applyTo(subject)

    Assert.assertEquals(font.typeface, subject.typeface)
    Assert.assertEquals(0f, subject.letterSpacing)
    Assert.assertEquals(font.fontSize.toFloat(), subject.textSize)
  }

  @Test
  fun applyTo_Paint() {
    val font = BpkText.getFont(context, BpkText.BASE)
    val subject = Paint()
    font.applyTo(subject)

    Assert.assertEquals(font.typeface, subject.typeface)
    Assert.assertEquals(font.letterSpacing, subject.letterSpacing)
    Assert.assertEquals(font.fontSize.toFloat(), subject.textSize)
  }

  @Test
  fun applyTo_Paint_withCustomFont() {
    val withCustomFont = ContextThemeWrapper(context, TestR.style.TestTextCustomFont)

    val font = BpkText.getFont(withCustomFont, BpkText.BASE)
    val subject = Paint()
    font.applyTo(subject)

    Assert.assertEquals(font.typeface, subject.typeface)
    Assert.assertEquals(0f, subject.letterSpacing)
    Assert.assertEquals(font.fontSize.toFloat(), subject.textSize)
  }
}
