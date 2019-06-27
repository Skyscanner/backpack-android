package net.skyscanner.backpack.toast

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkToastTests : BpkSnapshotTest() {

  @Before
  fun setUp() {
    setDimensions(42, 120)
  }

  @Test
  fun screenshotTestToast_Default() {
    val toast = BpkToast.makeText(testContext, "Test", BpkToast.LENGTH_SHORT)
    toast.show()
    snap(toast.view)
  }

  @Test
  fun screenshotTestToast_Themed() {
    val toast = BpkToast.makeText(createThemedContext(testContext), "Test", BpkToast.LENGTH_SHORT)
    toast.show()
    snap(toast.view)
  }
}
