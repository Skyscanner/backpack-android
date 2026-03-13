/**
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
 */

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.pricerange.BpkPriceRange
import net.skyscanner.backpack.compose.pricerange.BpkPriceRangeConfiguration
import net.skyscanner.backpack.compose.pricerange.BpkPriceRangeMarker
import net.skyscanner.backpack.compose.pricerange.BpkPriceRangeSegmentsLabeled
import net.skyscanner.backpack.compose.pricerange.BpkPriceRangeSegmentsPlain
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.demo.components.PriceRangeComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@PriceRangeComponent
@ComposeStory("All")
fun PriceRangeStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Xxl),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md)) {
            BpkText(
                text = "Default",
                style = BpkTheme.typography.heading3,
                color = BpkTheme.colors.textPrimary,
            )

            BpkPriceRange(
                configuration = BpkPriceRangeConfiguration.Default(
                    marker = BpkPriceRangeMarker.Label("£850", 85f),
                    segments = BpkPriceRangeSegmentsLabeled(
                        lower = BpkPriceRangeSegmentsLabeled.LabeledPoint("£200", 20f),
                        upper = BpkPriceRangeSegmentsLabeled.LabeledPoint("£800", 80f),
                    ),
                ),
                cardWidth = 240.dp,
            )

            BpkPriceRange(
                configuration = BpkPriceRangeConfiguration.Default(
                    marker = BpkPriceRangeMarker.Label("£500", 50f),
                    segments = BpkPriceRangeSegmentsLabeled(
                        lower = BpkPriceRangeSegmentsLabeled.LabeledPoint("£200", 20f),
                        upper = BpkPriceRangeSegmentsLabeled.LabeledPoint("£800", 80f),
                    ),
                ),
                cardWidth = 240.dp,
            )

            BpkPriceRange(
                configuration = BpkPriceRangeConfiguration.Default(
                    marker = BpkPriceRangeMarker.Label("£150", 15f),
                    segments = BpkPriceRangeSegmentsLabeled(
                        lower = BpkPriceRangeSegmentsLabeled.LabeledPoint("£200", 20f),
                        upper = BpkPriceRangeSegmentsLabeled.LabeledPoint("£800", 80f),
                    ),
                ),
                cardWidth = 240.dp,
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md)) {
            BpkText(
                text = "Collapsed",
                style = BpkTheme.typography.heading3,
                color = BpkTheme.colors.textPrimary,
            )

            BpkPriceRange(
                configuration = BpkPriceRangeConfiguration.Collapsed(
                    markerPercentage = 90f,
                    segments = BpkPriceRangeSegmentsPlain(lower = 20f, upper = 80f),
                ),
                cardWidth = 140.dp,
            )

            BpkPriceRange(
                configuration = BpkPriceRangeConfiguration.Collapsed(
                    markerPercentage = 50f,
                    segments = BpkPriceRangeSegmentsPlain(lower = 20f, upper = 80f),
                ),
                cardWidth = 140.dp,
            )

            BpkPriceRange(
                configuration = BpkPriceRangeConfiguration.Collapsed(
                    markerPercentage = 10f,
                    segments = BpkPriceRangeSegmentsPlain(lower = 20f, upper = 80f),
                ),
                cardWidth = 140.dp,
            )
        }
    }
}
