package net.skyscanner.backpack.text

import android.content.Context
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import net.skyscanner.backpack.R
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class BpkTextTest {
  private val testString: String = "Test"
  private lateinit var context: Context

  @Before
  fun setUp() {
    context = InstrumentationRegistry.getInstrumentation().targetContext
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
    Assert.assertEquals(true, text.emphasize)
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

  @Test
  fun emphasized_old() {
    val text = BpkText(context).apply {
      text = testString
      emphasize = true
    }

    val textSizePx = text.textSize
    val textSizeSp = textSizePx / context.resources.displayMetrics.scaledDensity

    val expectedAttributes = context.obtainStyledAttributes(R.style.bpkTextBaseEmphasized, R.styleable.BpkTextStyle)
    val expectedTextSizeSp = expectedAttributes.getDimensionPixelOffset(R.styleable.BpkTextStyle_android_textSize, 0) / context.resources.displayMetrics.scaledDensity

    Assert.assertEquals(expectedTextSizeSp, textSizeSp)
    Assert.assertEquals(testString, text.text)
    Assert.assertEquals(true, text.emphasize)
  }

  @Test
  fun weight_emphasized_compatibility() {
    val text = BpkText(context).apply {
      text = testString
      weight = BpkText.Weight.EMPHASIZED
    }

    Assert.assertTrue(text.emphasize)

    text.weight = BpkText.Weight.NORMAL
    Assert.assertFalse(text.emphasize)

    text.emphasize = true
    Assert.assertTrue(text.emphasize)
    Assert.assertEquals(BpkText.Weight.EMPHASIZED, text.weight)
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
}
