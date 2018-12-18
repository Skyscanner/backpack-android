package net.skyscanner.backpack.gradient

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkGradientsTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(100, 100)
  }

  @Test
  fun screenshotTestGradientDefault() {
    val gradient = BpkGradients.getPrimary(testContext)
    val view = View(testContext)
    view.background = gradient
    snap(view)
  }

  @Test
  fun screenshotTestGradient() {
    val gradient = BpkGradients.getPrimary(testContext, GradientDrawable.Orientation.LEFT_RIGHT)
    val view = View(testContext)
    view.background = gradient
    snap(view)
  }
}
