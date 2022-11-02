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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.rating.BpkRating
import net.skyscanner.backpack.compose.rating.BpkRatingScale
import net.skyscanner.backpack.compose.rating.BpkRatingSize
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
@Preview
fun RatingStory(
  modifier: Modifier = Modifier,
) {
  BpkTheme {
    BpkTheme.colors
    Column(
      modifier = modifier.padding(BpkSpacing.Base),
      verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
      BpkRatingDefaultSample()
      BpkRatingLargeSample()
      BpkRatingTitleOnlySample()
      BpkRatingNoScaleSample()
      BpkRatingTenScaleSample()
    }
  }
}

@Composable
@Preview
internal fun BpkRatingDefaultSample(
  modifier: Modifier = Modifier
) {
  BpkRating(
    title = stringResource(R.string.generic_title),
    value = 4.74f,
    subtitle = stringResource(R.string.generic_subtitle),
  )
}

@Composable
@Preview
internal fun BpkRatingLargeSample(
  modifier: Modifier = Modifier
) {
  BpkRating(
    title = stringResource(R.string.generic_title),
    value = 4.74f,
    size = BpkRatingSize.Large,
    subtitle = stringResource(R.string.generic_subtitle),
  )
}

@Composable
@Preview
internal fun BpkRatingTitleOnlySample(
  modifier: Modifier = Modifier
) {
  BpkRating(
    title = stringResource(R.string.generic_title),
    value = 4.74f,
  )
}

@Composable
@Preview
internal fun BpkRatingNoScaleSample(
  modifier: Modifier = Modifier
) {
  BpkRating(
    title = stringResource(R.string.generic_title),
    value = 4.74f,
    scale = null,
  )
}

@Composable
@Preview
internal fun BpkRatingTenScaleSample(
  modifier: Modifier = Modifier
) {
  BpkRating(
    title = stringResource(R.string.generic_title),
    value = 4.74f,
    subtitle = stringResource(R.string.generic_subtitle),
    scale = BpkRatingScale.ZeroToTen,
  )
}
