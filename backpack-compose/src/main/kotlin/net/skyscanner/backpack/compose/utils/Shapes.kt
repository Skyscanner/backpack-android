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

package net.skyscanner.backpack.compose.utils

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.util.lerp

/*
 * Creates a generic rectangle shape using relative range to determine the position of each side.
 * For instance, RelativeRectangleShape(horizontal = 0f..0.5f) creates a rect
 * which takes only left-half of its original size.
 */
internal fun RelativeRectangleShape(
  horizontal: ClosedFloatingPointRange<Float> = 0f..1f,
  vertical: ClosedFloatingPointRange<Float> = 0f..1f,
  autoMirror: Boolean = true,
): Shape =
  GenericShape { size, layoutDirection ->

    val top = lerp(0f, size.height, vertical.start)
    val bottom = lerp(0f, size.height, vertical.endInclusive)

    val mirror = layoutDirection == LayoutDirection.Rtl && autoMirror
    val left = lerp(0f, size.width, if (mirror) 1f - horizontal.start else horizontal.start)
    val right = lerp(0f, size.width, if (mirror) 1f - horizontal.endInclusive else horizontal.endInclusive)

    addRect(Rect(left, top, right, bottom))
  }
