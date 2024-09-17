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

package net.skyscanner.backpack.compose.progressbar.internal

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.progressbar.BpkProgressBarSize
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderSize
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import kotlin.math.abs

private val LinearIndicatorWidth = 240.dp

@Composable
internal fun BpkProgressBarImpl(
    progress: () -> Float,
    modifier: Modifier = Modifier,
    stepped: Boolean = false,
    max: Int = 1,
    progressBarSize: BpkProgressBarSize = BpkProgressBarSize.Small,
    color: Color = BpkTheme.colors.coreAccent,
    trackColor: Color = BpkTheme.colors.surfaceHighlight,
    strokeCap: StrokeCap = if (stepped) StrokeCap.Butt else StrokeCap.Round,
) {
    val height = if (progressBarSize == BpkProgressBarSize.Small) BpkSpacing.Md else BpkSpacing.Base
    val coercedProgress = { progress().coerceIn(0f, 1f) }
    val dividerColor = BpkTheme.colors.surfaceDefault

    Canvas(
        modifier
            .clip(CircleShape)
            .semantics(mergeDescendants = true) {
                progressBarRangeInfo = ProgressBarRangeInfo(coercedProgress(), 0f..1f)
            }
            .size(LinearIndicatorWidth, height),
    ) {
        val strokeWidth = size.height
        drawLinearIndicatorTrack(trackColor, strokeWidth, strokeCap)
        drawLinearIndicator(0f, coercedProgress(), color, strokeWidth, strokeCap)
        if (stepped) {
            drawStepIndicators(max, height, dividerColor)
        }
    }
}

private fun DrawScope.drawStepIndicators(
    max: Int,
    height: Dp,
    dividerColor: Color,
) {
    val numberOfDividers = max - 1

    val dividerWidth = BpkBorderSize.Lg.toPx()
    val sectionSize = size.width / max

    for (i in 1..numberOfDividers) {
        val dividerCenterY = sectionSize * i
        val dividerStartY = dividerCenterY - (dividerWidth / 2)
        drawRect(
            topLeft = Offset(dividerStartY, 0F),
            size = Size(dividerWidth, height.toPx()),
            color = dividerColor,
        )
    }
}

private fun DrawScope.drawLinearIndicatorTrack(
    color: Color,
    strokeWidth: Float,
    strokeCap: StrokeCap,
) = drawLinearIndicator(0f, 1f, color, strokeWidth, strokeCap)

private fun DrawScope.drawLinearIndicator(
    startFraction: Float,
    endFraction: Float,
    color: Color,
    strokeWidth: Float,
    strokeCap: StrokeCap,
) {
    val width = size.width
    val height = size.height
    // Start drawing from the vertical center of the stroke
    val yOffset = height / 2

    val isLtr = layoutDirection == LayoutDirection.Ltr
    val barStart = (if (isLtr) startFraction else 1f - endFraction) * width
    val barEnd = (if (isLtr) endFraction else 1f - startFraction) * width

    // if there isn't enough space to draw the stroke caps, fall back to StrokeCap.Butt
    if (strokeCap == StrokeCap.Butt || height > width) {
        // Progress line
        drawLine(color, Offset(barStart, yOffset), Offset(barEnd, yOffset), strokeWidth)
    } else {
        // need to adjust barStart and barEnd for the stroke caps
        val strokeCapOffset = strokeWidth / 2
        val coerceRange = strokeCapOffset..(width - strokeCapOffset)
        val adjustedBarStart = barStart.coerceIn(coerceRange)
        val adjustedBarEnd = barEnd.coerceIn(coerceRange)

        if (abs(endFraction - startFraction) > 0) {
            // Progress line
            drawLine(
                color,
                Offset(adjustedBarStart, yOffset),
                Offset(adjustedBarEnd, yOffset),
                strokeWidth,
                strokeCap,
            )
        }
    }
}
