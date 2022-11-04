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

package net.skyscanner.backpack.compose.calendar2.internal

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.calendar2.data.CalendarCell
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BpkCalendarDayCell(
  model: CalendarCell.Day,
  modifier: Modifier = Modifier,
  onClick: (CalendarCell.Day) -> Unit,
) {
  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .padding(bottom = BpkSpacing.Lg)
      .selectable(
        indication = null,
        selected = model.selection != null,
        enabled = !model.inactive,
        onClick = { onClick(model) },
        interactionSource = remember { MutableInteractionSource() },
      ),
  ) {

    BpkText(
      text = model.text.toString(),
      overflow = TextOverflow.Ellipsis,
      textAlign = TextAlign.Center,
      maxLines = 1,
      style = BpkTheme.typography.heading5,
      color = CalendarDayContentColors.dateColor(model),
      modifier = with(CalendarDayBackground) {
        Modifier
          .height(36.dp)
          .dateBackground(model)
      },
    )

    val label = model.info.label
    if (!model.inactive && !label.isNullOrEmpty()) {
      BpkText(
        text = label,
        modifier = Modifier.padding(horizontal = BpkSpacing.Sm),
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
        maxLines = 2,
        style = BpkTheme.typography.caption,
        color = CalendarDayContentColors.labelColor(model)
      )
    }
  }
}
