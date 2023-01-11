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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.starrating.BpkStarRating
import net.skyscanner.backpack.compose.starrating.RoundingType
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
fun BpkStarRatingStory(
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier.fillMaxHeight(),
    contentAlignment = Alignment.Center,
  ) {
    Column() {
      BpkText(
        text = stringResource(R.string.icons_large),
        style = BpkTheme.typography.label2,
      )
      BpkStarRating(maxRating = 5, rating = 1f, rounding = RoundingType.Up, iconSize = BpkIconSize.Large)
      BpkStarRating(maxRating = 5, rating = 2f, rounding = RoundingType.Up, iconSize = BpkIconSize.Large)
      BpkStarRating(maxRating = 5, rating = 3f, rounding = RoundingType.Up, iconSize = BpkIconSize.Large)
      BpkStarRating(maxRating = 5, rating = 3.5f, rounding = RoundingType.Up, iconSize = BpkIconSize.Large)
      BpkStarRating(maxRating = 5, rating = 4f, rounding = RoundingType.Up, iconSize = BpkIconSize.Large)
      BpkStarRating(maxRating = 5, rating = 5f, rounding = RoundingType.Up, iconSize = BpkIconSize.Large)
      Spacer(modifier = Modifier.padding(top = BpkSpacing.Lg))
      BpkText(
        text = stringResource(R.string.icons_small),
        style = BpkTheme.typography.label2,
      )
      BpkStarRating(maxRating = 5, rating = 1f, rounding = RoundingType.Up, iconSize = BpkIconSize.Small)
      BpkStarRating(maxRating = 5, rating = 2f, rounding = RoundingType.Up, iconSize = BpkIconSize.Small)
      BpkStarRating(maxRating = 5, rating = 3f, rounding = RoundingType.Up, iconSize = BpkIconSize.Small)
      BpkStarRating(maxRating = 5, rating = 3.5f, rounding = RoundingType.Up, iconSize = BpkIconSize.Small)
      BpkStarRating(maxRating = 5, rating = 4f, rounding = RoundingType.Up, iconSize = BpkIconSize.Small)
      BpkStarRating(maxRating = 5, rating = 5f, rounding = RoundingType.Up, iconSize = BpkIconSize.Small)
    }
  }
}

@Composable
fun BpkInteractiveStarRatingStory(
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier.fillMaxHeight(),
    contentAlignment = Alignment.Center,
  ) {
    Column() {
      BpkText(
        text = stringResource(R.string.icons_large),
        style = BpkTheme.typography.label2,
      )
      BpkStarRating(maxRating = 5, rating = 1f, rounding = RoundingType.Up, iconSize = BpkIconSize.Large)
      BpkStarRating(maxRating = 5, rating = 2f, rounding = RoundingType.Up, iconSize = BpkIconSize.Large)
      BpkStarRating(maxRating = 5, rating = 3f, rounding = RoundingType.Up, iconSize = BpkIconSize.Large)
      BpkStarRating(maxRating = 5, rating = 3.5f, rounding = RoundingType.Up, iconSize = BpkIconSize.Large)
      BpkStarRating(maxRating = 5, rating = 4f, rounding = RoundingType.Up, iconSize = BpkIconSize.Large)
      BpkStarRating(maxRating = 5, rating = 5f, rounding = RoundingType.Up, iconSize = BpkIconSize.Large)
      Spacer(modifier = Modifier.padding(top = BpkSpacing.Lg))
      BpkText(
        text = stringResource(R.string.icons_small),
        style = BpkTheme.typography.label2,
      )
      BpkStarRating(maxRating = 5, rating = 1f, rounding = RoundingType.Up, iconSize = BpkIconSize.Small)
      BpkStarRating(maxRating = 5, rating = 2f, rounding = RoundingType.Up, iconSize = BpkIconSize.Small)
      BpkStarRating(maxRating = 5, rating = 3f, rounding = RoundingType.Up, iconSize = BpkIconSize.Small)
      BpkStarRating(maxRating = 5, rating = 3.5f, rounding = RoundingType.Up, iconSize = BpkIconSize.Small)
      BpkStarRating(maxRating = 5, rating = 4f, rounding = RoundingType.Up, iconSize = BpkIconSize.Small)
      BpkStarRating(maxRating = 5, rating = 5f, rounding = RoundingType.Up, iconSize = BpkIconSize.Small)
    }
  }
}
