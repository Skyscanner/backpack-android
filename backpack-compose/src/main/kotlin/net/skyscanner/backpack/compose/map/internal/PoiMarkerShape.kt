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

internal fun PoiMarkerShape(): Shape =
    object : Shape {
        override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline =
            Outline.Generic(
                path = Path().apply {
                    addPoiOutlinePath(size)
                },
            )
    }

private fun Path.addPoiOutlinePath(
    size: Size,
) {
    val scale = size.height / IconHeight
    moveTo(24.0f * scale, 12.0f * scale)
    cubicTo(24f * scale, 16.8056f * scale, 19.9909f * scale, 22.4301f * scale, 16.1189f * scale, 26.8273f * scale)
    cubicTo(13.9289f * scale, 29.3142f * scale, 10.071f * scale, 29.3144f * scale, 7.8803f * scale, 26.8281f * scale)
    cubicTo(4.0014f * scale, 22.426f * scale, 0f * scale, 16.8108f * scale, 0f * scale, 12f * scale)
    cubicTo(0f * scale, 5.3726f * scale, 5.3726f * scale, 0f * scale, 12f * scale, 0f * scale)
    cubicTo(18.6274f * scale, 0f * scale, 24f * scale, 5.3726f * scale, 24f * scale, 12f * scale)
    close()
}

private const val IconHeight = 29f
