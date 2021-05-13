/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.starrating

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkInteractiveStarRatingTest : BpkSnapshotTest() {

  private val rating = BpkInteractiveStarRating(testContext)

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
}
