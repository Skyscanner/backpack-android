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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RangeSlider
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.theme.BpkTheme

@Composable
fun BpkSlider(
  value: Float,
  onValueChange: (Float) -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  minValue: Float = 0f,
  maxValue: Float = 1f,
  valueRange: ClosedFloatingPointRange<Float> = minValue..maxValue,
  steps: Int,
  onValueChangeFinished: (() -> Unit)? = null,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  Slider(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier,
    enabled = enabled,
    valueRange = valueRange,
    steps = steps,
    onValueChangeFinished = onValueChangeFinished,
    interactionSource = interactionSource,
    colors = sliderColors(),
  )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BpkRangeSlider(
  value: ClosedFloatingPointRange<Float>,
  onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  minValue: Float = 0f,
  maxValue: Float = 1f,
  valueRange: ClosedFloatingPointRange<Float> = minValue..maxValue,
  steps: Int,
  onValueChangeFinished: (() -> Unit)? = null,
) {
  RangeSlider(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier,
    enabled = enabled,
    valueRange = valueRange,
    steps = steps,
    onValueChangeFinished = onValueChangeFinished,
    colors = sliderColors(),
  )
}

@Composable
private fun sliderColors() = SliderDefaults.colors(
  thumbColor = BpkTheme.colors.coreAccent,
  activeTrackColor = BpkTheme.colors.coreAccent,
  inactiveTrackColor = BpkTheme.colors.line,
  activeTickColor = BpkTheme.colors.coreAccent,
  inactiveTickColor = BpkTheme.colors.line,
)
