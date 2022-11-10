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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.slider.BpkRangeSlider
import net.skyscanner.backpack.compose.slider.BpkSlider
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
fun BpkSliderStory() {
  Column(
    modifier = Modifier.padding(BpkSpacing.Xxl),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base, Alignment.CenterVertically)
  ) {
    DefaultSliderSample()
    SteppedSliderSample()
    RangeSliderSample()
  }
}

@Composable
fun RangeSliderSample() {
  Text(text = stringResource(R.string.generic_range), fontSize = BpkTheme.typography.label2.fontSize)
  var rangeSliderValue by remember { mutableStateOf(0.2f..0.8f) }
  BpkRangeSlider(
    value = rangeSliderValue,
    onValueChange = { newValue -> rangeSliderValue = newValue },
    steps = 0,
  )
}

@Composable
fun DefaultSliderSample() {
  Text(text = stringResource(R.string.generic_standard), fontSize = BpkTheme.typography.label2.fontSize)
  var sliderValue by remember { mutableStateOf(0.5f) }
  BpkSlider(
    value = sliderValue,
    onValueChange = { newValue -> sliderValue = newValue },
    steps = 0,
  )
}

@Composable
fun SteppedSliderSample() {
  Text(text = stringResource(R.string.generic_stepped), fontSize = BpkTheme.typography.label2.fontSize)
  var sliderValue by remember { mutableStateOf(0.5f) }
  BpkSlider(
    value = sliderValue,
    onValueChange = { newValue -> sliderValue = newValue },
    steps = 10,
  )
}
