/*
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.starrating.BpkInteractiveStarRating
import net.skyscanner.backpack.compose.starrating.BpkStarRatingSize
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.StarRatingInteractiveComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@StarRatingInteractiveComponent
@ComposeStory("Small")
fun InteractiveStarRatingStorySmall(modifier: Modifier = Modifier) {
    InteractiveStarRatingStoryContent(size = BpkStarRatingSize.Small, modifier = modifier)
}

@Composable
@StarRatingInteractiveComponent
@ComposeStory("Large")
fun InteractiveStarRatingStoryLarge(modifier: Modifier = Modifier) {
    InteractiveStarRatingStoryContent(size = BpkStarRatingSize.Large, modifier = modifier)
}

@Composable
@StarRatingInteractiveComponent
@ComposeStory("Extra Large")
fun InteractiveStarRatingStoryExtraLarge(modifier: Modifier = Modifier) {
    InteractiveStarRatingStoryContent(size = BpkStarRatingSize.ExtraLarge, modifier = modifier)
}

@Composable
private fun InteractiveStarRatingStoryContent(
    size: BpkStarRatingSize,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Lg),
    ) {
        val ratings = listOf(1, 2, 3, 4, 5)
        for (i in ratings) {
            key(i) {
                var rating by remember { mutableIntStateOf(i) }
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
