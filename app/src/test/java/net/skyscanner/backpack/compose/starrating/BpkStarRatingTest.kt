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

package net.skyscanner.backpack.compose.starrating

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Test

class BpkStarRatingTest : BpkSnapshotTest() {

    @Test
    fun ratingFullStar() = snap {
        BpkStarRating(
            rating = 2f,
            contentDescription = { value, max ->
                stringResource(R.string.star_rating_accessibility_status, value, max)
            },
        )
    }

    @Test
    fun ratingHalfStar() = snap {
        BpkStarRating(
            rating = 3.5f,
            contentDescription = { value, max ->
                stringResource(R.string.star_rating_accessibility_status, value, max)
            },
        )
    }

    @Test
    fun zeroRating() = snap {
        BpkStarRating(
            rating = 0f,
            contentDescription = { value, max ->
                stringResource(R.string.star_rating_accessibility_status, value, max)
            },
        )
    }

    @Test
    fun negativeRating() = snap {
        BpkStarRating(
            rating = -3f,
            contentDescription = { value, max ->
                stringResource(R.string.star_rating_accessibility_status, value, max)
            },
        )
    }

    @Test
    fun roundingTypeUp() = snap {
        BpkStarRating(
            rating = 2.9f,
            contentDescription = { value, max ->
                stringResource(R.string.star_rating_accessibility_status, value, max)
            },
            rounding = BpkRatingRounding.Up,
        )
    }

    @Test
    fun roundingTypeDown() = snap {
        BpkStarRating(
            rating = 2.9f,
            contentDescription = { value, max ->
                stringResource(R.string.star_rating_accessibility_status, value, max)
            },
            rounding = BpkRatingRounding.Down,
        )
    }

    @Test
    fun aboveMaxRating() = snap {
        BpkStarRating(
            rating = 7f,
            contentDescription = { value, max ->
                stringResource(R.string.star_rating_accessibility_status, value, max)
            },
        )
    }

    @Test
    fun starSizeSmall() = snap {
        BpkStarRating(
            rating = 3f,
            contentDescription = { value, max ->
                stringResource(R.string.star_rating_accessibility_status, value, max)
            },
            size = BpkStarRatingSize.Small,
        )
    }

    @Test
    fun starSizeLarge() = snap {
        BpkStarRating(
            rating = 3f,
            contentDescription = { value, max ->
                stringResource(R.string.star_rating_accessibility_status, value, max)
            },
            size = BpkStarRatingSize.Large,
        )
    }

    @Test
    fun hotelStarRating4() = snap {
        BpkHotelRating(
            rating = 4,
            contentDescription = { value, max ->
                stringResource(R.string.star_rating_accessibility_status, value, max)
            },
        )
    }

    @Test
    fun interactiveStarRating() = snap {
        BpkInteractiveStarRating(
            rating = 2,
            onRatingChanged = { },
            contentDescription = { value, max ->
                stringResource(R.string.star_rating_accessibility_status, value, max)
            },
        )
    }
}
