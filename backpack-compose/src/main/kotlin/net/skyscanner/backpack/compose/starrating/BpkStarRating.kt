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
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import androidx.compose.ui.semantics.stateDescription
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.roundToInt
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Star
import net.skyscanner.backpack.compose.tokens.StarHalf
import net.skyscanner.backpack.compose.tokens.StarOutline
import net.skyscanner.backpack.compose.utils.ContentDescriptionScope
import net.skyscanner.backpack.compose.utils.applyIf
import net.skyscanner.backpack.compose.utils.clickable
import net.skyscanner.backpack.compose.utils.rememberContentDescriptionScope

enum class BpkStarRatingSize {
  Large,
  Small,
}

enum class BpkRatingRounding {
  Down,
  Up,
  Nearest,
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
    maxRating = 5,
    rounding = rounding,
    modifier = modifier,
    rating = rating,
    contentDescription = contentDescription,
    size = size,
  )
}

@Composable
fun BpkHotelRating(
  rating: Int,
  contentDescription: ContentDescriptionScope.(Float, Int) -> String,
  modifier: Modifier = Modifier,
  size: BpkStarRatingSize = BpkStarRatingSize.Small,
) {
  BpkStarRating(
    maxRating = rating,
    rounding = BpkRatingRounding.Down,
    modifier = modifier,
    rating = rating.toFloat(),
    contentDescription = contentDescription,
    size = size,
  )
}

@Composable
fun BpkInteractiveStarRating(
  onRatingChanged: (Int) -> Unit,
  selectedRating: Int,
  contentDescription: ContentDescriptionScope.(Float, Int) -> String,
  modifier: Modifier = Modifier,
  rounding: BpkRatingRounding = BpkRatingRounding.Down,
  size: BpkStarRatingSize = BpkStarRatingSize.Small,
) {
  BpkStarRating(
    maxRating = 5,
    rounding = rounding,
    modifier = modifier,
    onRatingChanged = onRatingChanged,
    rating = selectedRating.toFloat(),
    contentDescription = contentDescription,
    size = size,
  )
}

@Composable
private fun BpkStarRating(
  rating: Float,
  maxRating: Int,
  rounding: BpkRatingRounding,
  contentDescription: ContentDescriptionScope.(Float, Int) -> String,
  modifier: Modifier = Modifier,
  size: BpkStarRatingSize = BpkStarRatingSize.Small,
  onRatingChanged: ((Int) -> Unit)? = null,
) {
  val iconSize = when (size) {
    BpkStarRatingSize.Large -> BpkIconSize.Large
    BpkStarRatingSize.Small -> BpkIconSize.Small
  }
  val coercedRating = rating.coerceIn(0f, maxRating.toFloat())
  val roundedRating = when (rounding) {
    BpkRatingRounding.Down -> floor(coercedRating * 2) / 2
    BpkRatingRounding.Up -> ceil(coercedRating * 2) / 2
    BpkRatingRounding.Nearest -> round(coercedRating * 2) / 2
  }
  Row(
    modifier = modifier.interactiveStarRatingSemantics(
      contentDescription = contentDescription,
      maxRating = maxRating,
      onRatingChanged = onRatingChanged,
      rating = rating,
    ),
  ) {
    for (item in 0 until maxRating) {
      val value = (roundedRating - item).coerceIn(0f, 1f)
      val starModifier = Modifier
        .applyIf(onRatingChanged != null) {
          clickable { onRatingChanged?.invoke(item + 1) }
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
        )
        else -> BpkStar(
          icon = BpkRatingStarType.Full,
          iconSize = iconSize,
          modifier = starModifier,
        )
      }
    }
  }
}

@Composable
private fun BpkStar(
  icon: BpkRatingStarType,
  iconSize: BpkIconSize,
  modifier: Modifier = Modifier,
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
      tint = BpkTheme.colors.statusWarningSpot,
      modifier = modifier,
    )
    BpkRatingStarType.Full -> BpkIcon(
      icon = BpkIcon.Star,
      contentDescription = null,
      size = iconSize,
      tint = BpkTheme.colors.statusWarningSpot,
      modifier = modifier,
    )
  }
}

private enum class BpkRatingStarType {
  Empty,
  Half,
  Full,
}

private fun Modifier.interactiveStarRatingSemantics(
  rating: Float,
  maxRating: Int,
  onRatingChanged: ((Int) -> Unit)?,
  contentDescription: ContentDescriptionScope.(Float, Int) -> String,
): Modifier = composed {
  val range = 0f..maxRating.toFloat()
  val scope = rememberContentDescriptionScope()
  semantics(mergeDescendants = true) {

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
    .progressSemantics(
      // this is needed to use volume keys
      value = rating,
      valueRange = range,
      steps = maxRating,
    )
}
