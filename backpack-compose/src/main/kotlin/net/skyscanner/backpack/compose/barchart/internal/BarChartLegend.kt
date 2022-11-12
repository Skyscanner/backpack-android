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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import net.skyscanner.backpack.barchart.BpkBarChartModel
import net.skyscanner.backpack.compose.badge.BpkBadge
import net.skyscanner.backpack.compose.badge.BpkBadgeType
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BarChartLegend(
  legend: BpkBarChartModel.Legend,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
  ) {
    BpkBadge(
      text = legend.selectedTitle,
      backgroundColor = BpkTheme.colors.coreAccent,
      contentColor = BpkTheme.colors.textPrimaryInverse,
      borderColor = Color.Transparent,
    )
    BpkBadge(text = legend.inactiveTitle, type = BpkBadgeType.Normal)
    BpkBadge(text = legend.activeTitle, type = BpkBadgeType.Strong)
  }
}
