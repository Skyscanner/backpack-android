package net.skyscanner.backpack

import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.test.runner.AndroidJUnit4
import io.github.backpack.backpack.R
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkGradientsTest {

  @Test
  fun getPrimary() {

    val expectedGradient = GradientDrawable(
      GradientDrawable.Orientation.TL_BR,
      intArrayOf(R.color.bpkBlue500, R.color.bpkWhite))

    val gradient = BpkGradients.getPrimary()

    assertEquals(expectedGradient.orientation, gradient.orientation)
    assertEquals(expectedGradient.alpha, gradient.alpha)
    if (Build.VERSION.SDK_INT >= 24) {
      assertEquals(R.color.bpkBlue500, gradient.colors[0])
      assertEquals(R.color.bpkWhite, gradient.colors[1])
    }
  }

  @Test
  fun getPrimaryDefault() {

    val expectedGradient = GradientDrawable(
      GradientDrawable.Orientation.BOTTOM_TOP,
      intArrayOf(R.color.bpkBlue500, R.color.bpkWhite))

    val gradient = BpkGradients.getPrimary(GradientDrawable.Orientation.BOTTOM_TOP)

    assertEquals(expectedGradient.orientation, gradient.orientation)
    assertEquals(expectedGradient.alpha, gradient.alpha)
    if (Build.VERSION.SDK_INT >= 24) {
      assertEquals(R.color.bpkBlue500, gradient.colors[0])
      assertEquals(R.color.bpkWhite, gradient.colors[1])
    }
  }

}
