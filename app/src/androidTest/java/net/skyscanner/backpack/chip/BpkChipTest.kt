package net.skyscanner.backpack.chip

import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkChipTest : BpkSnapshotTest() {
  @Before
  fun setup() {
    setDimensions(36, 100)
  }

  @Test
  fun screenshotTestDefault() {
    val view = BpkChip(testContext)
    view.text = "tag"
    snap(view)
  }

  @Test
  fun screenshotTestDisabled() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.disabled = true
    snap(view)
  }

  @Test
  fun screenshotTestSelected() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.isSelected = true
    snap(view)
  }

  @Test
  fun screenshotTestSelected_withTheme() {
    val view = BpkChip(createThemedContext(testContext))
    view.text = "tag"
    view.isSelected = true
    snap(view)
  }

  @Test
  fun screenshotTestCustomBackground() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.chipBackgroundColor = ContextCompat.getColor(testContext, R.color.bpkErfoud)
    snap(view)
  }

  @Test
  fun screenshotTestCustomSelectedBackground() {
    val view = BpkChip(testContext)
    view.text = "tag"
    view.selectedBackgroundColor = ContextCompat.getColor(testContext, R.color.bpkPanjin)
    view.toggle()
    snap(view)
  }
}
