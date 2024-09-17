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

@file:OptIn(ExperimentalMaterial3Api::class)

package net.skyscanner.backpack.compose.slider.internal

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSliderState
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.theme.BpkTheme

private val ThumbSize = 20.dp
private val ThumbDefaultElevation = 1.dp
private val ThumbPressedElevation = 6.dp
private val StateLayerSize = 40.0.dp
private val TickSize = 2.0.dp
private val TrackHeight = 4.0.dp

@Composable
internal fun SliderThumb(
    enabled: Boolean,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
) {
    val interactions = remember { mutableStateListOf<Interaction>() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> interactions.add(interaction)
                is PressInteraction.Release -> interactions.remove(interaction.press)
                is PressInteraction.Cancel -> interactions.remove(interaction.press)
                is DragInteraction.Start -> interactions.add(interaction)
                is DragInteraction.Stop -> interactions.remove(interaction.start)
                is DragInteraction.Cancel -> interactions.remove(interaction.start)
            }
        }
    }

    val elevation = if (interactions.isNotEmpty()) {
        ThumbPressedElevation
    } else {
        ThumbDefaultElevation
    }

    @Suppress("DEPRECATION_ERROR")
    (Spacer(
        modifier
            .size(ThumbSize)
            .indication(
                interactionSource = interactionSource,
                indication = androidx.compose.material.ripple.rememberRipple(
                    bounded = false,
                    radius = StateLayerSize / 2,
                ),
            )
            .hoverable(interactionSource = interactionSource)
            .shadow(if (enabled) elevation else 0.dp, CircleShape, clip = false)
            .background(sliderColors().thumbColor(enabled = enabled), CircleShape),
    ))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SliderTrack(
    enabled: Boolean,
    sliderState: SliderState,
    modifier: Modifier = Modifier,
) {
    val colors = sliderColors()
    val inactiveTrackColor = colors.trackColor(enabled, active = false)
    val activeTrackColor = colors.trackColor(enabled, active = true)
    val inactiveTickColor = colors.tickColor(enabled, active = false)
    val activeTickColor = colors.tickColor(enabled, active = true)
    Canvas(
        modifier
            .fillMaxWidth()
            .height(TrackHeight),
    ) {
        drawTrack(
            stepsToTickFractions(sliderState.steps),
            0f,
            sliderState.coercedValueAsFraction,
            inactiveTrackColor,
            activeTrackColor,
            inactiveTickColor,
            activeTickColor,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RangeSliderTrack(
    enabled: Boolean,
    rangeSliderState: RangeSliderState,
    modifier: Modifier = Modifier,
) {
    val colors = sliderColors()
    val inactiveTrackColor = colors.trackColor(enabled, active = false)
    val activeTrackColor = colors.trackColor(enabled, active = true)
    val inactiveTickColor = colors.tickColor(enabled, active = false)
    val activeTickColor = colors.tickColor(enabled, active = true)
    Canvas(
        modifier
            .fillMaxWidth()
            .height(TrackHeight),
    ) {
        drawTrack(
            stepsToTickFractions(rangeSliderState.steps),
            rangeSliderState.coercedActiveRangeStartAsFraction,
            rangeSliderState.coercedActiveRangeEndAsFraction,
            inactiveTrackColor,
            activeTrackColor,
            inactiveTickColor,
            activeTickColor,
        )
    }
}

@Composable
internal fun sliderColors() = SliderDefaults.colors(
    thumbColor = BpkTheme.colors.coreAccent,
    activeTrackColor = BpkTheme.colors.coreAccent,
    inactiveTrackColor = BpkTheme.colors.line,
    activeTickColor = BpkTheme.colors.coreAccent,
    inactiveTickColor = BpkTheme.colors.line,
)

@Stable
internal fun SliderColors.thumbColor(enabled: Boolean): Color =
    if (enabled) thumbColor else disabledThumbColor

@Stable
internal fun SliderColors.trackColor(enabled: Boolean, active: Boolean): Color =
    if (enabled) {
        if (active) activeTrackColor else inactiveTrackColor
    } else {
        if (active) disabledActiveTrackColor else disabledInactiveTrackColor
    }

@Stable
internal fun SliderColors.tickColor(enabled: Boolean, active: Boolean): Color =
    if (enabled) {
        if (active) activeTickColor else inactiveTickColor
    } else {
        if (active) disabledActiveTickColor else disabledInactiveTickColor
    }

private fun DrawScope.drawTrack(
    tickFractions: FloatArray,
    activeRangeStart: Float,
    activeRangeEnd: Float,
    inactiveTrackColor: Color,
    activeTrackColor: Color,
    inactiveTickColor: Color,
    activeTickColor: Color,
) {
    val isRtl = layoutDirection == LayoutDirection.Rtl
    val sliderLeft = Offset(0f, center.y)
    val sliderRight = Offset(size.width, center.y)
    val sliderStart = if (isRtl) sliderRight else sliderLeft
    val sliderEnd = if (isRtl) sliderLeft else sliderRight
    val tickSize = TickSize.toPx()
    val trackStrokeWidth = TrackHeight.toPx()
    drawLine(
        inactiveTrackColor,
        sliderStart,
        sliderEnd,
        trackStrokeWidth,
        StrokeCap.Round,
    )
    val sliderValueEnd = Offset(
        sliderStart.x +
            (sliderEnd.x - sliderStart.x) * activeRangeEnd,
        center.y,
    )

    val sliderValueStart = Offset(
        sliderStart.x +
            (sliderEnd.x - sliderStart.x) * activeRangeStart,
        center.y,
    )

    drawLine(
        activeTrackColor,
        sliderValueStart,
        sliderValueEnd,
        trackStrokeWidth,
        StrokeCap.Round,
    )

    for (tick in tickFractions) {
        val outsideFraction = tick > activeRangeEnd || tick < activeRangeStart
        drawCircle(
            color = if (outsideFraction) inactiveTickColor else activeTickColor,
            center = Offset(lerp(sliderStart, sliderEnd, tick).x, center.y),
            radius = tickSize / 2f,
        )
    }
}

internal val RangeSliderState.coercedActiveRangeStartAsFraction
    get() = calcFraction(valueRange.start, valueRange.endInclusive, activeRangeStart)

internal val RangeSliderState.coercedActiveRangeEndAsFraction
    get() = calcFraction(valueRange.start, valueRange.endInclusive, activeRangeEnd)

internal val SliderState.coercedValueAsFraction
    get() =
        calcFraction(
            valueRange.start,
            valueRange.endInclusive,
            value.coerceIn(valueRange.start, valueRange.endInclusive),
        )

private fun stepsToTickFractions(steps: Int): FloatArray {
    return if (steps == 0) floatArrayOf() else FloatArray(steps + 2) { it.toFloat() / (steps + 1) }
}

private fun calcFraction(a: Float, b: Float, pos: Float) =
    (if (b - a == 0f) 0f else (pos - a) / (b - a)).coerceIn(0f, 1f)
