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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing


@Composable
fun BpkSlider(
  modifier: Modifier
) {
  var sliderValue by remember {
    mutableStateOf(0f) }

  Box(
//    shape = RoundedCornerShape(4.dp),
    modifier = Modifier.padding(BpkSpacing.Xl),
//    elevation = 0.dp
  ) {
    Slider(
      value = sliderValue,
      onValueChange = { newValue -> sliderValue = newValue },
      modifier = Modifier.padding(vertical = 0.dp),
      colors = SliderDefaults.colors(
      thumbColor = BpkTheme.colors.coreAccent,
      activeTrackColor = BpkTheme.colors.coreAccent,
      inactiveTrackColor = BpkTheme.colors.line))

  }


}
