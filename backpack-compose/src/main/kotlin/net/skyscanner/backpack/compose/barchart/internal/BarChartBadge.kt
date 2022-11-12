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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.barchart.BpkBarChartModel
import net.skyscanner.backpack.compose.flare.BpkFlarePointerDirection
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.FlareShape

@Composable
internal fun BarChartBadge(
  selected: BpkBarChartModel.Item?,
  direction: BpkFlarePointerDirection,
  modifier: Modifier = Modifier,
) {
  var lastText by remember { mutableStateOf(selected?.badge) }
  if (selected != null) {
    lastText = selected.badge
  }
  AnimatedVisibility(
    modifier = modifier,
    visible = selected != null,
  ) {
    BpkText(
      text = lastText ?: "",
      color = BpkTheme.colors.textPrimaryInverse,
      style = BpkTheme.typography.label2,
      maxLines = 1,
      modifier = Modifier
        .background(
          color = BpkTheme.colors.coreAccent,
          shape = FlareShape(borderRadius = BpkBorderRadius.Xs, flareHeight = BpkSpacing.Sm, pointerDirection = direction),
        )
        .padding(bottom = BpkSpacing.Sm)
        .padding(BpkSpacing.Md),
    )
  }
}
