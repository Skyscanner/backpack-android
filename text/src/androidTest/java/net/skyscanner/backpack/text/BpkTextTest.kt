package net.skyscanner.backpack.text

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import net.skyscanner.backpack.core.R
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkTextTest {

  val TEST_STRING: String = "Test";

  @Test
  fun default_values() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val text = BpkText(context)
    text.text = TEST_STRING;

    var textSizePx = text.getTextSize();
    var textSizeSp = textSizePx / context.resources.displayMetrics.scaledDensity

    val expectedAttributes = context.obtainStyledAttributes(net.skyscanner.backpack.core.R.style.bpkTextBase, net.skyscanner.backpack.core.R.styleable.BpkTextStyle)
    val expectedTextSizeSp = expectedAttributes.getDimensionPixelOffset(net.skyscanner.backpack.core.R.styleable.BpkTextStyle_android_textSize, 0) / context.resources.displayMetrics.scaledDensity

    Assert.assertEquals(expectedTextSizeSp, textSizeSp)
    Assert.assertEquals(TEST_STRING, text.text)
  }

  @Test
  fun emphasized() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val text = BpkText(context)
    text.text = TEST_STRING;
    text.emphasize =  true;

    var textSizePx = text.getTextSize();
    var textSizeSp = textSizePx / context.resources.displayMetrics.scaledDensity

    val expectedAttributes = context.obtainStyledAttributes(net.skyscanner.backpack.core.R.style.bpkTextBase, net.skyscanner.backpack.core.R.styleable.BpkTextStyle)
    val expectedTextSizeSp = expectedAttributes.getDimensionPixelOffset(net.skyscanner.backpack.core.R.styleable.BpkTextStyle_android_textSize, 0) / context.resources.displayMetrics.scaledDensity

    Assert.assertEquals(expectedTextSizeSp, textSizeSp)
    Assert.assertEquals(TEST_STRING, text.text)
    Assert.assertEquals(true, text.emphasize)
  }
}
