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

package net.skyscanner.backpack.compose.progressbar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderSize
import net.skyscanner.backpack.compose.tokens.BpkSpacing

enum class BpkProgressBarSize {
    Small,
    Large,
}

@Composable
fun BpkProgressBar(
    value: Float,
    modifier: Modifier = Modifier,
    size: BpkProgressBarSize = BpkProgressBarSize.Small,
    max: Int = 1,
    stepped: Boolean = false,
) {
    val height = if (size == BpkProgressBarSize.Small) BpkSpacing.Md else BpkSpacing.Base

    val progress = value
        .coerceIn(0F, max.toFloat())
        .div(max.toFloat())

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
    )

    val dividerColor = BpkTheme.colors.surfaceDefault

    val numberOfSteps = max

    LinearProgressIndicator(
        progress = animatedProgress,
        color = BpkTheme.colors.coreAccent,
        trackColor = BpkTheme.colors.surfaceHighlight,
        strokeCap = if (stepped) StrokeCap.Butt else StrokeCap.Round,
        modifier = modifier
            .height(height)
            .clip(CircleShape)
            .progressSemantics(
                value = value,
                valueRange = 0F..max.toFloat(),
                steps = if (stepped) numberOfSteps else 0,
            )
            .drawWithCache {
                onDrawWithContent {
                    drawContent()

                    if (stepped) {
                        val numberOfDividers = numberOfSteps - 1

                        val dividerWidth = BpkBorderSize.Lg.toPx()
                        val sectionSize = this.size.width / numberOfSteps

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
                }
            },
    )
}