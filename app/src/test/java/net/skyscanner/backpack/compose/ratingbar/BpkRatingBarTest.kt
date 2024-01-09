/*
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
 *
 */

package net.skyscanner.backpack.compose.ratingbar

import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.rating.BpkRatingScale
import net.skyscanner.backpack.demo.compose.RatingBarSample
import org.junit.Test

class BpkRatingBarTest : BpkSnapshotTest() {

    @Test
    fun default() {
        snap { RatingBarSample() }
    }

    @Test
    fun onContrast() {
        snap { RatingBarSample(style = BpkRatingBarStyle.OnContrast) }
    }

    @Test
    fun noScale() {
        snap { RatingBarSample(showScale = false) }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun zero_to_10() {
        snap { RatingBarSample(scale = BpkRatingScale.ZeroToTen) }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun onContrast_zero_to_10() {
        snap {
            RatingBarSample(
                style = BpkRatingBarStyle.OnContrast,
                scale = BpkRatingScale.ZeroToTen,
            )
        }
    }
}
