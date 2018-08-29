package net.skyscanner.backpack


import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.widget.TextView
import com.facebook.testing.screenshot.Screenshot
import com.facebook.testing.screenshot.ViewHelpers
import net.skyscanner.backpack.badge.BpkBadge
import net.skyscanner.backpack.panel.BpkPanel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkPanelTest {
  private lateinit var testContext: Context

  @Before
  fun setup() {
    testContext = InstrumentationRegistry.getTargetContext()
  }

  @Test
  fun screenshotTestPanelDefault() {
    val panel = BpkPanel(testContext)
    val text = TextView(testContext)
    text.text = "message"
    panel.addView(text)

    ViewHelpers.setupView(panel)
      .layout()
    Screenshot.snap(panel)
      .record()
  }

  @Test
  fun screenshotTestPanelWithPadding() {
    val panel = BpkPanel(testContext)
    val text = TextView(testContext)
    text.text = "message"
    panel.addView(text)
    panel.padding = true
    ViewHelpers.setupView(panel)
      .layout()
    Screenshot.snap(panel)
      .record()
  }

  @Test
  fun screenshotTestPanelWithoutPadding() {
    val panel = BpkPanel(testContext)
    val text = TextView(testContext)
    text.text = "message"
    panel.addView(text)
    panel.padding = false
    ViewHelpers.setupView(panel)
      .layout()
    Screenshot.snap(panel)
      .record()
  }
}
