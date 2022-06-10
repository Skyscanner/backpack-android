/**
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

package net.skyscanner.backpack.compose.flare.internal

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import net.skyscanner.backpack.compose.flare.BpkFlareRadius
import net.skyscanner.backpack.compose.tokens.BpkDimension

internal class RoundedRectShape(
  private val radius: BpkFlareRadius,
) : Shape {

  override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
    return when (radius) {
      BpkFlareRadius.None -> Outline.Rectangle(size.toRect())
      BpkFlareRadius.Medium -> Outline.Rounded(RoundRect(
        rect = size.toRect(),
        cornerRadius = CornerRadius(with(density) { BpkDimension.BorderRadius.Md.toPx() }),
      ))
    }
  }

}
