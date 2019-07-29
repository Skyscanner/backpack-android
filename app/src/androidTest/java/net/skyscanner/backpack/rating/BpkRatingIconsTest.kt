package net.skyscanner.backpack.rating

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkRatingIconsTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(150, 150)
  }

  @Test
  fun screenshotTestRating_Low() {
    val subject = createTestRating(testContext, size = BpkRating.Size.Icon, value = 3.0f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_Medium() {
    val subject = createTestRating(testContext, size = BpkRating.Size.Icon, value = 6.0f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_High() {
    val subject = createTestRating(testContext, size = BpkRating.Size.Icon, value = 8.0f)
    snap(subject)
  }
}
