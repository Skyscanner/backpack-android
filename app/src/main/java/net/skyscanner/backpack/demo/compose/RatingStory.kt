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
 */

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import net.skyscanner.backpack.compose.rating.BpkRating
import net.skyscanner.backpack.compose.rating.BpkRatingScale
import net.skyscanner.backpack.compose.rating.BpkRatingSize
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.starrating.BpkStarRating

@Composable
@Preview
fun RatingStory(
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
      .verticalScroll(rememberScrollState())
      .padding(BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
  ) {
    BpkRatingDefaultSample()
    BpkRatingTitleOnlySample()
    BpkRatingNoScaleSample()
    BpkRatingCustomContentSample()

    BpkRatingLargeSample()
    BpkRatingLargeTitleOnlySample()
    BpkRatingLargeNoScaleSample()
    BpkRatingLargeCustomContentSample()
    BpkRatingLargeCustomContentNoSubtitleSample()
  }
}

@Composable
@Preview
internal fun BpkRatingDefaultSample(
  modifier: Modifier = Modifier
) {
  BpkRating(
    title = stringResource(R.string.rating_title),
    subtitle = stringResource(R.string.rating_subtitle),
    value = RatingValue,
  )
}

@Composable
@Preview
internal fun BpkRatingTitleOnlySample(
  modifier: Modifier = Modifier
) {
  BpkRating(
    title = stringResource(R.string.rating_title),
    value = RatingValue,
    scale = BpkRatingScale.ZeroToTen,
  )
}

@Composable
@Preview
internal fun BpkRatingNoScaleSample(
  modifier: Modifier = Modifier,
) {
  BpkRating(
    title = stringResource(R.string.rating_title),
    subtitle = stringResource(R.string.rating_subtitle),
    value = RatingValue,
    showScale = false,
  )
}

@Composable
@Preview
internal fun BpkRatingCustomContentSample(
  modifier: Modifier = Modifier,
) {
  BpkRating(
    value = RatingValue,
    subtitle = stringResource(R.string.rating_subtitle),
  ) {
    CustomContent()
  }
}

@Composable
@Preview
internal fun BpkRatingLargeSample(
  modifier: Modifier = Modifier,
) {
  BpkRating(
    title = stringResource(R.string.rating_title),
    subtitle = stringResource(R.string.rating_subtitle),
    value = RatingValue,
    size = BpkRatingSize.Large,
  )
}

@Composable
@Preview
internal fun BpkRatingLargeTitleOnlySample(
  modifier: Modifier = Modifier,
) {
  BpkRating(
    title = stringResource(R.string.rating_title),
    value = RatingValue,
    size = BpkRatingSize.Large,
    scale = BpkRatingScale.ZeroToTen,
  )
}

@Composable
@Preview
internal fun BpkRatingLargeNoScaleSample(
  modifier: Modifier = Modifier,
) {
  BpkRating(
    title = stringResource(R.string.rating_title),
    subtitle = stringResource(R.string.rating_subtitle),
    value = RatingValue,
    size = BpkRatingSize.Large,
    showScale = false,
  )
}

@Composable
@Preview
internal fun BpkRatingLargeCustomContentSample(
  modifier: Modifier = Modifier,
) {
  BpkRating(
    value = RatingValue,
    size = BpkRatingSize.Large,
    subtitle = stringResource(R.string.rating_subtitle),
  ) {
    CustomContent()
  }
}

@Composable
@Preview
internal fun BpkRatingLargeCustomContentNoSubtitleSample(
  modifier: Modifier = Modifier,
) {
  BpkRating(
    value = RatingValue,
    size = BpkRatingSize.Large,
  ) {
    CustomContent()
  }
}

@Composable
private fun CustomContent(
  modifier: Modifier = Modifier,
) {
  AndroidView(
    modifier = modifier,
    factory = {
      BpkStarRating(it).apply {
        maxRating = 5
        rating = RatingValue
      }
    }
  )
}

private const val RatingValue = 4.57f
