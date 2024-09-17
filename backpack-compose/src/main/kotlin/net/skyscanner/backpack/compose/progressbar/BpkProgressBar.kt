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
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.progressbar.internal.BpkProgressBarImpl

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
    val progress = value
        .coerceIn(0F, max.toFloat())
        .div(max.toFloat())

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
    )

    BpkProgressBarImpl(
        progress = { animatedProgress },
        progressBarSize = size,
        max = max,
        stepped = stepped,
        modifier = modifier
            .progressSemantics(
                value = value,
                valueRange = 0F..max.toFloat(),
                steps = if (stepped) max else 0,
            ),
    )
}
