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
package net.skyscanner.backpack.compose.slider.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.hideFromAccessibility
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.flare.BpkFlarePointerDirection
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.FlareShape
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BpkRangeSliderImpl(
    value: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    enabled: Boolean,
    minValue: Float,
    maxValue: Float,
    steps: Int,
    onValueChangeFinished: (() -> Unit)?,
    modifier: Modifier = Modifier,
    lowerThumbLabel: String? = null,
    upperThumbLabel: String? = null,
) {
    val startInteractionSource = remember { MutableInteractionSource() }
    val endInteractionSource = remember { MutableInteractionSource() }

    val isLowerThumbBeingTouched = startInteractionSource.collectIsDraggedAsState().value
    val isUpperThumbBeingTouched = endInteractionSource.collectIsDraggedAsState().value

    val needsA11yProxy = lowerThumbLabel != null || upperThumbLabel != null

    val sliderContent: @Composable (Modifier) -> Unit = { sliderModifier ->
        RangeSlider(
            value = value,
            onValueChange = onValueChange,
            modifier = sliderModifier,
            enabled = enabled,
            startInteractionSource = startInteractionSource,
            endInteractionSource = endInteractionSource,
            valueRange = minValue..maxValue,
            steps = steps,
            onValueChangeFinished = onValueChangeFinished,
            startThumb = {
                if (isLowerThumbBeingTouched && lowerThumbLabel != null) {
                    SlideRangeLabel(
                        label = lowerThumbLabel,
                        enabled = enabled,
                        interactionSource = startInteractionSource,
                    )
                } else {
                    SliderThumb(
                        interactionSource = startInteractionSource,
                        enabled = enabled,
                    )
                }
            },
            endThumb = {
                if (isUpperThumbBeingTouched && upperThumbLabel != null) {
                    SlideRangeLabel(
                        label = upperThumbLabel,
                        enabled = enabled,
                        interactionSource = endInteractionSource,
                    )
                } else {
                    SliderThumb(
                        interactionSource = endInteractionSource,
                        enabled = enabled,
                    )
                }
            },
            track = { rangeSliderState ->
                RangeSliderTrack(
                    enabled = enabled,
                    rangeSliderState = rangeSliderState,
                )
            },
            colors = sliderColors(),
        )
    }

    if (needsA11yProxy) {
        Box(modifier = modifier.semantics { isTraversalGroup = true }) {
            sliderContent(Modifier.clearAndSetSemantics {})
            Row(modifier = Modifier.matchParentSize()) {
                if (lowerThumbLabel != null) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .rangeThumbSemantics(
                                label = lowerThumbLabel,
                                currentValue = value.start,
                                fullRange = minValue..maxValue,
                                clampRange = minValue..value.endInclusive,
                                steps = steps,
                                enabled = enabled,
                                onValueChange = { newStart ->
                                    onValueChange(newStart..value.endInclusive)
                                    onValueChangeFinished?.invoke()
                                },
                            ),
                    )
                }
                if (upperThumbLabel != null) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .rangeThumbSemantics(
                                label = upperThumbLabel,
                                currentValue = value.endInclusive,
                                fullRange = minValue..maxValue,
                                clampRange = value.start..maxValue,
                                steps = steps,
                                enabled = enabled,
                                onValueChange = { newEnd ->
                                    onValueChange(value.start..newEnd)
                                    onValueChangeFinished?.invoke()
                                },
                            ),
                    )
                }
            }
        }
    } else {
        sliderContent(modifier)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SlideRangeLabel(
    enabled: Boolean,
    label: String,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
) {
    LabelLayout(modifier) {
        Box(
            modifier = Modifier
                .background(
                    color = BpkTheme.colors.coreAccent,
                    shape = FlareShape(
                        borderRadius = BorderRadius,
                        flareHeight = FlareHeight,
                        pointerDirection = BpkFlarePointerDirection.Down,
                    ),
                )
                .padding(bottom = FlareHeight)
                .height(BpkSpacing.Lg)
                .padding(horizontal = BpkSpacing.Md),
            contentAlignment = Alignment.Center,
        ) {
            BpkText(
                text = label,
                color = BpkTheme.colors.textPrimaryInverse,
                style = BpkTheme.typography.label2,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier
                    .semantics { hideFromAccessibility() },
            )
        }
        SliderThumb(
            interactionSource = interactionSource,
            enabled = enabled,
        )
    }
}

@Composable
private fun LabelLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->
        val labelMeasurable = measurables[0]
        val thumbMeasurable = measurables[1]
        val thumbPlaceable = thumbMeasurable.measure(constraints)
        val labelPlaceable = labelMeasurable.measure(constraints)

        // ignore the label width as it'll be drawn outside the bounds of the range slider to avoid jumping around
        layout(thumbPlaceable.width, thumbPlaceable.height) {
            // ensure the label is centred on the thumb
            labelPlaceable.placeRelative(
                x = (thumbPlaceable.width - labelPlaceable.width) / 2,
                y = -(thumbPlaceable.width + labelPlaceable.height / 2),
            )
            thumbPlaceable.placeRelative(x = 0, y = 0)
        }
    }
}

private fun Modifier.rangeThumbSemantics(
    label: String,
    currentValue: Float,
    fullRange: ClosedFloatingPointRange<Float>,
    clampRange: ClosedFloatingPointRange<Float>,
    steps: Int,
    enabled: Boolean,
    onValueChange: (Float) -> Unit,
): Modifier = semantics {
    stateDescription = label
    progressBarRangeInfo = ProgressBarRangeInfo(currentValue, fullRange, steps)
    if (!enabled) disabled()
    setProgress { targetValue ->
        val newValue = snapToStep(targetValue, fullRange, steps)
            .coerceIn(clampRange.start, clampRange.endInclusive)
        if (newValue != currentValue) {
            onValueChange(newValue)
            true
        } else false
    }
}

// Mirrors M3's internal step-snapping logic: snaps a raw targetValue to the nearest
// discrete tick position defined by [steps]. steps=0 means continuous (no snapping).
private fun snapToStep(
    targetValue: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    steps: Int,
): Float {
    val rangeSize = valueRange.endInclusive - valueRange.start
    // Guard against zero-length range to avoid NaN/Infinity
    if (rangeSize <= 0f) return valueRange.start

    if (steps == 0) return targetValue.coerceIn(valueRange.start, valueRange.endInclusive)
    val totalIntervals = steps + 1
    val nearestIndex = ((targetValue - valueRange.start) / rangeSize * totalIntervals)
        .roundToInt()
        .coerceIn(0, totalIntervals)
    return valueRange.start + nearestIndex * rangeSize / totalIntervals
}

private val FlareHeight = 6.dp
private val BorderRadius = 6.dp
