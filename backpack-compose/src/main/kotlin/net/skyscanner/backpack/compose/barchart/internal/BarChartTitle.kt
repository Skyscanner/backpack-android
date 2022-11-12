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

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.barchart.BpkBarChartModel
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme

@Composable
internal fun BarChartTitle(
  model: BpkBarChartModel,
  state: LazyListState,
  modifier: Modifier = Modifier,
) {

  val title by remember { derivedStateOf { model.items[state.firstVisibleItemIndex].group } }

  Crossfade(
    targetState = title,
    modifier = modifier,
  ) {
    BpkText(
      text = it,
      maxLines = 1,
      style = BpkTheme.typography.heading4,
      color = BpkTheme.colors.textPrimary,
    )
  }
}
