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

package net.skyscanner.backpack.compose.map.internal

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

internal fun IconMarkerShape(): Shape =
    object : Shape {
        override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline =
            Outline.Generic(
                path = Path().apply {
                    addPath(size)
                },
            )
    }

private fun Path.addPath(
    size: Size,
) {
    val scale = size.height / ICON_HEIGHT

    moveTo(32.0f * scale, 16.2575f * scale)
    lineTo(31.9995f * scale, 16.3817f * scale)
    cubicTo(32.0032f * scale, 167936f * scale, 31.9852f * scale, 17.2038f * scale, 31.9455f * scale, 17.6093f * scale)
    cubicTo(31.7127f * scale, 20.4826f * scale, 30.7446f * scale, 23.1441f * scale, 29.2328f * scale, 25.3991f * scale)
    cubicTo(26.2125f * scale, 30.7416f * scale, 21.1896f * scale, 35.9609f * scale, 17.2875f * scale, 39.5f * scale)
    cubicTo(16.5525f * scale, 40.1667f * scale, 15.4475f * scale, 40.1667f * scale, 14.7125f * scale, 39.5f * scale)
    cubicTo(10.8104f * scale, 35.9609f * scale, 5.7875f * scale, 30.7416f * scale, 2.7672f * scale, 25.3991f * scale)
    cubicTo(1.2554f * scale, 23.1441f * scale, 0.2873f * scale, 20.4826f * scale, 0.0545f * scale, 17.6093f * scale)
    cubicTo(0.0148f * scale, 17.2038f * scale, -0.0032f * scale, 16.7936f * scale, 5.0E-4f * scale, 16.3817f * scale)
    lineTo(0.0f * scale, 16.2575f * scale)
    cubicTo(0.0f * scale, 7.2787f * scale, 7.1634f * scale, 0.0f * scale, 16.0f * scale, 0.0f * scale)
    cubicTo(24.8366f * scale, 0.0f * scale, 32.0f * scale, 7.2787f * scale, 32.0f * scale, 16.2575f * scale)
    close()
}

private const val ICON_HEIGHT = 40f
