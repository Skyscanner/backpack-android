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
 */

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import net.skyscanner.backpack.compose.barchart.BpkBarChart
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.components.BarChartComponent
import net.skyscanner.backpack.demo.data.BpkBarChartData
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.demo.ui.LocalAutomationMode

// todo
@Composable
@BarChartComponent
@ComposeStory
fun BarChartStory(modifier: Modifier = Modifier) {

  val automationMode = LocalAutomationMode.current
  val context = LocalContext.current
  val model = remember(context) { BpkBarChartData.generateModel(context) }
  var selectedItem by remember { mutableStateOf(model.items[10].takeIf { automationMode }) }

  Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {

    BpkBarChart(
      model = model,
      selected = selectedItem,
      onSelectionChange = { selectedItem = it },
      modifier = Modifier.padding(vertical = BpkSpacing.Md),
    )
  }
}
