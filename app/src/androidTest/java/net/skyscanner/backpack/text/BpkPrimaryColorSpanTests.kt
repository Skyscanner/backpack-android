package net.skyscanner.backpack.text

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkPrimaryColorSpanTests : BpkSnapshotTest() {

  private val textView = TextView(testContext).apply { setBackgroundColor(Color.WHITE) }

  @Before
  fun setup() {
    setDimensions(20, 50)
  }

  @Test
  fun screenshotTestPrimaryColorSpan_Default() {
    textView.text = "Test"
    snap(textView)
  }

  @Test
  fun screenshotTestPrimaryColorSpan_Custom() {
    val span = BpkPrimaryColorSpan(testContext)
    textView.text = SpannableStringBuilder().append("Test", span, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    snap(textView)
  }

  @Test
  fun screenshotTestPrimaryColorSpan_withTheme() {
    val span = BpkPrimaryColorSpan(createThemedContext(testContext))
    textView.text = SpannableStringBuilder().append("Test", span, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    snap(textView)
  }
}
