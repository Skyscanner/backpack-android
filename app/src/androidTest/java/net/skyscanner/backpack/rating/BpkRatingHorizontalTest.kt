package net.skyscanner.backpack.rating

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkRatingHorizontalTest : BpkSnapshotTest() {

  private val orientation = BpkRating.Orientation.Horizontal

  @Before
  fun setup() {
    setDimensions(150, 150)
  }

  @Test
  fun screenshotTestRating_Default() {
    val subject = createTestRating(testContext, orientation = orientation)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_Icon() {
    val subject = createTestRating(testContext, orientation = orientation, size = BpkRating.Size.Icon)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_ExtraSmall() {
    val subject = createTestRating(testContext, orientation = orientation, size = BpkRating.Size.ExtraSmall)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_Small() {
    val subject = createTestRating(testContext, orientation = orientation, size = BpkRating.Size.Small)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_Base() {
    val subject = createTestRating(testContext, orientation = orientation, size = BpkRating.Size.Base)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_Large() {
    val subject = createTestRating(testContext, orientation = orientation, size = BpkRating.Size.Large)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_DefaultRtl() {
    val subject = createTestRating(testContext, rtl = true)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_IconRtl() {
    val subject = createTestRating(testContext, rtl = true, orientation = orientation, size = BpkRating.Size.Icon)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_ExtraSmallRtl() {
    val subject = createTestRating(testContext, rtl = true, orientation = orientation, size = BpkRating.Size.ExtraSmall)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_SmallRtl() {
    val subject = createTestRating(testContext, rtl = true, orientation = orientation, size = BpkRating.Size.Small)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_BaseRtl() {
    val subject = createTestRating(testContext, rtl = true, orientation = orientation, size = BpkRating.Size.Base)
    snap(subject)
  }

  @Test
  fun screenshotTestRating_LargeRtl() {
    val subject = createTestRating(testContext, rtl = true, orientation = orientation, size = BpkRating.Size.Large)
    snap(subject)
  }
}
