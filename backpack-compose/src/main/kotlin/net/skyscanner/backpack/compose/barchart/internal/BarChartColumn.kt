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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import net.skyscanner.backpack.compose.barchart.BpkBarChartModel
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.applyIf
import net.skyscanner.backpack.compose.utils.inset
import kotlin.math.max
import kotlin.math.roundToInt

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun BarChartColumn(
  model: BpkBarChartModel.Item,
  selected: Boolean,
  onSelected: (BpkBarChartModel.Item) -> Unit,
  onSelectedAndPositioned: (LayoutCoordinates) -> Unit,
  modifier: Modifier = Modifier,
) {

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .semantics { contentDescription = model.accessibilityLabel }
      .selectable(
      selected = selected,
      enabled = model.values != null,
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
        .run {
          when (model.values) {
            null -> inset { bounds ->
              bounds.copy(top = bounds.height - BpkSpacing.Lg.roundToPx())
            }

            else -> composed {
              val value by animateFloatAsState(model.values.percent)
              inset { bounds ->
                bounds.copy(top = bounds.height - max((bounds.height * value).roundToInt(), bounds.width))
              }
            }
          }
        }
        .applyIf(selected) { onGloballyPositioned(onSelectedAndPositioned) }
        .background(
          shape = CircleShape,
          color = animateColorAsState(
            targetValue = when {
              model.values == null -> BpkTheme.colors.line
              selected -> BpkTheme.colors.coreAccent
              else -> BpkTheme.colors.corePrimary
            }
          ).value,
        ),
    )

    BpkText(
      text = model.title,
      style = BpkTheme.typography.label2,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      color = animateColorAsState(if (selected) BpkTheme.colors.coreAccent else BpkTheme.colors.textPrimary).value,
      modifier = Modifier.semantics { invisibleToUser() },
    )

    BpkText(
      text = model.subtitle,
      style = BpkTheme.typography.footnote,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      color = animateColorAsState(if (selected) BpkTheme.colors.coreAccent else BpkTheme.colors.textSecondary).value,
      modifier = Modifier.semantics { invisibleToUser() },
    )
  }
}
