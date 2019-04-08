package net.skyscanner.backpack.toggle

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
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

  @Test
  fun screenshotTestSwitchDefaultChecked_withTheme() {
    snap(BpkSwitch(createThemedContext(testContext)).apply { isChecked = true })
  }
}
