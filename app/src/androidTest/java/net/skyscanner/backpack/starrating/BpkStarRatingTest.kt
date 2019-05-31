package net.skyscanner.backpack.starrating

import android.graphics.Color
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
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
  fun screenshotTestStarRating_NegativeValue() {
    rating.maxRating = 5
    rating.rating = -0.5f
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_ZeroValue() {
    rating.maxRating = 5
    rating.rating = 0.0f
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_BeforeHalfStarValue() {
    rating.maxRating = 5
    rating.rating = 0.4999999f
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_AfterHalfStarValue() {
    rating.maxRating = 5
    rating.rating = 0.5f
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_BeforeFullStarValue() {
    rating.maxRating = 5
    rating.rating = 0.9999999f
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_AfterFullStarValue() {
    rating.maxRating = 5
    rating.rating = 1.0f
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_OverMaxValue() {
    rating.maxRating = 5
    rating.rating = 6.0f
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_StarColor() {
    rating.maxRating = 5
    rating.rating = 2.5f
    rating.starColor = Color.RED
    snap(rating)
  }

  @Test
  fun screenshotTestStarRating_StarFilledColor() {
    rating.maxRating = 5
    rating.rating = 2.5f
    rating.starFilledColor = Color.RED
    snap(rating)
  }
}
