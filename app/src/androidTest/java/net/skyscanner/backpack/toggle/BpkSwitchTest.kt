package net.skyscanner.backpack.toggle

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkSwitchTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(100, 100)
  }

  @Test
  fun screenshotTestSwitchDefault() {
    snap(BpkSwitch(testContext))
  }

  @Test
  fun screenshotTestSwitchDefaultChecked() {
    snap(BpkSwitch(testContext).apply { isChecked = true })
  }
}
