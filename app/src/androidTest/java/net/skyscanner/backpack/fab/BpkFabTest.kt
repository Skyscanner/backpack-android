package net.skyscanner.backpack.fab

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkFabTest : BpkSnapshotTest() {

  private val fab = BpkFab(testContext)

  @Before
  fun setup() {
    setDimensions(56, 56)
  }

  @Test
  fun screenshotTestFab_Default() {
    snap(fab)
  }

  @Test
  fun screenshotTestFab_CustomIcon() {
    fab.setImageResource(R.drawable.bpk_search)
    snap(fab)
  }

  @Test
  fun screenshotTestFab_CustomIcon_Disabled() {
    fab.setImageResource(R.drawable.bpk_search)
    fab.isEnabled = false
    snap(fab)
  }

  @Test
  fun screenshotTestFab_withTheme() {
    val fab = BpkFab(createThemedContext(testContext))
    fab.setImageResource(R.drawable.bpk_search)
    snap(fab)
  }
}
