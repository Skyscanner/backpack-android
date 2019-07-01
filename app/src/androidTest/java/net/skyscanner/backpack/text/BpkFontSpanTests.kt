package net.skyscanner.backpack.text

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkFontSpanTests : BpkSnapshotTest() {

  private val textView = TextView(testContext).apply { setBackgroundColor(Color.WHITE) }

  @Before
  fun setup() {
    setDimensions(40, 120)
  }

  @Test
  fun screenshotTestFontSpan_Default() {
    textView.text = "Test"
    snap(textView)
  }

  @Test
  fun screenshotTestFontSpan_Custom() {
    val span = BpkFontSpan(testContext, BpkText.XXL, BpkText.Weight.EMPHASIZED)
    textView.text = SpannableStringBuilder().append("Test", span, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    snap(textView)
  }

  @Test
  fun screenshotTestFontSpan_Injected() {
    val font = BpkText.getFont(testContext, BpkText.XXL, BpkText.Weight.EMPHASIZED)
    textView.text = SpannableStringBuilder().append("Test", BpkFontSpan(font), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    snap(textView)
  }
}
