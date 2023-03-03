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

package net.skyscanner.backpack.compose.overlay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import net.skyscanner.backpack.compose.theme.BpkTheme

enum class BpkOverlayType {
  SolidLow,
  SolidMedium,
  SolidHigh,
  TopLow,
  TopMedium,
  TopHigh,
  BottomLow,
  BottomMedium,
  BottomHigh,
  LeftLow,
  LeftMedium,
  LeftHigh,
  RightLow,
  RightMedium,
  RightHigh,
  Vignette,
}

enum class GradientLevel {
  Low,
  Medium,
  High,
}

enum class GradientDirection {
  Solid,
  Top,
  Bottom,
  Left,
  Right,
  Vignette,
}

@Composable
fun BpkOverlayType.toGradientlevel(): GradientLevel =
  when (this) {
    BpkOverlayType.SolidLow,
    BpkOverlayType.TopLow,
    BpkOverlayType.BottomLow,
    BpkOverlayType.LeftLow,
    BpkOverlayType.RightLow,
    -> GradientLevel.Low
    BpkOverlayType.SolidMedium,
    BpkOverlayType.TopMedium,
    BpkOverlayType.BottomMedium,
    BpkOverlayType.LeftMedium,
    BpkOverlayType.RightMedium,
    -> GradientLevel.Medium
    BpkOverlayType.SolidHigh,
    BpkOverlayType.TopHigh,
    BpkOverlayType.BottomHigh,
    BpkOverlayType.LeftHigh,
    BpkOverlayType.RightHigh,
    -> GradientLevel.High
    BpkOverlayType.Vignette -> GradientLevel.High
  }

@Composable
fun BpkOverlayType.toDirection(): GradientDirection =
  when (this) {
    BpkOverlayType.SolidLow,
    BpkOverlayType.SolidMedium,
    BpkOverlayType.SolidHigh,
    -> GradientDirection.Solid
    BpkOverlayType.TopLow,
    BpkOverlayType.TopMedium,
    BpkOverlayType.TopHigh,
    -> GradientDirection.Top
    BpkOverlayType.BottomLow,
    BpkOverlayType.BottomMedium,
    BpkOverlayType.BottomHigh,
    -> GradientDirection.Bottom
    BpkOverlayType.LeftLow,
    BpkOverlayType.LeftMedium,
    BpkOverlayType.LeftHigh,
    -> if (LocalLayoutDirection.current != LayoutDirection.Rtl) GradientDirection.Left else GradientDirection.Right
    BpkOverlayType.RightLow,
    BpkOverlayType.RightMedium,
    BpkOverlayType.RightHigh,
    -> if (LocalLayoutDirection.current != LayoutDirection.Rtl) GradientDirection.Right else GradientDirection.Left
    BpkOverlayType.Vignette -> GradientDirection.Vignette
  }

@Composable
fun BpkOverlay(
  modifier: Modifier = Modifier,
  overlayType: BpkOverlayType = BpkOverlayType.SolidHigh,
  foregroundContent: (@Composable BoxScope.() -> Unit)? = null,
  content: @Composable BoxScope.() -> Unit,
) {
  Box(
    modifier = modifier,
  ) {
    content()
    Overlay(overlayType = overlayType)
    if (foregroundContent != null) {
      foregroundContent()
    }
  }
}

@Composable
fun BoxScope.Overlay(overlayType: BpkOverlayType, modifier: Modifier = Modifier) {
  val direction = overlayType.toDirection()
  val level = overlayType.toGradientlevel()
  Box(
    modifier = modifier
      .matchParentSize()
      .background(direction.toBrush(gradientLevel = level)),
  )
}

@Composable
private fun GradientLevel.toColor(): Color =
  when (this) {
    GradientLevel.Low -> BpkTheme.colors.textOnLight.copy(alpha = 0.15f)
    GradientLevel.Medium -> BpkTheme.colors.textOnLight.copy(alpha = 0.3f)
    GradientLevel.High -> BpkTheme.colors.textOnLight.copy(alpha = 0.45f)
  }

@Composable
private fun GradientDirection.toBrush(gradientLevel: GradientLevel): Brush =
  when (this) {
    GradientDirection.Solid -> Brush.verticalGradient(listOf(gradientLevel.toColor(), gradientLevel.toColor()))
    GradientDirection.Top -> Brush.verticalGradient(listOf(gradientLevel.toColor(), Color.Transparent))
    GradientDirection.Bottom -> Brush.verticalGradient(listOf(Color.Transparent, gradientLevel.toColor()))
    GradientDirection.Left -> Brush.horizontalGradient(listOf(gradientLevel.toColor(), Color.Transparent))
    GradientDirection.Right -> Brush.horizontalGradient(listOf(Color.Transparent, gradientLevel.toColor()))
    GradientDirection.Vignette -> Brush.radialGradient(listOf(Color.Transparent, gradientLevel.toColor()))
  }
