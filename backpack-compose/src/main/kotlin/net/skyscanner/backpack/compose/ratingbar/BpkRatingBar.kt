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

package net.skyscanner.backpack.compose.ratingbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.semantics
import net.skyscanner.backpack.compose.progressbar.internal.BpkProgressBarImpl
import net.skyscanner.backpack.compose.rating.BpkRatingScale
import net.skyscanner.backpack.compose.rating.BpkRatingSize
import net.skyscanner.backpack.compose.rating.internal.BpkRatingNumbers
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.internal.BpkRatingBarColors
import net.skyscanner.backpack.compose.utils.invisibleSemantic

@Composable
fun BpkRatingBar(
    label: String,
    rating: Float,
    modifier: Modifier = Modifier,
    style: BpkRatingBarStyle = BpkRatingBarStyle.Default,
    scale: BpkRatingScale = BpkRatingScale.ZeroToFive,
    showScale: Boolean = true,
) {
    Column(
        modifier = modifier.semantics(mergeDescendants = true) { },
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            BpkText(
                text = label,
                style = BpkTheme.typography.footnote,
                modifier = Modifier.weight(1f),
            )
            BpkRatingNumbers(value = rating, scale = scale, size = BpkRatingSize.Base, showScale = showScale)
        }
        BpkProgressBarImpl(
            progress = {
                when (scale) {
                    BpkRatingScale.ZeroToFive -> rating / 5F
                    BpkRatingScale.ZeroToTen -> rating / 10F
                }
            },
            color = BpkTheme.colors.corePrimary,
            trackColor = when (style) {
                BpkRatingBarStyle.Default -> BpkRatingBarColors.barTrackDefault
                BpkRatingBarStyle.OnContrast -> BpkRatingBarColors.barTrackOnContrast
            },
            strokeCap = StrokeCap.Round,
            modifier = Modifier
                .fillMaxWidth()
                .invisibleSemantic(),
        )
    }
}

enum class BpkRatingBarStyle {
    Default,
    OnContrast,
}
