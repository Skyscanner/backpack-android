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

import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import org.junit.Test

class BpkInteractiveStarRatingTest : BpkSnapshotTest() {

    private val rating = BpkInteractiveStarRating(testContext)

    @Test
    fun default() {
        snap(rating)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun maxRatingIncreases() {
        rating.maxRating = 5
        rating.maxRating = 10
        snap(rating)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun maxRatingDecreases() {
        rating.maxRating = 5
        rating.maxRating = 3
        snap(rating)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun negativeRating() {
        rating.maxRating = 5
        rating.rating = -0.5f
        snap(rating)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun zeroRating() {
        rating.maxRating = 5
        rating.rating = 0.0f
        snap(rating)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun ratingValueBetween0And0_5() {
        rating.maxRating = 5
        rating.rating = 0.4999999f
        snap(rating)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun ratingValue0_5() {
        rating.maxRating = 5
        rating.rating = 0.5f
        snap(rating)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun ratingValueBetween0_5And1() {
        rating.maxRating = 5
        rating.rating = 0.9999999f
        snap(rating)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun ratingValue1() {
        rating.maxRating = 5
        rating.rating = 1.0f
        snap(rating)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun SixWithMax5() {
        rating.maxRating = 5
        rating.rating = 6.0f
        snap(rating)
    }
}
