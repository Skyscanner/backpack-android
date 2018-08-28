package net.skyscanner.backpack.text

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import net.skyscanner.backpack.core.R

@RunWith(AndroidJUnit4::class)
class BpkTextTest {
  private val testString: String = "Test"

  @Test
  fun default_values() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
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
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val text = BpkText(context).apply {
      text = testString
      emphasize = true
    }

    val textSizePx = text.textSize
    val textSizeSp = textSizePx / context.resources.displayMetrics.scaledDensity

    val expectedAttributes = context.obtainStyledAttributes(R.style.bpkTextBase, R.styleable.BpkTextStyle)
    val expectedTextSizeSp = expectedAttributes.getDimensionPixelOffset(R.styleable.BpkTextStyle_android_textSize, 0) / context.resources.displayMetrics.scaledDensity

    Assert.assertEquals(expectedTextSizeSp, textSizeSp)
    Assert.assertEquals(testString, text.text)
    Assert.assertEquals(true, text.emphasize)
  }
}
