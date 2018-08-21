package net.skyscanner.backpack

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.InstrumentationRegistry
import android.support.v4.content.ContextCompat
import net.skyscanner.backpack.core.R
import org.junit.Before


@RunWith(AndroidJUnit4::class)

class BpkGradientsTest {
  private lateinit var testContext: Context

  @Before
  fun setup() {
    testContext = InstrumentationRegistry.getContext()
  }

  @Test
  fun getPrimary() {

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
