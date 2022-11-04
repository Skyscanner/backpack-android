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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.calendar2.CalendarParams
import net.skyscanner.backpack.calendar2.data.CalendarCell
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.applyIf

@Composable
internal fun BpkCalendarHeaderCell(
  model: CalendarCell.Header,
  modifier: Modifier = Modifier,
  onSelectWholeMonth: (CalendarCell.Header) -> Unit,
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
  ) {

    BpkText(
      text = model.title,
      maxLines = 1,
      color = BpkTheme.colors.textPrimary,
      style = BpkTheme.typography.heading4,
      modifier = Modifier
        .weight(1f)
        .semantics { heading() }
        .padding(vertical = BpkSpacing.Lg),
    )

    val monthSelectionMode = model.monthSelectionMode
    if (monthSelectionMode is CalendarParams.MonthSelectionMode.SelectWholeMonth) {
      BpkButton(
        text = monthSelectionMode.label,
        enabled = model.calendarSelectionMode !is CalendarParams.SelectionMode.Disabled,
        type = BpkButtonType.Link,
        onClick = { onSelectWholeMonth(model) },
      )
    }
  }
}

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
        selected = model.selection != null,
        enabled = !model.inactive,
        onClick = { onClick(model) }
      ),
  ) {

    BpkText(
      text = model.text.toString(),
      overflow = TextOverflow.Ellipsis,
      textAlign = TextAlign.Center,
      maxLines = 1,
      style = BpkTheme.typography.heading5,
      color = CalendarDayContentColors.dateColor(model),
      modifier = with(CalendarBackgroundDay) {
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

@Composable
internal fun BpkCalendarSpaceCell(
  model: CalendarCell.Space,
  modifier: Modifier = Modifier,
) {
  Spacer(
    modifier = modifier.applyIf(model.selected) {
      background(
        brush = CalendarBackgroundDay.selectionTopBrush(CalendarCell.Selection.Middle),
        shape = CalendarBackgroundDay.selectionTopShape(CalendarCell.Selection.Middle),
      )
    }
  )
}
