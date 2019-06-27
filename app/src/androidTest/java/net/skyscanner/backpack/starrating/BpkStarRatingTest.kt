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
class BpkStarRatingTest : BpkSnapshotTest() {

  private val rating = BpkStarRating(testContext).apply { setBackgroundColor(Color.WHITE) }

  @Before
  fun setup() {
    setDimensions(16, 80)
  }

  @Test
  fun screenshotTestStarRating_Default() {
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_CustomMaxRatingIncreasing() {
    setDimensions(16, 160)
    rating.maxRating = 5
    rating.maxRating = 10
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_CustomMaxRatingDecreasing() {
    rating.maxRating = 5
    rating.maxRating = 3
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_NegativeRating() {
    rating.maxRating = 5
    rating.rating = -0.5f
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_ZeroRating() {
    rating.maxRating = 5
    rating.rating = 0.0f
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_RatingValueBetween0And0_5() {
    rating.maxRating = 5
    rating.rating = 0.4999999f
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_RatingValue0_5() {
    rating.maxRating = 5
    rating.rating = 0.5f
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_RatingValueBetween0_5And1() {
    rating.maxRating = 5
    rating.rating = 0.9999999f
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_RatingValue1() {
    rating.maxRating = 5
    rating.rating = 1.0f
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_6withMax5() {
    rating.maxRating = 5
    rating.rating = 6.0f
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_withTheme() {
    val rating = BpkStarRating(createThemedContext(testContext)).apply { setBackgroundColor(Color.WHITE) }
    rating.maxRating = 5
    rating.rating = 2.5f
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_rtl() {
    rating.layoutDirection = View.LAYOUT_DIRECTION_RTL
    rating.maxRating = 5
    rating.rating = 2.5f
    snap(rating)
  }
}
