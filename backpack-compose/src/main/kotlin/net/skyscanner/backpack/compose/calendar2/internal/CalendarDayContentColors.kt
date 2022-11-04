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

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import net.skyscanner.backpack.calendar2.CellStatus
import net.skyscanner.backpack.calendar2.CellStatusStyle
import net.skyscanner.backpack.calendar2.data.CalendarCell
import net.skyscanner.backpack.compose.theme.BpkTheme

internal object CalendarDayContentColors {

  @Composable
  fun labelColor(model: CalendarCell.Day): Color {
    val info = model.info
    val status = model.info.status

    return when {
      info.style == CellStatusStyle.Label && status != null ->
        when (status) {
          CellStatus.Positive -> BpkTheme.colors.statusSuccessSpot
          CellStatus.Neutral -> BpkTheme.colors.textSecondary
          CellStatus.Negative -> BpkTheme.colors.textSecondary
          CellStatus.Empty -> BpkTheme.colors.textDisabled
        }
      else -> BpkTheme.colors.textSecondary
    }
  }

  @Composable
  fun dateColor(model: CalendarCell.Day): Color {
    val selection = model.selection
    val info = model.info
    val status = model.info.status
    return when {
      selection != null -> selectedColor(selection)
      model.inactive -> BpkTheme.colors.textDisabled
      info.style == CellStatusStyle.Background && status != null -> statusColor(status)
      else -> BpkTheme.colors.textPrimary
    }
  }

  @Composable
  private fun selectedColor(selection: CalendarCell.Selection): Color =
    when (selection) {
      CalendarCell.Selection.Single -> BpkTheme.colors.textPrimaryInverse
      CalendarCell.Selection.Double -> BpkTheme.colors.textPrimaryInverse
      CalendarCell.Selection.Start -> BpkTheme.colors.textPrimaryInverse
      CalendarCell.Selection.StartMonth -> BpkTheme.colors.textPrimary
      CalendarCell.Selection.Middle -> BpkTheme.colors.textPrimary
      CalendarCell.Selection.End -> BpkTheme.colors.textPrimaryInverse
      CalendarCell.Selection.EndMonth -> BpkTheme.colors.textPrimary
    }

  @Composable
  private fun statusColor(status: CellStatus): Color =
    when (status) {
      CellStatus.Positive -> BpkTheme.colors.textPrimaryInverse
      CellStatus.Neutral -> BpkTheme.colors.textOnLight
      CellStatus.Negative -> BpkTheme.colors.textPrimaryInverse
      CellStatus.Empty -> BpkTheme.colors.textPrimary
    }

}
