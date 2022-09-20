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

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.FloatingActionButtonElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.button.FloatingActionButton
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Aircraft
import net.skyscanner.backpack.compose.tokens.Airline
import net.skyscanner.backpack.compose.tokens.ArrowLeft
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Flight
import net.skyscanner.backpack.compose.tokens.Search
import net.skyscanner.backpack.compose.tokens.Star


@Composable
fun BpkFabStory(
) {
  Column(
    modifier = Modifier.padding(BpkSpacing.Xl),
    verticalArrangement = Arrangement.Center
  ) {
    FloatingActionButton(onClick = { /*TODO*/ }, icon = BpkIcon.Search, contentDescription = "Localised description")
    Spacer(modifier = Modifier.height(BpkSpacing.Xxl))
    FloatingActionButton(onClick = { /*TODO*/ }, icon = BpkIcon.Star, contentDescription = "Localised description")
    Spacer(modifier = Modifier.height(BpkSpacing.Xxl))
    FloatingActionButton(onClick = { /*TODO*/ }, icon = BpkIcon.Flight, contentDescription = "Localised description")

//  {
//
//  }
//  BpkIcon(icon = icon, contentDescription = contentDescription)

  }


}
