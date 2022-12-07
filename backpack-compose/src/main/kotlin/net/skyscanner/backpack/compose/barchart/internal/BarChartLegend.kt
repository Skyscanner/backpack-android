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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import net.skyscanner.backpack.compose.badge.BpkBadge
import net.skyscanner.backpack.compose.badge.BpkBadgeType
import net.skyscanner.backpack.compose.barchart.BpkBarChartModel
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun BarChartLegend(
  legend: BpkBarChartModel.Legend,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier.semantics { invisibleToUser() },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
  ) {
    BpkBadge(text = legend.selectedTitle, type = BpkBadgeType.Brand)
    BpkBadge(text = legend.inactiveTitle, type = BpkBadgeType.Normal)
    BpkBadge(text = legend.activeTitle, type = BpkBadgeType.Strong)
  }
}
