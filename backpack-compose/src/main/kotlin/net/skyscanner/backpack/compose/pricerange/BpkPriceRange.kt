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

package net.skyscanner.backpack.compose.pricerange

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.pricerange.internal.BpkPriceRangeImpl
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
fun BpkPriceRange(
    configuration: BpkPriceRangeConfiguration,
    modifier: Modifier = Modifier,
    cardWidth: Dp? = null,
    contentDescription: String? = null,
) {
    BpkPriceRangeImpl(
        configuration = configuration,
        modifier = modifier,
        cardWidth = cardWidth,
        contentDescription = contentDescription,
    )
}

@Immutable
sealed class BpkPriceRangeConfiguration(
    open val min: Float,
    open val max: Float,
    open val lowerSegmentPercentage: Float,
    open val upperSegmentPercentage: Float,
    open val markerPercentage: Float,
    open val barHeight: Dp,
    open val markerHeight: Dp,
    open val markerBackgroundColor: Color?,
    open val markerTextColor: Color?,
) {
    val range: Float get() = max - min

    @Immutable
    data class Default(
        val marker: BpkPriceRangeMarker.Label,
        val segments: BpkPriceRangeSegmentsLabeled,
        override val min: Float = 0f,
        override val max: Float = 100f,
        override val lowerSegmentPercentage: Float = segments.lower.percentage,
        override val upperSegmentPercentage: Float = segments.upper.percentage,
        override val markerPercentage: Float = marker.percentage,
        override val barHeight: Dp = BpkSpacing.Md,
        override val markerHeight: Dp = 36.dp,
        override val markerBackgroundColor: Color? = null,
        override val markerTextColor: Color? = null,
    ) : BpkPriceRangeConfiguration(
        min = min,
        max = max,
        lowerSegmentPercentage = lowerSegmentPercentage,
        upperSegmentPercentage = upperSegmentPercentage,
        markerPercentage = markerPercentage,
        barHeight = barHeight,
        markerHeight = markerHeight,
        markerBackgroundColor = markerBackgroundColor,
        markerTextColor = markerTextColor,
    )

    @Immutable
    data class Collapsed(
        val segments: BpkPriceRangeSegmentsPlain,
        override val markerPercentage: Float,
        override val min: Float = 0f,
        override val max: Float = 100f,
        override val lowerSegmentPercentage: Float = segments.lower,
        override val upperSegmentPercentage: Float = segments.upper,
        override val barHeight: Dp = BpkSpacing.Sm,
        override val markerHeight: Dp = 12.dp,
        override val markerBackgroundColor: Color? = null,
        override val markerTextColor: Color? = null,
    ) : BpkPriceRangeConfiguration(
        min = min,
        max = max,
        lowerSegmentPercentage = lowerSegmentPercentage,
        upperSegmentPercentage = upperSegmentPercentage,
        markerPercentage = markerPercentage,
        barHeight = barHeight,
        markerHeight = markerHeight,
        markerBackgroundColor = markerBackgroundColor,
        markerTextColor = markerTextColor,
    )
}

@Immutable
sealed class BpkPriceRangeMarker {
    @Immutable
    data class Label(
        val text: String,
        val percentage: Float,
    ) : BpkPriceRangeMarker()

    @Immutable
    data class Circle(
        val percentage: Float,
    ) : BpkPriceRangeMarker()
}

@Immutable
data class BpkPriceRangeSegmentsLabeled(
    val lower: LabeledPoint,
    val upper: LabeledPoint,
) {
    @Immutable
    data class LabeledPoint(val text: String, val percentage: Float)
}

@Immutable
data class BpkPriceRangeSegmentsPlain(
    val lower: Float,
    val upper: Float,
)
