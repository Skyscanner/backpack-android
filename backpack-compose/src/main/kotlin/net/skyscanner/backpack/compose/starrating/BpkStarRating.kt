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

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Star
import net.skyscanner.backpack.compose.tokens.StarHalf
import net.skyscanner.backpack.compose.tokens.StarOutline

enum class BpkStarRatingSize {
  Large,
  Small,
}

@Composable
fun BpkStarRating(
  rating: Float,
  contentDescription: (Float, Int) -> String,
  modifier: Modifier = Modifier,
  rounding: RoundingType = RoundingType.Down,
  size: BpkStarRatingSize = BpkStarRatingSize.Small,
) {
  BpkStarRating(
    maxRating = 5,
    rounding = rounding,
    iconSize = when (size) {
      BpkStarRatingSize.Large -> BpkIconSize.Large
      BpkStarRatingSize.Small -> BpkIconSize.Small
    },
    modifier = modifier,
    rating = rating,
    contentDescription = contentDescription,
  )
}

@Composable
fun BpkHotelRating(
  rating: Float,
  contentDescription: (Float, Int) -> String,
  modifier: Modifier = Modifier,
  size: BpkStarRatingSize = BpkStarRatingSize.Small,
) {
  BpkStarRating(
    maxRating = rating.toInt(),
    rounding = RoundingType.Down,
    iconSize = when (size) {
      BpkStarRatingSize.Large -> BpkIconSize.Large
      BpkStarRatingSize.Small -> BpkIconSize.Small
    },
    modifier = modifier,
    rating = rating,
    contentDescription = contentDescription,
  )
}

@Composable
private fun BpkStarRating(
  rating: Float,
  maxRating: Int,
  rounding: RoundingType,
  iconSize: BpkIconSize,
  contentDescription: (Float, Int) -> String,
  modifier: Modifier = Modifier,
) {
  val coercedRating = rating.coerceIn(0f, maxRating.toFloat())
  val roundedRating = when (rounding) {
    RoundingType.Down -> floor(coercedRating * 2) / 2
    RoundingType.Up -> ceil(coercedRating * 2) / 2
    RoundingType.Nearest -> round(coercedRating * 2) / 2
  }
  Row(modifier = modifier.semantics { this.contentDescription = contentDescription(roundedRating, maxRating) }) {

    for (item in 0 until maxRating) {
      val value = (roundedRating - item).coerceIn(0f, 1f)
      when {
        (value >= 0.0f && value < 0.5f) -> BpkStar(icon = StarType.Empty, iconSize = iconSize)
        (value >= 0.5f && value < 1.0f) -> BpkStar(icon = StarType.Half, iconSize = iconSize)
        else -> BpkStar(icon = StarType.Full, iconSize = iconSize)
      }
    }
  }
}

@Composable
private fun BpkStar(icon: StarType, iconSize: BpkIconSize) {
  when (icon) {
    StarType.Empty -> {
      BpkIcon(icon = BpkIcon.StarOutline, contentDescription = null, size = iconSize, tint = BpkTheme.colors.textDisabled)
    }
    StarType.Half -> {
      BpkIcon(icon = BpkIcon.StarHalf, contentDescription = null, size = iconSize, tint = BpkTheme.colors.statusWarningSpot)
    }
    StarType.Full -> {
      BpkIcon(icon = BpkIcon.Star, contentDescription = null, size = iconSize, tint = BpkTheme.colors.statusWarningSpot)
    }
  }
}

private enum class StarType {
  Empty,
  Half,
  Full,
}

enum class RoundingType {
  Down,
  Up,
  Nearest,
}
