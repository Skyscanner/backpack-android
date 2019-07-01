package net.skyscanner.backpack.checkbox

import android.graphics.Color
import android.view.View
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkCheckboxTests : BpkSnapshotTest() {

  private val checkbox = BpkCheckbox(testContext).apply {
    text = "Checkbox"
    setBackgroundColor(Color.WHITE)
  }

  @Before
  fun setup() {
    setDimensions(40, 120)
  }

  @Test
  fun screenshotTestCheckbox_Default() {
    snap(checkbox)
  }

  @Test
  fun screenshotTestCheckbox_Checked() {
    checkbox.isChecked = true
    snap(checkbox)
  }

  @Test
  fun screenshotTestCheckbox_Disabled() {
    checkbox.isEnabled = false
    snap(checkbox)
  }

  @Test
  fun screenshotTestCheckbox_CheckedDisabled() {
    checkbox.isEnabled = false
    checkbox.isChecked = true
    snap(checkbox)
  }

  @Test
  fun screenshotTestCheckbox_rtl() {
    checkbox.layoutDirection = View.LAYOUT_DIRECTION_RTL
    snap(checkbox)
  }

  @Test
  fun screenshotTestCheckbox_withTheme() {
    val checkbox = BpkCheckbox(createThemedContext(testContext)).apply {
      setBackgroundColor(Color.WHITE)
      text = "Checkbox"
    }
    checkbox.isChecked = true
    snap(checkbox)
  }
}
