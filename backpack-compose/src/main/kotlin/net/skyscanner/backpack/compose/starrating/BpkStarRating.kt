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

package net.skyscanner.backpack.compose.starrating

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.starrating.internal.BpkStarRatingImpl
import net.skyscanner.backpack.compose.starrating.internal.starColor
import net.skyscanner.backpack.compose.utils.ContentDescriptionScope
import net.skyscanner.backpack.util.ExperimentalBackpackApi

enum class BpkStarRatingSize {
    Large,
    Small,
    ExtraLarge,
}

enum class BpkRatingRounding {
    Down,
    Up,
    Nearest,
}

@ExperimentalBackpackApi
enum class BpkRatingColor {
    Yellow,
    Gray,
}

@Composable
fun BpkStarRating(
    rating: Float,
    contentDescription: ContentDescriptionScope.(Float, Int) -> String,
    modifier: Modifier = Modifier,
    rounding: BpkRatingRounding = BpkRatingRounding.Down,
    size: BpkStarRatingSize = BpkStarRatingSize.Small,
) {
    BpkStarRatingImpl(
        rating = rating,
        maxRating = 5,
        numberOfStars = 5,
        rounding = rounding,
        modifier = modifier,
        contentDescription = contentDescription,
        size = size,
    )
}

@OptIn(ExperimentalBackpackApi::class)
@Composable
fun BpkHotelRating(
    rating: Int,
    contentDescription: ContentDescriptionScope.(Float, Int) -> String,
    modifier: Modifier = Modifier,
    size: BpkStarRatingSize = BpkStarRatingSize.Small,
) {
    BpkHotelRating(
        rating = rating,
        contentDescription = contentDescription,
        modifier = modifier,
        size = size,
        color = BpkRatingColor.Yellow,
    )
}

@OptIn(ExperimentalBackpackApi::class)
@Composable
fun BpkHotelRating(
    rating: Int,
    contentDescription: ContentDescriptionScope.(Float, Int) -> String,
    color: BpkRatingColor,
    modifier: Modifier = Modifier,
    size: BpkStarRatingSize = BpkStarRatingSize.Small,
) {
    BpkStarRatingImpl(
        rating = rating.toFloat(),
        maxRating = 5,
        numberOfStars = rating,
        rounding = BpkRatingRounding.Down,
        modifier = modifier,
        contentDescription = contentDescription,
        size = size,
        color = starColor(color),
    )
}

@Composable
fun BpkInteractiveStarRating(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    contentDescription: ContentDescriptionScope.(Float, Int) -> String,
    modifier: Modifier = Modifier,
    size: BpkStarRatingSize = BpkStarRatingSize.Small,
) {
    BpkStarRatingImpl(
        rating = rating.toFloat(),
        maxRating = 5,
        numberOfStars = 5,
        rounding = BpkRatingRounding.Down,
        modifier = modifier,
        onRatingChanged = onRatingChanged,
        contentDescription = contentDescription,
        size = size,
    )
}
