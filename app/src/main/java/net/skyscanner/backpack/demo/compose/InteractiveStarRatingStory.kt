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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.starrating.BpkInteractiveStarRating
import net.skyscanner.backpack.compose.starrating.BpkStarRatingSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.StarRatingInteractiveComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@StarRatingInteractiveComponent
@ComposeStory
fun InteractiveStarRatingStory(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxHeight(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(start = BpkSpacing.Xl),
            verticalArrangement = Arrangement.spacedBy(BpkSpacing.Lg),
        ) {
            InteractiveRatingSample(
                size = BpkStarRatingSize.Large,
                text = stringResource(R.string.icons_large),
            )
            InteractiveRatingSample(
                size = BpkStarRatingSize.Small,
                text = stringResource(R.string.icons_small),
            )
        }
    }
}

@Composable
private fun InteractiveRatingSample(
    size: BpkStarRatingSize,
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        BpkText(
            text = text,
            style = BpkTheme.typography.heading3,
        )
        val ratings = listOf(1, 2, 3, 4, 5)
        for (i in ratings) {
            key(i) {
                var rating by remember { mutableStateOf(i) }
                BpkInteractiveStarRating(
                    rating = rating,
                    onRatingChanged = { rating = it },
                    contentDescription = { value, max ->
                        stringResource(R.string.star_rating_accessibility_status, value, max)
                    },
                    size = size,
                )
            }
        }
    }
}
