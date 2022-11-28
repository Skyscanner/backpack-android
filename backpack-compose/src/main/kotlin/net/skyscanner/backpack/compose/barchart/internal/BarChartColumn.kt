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

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextOverflow
import net.skyscanner.backpack.barchart.BpkBarChartModel
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.applyIf
import net.skyscanner.backpack.compose.utils.inset
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
internal fun BarChartColumn(
  model: BpkBarChartModel.Item,
  selected: Boolean,
  onSelected: (BpkBarChartModel.Item) -> Unit,
  onSelectedAndPositioned: (LayoutCoordinates) -> Unit,
  modifier: Modifier = Modifier,
) {

  val value by animateFloatAsState(model.value)
  val primaryColor by animateColorAsState(if (selected) BpkTheme.colors.coreAccent else BpkTheme.colors.corePrimary)
  val secondaryColor by animateColorAsState(if (selected) BpkTheme.colors.coreAccent else BpkTheme.colors.textSecondary)

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .selectable(
        selected = selected,
        enabled = true,
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = { onSelected(model) },
      ),
  ) {

    Spacer(
      modifier = Modifier
        .padding(bottom = BpkSpacing.Md)
        .width(BpkSpacing.Base)
        .weight(1f, fill = false)
        .background(BpkTheme.colors.surfaceHighlight, CircleShape)
        .inset { bounds -> bounds.copy(top = bounds.height - max((bounds.height * value).roundToInt(), bounds.width)) }
        .background(primaryColor, CircleShape)
        .applyIf(selected) { onGloballyPositioned(onSelectedAndPositioned) },
    )

    BpkText(
      text = model.title,
      style = BpkTheme.typography.label2,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      color = primaryColor,
    )

    BpkText(
      text = model.subtitle,
      style = BpkTheme.typography.footnote,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      color = secondaryColor,
    )
  }
}
