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

package net.skyscanner.backpack.compose.calendar.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.calendar2.CellStatus
import net.skyscanner.backpack.calendar2.CellStatusStyle
import net.skyscanner.backpack.calendar2.data.CalendarCell
import net.skyscanner.backpack.calendar2.data.CalendarCell.Selection
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.DeflatedRect

@Composable
internal fun BpkCalendarDayCell(
  model: CalendarCell.Day,
  modifier: Modifier = Modifier,
  onClick: (CalendarCell.Day) -> Unit,
) {

  val selection = model.selection
  val inactive = model.inactive

  val status = model.info.status
  val style = model.info.style
  val label = model.info.label

  Column(
    verticalArrangement = Arrangement.Top,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .padding(bottom = BpkSpacing.Lg)
      .selectable(
        indication = null,
        selected = selection != null,
        enabled = !inactive,
        onClick = { onClick(model) },
        interactionSource = remember { MutableInteractionSource() },
      ),
  ) {

    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier
        .fillMaxWidth()
        .cellSelectionBackground(selection),
    ) {

      Spacer(
        Modifier
          .size(BpkCalendarSizes.SelectionHeight)
          .cellDayBackground(selection, status, inactive, style)
      )

      BpkText(
        text = model.text.toString(),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = BpkTheme.typography.heading5,
        color = dateColor(selection, status, inactive, style),
      )
    }

    if (!inactive && !label.isNullOrEmpty()) {
      BpkText(
        text = label,
        modifier = Modifier.padding(horizontal = BpkSpacing.Sm),
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
        maxLines = 2,
        style = BpkTheme.typography.caption,
        color = labelColor(status, style)
      )
    }
  }
}

private fun Modifier.cellSelectionBackground(selection: Selection?): Modifier = composed {
  when (selection) {
    Selection.Start,
    Selection.StartMonth -> background(BpkTheme.colors.surfaceHighlight, EndSemiRect)

    Selection.End,
    Selection.EndMonth -> background(BpkTheme.colors.surfaceHighlight, StartSemiRect)

    Selection.Middle -> background(BpkTheme.colors.surfaceHighlight, RectangleShape)

    Selection.Single,
    Selection.Double,
    null -> this
  }
}

private fun Modifier.cellDayBackground(
  selection: Selection?,
  status: CellStatus?,
  inactive: Boolean,
  style: CellStatusStyle?,
): Modifier = composed {
  when {
    selection != null ->
      when (selection) {
        Selection.Double -> this
          .border(1.dp, BpkTheme.colors.coreAccent, CircleShape)
          .padding(3.dp)
          .background(BpkTheme.colors.coreAccent, CircleShape)

        Selection.StartMonth,
        Selection.Middle,
        Selection.EndMonth -> background(BpkTheme.colors.surfaceHighlight, CircleShape)

        Selection.Single,
        Selection.Start,
        Selection.End -> background(BpkTheme.colors.coreAccent, CircleShape)
      }

    style == CellStatusStyle.Background && status != null && !inactive ->
      when (status) {
        CellStatus.Positive -> background(BpkTheme.colors.statusSuccessSpot, CircleShape)
        CellStatus.Neutral -> background(BpkTheme.colors.statusWarningSpot, CircleShape)
        CellStatus.Negative -> background(BpkTheme.colors.statusDangerSpot, CircleShape)
        CellStatus.Empty -> background(BpkTheme.colors.surfaceHighlight, CircleShape)
      }

    else -> this
  }
}

@Composable
private fun dateColor(
  selection: Selection?,
  status: CellStatus?,
  inactive: Boolean,
  style: CellStatusStyle?,
): Color =
  when {
    selection != null ->
      when (selection) {
        Selection.Single,
        Selection.Double,
        Selection.Start,
        Selection.End -> BpkTheme.colors.textPrimaryInverse

        Selection.StartMonth,
        Selection.Middle,
        Selection.EndMonth -> BpkTheme.colors.textPrimary
      }

    inactive -> BpkTheme.colors.textDisabled

    style == CellStatusStyle.Background && status != null ->
      when (status) {
        CellStatus.Positive,
        CellStatus.Negative -> BpkTheme.colors.textPrimaryInverse

        CellStatus.Neutral -> BpkTheme.colors.textOnLight
        CellStatus.Empty -> BpkTheme.colors.textPrimary
      }

    else -> BpkTheme.colors.textPrimary
  }

@Composable
private fun labelColor(status: CellStatus?, style: CellStatusStyle?): Color =
  when {

    style == CellStatusStyle.Label && status != null ->
      when (status) {
        CellStatus.Positive -> BpkTheme.colors.statusSuccessSpot
        CellStatus.Neutral -> BpkTheme.colors.textSecondary
        CellStatus.Negative -> BpkTheme.colors.textSecondary
        CellStatus.Empty -> BpkTheme.colors.textDisabled
      }

    else -> BpkTheme.colors.textSecondary
  }

private val StartSemiRect = DeflatedRect(horizontal = 0f..0.5f)
private val EndSemiRect = DeflatedRect(horizontal = 0.5f..1f)
