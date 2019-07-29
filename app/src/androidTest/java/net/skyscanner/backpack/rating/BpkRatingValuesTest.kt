package net.skyscanner.backpack.rating

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkRatingValuesTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(150, 150)
  }

  @Test
  fun screenshotTestRating_Zero() {
    val subject = createTestRating(testContext, value = 0.0f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_Low() {
    val subject = createTestRating(testContext, value = 3.0f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_LowBoundary() {
    val subject = createTestRating(testContext, value = 5.999999f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_Medium() {
    val subject = createTestRating(testContext, value = 6.0f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_MediumBoundary() {
    val subject = createTestRating(testContext, value = 7.999999f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_High() {
    val subject = createTestRating(testContext, value = 8.0f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_Max() {
    val subject = createTestRating(testContext, value = 10.0f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_clampsDown() {
    val subject = createTestRating(testContext, value = -10.0f)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_clampsUp() {
    val subject = createTestRating(testContext, value = 20.0f)
    snap(subject)
  }
}
