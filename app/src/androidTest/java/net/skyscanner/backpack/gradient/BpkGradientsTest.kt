package net.skyscanner.backpack.gradient

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.R
import net.skyscanner.backpack.createThemedContext
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
    val gradient = BpkGradients(testContext)
    val view = View(testContext)
    view.background = gradient
    snap(view)
  }

  @Test
  fun screenshotTestGradient_withCustomColor() {
    val gradient = BpkGradients(
      testContext,
      GradientDrawable.Orientation.TL_BR,
      intArrayOf(
        ContextCompat.getColor(testContext, R.color.bpkGreen500),
        ContextCompat.getColor(testContext, R.color.bpkGreen300)))

    val view = View(testContext)
    view.background = gradient
    snap(view)
  }

  @Test
  fun screenshotTestGradient_getPrimary() {
    val gradient = BpkGradients.getPrimary(testContext)
    val view = View(testContext)
    view.background = gradient
    snap(view)
  }

  @Test
  fun screenshotTestGradient_getPrimary_withTheme() {
    val gradient = BpkGradients.getPrimary(createThemedContext(testContext))
    val view = View(testContext)
    view.background = gradient
    snap(view)
  }

  @Test
  fun screenshotTestGradient_getPrimary_withCustomOrientation() {
    val gradient = BpkGradients.getPrimary(testContext, GradientDrawable.Orientation.BL_TR)
    val view = View(testContext)
    view.background = gradient
    snap(view)
  }

  @Test
  fun screenshotTestGradient() {
    val gradient = BpkGradients(testContext, GradientDrawable.Orientation.LEFT_RIGHT)
    val view = View(testContext)
    view.background = gradient
    snap(view)
  }
}
