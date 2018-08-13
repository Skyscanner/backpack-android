package net.skyscanner.backpack

import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.v4.content.ContextCompat
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class BpkGradientsTest {

  @Test
  fun getPrimary() {
    val testContext = RuntimeEnvironment.application.applicationContext
    val expectedGradient = GradientDrawable(
      GradientDrawable.Orientation.TL_BR,
      intArrayOf(ContextCompat.getColor(testContext, R.color.bpkBlue500), ContextCompat.getColor(testContext, R.color.bpkPrimaryGradientLight)))

    val gradient = BpkGradients.getPrimary(testContext)

    assertEquals(expectedGradient.orientation, gradient.orientation)
    assertEquals(expectedGradient.alpha, gradient.alpha)
    if (Build.VERSION.SDK_INT >= 24) {
      assertEquals(ContextCompat.getColor(testContext, R.color.bpkBlue500), gradient.colors[0])
      assertEquals(ContextCompat.getColor(testContext, R.color.bpkPrimaryGradientLight), gradient.colors[1])
    }
  }

  @Test
  fun getPrimaryDefault() {
    val testContext = RuntimeEnvironment.application.applicationContext

    val expectedGradient = GradientDrawable(
      GradientDrawable.Orientation.BOTTOM_TOP,
      intArrayOf(ContextCompat.getColor(testContext, R.color.bpkBlue500), ContextCompat.getColor(testContext, R.color.bpkPrimaryGradientLight)))

    val gradient = BpkGradients.getPrimary(testContext, GradientDrawable.Orientation.BOTTOM_TOP)

    assertEquals(expectedGradient.orientation, gradient.orientation)
    assertEquals(expectedGradient.alpha, gradient.alpha)
    if (Build.VERSION.SDK_INT >= 24) {
      assertEquals(ContextCompat.getColor(testContext, R.color.bpkBlue500), gradient.colors[0])
      assertEquals(ContextCompat.getColor(testContext, R.color.bpkPrimaryGradientLight), gradient.colors[1])
    }
  }

}
