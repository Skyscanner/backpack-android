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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import net.skyscanner.backpack.calendar2.CellStatus
import net.skyscanner.backpack.calendar2.data.CalendarCell
import net.skyscanner.backpack.compose.theme.BpkTheme

internal object CalendarDayBackgroundBrushes {

  @Composable
  fun status(status: CellStatus?): Brush =
    when (status) {
      CellStatus.Positive -> BpkTheme.colors.statusSuccessSpot
      CellStatus.Neutral -> BpkTheme.colors.statusWarningSpot
      CellStatus.Negative -> BpkTheme.colors.statusDangerSpot
      CellStatus.Empty -> BpkTheme.colors.surfaceHighlight
      null -> Color.Transparent
    }.let(::SolidColor)

  @Composable
  fun selectionTop(selection: CalendarCell.Selection): Brush =
    when (selection) {
      CalendarCell.Selection.Single -> BpkTheme.colors.coreAccent
      CalendarCell.Selection.Double -> BpkTheme.colors.coreAccent
      CalendarCell.Selection.Start -> BpkTheme.colors.coreAccent
      CalendarCell.Selection.StartMonth -> BpkTheme.colors.surfaceHighlight
      CalendarCell.Selection.Middle -> BpkTheme.colors.surfaceHighlight
      CalendarCell.Selection.End -> BpkTheme.colors.coreAccent
      CalendarCell.Selection.EndMonth -> BpkTheme.colors.surfaceHighlight
    }.let(::SolidColor)

  @Composable
  fun selectionBottom(selection: CalendarCell.Selection): Brush? =
    when (selection) {
      CalendarCell.Selection.Single -> null
      CalendarCell.Selection.Double -> BpkTheme.colors.coreAccent
      CalendarCell.Selection.Start -> BpkTheme.colors.surfaceHighlight
      CalendarCell.Selection.StartMonth -> BpkTheme.colors.surfaceHighlight
      CalendarCell.Selection.Middle -> null
      CalendarCell.Selection.End -> BpkTheme.colors.surfaceHighlight
      CalendarCell.Selection.EndMonth -> BpkTheme.colors.surfaceHighlight
    }?.let(::SolidColor)

}
