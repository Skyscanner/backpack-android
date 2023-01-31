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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.starrating.BpkHotelRating
import net.skyscanner.backpack.compose.starrating.BpkRatingRounding
import net.skyscanner.backpack.compose.starrating.BpkStarRating
import net.skyscanner.backpack.compose.starrating.BpkStarRatingSize
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
    Column(
      modifier = Modifier.padding(start = BpkSpacing.Xl),
      verticalArrangement = Arrangement.spacedBy(BpkSpacing.Lg),
    ) {
      BpkText(
        text = stringResource(R.string.static_default_rating_title),
        style = BpkTheme.typography.heading2,
      )
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Lg),
      ) {
        StaticRatingSample(
          size = BpkStarRatingSize.Large,
          text = stringResource(R.string.icons_large),
        )
        StaticRatingSample(
          size = BpkStarRatingSize.Small,
          text = stringResource(R.string.icons_small),
        )
      }
      Column {
        BpkText(
          text = stringResource(R.string.hotel_star_rating_title),
          style = BpkTheme.typography.heading2,
        )
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Lg),
        ) {
          HotelRatingSample(
            size = BpkStarRatingSize.Large,
            text = stringResource(R.string.icons_large),
          )
          HotelRatingSample(
            size = BpkStarRatingSize.Small,
            text = stringResource(R.string.icons_small),
          )
        }
      }
    }
  }
}

@Composable
private fun HotelRatingSample(
  size: BpkStarRatingSize,
  text: String,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    BpkText(
      text = text,
      style = BpkTheme.typography.heading3,
    )
    for (i in 1..5) {
      BpkHotelRating(
        rating = i,
        contentDescription = { value, max ->
          stringResource(R.string.star_rating_accessibility_status, value, max)
        },
        size = size,
      )
    }
  }
}

@Composable
private fun StaticRatingSample(
  size: BpkStarRatingSize,
  text: String,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    BpkText(
      text = text,
      style = BpkTheme.typography.heading3,
    )
    val ratings = listOf(1f, 2f, 3f, 3.5f, 4f, 5f)
    for (i in ratings) {
      BpkStarRating(
        rating = i,
        contentDescription = { value, max ->
          stringResource(R.string.star_rating_accessibility_status, value, max)
        },
        rounding = BpkRatingRounding.Up,
        size = size,
      )
    }
  }
}
