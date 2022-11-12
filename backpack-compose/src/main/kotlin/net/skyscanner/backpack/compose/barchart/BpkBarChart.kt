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

package net.skyscanner.backpack.compose.barchart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.barchart.BpkBarChartModel
import net.skyscanner.backpack.compose.barchart.internal.BarChartLegend
import net.skyscanner.backpack.compose.barchart.internal.BarChartList
import net.skyscanner.backpack.compose.barchart.internal.BarChartTitle
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
fun BpkBarChart(
  model: BpkBarChartModel,
  selected: BpkBarChartModel.Item?,
  onSelectionChange: (BpkBarChartModel.Item) -> Unit,
  modifier: Modifier = Modifier,
  state: LazyListState = rememberLazyListState(),
) {
  Column(modifier = modifier) {

    model.legend?.let { legend ->
      BarChartLegend(
        legend = legend,
        modifier = Modifier
          .align(Alignment.End)
          .padding(horizontal = BpkSpacing.Base),
      )
    }

    BarChartTitle(
      model = model,
      state = state,
      modifier = Modifier.padding(horizontal = BpkSpacing.Base),
    )

    BarChartList(
      modifier = Modifier.padding(top = BpkSpacing.Lg, bottom = BpkSpacing.Xl),
      model = model,
      selected = selected,
      onSelected = onSelectionChange,
      state = state,
    )
  }
}
