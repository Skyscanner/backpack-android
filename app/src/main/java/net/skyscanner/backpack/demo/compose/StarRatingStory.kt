/*
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.starrating.BpkHotelRating
import net.skyscanner.backpack.compose.starrating.BpkRatingColor
import net.skyscanner.backpack.compose.starrating.BpkRatingRounding
import net.skyscanner.backpack.compose.starrating.BpkStarRating
import net.skyscanner.backpack.compose.starrating.BpkStarRatingSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.StarRatingComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.util.ExperimentalBackpackApi

@Composable
@StarRatingComponent
@ComposeStory("Small")
fun StarRatingStorySmall(modifier: Modifier = Modifier) {
    StarRatingStoryContent(size = BpkStarRatingSize.Small, modifier = modifier)
}

@Composable
@StarRatingComponent
@ComposeStory("Large")
fun StarRatingStoryLarge(modifier: Modifier = Modifier) {
    StarRatingStoryContent(size = BpkStarRatingSize.Large, modifier = modifier)
}

@Composable
@StarRatingComponent
@ComposeStory("Extra Large")
fun StarRatingStoryExtraLarge(modifier: Modifier = Modifier) {
    StarRatingStoryContent(size = BpkStarRatingSize.ExtraLarge, modifier = modifier)
}

@Composable
private fun StarRatingStoryContent(
    size: BpkStarRatingSize,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Lg),
    ) {
        StaticRatingSample(size = size)
        HotelRatingSample(size = size)
    }
}

@OptIn(ExperimentalBackpackApi::class)
@Composable
private fun HotelRatingSample(
    size: BpkStarRatingSize,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        BpkText(
            text = stringResource(R.string.hotel_star_rating_title),
            style = BpkTheme.typography.heading3,
        )
        for (i in 1..5) {
            key(i) {
                BpkHotelRating(
                    rating = i,
                    contentDescription = { value, max ->
                        stringResource(R.string.star_rating_accessibility_status, value, max)
                    },
                    size = size,
                )
            }
        }
        BpkHotelRating(
            rating = 5,
            contentDescription = { value, max ->
                stringResource(R.string.star_rating_accessibility_status, value, max)
            },
            size = size,
            color = BpkRatingColor.Gray,
        )
    }
}

@Composable
private fun StaticRatingSample(
    size: BpkStarRatingSize,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        BpkText(
            text = stringResource(R.string.static_default_rating_title),
            style = BpkTheme.typography.heading3,
        )
        val ratings = listOf(1f, 2f, 3f, 3.5f, 4f, 5f)
        for (i in ratings) {
            key(i) {
                BpkStarRating(
                    rating = i,
                    contentDescription = { value, max ->
                        stringResource(R.string.star_rating_decimal_accessibility_status, value, max)
                    },
                    rounding = BpkRatingRounding.Up,
                    size = size,
                )
            }
        }
    }
}
