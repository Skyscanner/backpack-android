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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import net.skyscanner.backpack.compose.flare.BpkFlarePointerDirection
import net.skyscanner.backpack.compose.flare.BpkFlareRadius
import net.skyscanner.backpack.compose.tokens.BpkDimension

internal class FlareShape(
  private val radius: BpkFlareRadius,
  private val pointerDirection: BpkFlarePointerDirection,
) : Shape {

  override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
    return Outline.Generic(
      path = drawFlarePath(size, density.density, radius, pointerDirection)
    )
  }

  private fun drawFlarePath(
    size: Size,
    density: Float,
    radius: BpkFlareRadius,
    pointerDirection: BpkFlarePointerDirection,
  ): Path {
    return Path().apply {
      val flareHeight = FlareHeight * density
      var scale = flareHeight / FlareVectorHeight
      if (pointerDirection == BpkFlarePointerDirection.Up) {
        scale *= -1
      }
      addRect(flareHeight, size, density, radius, pointerDirection)
      addFlare(flareHeight, scale, size, pointerDirection)
    }
  }
}

private fun Path.addRect(
  flareHeight: Float,
  size: Size,
  density: Float,
  radius: BpkFlareRadius,
  pointerDirection: BpkFlarePointerDirection,
) {
  val borderRect = when (pointerDirection) {
    BpkFlarePointerDirection.Up -> Rect(0f, flareHeight, size.width, size.height)
    BpkFlarePointerDirection.Down -> Rect(0f, 0f, size.width, size.height - flareHeight)
  }
  when (radius) {
    BpkFlareRadius.None -> addRect(borderRect)
    BpkFlareRadius.Medium -> {
      val cornerRadius = BpkDimension.BorderRadius.Md.value * density
      addRoundRect(RoundRect(borderRect, CornerRadius(cornerRadius, cornerRadius)))
    }
  }
}

private fun Path.addFlare(
  flareHeight: Float,
  scale: Float,
  size: Size,
  pointerDirection: BpkFlarePointerDirection,
) {
  val flareWidth = scale * FlareVectorWidth
  val flareX = (size.width - flareWidth) / 2
  val flareY = when (pointerDirection) {
    BpkFlarePointerDirection.Up -> flareHeight
    BpkFlarePointerDirection.Down -> size.height - flareHeight
  }
  moveTo(flareX + 4.329f * scale, flareY)
  cubicTo(
    flareX + 14.663905f * scale, flareY + 0.409248008f * scale,
    flareX + 24.743092f * scale, flareY + 3.33270635f * scale,
    flareX + 33.693f * scale, flareY + 8.517f * scale,
  )
  lineTo(flareX + 101.736f * scale, flareY + 47.858f * scale)
  cubicTo(
    flareX + 112.368f * scale, flareY + 54.043f * scale,
    flareX + 125.531f * scale, flareY + 54.043f * scale,
    flareX + 136.264f * scale, flareY + 47.858f * scale,
  )
  lineTo(flareX + 204.408f * scale, flareY + 8.518f * scale)
  cubicTo(
    flareX + 213.318f * scale, flareY + 3.345f * scale,
    flareX + 223.396f * scale, flareY + 0.303f * scale,
    flareX + 233.724f * scale, flareY + 0f * scale,
  )
  lineTo(flareX + 238f * scale, flareY + 0f * scale)
}

private const val FlareVectorHeight = 53
private const val FlareVectorWidth = 234
internal const val FlareHeight = 11f
