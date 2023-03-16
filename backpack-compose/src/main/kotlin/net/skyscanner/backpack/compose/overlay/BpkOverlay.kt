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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.hypot
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

private enum class GradientLevel {
    Low,
    Medium,
    High,
    Vignette,
}

private enum class GradientDirection {
    Solid,
    Top,
    Bottom,
    Left,
    Right,
    Vignette,
}

private fun BpkOverlayType.toGradientlevel(): GradientLevel =
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
        BpkOverlayType.Vignette -> GradientLevel.Vignette
    }

private fun BpkOverlayType.toDirection(layoutDirection: LayoutDirection): GradientDirection =
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
        -> if (layoutDirection == LayoutDirection.Ltr) GradientDirection.Left else GradientDirection.Right
        BpkOverlayType.RightLow,
        BpkOverlayType.RightMedium,
        BpkOverlayType.RightHigh,
        -> if (layoutDirection == LayoutDirection.Ltr) GradientDirection.Right else GradientDirection.Left
        BpkOverlayType.Vignette -> GradientDirection.Vignette
    }

@Composable
private fun BoxScope.Overlay(
    overlayType: BpkOverlayType,
    modifier: Modifier = Modifier,
) {
    val gradientColor = BpkTheme.colors.textOnLight
    Box(
        modifier = modifier
            .matchParentSize()
            .drawWithCache {
                val direction = overlayType.toDirection(layoutDirection)
                val level = overlayType.toGradientlevel()
                val brush = direction.toBrush(gradientLevel = level, color = gradientColor)
                onDrawBehind {
                    if (overlayType == BpkOverlayType.Vignette) {
                        if (size.isUnspecified || size.isEmpty()) {
                            return@onDrawBehind
                        }
                        val diagonal = hypot(x = size.width, y = size.height)
                        val scaleFactor = diagonal / size.minDimension
                        clipRect {
                            scale(scaleFactor) {
                                drawRect(brush)
                            }
                        }
                    } else {
                        drawRect(brush)
                    }
                }
            },
    )
}

private fun GradientLevel.toColor(color: Color): Color =
    when (this) {
        GradientLevel.Low -> color.copy(alpha = 0.15f)
        GradientLevel.Medium -> color.copy(alpha = 0.3f)
        GradientLevel.High -> color.copy(alpha = 0.45f)
        GradientLevel.Vignette -> color.copy(alpha = 0.06f)
    }

private fun GradientDirection.toBrush(gradientLevel: GradientLevel, color: Color): Brush =
    when (this) {
        GradientDirection.Solid -> Brush.verticalGradient(listOf(gradientLevel.toColor(color), gradientLevel.toColor(color)))
        GradientDirection.Top -> Brush.verticalGradient(listOf(gradientLevel.toColor(color), Color.Transparent))
        GradientDirection.Bottom -> Brush.verticalGradient(listOf(Color.Transparent, gradientLevel.toColor(color)))
        GradientDirection.Left -> Brush.horizontalGradient(listOf(gradientLevel.toColor(color), Color.Transparent))
        GradientDirection.Right -> Brush.horizontalGradient(listOf(Color.Transparent, gradientLevel.toColor(color)))
        GradientDirection.Vignette -> Brush.radialGradient(listOf(Color.Transparent, gradientLevel.toColor(color)))
    }
