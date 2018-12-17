package net.skyscanner.backpack.chip

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkDismissibleChipTest : BpkSnapshotTest() {
  @Before
  fun setup() {
    setDimensions(36, 100)
  }

  @Test
  fun screenshotTestDefault() {
    val view = BpkDismissibleChip(testContext)
    view.text = "tag"
    snap(view)
  }

  @Test
  fun screenshotTestDisabled() {
    val view = BpkDismissibleChip(testContext)
    view.text = "tag"
    view.disabled = true
    snap(view)
  }
}
