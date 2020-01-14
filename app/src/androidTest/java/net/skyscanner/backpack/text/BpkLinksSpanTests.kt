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
class BpkLinksSpanTests : BpkSnapshotTest() {

  private val link = "https://backpack.github.io/"
  private val handler = { _: String ->
  }

  private val textView = TextView(testContext).apply { setBackgroundColor(Color.WHITE) }

  @Before
  fun setup() {
    setDimensions(20, 50)
  }

  @Test
  fun screenshotTestLinkSpan_Custom() {
    val span = BpkLinkSpan(testContext, link, handler)
    textView.text = SpannableStringBuilder().append("Test", span, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    snap(textView)
  }

  @Test
  fun screenshotTestLinkSpan_withTheme() {
    val span = BpkLinkSpan(createThemedContext(testContext), link, handler)
    textView.text = SpannableStringBuilder().append("Test", span, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    snap(textView)
  }
}
