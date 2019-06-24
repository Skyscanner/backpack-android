package net.skyscanner.backpack.starrating

import android.graphics.Color
import android.view.View
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.createThemedContext
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class BpkInteractiveStarRatingTest : BpkSnapshotTest() {

  private val rating = BpkInteractiveStarRating(testContext).apply { setBackgroundColor(Color.WHITE) }

  @Before
  fun setup() {
    setDimensions(40, 200)
  }

  @Test
  fun screenshotTestInteractiveStarRating_Default() {
    snap(rating)
  }

  @Test
  fun screenshotTestInteractiveStarRating_CustomMaxRatingIncreasing() {
    setDimensions(40, 400)
    rating.maxRating = 5
    rating.maxRating = 10
    snap(rating)
  }

  @Test
  fun screenshotTestInteractiveStarRating_CustomMaxRatingDecreasing() {
    rating.maxRating = 5
    rating.maxRating = 3
    snap(rating)
  }

  @Test
  fun screenshotTestInteractiveStarRating_NegativeRating() {
    rating.maxRating = 5
    rating.rating = -0.5f
    snap(rating)
  }

  @Test
  fun screenshotTestInteractiveStarRating_ZeroRating() {
    rating.maxRating = 5
    rating.rating = 0.0f
    snap(rating)
  }

  @Test
  fun screenshotTestInteractiveStarRating_RatingValueBetween0And0_5() {
    rating.maxRating = 5
    rating.rating = 0.4999999f
    snap(rating)
  }

  @Test
  fun screenshotTestInteractiveStarRating_RatingValue0_5() {
    rating.maxRating = 5
    rating.rating = 0.5f
    snap(rating)
  }

  @Test
  fun screenshotTestInteractiveStarRating_RatingValueBetween0_5And1() {
    rating.maxRating = 5
    rating.rating = 0.9999999f
    snap(rating)
  }

  @Test
  fun screenshotTestInteractiveStarRating_RatingValue1() {
    rating.maxRating = 5
    rating.rating = 1.0f
    snap(rating)
  }

  @Test
  fun screenshotTestInteractiveStarRating_6withMax5() {
    rating.maxRating = 5
    rating.rating = 6.0f
    snap(rating)
  }

  @Test
  fun screenshotTestInteractiveStarRating_withTheme() {
    val rating = BpkInteractiveStarRating(createThemedContext(testContext)).apply { setBackgroundColor(Color.WHITE) }
    rating.maxRating = 5
    rating.rating = 2.5f
    snap(rating)
  }

  @Test
  fun screenshotTestInteractiveStarRating_rtl() {
    rating.layoutDirection = View.LAYOUT_DIRECTION_RTL
    rating.maxRating = 5
    rating.rating = 2.5f
    snap(rating)
  }
}
