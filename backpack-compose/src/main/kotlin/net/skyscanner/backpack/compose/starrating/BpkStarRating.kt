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
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Star
import net.skyscanner.backpack.compose.tokens.StarHalf
import net.skyscanner.backpack.compose.tokens.StarOutline

@Composable
fun BpkStarRating(
  maxRating: Int,
  rounding: RoundingType,
  iconSize: BpkIconSize,
  modifier: Modifier = Modifier,
  rating: Float = 2.5f,
) {

  Row(modifier = modifier) {
    val roundedRating = when (rounding) {
      RoundingType.Down -> floor(rating * 2) / 2
      RoundingType.Up -> ceil(rating * 2) / 2
      RoundingType.Nearest -> round(rating * 2) / 2
    }
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
