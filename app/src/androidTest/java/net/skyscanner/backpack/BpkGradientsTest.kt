package net.skyscanner.backpack

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.view.View
import com.facebook.testing.screenshot.Screenshot
import com.facebook.testing.screenshot.ViewHelpers
import net.skyscanner.backpack.badge.BpkBadge
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkGradientsTest: BpkSnapshotTest()  {
  private lateinit var testContext: Context

  @Before
  fun setup() {
    testContext = InstrumentationRegistry.getTargetContext()
    setDimensions(100,100)
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
