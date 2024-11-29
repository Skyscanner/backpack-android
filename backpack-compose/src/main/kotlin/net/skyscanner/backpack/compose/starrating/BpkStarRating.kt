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

import android.content.res.Resources
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import androidx.compose.ui.semantics.stateDescription
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Star
import net.skyscanner.backpack.compose.tokens.StarHalf
import net.skyscanner.backpack.compose.tokens.StarOutline
import net.skyscanner.backpack.compose.utils.ContentDescriptionScope
import net.skyscanner.backpack.compose.utils.applyIf
import net.skyscanner.backpack.compose.utils.clickableWithRipple
import net.skyscanner.backpack.util.ExperimentalBackpackApi
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.roundToInt

enum class BpkStarRatingSize {
    Large,
    Small,
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
    BpkStarRating(
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
    BpkStarRating(
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
    BpkStarRating(
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

@Composable
private fun BpkStarRating(
    rating: Float,
    maxRating: Int,
    numberOfStars: Int,
    rounding: BpkRatingRounding,
    contentDescription: ContentDescriptionScope.(Float, Int) -> String,
    modifier: Modifier = Modifier,
    size: BpkStarRatingSize = BpkStarRatingSize.Large,
    onRatingChanged: ((Int) -> Unit)? = null,
    color: Color? = null,
) {
    val iconSize = when (size) {
        BpkStarRatingSize.Large -> BpkIconSize.Large
        BpkStarRatingSize.Small -> BpkIconSize.Small
    }
    val coercedRating = rating.coerceIn(0f, numberOfStars.toFloat())
    val roundedRating = when (rounding) {
        BpkRatingRounding.Down -> floor(coercedRating * 2) / 2
        BpkRatingRounding.Up -> ceil(coercedRating * 2) / 2
        BpkRatingRounding.Nearest -> round(coercedRating * 2) / 2
    }
    Row(
        modifier = modifier.starRatingSemantics(
            resources = LocalContext.current.resources,
            rating = rating,
            maxRating = maxRating,
            numberOfStars = numberOfStars,
            onRatingChanged = onRatingChanged,
            contentDescription = contentDescription,
        ),
    ) {
        for (item in 0..<numberOfStars) {
            key(item) {
                val value = (roundedRating - item).coerceIn(0f, 1f)
                val starModifier = Modifier
                    .applyIf(onRatingChanged != null) {
                        clickableWithRipple(bounded = false) { onRatingChanged?.invoke(item + 1) }
                    }
                when {
                    (value >= 0.0f && value < 0.5f) -> BpkStar(
                        icon = BpkRatingStarType.Empty,
                        iconSize = iconSize,
                        modifier = starModifier,
                    )
                    (value >= 0.5f && value < 1.0f) -> BpkStar(
                        icon = BpkRatingStarType.Half,
                        iconSize = iconSize,
                        modifier = starModifier,
                        color = color,
                    )
                    else -> BpkStar(
                        icon = BpkRatingStarType.Full,
                        iconSize = iconSize,
                        modifier = starModifier,
                        color = color,
                    )
                }
            }
        }
    }
}

@Composable
private fun BpkStar(
    icon: BpkRatingStarType,
    iconSize: BpkIconSize,
    modifier: Modifier = Modifier,
    color: Color? = null,
) {
    when (icon) {
        BpkRatingStarType.Empty -> BpkIcon(
            icon = BpkIcon.StarOutline,
            contentDescription = null,
            size = iconSize,
            tint = BpkTheme.colors.textDisabled,
            modifier = modifier,
        )
        BpkRatingStarType.Half -> BpkIcon(
            icon = BpkIcon.StarHalf,
            contentDescription = null,
            size = iconSize,
            tint = color ?: BpkTheme.colors.statusWarningSpot,
            modifier = modifier,
        )
        BpkRatingStarType.Full -> BpkIcon(
            icon = BpkIcon.Star,
            contentDescription = null,
            size = iconSize,
            tint = color ?: BpkTheme.colors.statusWarningSpot,
            modifier = modifier,
        )
    }
}

private enum class BpkRatingStarType {
    Empty,
    Half,
    Full,
}

private fun Modifier.starRatingSemantics(
    resources: Resources,
    rating: Float,
    maxRating: Int,
    numberOfStars: Int,
    onRatingChanged: ((Int) -> Unit)?,
    contentDescription: ContentDescriptionScope.(Float, Int) -> String,
): Modifier {
    val range = 0f..numberOfStars.toFloat()
    val scope = ContentDescriptionScope(resources)
    return semantics(mergeDescendants = true) {

        // this is needed to use volume keys
        if (onRatingChanged != null) {
            setProgress { targetValue ->
                val newValue = targetValue
                    .coerceIn(range)
                if (newValue != rating) {
                    onRatingChanged(newValue.roundToInt())
                    true
                } else {
                    false
                }
            }
        }
        // override describing percents
        stateDescription = scope.contentDescription(rating, maxRating)
    }
        .applyIf(onRatingChanged != null) {
            progressSemantics(
                // this is needed to use volume keys
                value = rating,
                valueRange = range,
                steps = numberOfStars,
            )
        }
}

@OptIn(ExperimentalBackpackApi::class)
@Composable
private fun starColor(color: BpkRatingColor?): Color {
    return when (color) {
        BpkRatingColor.Gray -> BpkTheme.colors.textSecondary
        else -> BpkTheme.colors.statusWarningSpot
    }
}
