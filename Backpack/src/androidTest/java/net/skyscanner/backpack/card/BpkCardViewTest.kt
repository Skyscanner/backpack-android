package net.skyscanner.backpack.card

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import net.skyscanner.backpack.R
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkCardViewTest {

  @Test
  fun test_with_padding() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val card = BpkCardView(context)
    card.padded = true
    Assert.assertEquals(context.resources.getDimensionPixelOffset(R.dimen.bpkSpacingBase), card.paddingBottom)
    Assert.assertEquals(context.resources.getDimensionPixelOffset(R.dimen.bpkSpacingBase), card.paddingLeft)
    Assert.assertEquals(context.resources.getDimensionPixelOffset(R.dimen.bpkSpacingBase), card.paddingTop)
    Assert.assertEquals(context.resources.getDimensionPixelOffset(R.dimen.bpkSpacingBase), card.paddingRight)
  }

  @Test
  fun test_without_padding() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val card = BpkCardView(context)
    card.padded = false
    Assert.assertEquals(0, card.paddingBottom)
    Assert.assertEquals(0, card.paddingLeft)
    Assert.assertEquals(0, card.paddingTop)
    Assert.assertEquals(0, card.paddingRight)
  }

  @Test
  fun test_without_focus() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val card = BpkCardView(context)
    card.focused = false
    Assert.assertEquals(context.resources.getDimension(R.dimen.bpkElevationSm), card.cardElevation)
  }

  @Test
  fun test_with_focus() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val card = BpkCardView(context)
    card.focused = true
    Assert.assertEquals(context.resources.getDimension(R.dimen.bpkElevationLg), card.cardElevation)
  }

  @Test
  fun test_with_corner_style_large() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val card = BpkCardView(context)
    card.cornerStyle = BpkCardView.CornerStyle.LARGE
    Assert.assertEquals(context.resources.getDimension(R.dimen.bpkBorderRadiusLg), card.radius)
  }
}
