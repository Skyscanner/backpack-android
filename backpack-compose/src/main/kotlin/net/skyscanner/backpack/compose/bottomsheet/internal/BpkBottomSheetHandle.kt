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

package net.skyscanner.backpack.compose.bottomsheet.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BpkBottomSheetHandle(
    modifier: Modifier = Modifier,
    dragHandleStyle: BpkDragHandleStyle = BpkDragHandleStyle.Default,
) {
    val dragHandleColor: Color =
        when (dragHandleStyle) {
            BpkDragHandleStyle.Default -> BpkTheme.colors.line
            is BpkDragHandleStyle.OnImage -> {
                if (dragHandleStyle.type == BpkDragHandleStyle.OnImage.Type.Dark)
                    BpkTheme.colors.line
                else
                    BpkTheme.colors.lineOnDark
            }
        }
    Spacer(
        modifier = modifier
            .height(HandleHeight)
            .padding(top = BpkSpacing.Md, bottom = 12.dp)
            .width(HandleWidth)
            .background(dragHandleColor, CircleShape),
    )
}

sealed class BpkDragHandleStyle {
    data object Default : BpkDragHandleStyle()
    data class OnImage(val type: Type = Type.Light) : BpkDragHandleStyle() {
        enum class Type {
            Light, Dark
        }
    }
}

private val HandleWidth = 40.dp
internal val HandleHeight = 24.dp
