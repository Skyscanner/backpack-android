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

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.calendar2.data.CalendarCell

internal object CalendarBackgroundDayShapes {

  fun status(): Shape =
    CircleShape

  fun selectionTop(selection: CalendarCell.Selection): Shape =
    when (selection) {
      CalendarCell.Selection.Single -> CircleShape
      CalendarCell.Selection.Double -> PaddedCircleShape(3.dp)
      CalendarCell.Selection.Start -> CircleShape
      CalendarCell.Selection.StartMonth -> CircleShape
      CalendarCell.Selection.Middle -> RectangleShape
      CalendarCell.Selection.End -> CircleShape
      CalendarCell.Selection.EndMonth -> CircleShape
    }

  fun selectionBottom(selection: CalendarCell.Selection): Shape? =
    when (selection) {
      CalendarCell.Selection.Single -> null
      CalendarCell.Selection.Double -> CircleShape
      CalendarCell.Selection.Start -> EndSemiRect
      CalendarCell.Selection.StartMonth -> EndSemiRect
      CalendarCell.Selection.Middle -> RectangleShape
      CalendarCell.Selection.End -> StartSemiRect
      CalendarCell.Selection.EndMonth -> StartSemiRect
    }

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

  private fun PaddedCircleShape(padding: Dp) : Shape =
    object : Shape {
      override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val deflateBy = with(density) { padding.toPx() }
        return Outline.Generic(Path().apply {
          addOval(size.toRect().deflate(deflateBy))
        })
      }
    }

}
