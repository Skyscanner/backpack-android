/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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
class BpkStarRatingTest : BpkSnapshotTest() {

  private val rating = BpkStarRating(testContext)

  @Before
  fun setup() {
    setDimensions(16, 80)
  }

  @Test
  fun default() {
    snap(rating)
  }

  @Test
  fun customMaxRatingIncreasing() {
    setDimensions(16, 160)
    rating.maxRating = 5
    rating.maxRating = 10
    snap(rating)
  }

  @Test
  fun customMaxRatingDecreasing() {
    rating.maxRating = 5
    rating.maxRating = 3
    snap(rating)
  }

  @Test
  fun negativeRating() {
    rating.maxRating = 5
    rating.rating = -0.5f
    snap(rating)
  }

  @Test
  fun zeroRating() {
    rating.maxRating = 5
    rating.rating = 0.0f
    snap(rating)
  }

  @Test
  fun ratingValueBetween0And0_5() {
    rating.maxRating = 5
    rating.rating = 0.4999999f
    snap(rating)
  }

  @Test
  fun ratingValue0_5() {
    rating.maxRating = 5
    rating.rating = 0.5f
    snap(rating)
  }

  @Test
  fun ratingValueBetween0_5And1() {
    rating.maxRating = 5
    rating.rating = 0.9999999f
    snap(rating)
  }

  @Test
  fun ratingValue1() {
    rating.maxRating = 5
    rating.rating = 1.0f
    snap(rating)
  }

  @Test
  fun aboveMaxValue() {
    rating.maxRating = 5
    rating.rating = 6.0f
    snap(rating)
  }

  @Test
  fun roundingDown() {
    rating.rounding = RoundingType.DOWN
    rating.rating = 3.9f
    snap(rating)
  }

  @Test
  fun roundingUp() {
    rating.rounding = RoundingType.UP
    rating.rating = 3.9f
    snap(rating)
  }

  @Test
  fun roundingNearestDown() {
    rating.rounding = RoundingType.NEAREST
    rating.rating = 3.7f
    snap(rating)
  }

  @Test
  fun roundingNearestUp() {
    rating.rounding = RoundingType.NEAREST
    rating.rating = 3.9f
    snap(rating)
  }
}
