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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.rating.BpkRating
import net.skyscanner.backpack.compose.rating.BpkRatingScale
import net.skyscanner.backpack.compose.rating.BpkRatingSize
import net.skyscanner.backpack.compose.starrating.BpkStarRating
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.RatingComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@RatingComponent
@ComposeStory
fun RatingStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        BpkRatingDefaultSample()
        BpkRatingTitleOnlySample()
        BpkRatingNoTitleSample()
        BpkRatingNoScaleSample()
        BpkRatingCustomContentSample()

        BpkRatingLargeSample()
        BpkRatingLargeTitleOnlySample()
        BpkRatingLargeNoTitleSample()
        BpkRatingLargeNoScaleSample()
        BpkRatingLargeCustomContentSample()
        BpkRatingLargeCustomContentNoSubtitleSample()
    }
}

@Composable
internal fun BpkRatingDefaultSample(modifier: Modifier = Modifier) {
    BpkRating(
        modifier = modifier,
        title = stringResource(R.string.rating_title),
        subtitle = stringResource(R.string.rating_subtitle),
        value = RatingValue,
    )
}

@Composable
internal fun BpkRatingNoTitleSample(modifier: Modifier = Modifier) {
    BpkRating(
        modifier = modifier,
        title = null,
        subtitle = stringResource(R.string.rating_subtitle),
        value = RatingValue,
        scale = BpkRatingScale.ZeroToTen,
    )
}

@Composable
internal fun BpkRatingTitleOnlySample(modifier: Modifier = Modifier) {
    BpkRating(
        modifier = modifier,
        title = stringResource(R.string.rating_title),
        value = RatingValue,
        scale = BpkRatingScale.ZeroToTen,
    )
}

@Composable
internal fun BpkRatingNoScaleSample(modifier: Modifier = Modifier) {
    BpkRating(
        modifier = modifier,
        title = stringResource(R.string.rating_title),
        subtitle = stringResource(R.string.rating_subtitle),
        value = RatingValue,
        showScale = false,
    )
}

@Composable
internal fun BpkRatingCustomContentSample(modifier: Modifier = Modifier) {
    BpkRating(
        modifier = modifier,
        value = RatingValue,
        subtitle = stringResource(R.string.rating_subtitle),
    ) {
        CustomContent()
    }
}

@Composable
internal fun BpkRatingLargeSample(modifier: Modifier = Modifier) {
    BpkRating(
        modifier = modifier,
        title = stringResource(R.string.rating_title),
        subtitle = stringResource(R.string.rating_subtitle),
        value = RatingValue,
        size = BpkRatingSize.Large,
    )
}

@Composable
internal fun BpkRatingLargeTitleOnlySample(modifier: Modifier = Modifier) {
    BpkRating(
        modifier = modifier,
        title = stringResource(R.string.rating_title),
        value = RatingValue,
        size = BpkRatingSize.Large,
        scale = BpkRatingScale.ZeroToTen,
    )
}

@Composable
internal fun BpkRatingLargeNoTitleSample(modifier: Modifier = Modifier) {
    BpkRating(
        modifier = modifier,
        title = null,
        subtitle = stringResource(R.string.rating_subtitle),
        value = RatingValue,
        size = BpkRatingSize.Large,
    )
}

@Composable
internal fun BpkRatingLargeNoScaleSample(modifier: Modifier = Modifier) {
    BpkRating(
        modifier = modifier,
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
internal fun BpkRatingLargeCustomContentNoSubtitleSample(modifier: Modifier = Modifier) {
    BpkRating(
        modifier = modifier,
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
    val context = LocalContext.current
    BpkStarRating(
        rating = RatingValue,
        contentDescription = { _, max ->
            context.getString(R.string.star_rating_accessibility_status, RatingValue, max)
        },
        modifier = modifier,
    )
}

private const val RatingValue = 4.57f
