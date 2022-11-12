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

package net.skyscanner.backpack.compose.barchart.internal

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.barchart.BpkBarChartModel
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BarChartList(
  model: BpkBarChartModel,
  selected: BpkBarChartModel.Item?,
  onSelected: (BpkBarChartModel.Item) -> Unit,
  state: LazyListState,
  modifier: Modifier = Modifier,
) {

  var selectedTopOffset by remember { mutableStateOf(0) }

  LazyRow(
    state = state,
    modifier = modifier.drawSelectionLine(selected, selectedTopOffset),
    contentPadding = PaddingValues(horizontal = BpkSpacing.Base),
  ) {
    items(
      items = model.items,
      key = { it.key },
    ) { item ->
      BarChartColumn(
        modifier = Modifier.requiredSize(ItemWidth, ItemHeight),
        model = item,
        selected = item == selected,
        onSelected = { it, topOffset ->
          selectedTopOffset = topOffset
          onSelected(it)
        },
      )
    }
  }
}


private fun Modifier.drawSelectionLine(
  selected: BpkBarChartModel.Item?,
  topOffset: Int,
  strokeWidth: Dp = 1.dp,
): Modifier =
  composed {

    val y = if (topOffset != 0) animateFloatAsState(topOffset.toFloat()).value else 0f
    val alpha by animateFloatAsState(if (selected != null) 1f else 0f)
    val color = BpkTheme.colors.coreAccent

    drawWithContent {
      drawContent()
      drawLine(color.copy(alpha = alpha), start = Offset(0f, y), end = Offset(size.width, y), strokeWidth.toPx())
    }
  }

private val ItemWidth = 32.dp
private val ItemHeight = 168.dp
