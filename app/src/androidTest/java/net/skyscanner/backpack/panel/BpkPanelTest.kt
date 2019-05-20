package net.skyscanner.backpack.panel

import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkPanelTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(100, 100)
  }

  @Test
  fun screenshotTestPanelDefault() {
    val panel = BpkPanel(testContext)
    val text = TextView(testContext)
    text.text = "message"
    panel.addView(text)
    snap(panel)
  }

  @Test
  fun screenshotTestPanelWithPadding() {
    val panel = BpkPanel(testContext)
    val text = TextView(testContext)
    text.text = "message"
    panel.addView(text)
    panel.padding = true
    snap(panel)
  }

  @Test
  fun screenshotTestPanelWithoutPadding() {
    val panel = BpkPanel(testContext)
    val text = TextView(testContext)
    text.text = "message"
    panel.addView(text)
    panel.padding = false
    snap(panel)
  }

  @Test
  fun screenshotTestPanel_withTheme() {
    val panel = BpkPanel(createThemedContext(testContext))
    val text = TextView(createThemedContext(testContext))
    text.text = "message"
    panel.addView(text)
    snap(panel)
  }
}
