package net.skyscanner.backpack.nudger

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * FIXME
 *
 * The buttons show up without icons. The snapshot test framework is not rendering
 * the icons when using `setCompoundDrawablesRelativeWithIntrinsicBounds` instead of
 * `setCompoundDrawablesWithIntrinsicBounds` on API-21 which is required to support RTL. We can add
 * them when we update the emulators on the CI or move to a device farm for snapshot testing
 */
@RunWith(AndroidJUnit4::class)
class BpkNudgerTest : BpkSnapshotTest() {

  private val nudger = BpkNudger(testContext)

  @Before
  fun setup() {
    setDimensions(50, 150)
  }

  @Test
  fun screenshotTestNudger_minusDisabled() {
    snap(nudger)
  }

  @Test
  fun screenshotTestNudger_plusDisabled() {
    nudger.value = 10
    nudger.maximumValue = 10
    snap(nudger)
  }

  @Test
  fun screenshotTestNudger_standard() {
    nudger.value = 5
    snap(nudger)
  }

  @Test
  fun screenshotTestNudger_withTheme() {
    val nudger = BpkNudger(createThemedContext(testContext))
    snap(nudger)
  }
}
