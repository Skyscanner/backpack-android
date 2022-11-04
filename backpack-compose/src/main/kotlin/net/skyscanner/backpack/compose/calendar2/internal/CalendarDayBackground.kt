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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.calendar2.CellStatus
import net.skyscanner.backpack.calendar2.CellStatusStyle
import net.skyscanner.backpack.calendar2.data.CalendarCell
import net.skyscanner.backpack.compose.theme.BpkTheme

internal object CalendarDayBackground {

  fun Modifier.dateBackground(
    model: CalendarCell.Day,
  ): Modifier = composed {
    val selection = model.selection
    val info = model.info
    val status = model.info.status

    when {
      selection != null ->
        this
          .background(selectionBottomBrush(selection), selectionBottomShape(selection))
          .background(selectionTopBrush(selection), selectionTopShape(selection))

      model.inactive -> this
      info.style == CellStatusStyle.Background -> background(statusColor(status), CircleShape)
      else -> this
    }
  }

  internal fun selectionTopShape(selection: CalendarCell.Selection): Shape =
    when (selection) {
      CalendarCell.Selection.Single -> CircleShape
      CalendarCell.Selection.Double -> PaddedCircleShape(3.dp)
      CalendarCell.Selection.Start -> CircleShape
      CalendarCell.Selection.StartMonth -> CircleShape
      CalendarCell.Selection.Middle -> RectangleShape
      CalendarCell.Selection.End -> CircleShape
      CalendarCell.Selection.EndMonth -> CircleShape
    }

  private fun selectionBottomShape(selection: CalendarCell.Selection): Shape =
    when (selection) {
      CalendarCell.Selection.Single -> RectangleShape
      CalendarCell.Selection.Double -> CircleShape
      CalendarCell.Selection.Start -> EndSemiRect
      CalendarCell.Selection.StartMonth -> EndSemiRect
      CalendarCell.Selection.Middle -> RectangleShape
      CalendarCell.Selection.End -> StartSemiRect
      CalendarCell.Selection.EndMonth -> StartSemiRect
    }

  @Composable
  private fun statusColor(status: CellStatus?): Color =
    when (status) {
      CellStatus.Positive -> BpkTheme.colors.statusSuccessSpot
      CellStatus.Neutral -> BpkTheme.colors.statusWarningSpot
      CellStatus.Negative -> BpkTheme.colors.statusDangerSpot
      CellStatus.Empty -> BpkTheme.colors.surfaceHighlight
      null -> Color.Transparent
    }

  @Composable
  internal fun selectionTopBrush(selection: CalendarCell.Selection): Brush =
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
  private fun selectionBottomBrush(selection: CalendarCell.Selection): Brush =
    when (selection) {
      CalendarCell.Selection.Single -> Color.Unspecified
      CalendarCell.Selection.Double -> BpkTheme.colors.coreAccent
      CalendarCell.Selection.Start -> BpkTheme.colors.surfaceHighlight
      CalendarCell.Selection.StartMonth -> BpkTheme.colors.surfaceHighlight
      CalendarCell.Selection.Middle -> Color.Unspecified
      CalendarCell.Selection.End -> BpkTheme.colors.surfaceHighlight
      CalendarCell.Selection.EndMonth -> BpkTheme.colors.surfaceHighlight
    }.let(::SolidColor)

  private val StartSemiRect = GenericShape { size, layoutDirection ->
    addRect(
      when (layoutDirection) {
        LayoutDirection.Ltr -> Rect(
          topLeft = Offset.Zero,
          bottomRight = Offset(size.width / 2, size.height),
        )

        LayoutDirection.Rtl -> Rect(
          topLeft = Offset(size.width / 2, 0f),
          bottomRight = Offset(size.width, size.height),
        )
      }
    )
  }

  private val EndSemiRect = GenericShape { size, layoutDirection ->
    addRect(
      when (layoutDirection) {
        LayoutDirection.Ltr -> Rect(
          topLeft = Offset(size.width / 2, 0f),
          bottomRight = Offset(size.width, size.height),
        )

        LayoutDirection.Rtl -> Rect(
          topLeft = Offset.Zero,
          bottomRight = Offset(size.width / 2, size.height),
        )
      }
    )
  }

  private fun PaddedCircleShape(padding: Dp): Shape =
    object : Shape {
      override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val deflateBy = with(density) { padding.toPx() }
        return Outline.Generic(Path().apply {
          addOval(size.toRect().deflate(deflateBy))
        })
      }
    }

}
