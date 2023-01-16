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

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
  ) {
    Column() {
      BpkText(
        text = stringResource(R.string.static_default_rating_title),
        style = BpkTheme.typography.heading2,
      )
      BpkText(
        text = stringResource(R.string.icons_large),
        style = BpkTheme.typography.heading3,
      )
      BpkStarRating(
        rating = 1f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
        rounding = RoundingType.Up,
        size = BpkStarRatingSize.Large,
      )
      BpkStarRating(
        rating = 2f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
        rounding = RoundingType.Up,
        size = BpkStarRatingSize.Large,
      )
      BpkStarRating(
        rating = 3f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
        rounding = RoundingType.Up,
        size = BpkStarRatingSize.Large,
      )
      BpkStarRating(
        rating = 3.5f,
        contentDescription = { value, max ->
          Log.d("TAG", "The rating is $value out of $max")
          context.getString(R.string.star_rating_decimal_accessibility_status, value, max)
        },
        rounding = RoundingType.Up,
        size = BpkStarRatingSize.Large,
      )
      BpkStarRating(
        rating = 4f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
        rounding = RoundingType.Up,
        size = BpkStarRatingSize.Large,
      )
      BpkStarRating(
        rating = 5f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
        rounding = RoundingType.Up,
        size = BpkStarRatingSize.Large,
      )
//      Spacer(modifier = Modifier.padding(top = BpkSpacing.Lg))
      BpkText(
        text = stringResource(R.string.icons_small),
        style = BpkTheme.typography.heading3,
      )
      BpkStarRating(
        rating = 1f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
        rounding = RoundingType.Up,
      )
      BpkStarRating(
        rating = 2f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
        rounding = RoundingType.Up,
      )
      BpkStarRating(
        rating = 3f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
        rounding = RoundingType.Up,
      )
      BpkStarRating(
        rating = 3.5f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_decimal_accessibility_status, value, max)
        },
        rounding = RoundingType.Up,
      )
      BpkStarRating(
        rating = 4f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
        rounding = RoundingType.Up,
      )
      BpkStarRating(
        rating = 5f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
        rounding = RoundingType.Up,
      )
      Spacer(modifier = Modifier.padding(top = BpkSpacing.Lg))
      BpkText(
        text = stringResource(R.string.hotel_star_rating_title),
        style = BpkTheme.typography.heading2,
      )
      BpkText(
        text = stringResource(R.string.icons_large),
        style = BpkTheme.typography.heading3,
      )
      BpkHotelRating(
        rating = 1f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
        size = BpkStarRatingSize.Large,
      )
      BpkHotelRating(
        rating = 2f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
        size = BpkStarRatingSize.Large,
      )
      BpkHotelRating(
        rating = 3f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
        size = BpkStarRatingSize.Large,
      )
      BpkHotelRating(
        rating = 4f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
        size = BpkStarRatingSize.Large,
      )
      BpkHotelRating(
        rating = 5f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
        size = BpkStarRatingSize.Large,
      )
//      Spacer(modifier = Modifier.padding(top = BpkSpacing.Lg))
      BpkText(
        text = stringResource(R.string.icons_small),
        style = BpkTheme.typography.heading3,
      )
      BpkHotelRating(
        rating = 1f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
      )
      BpkHotelRating(
        rating = 2f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
      )
      BpkHotelRating(
        rating = 3f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
      )
      BpkHotelRating(
        rating = 4f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
      )
      BpkHotelRating(
        rating = 5f,
        contentDescription = { value, max ->
          context.getString(R.string.star_rating_accessibility_status, value, max)
        },
      )
    }
  }
}
