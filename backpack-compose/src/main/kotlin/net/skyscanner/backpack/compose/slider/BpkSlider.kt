/*
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
 *
 */

package net.skyscanner.backpack.compose.slider

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.slider.internal.BpkRangeSliderImpl
import net.skyscanner.backpack.compose.slider.internal.sliderColors

@Composable
fun BpkSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    minValue: Float = 0f,
    maxValue: Float = 1f,
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Slider(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        valueRange = minValue..maxValue,
        steps = steps,
        onValueChangeFinished = onValueChangeFinished,
        interactionSource = interactionSource,
        colors = sliderColors(),
    )
}

@Composable
fun BpkRangeSlider(
    value: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    minValue: Float = 0f,
    maxValue: Float = 1f,
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
) {
    BpkRangeSliderImpl(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        minValue = minValue,
        maxValue = maxValue,
        steps = steps,
        onValueChangeFinished = onValueChangeFinished,
    )
}

@Composable
fun BpkRangeSlider(
    value: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    lowerThumbLabel: String,
    upperThumbLabel: String,
    modifier: Modifier = Modifier,
    maxValue: Float = 1f,
    enabled: Boolean = true,
    minValue: Float = 0f,
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
) {
    BpkRangeSliderImpl(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        minValue = minValue,
        maxValue = maxValue,
        steps = steps,
        lowerThumbLabel = lowerThumbLabel,
        upperThumbLabel = upperThumbLabel,
        onValueChangeFinished = onValueChangeFinished,
    )
}
