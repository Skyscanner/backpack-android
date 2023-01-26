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

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.starrating.BpkHotelRating
import net.skyscanner.backpack.compose.starrating.BpkStarRating
import net.skyscanner.backpack.compose.starrating.BpkStarRatingSize
import net.skyscanner.backpack.compose.starrating.RoundingType
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
fun BpkStarRatingStory(
  modifier: Modifier = Modifier,
) {
  val context = LocalContext.current
  Box(
    modifier = modifier.fillMaxHeight(),
    contentAlignment = Alignment.Center,
  ) {
    Column(modifier = Modifier.padding(start = BpkSpacing.Lg)) {
      BpkText(
        text = stringResource(R.string.static_default_rating_title),
        style = BpkTheme.typography.heading2,
      )
      Row() {
        Column(modifier = Modifier.padding(end = BpkSpacing.Xl)) {
          StaticRatingSample(
            context = context,
            size = BpkStarRatingSize.Large,
            text = stringResource(R.string.icons_large),
          )
        }
        Column() {
          StaticRatingSample(
            context = context,
            size = BpkStarRatingSize.Small,
            text = stringResource(R.string.icons_small),
          )
        }
      }
      Spacer(modifier = Modifier.padding(top = BpkSpacing.Lg))
      Column {
        BpkText(
          text = stringResource(R.string.hotel_star_rating_title),
          style = BpkTheme.typography.heading2,
        )
        Row {
          Column(modifier = Modifier.padding(end = BpkSpacing.Xl)) {
            HotelRatingSample(
              context = context,
              size = BpkStarRatingSize.Large,
              text = stringResource(R.string.icons_large),
            )
          }
          Column {
            HotelRatingSample(
              context = context,
              size = BpkStarRatingSize.Small,
              text = stringResource(R.string.icons_small),
            )
          }
        }
      }
    }
  }
}

@Composable
private fun HotelRatingSample(context: Context, size: BpkStarRatingSize, text: String) {
  BpkText(
    text = text,
    style = BpkTheme.typography.heading3,
  )
  for (i in 1..5) {
    BpkHotelRating(
      rating = i,
      contentDescription = { value, max ->
        context.getString(R.string.star_rating_accessibility_status, value, max)
      },
      size = size,
    )
  }
}

@Composable
private fun StaticRatingSample(context: Context, size: BpkStarRatingSize, text: String) {
  BpkText(
    text = text,
    style = BpkTheme.typography.heading3,
  )
  val ratings = listOf(1, 2, 3, 3.5, 4, 5)
  for (i in ratings) {
    BpkStarRating(
      rating = i.toFloat(),
      contentDescription = { value, max ->
        context.getString(R.string.star_rating_accessibility_status, value, max)
      },
      rounding = RoundingType.Up,
      size = size,
    )
  }
}
