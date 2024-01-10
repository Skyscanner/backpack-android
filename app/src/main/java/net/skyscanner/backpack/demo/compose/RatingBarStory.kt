/**
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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.rating.BpkRatingScale
import net.skyscanner.backpack.compose.ratingbar.BpkRatingBar
import net.skyscanner.backpack.compose.ratingbar.BpkRatingBarStyle
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.RatingBarComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@RatingBarComponent
@ComposeStory
fun RatingBarStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = BpkSpacing.Md),
    ) {
        RatingBarSection(style = BpkRatingBarStyle.Default)
        RatingBarSection(style = BpkRatingBarStyle.OnContrast)
    }
}

@Composable
internal fun RatingBarSection(style: BpkRatingBarStyle, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        BpkText(
            text = style.name,
            style = BpkTheme.typography.heading5,
            modifier = Modifier.padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Md),
        )
        RatingBarSample(style = style, scale = BpkRatingScale.ZeroToFive)
        RatingBarSample(style = style, scale = BpkRatingScale.ZeroToTen)
    }
}

@Composable
internal fun RatingBarSample(style: BpkRatingBarStyle, scale: BpkRatingScale, modifier: Modifier = Modifier) {
    BpkRatingBar(
        modifier = modifier
            .background(
                when (style) {
                    BpkRatingBarStyle.Default -> BpkTheme.colors.canvas
                    BpkRatingBarStyle.OnContrast -> BpkTheme.colors.canvasContrast
                },
            )
            .padding(BpkSpacing.Base),
        label = stringResource(R.string.generic_leading_text),
        rating = 4.7f,
        style = style,
        scale = scale,
    )
}
