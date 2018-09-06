package net.skyscanner.backpack.panel


import android.support.test.runner.AndroidJUnit4
import android.widget.TextView
import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkPanelTest: BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(32,96)
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
}
