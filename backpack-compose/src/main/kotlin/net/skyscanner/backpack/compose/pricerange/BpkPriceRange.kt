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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.theme.BpkTheme
import kotlin.math.roundToInt
import net.skyscanner.backpack.compose.flare.BpkFlarePointerDirection
import net.skyscanner.backpack.compose.utils.FlareShape

@Composable
fun BpkPriceRange(
    configuration: BpkPriceRangeConfiguration,
    modifier: Modifier = Modifier,
    cardWidth: Dp? = null,
) {
    val contentModifier = if (cardWidth != null) modifier.width(cardWidth) else modifier

    Box(modifier = contentModifier.semantics(mergeDescendants = true) {}) {
        when (configuration) {
            is BpkPriceRangeConfiguration.Default -> {
                Column(verticalArrangement = Arrangement.spacedBy(BpkSpacing.Sm)) {
                    Marker(
                        configuration = configuration,
                        marker = configuration.marker,
                        collapsed = false,
                        modifier = Modifier
                            .height(configuration.markerHeight)
                            .fillMaxWidth(),
                    )
                    Bar(
                        configuration = configuration,
                        modifier = Modifier
                            .height(configuration.barHeight)
                            .fillMaxWidth(),
                    )
                    SegmentLabels(
                        configuration = configuration,
                        segments = configuration.segments,
                        modifier = Modifier.height(SegmentLabelHeight),
                    )
                }
            }

            is BpkPriceRangeConfiguration.Collapsed -> {
                Box {
                    Bar(
                        configuration = configuration,
                        modifier = Modifier
                            .height(configuration.barHeight)
                            .fillMaxWidth()
                            .align(Alignment.CenterStart),
                    )
                    Marker(
                        configuration = configuration,
                        marker = BpkPriceRangeMarker.Circle(configuration.markerPercentage),
                        collapsed = true,
                        modifier = Modifier
                            .height(configuration.markerHeight)
                            .fillMaxWidth()
                            .align(Alignment.CenterStart),
                    )
                }
            }
        }
    }
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

private val BarSpacing = 2.dp
private val SegmentLabelHeight = 20.dp
private val MarkerFlareHeight = 6.dp

@Composable
private fun Bar(
    configuration: BpkPriceRangeConfiguration,
    modifier: Modifier = Modifier,
) {
    val leadingColor = BpkTheme.colors.statusSuccessSpot
    val middleColor = BpkTheme.colors.corePrimary
    val trailingColor = BpkTheme.colors.statusDangerSpot

    BoxWithConstraints(modifier = modifier) {
        val spacing = BarSpacing
        val available = (maxWidth - spacing - spacing).coerceAtLeast(0.dp)

        val range = configuration.range.takeIf { it > 0f } ?: 1f
        val wpp = available / range

        val leadPct = (configuration.lowerSegmentPercentage - configuration.min).coerceAtLeast(0f)
        val midPct = (configuration.upperSegmentPercentage - configuration.lowerSegmentPercentage).coerceAtLeast(0f)

        val wLead = (wpp * leadPct).coerceIn(0.dp, available)
        val wMid = (wpp * midPct).coerceIn(0.dp, (available - wLead).coerceAtLeast(0.dp))
        val wTrail = (available - wLead - wMid).coerceAtLeast(0.dp)

        val radius = configuration.barHeight / 2f
        val startCorners = RoundedCornerShape(topStart = radius, bottomStart = radius)
        val endCorners = RoundedCornerShape(topEnd = radius, bottomEnd = radius)

        Row {
            Box(
                Modifier
                    .width(wLead)
                    .height(configuration.barHeight)
                    .clip(startCorners)
                    .background(leadingColor),
            )
            Spacer(Modifier.width(spacing))
            Box(
                Modifier
                    .width(wMid)
                    .height(configuration.barHeight)
                    .background(middleColor),
            )
            Spacer(Modifier.width(spacing))
            Box(
                Modifier
                    .width(wTrail)
                    .height(configuration.barHeight)
                    .clip(endCorners)
                    .background(trailingColor),
            )
        }
    }
}

@Composable
private fun Marker(
    configuration: BpkPriceRangeConfiguration,
    marker: BpkPriceRangeMarker,
    collapsed: Boolean,
    modifier: Modifier = Modifier,
) {
    val bg = resolvedMarkerBackgroundColor(configuration)
    val textColor = resolvedMarkerTextColor(configuration)

    SubcomposeLayout(modifier = modifier) { constraints ->
        val widthPx = constraints.maxWidth
        val heightPx = constraints.maxHeight

        val markerPct = when (marker) {
            is BpkPriceRangeMarker.Label -> marker.percentage
            is BpkPriceRangeMarker.Circle -> marker.percentage
        }

        val centerFromStart = centerFromStartPx(
            percentage = markerPct,
            configuration = configuration,
            availableWidthPx = widthPx.toFloat(),
        )

        val placeables = subcompose("marker") {
            when {
                collapsed -> CircleMarker(background = bg, size = configuration.markerHeight)
                marker is BpkPriceRangeMarker.Label -> LabelMarker(
                    text = marker.text,
                    background = bg,
                    textColor = textColor,
                )
            }
        }.map { it.measure(Constraints()) }

        val p = placeables.firstOrNull()
        val markerW = p?.width ?: 0
        val markerH = p?.height ?: 0

        val x = (centerFromStart - markerW / 2f)
            .roundToInt()
            .coerceIn(0, (widthPx - markerW).coerceAtLeast(0))

        val y = if (collapsed) {
            ((heightPx - markerH) / 2f).roundToInt().coerceAtLeast(0)
        } else {
            0
        }

        layout(widthPx, heightPx) {
            p?.placeRelative(x, y)
        }
    }
}

@Composable
private fun CircleMarker(
    background: Color,
    size: Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(size)
            .background(background, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(4.dp)
                .background(BpkTheme.colors.textPrimaryInverse, CircleShape),
        )
    }
}

@Composable
private fun LabelMarker(
    text: String,
    background: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = background,
                shape = FlareShape(
                    borderRadius = 4.dp,
                    flareHeight = MarkerFlareHeight,
                    pointerDirection = BpkFlarePointerDirection.Down,
                ),
            )
            .padding(bottom = MarkerFlareHeight)
            .padding(horizontal = BpkSpacing.Md, vertical = BpkSpacing.Sm),
        contentAlignment = Alignment.Center,
    ) {
        BpkText(
            text = text,
            style = BpkTheme.typography.label2,
            color = textColor,
        )
    }
}

@Composable
private fun SegmentLabels(
    configuration: BpkPriceRangeConfiguration,
    segments: BpkPriceRangeSegmentsLabeled,
    modifier: Modifier = Modifier,
) {
    Layout(
        modifier = modifier,
        content = {
            BpkText(text = segments.lower.text, style = BpkTheme.typography.footnote)
            BpkText(text = segments.upper.text, style = BpkTheme.typography.footnote)
        },
    ) { measurables, constraints ->
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        val lower = measurables[0].measure(Constraints())
        val upper = measurables[1].measure(Constraints())

        val lowerCenterFromStart = centerFromStartPx(
            percentage = segments.lower.percentage,
            configuration = configuration,
            availableWidthPx = width.toFloat(),
        )
        val upperCenterFromStart = centerFromStartPx(
            percentage = segments.upper.percentage,
            configuration = configuration,
            availableWidthPx = width.toFloat(),
        )

        val xLower = (lowerCenterFromStart - lower.width / 2f)
            .roundToInt()
            .coerceIn(0, (width - lower.width).coerceAtLeast(0))

        val xUpper = (upperCenterFromStart - upper.width / 2f)
            .roundToInt()
            .coerceIn(0, (width - upper.width).coerceAtLeast(0))

        val yLower = ((height - lower.height) / 2f).roundToInt()
        val yUpper = ((height - upper.height) / 2f).roundToInt()

        layout(width, height) {
            lower.placeRelative(xLower, yLower)
            upper.placeRelative(xUpper, yUpper)
        }
    }
}

private fun centerFromStartPx(
    percentage: Float,
    configuration: BpkPriceRangeConfiguration,
    availableWidthPx: Float,
): Float {
    val range = configuration.range
    if (range <= 0f) return 0f

    val clamped = percentage.coerceIn(configuration.min, configuration.max)
    val fraction = (clamped - configuration.min) / range
    return fraction * availableWidthPx
}

@Composable
private fun resolvedMarkerBackgroundColor(configuration: BpkPriceRangeConfiguration): Color {
    configuration.markerBackgroundColor?.let { return it }
    return when {
        configuration.markerPercentage < configuration.lowerSegmentPercentage -> BpkTheme.colors.statusSuccessSpot
        configuration.markerPercentage > configuration.upperSegmentPercentage -> BpkTheme.colors.statusDangerSpot
        else -> BpkTheme.colors.corePrimary
    }
}

@Composable
private fun resolvedMarkerTextColor(configuration: BpkPriceRangeConfiguration): Color {
    configuration.markerTextColor?.let { return it }
    return when {
        configuration.markerPercentage < configuration.lowerSegmentPercentage ||
            configuration.markerPercentage > configuration.upperSegmentPercentage ->
            BpkTheme.colors.textPrimaryInverse
        else ->
            BpkTheme.colors.textOnDark
    }
}
