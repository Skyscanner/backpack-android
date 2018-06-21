package net.skyscanner.backpack

import android.graphics.drawable.GradientDrawable
import android.support.test.runner.AndroidJUnit4
import io.github.backpack.backpack.R
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkGradientsTest {

  @Test
  fun getPrimary() {

    val testGradient = GradientDrawable(
      GradientDrawable.Orientation.TL_BR,
      intArrayOf(R.color.bpkBlue500, R.color.bpkWhite))

    assertEquals(testGradient.orientation, BpkGradients.getPrimary().orientation)
    assertEquals(testGradient.alpha, BpkGradients.getPrimary().alpha)
  }

  @Test
  fun getPrimaryDefault() {

    val testGradient = GradientDrawable(
      GradientDrawable.Orientation.BOTTOM_TOP,
      intArrayOf(R.color.bpkBlue500, R.color.bpkWhite))

    assertEquals(testGradient.orientation, BpkGradients.getPrimary(GradientDrawable.Orientation.BOTTOM_TOP).orientation)
    assertEquals(testGradient.alpha, BpkGradients.getPrimary(GradientDrawable.Orientation.BOTTOM_TOP).alpha)
  }

}
