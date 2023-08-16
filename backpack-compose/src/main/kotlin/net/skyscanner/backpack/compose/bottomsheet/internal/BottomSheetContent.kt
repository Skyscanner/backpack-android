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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun bottomSheetContent(
    dragHandleStyle: BpkDragHandleStyle,
    content: @Composable (ColumnScope.() -> Unit),
): @Composable (ColumnScope.() -> Unit) = {
    when (dragHandleStyle) {
        BpkDragHandleStyle.Default -> content()
        is BpkDragHandleStyle.OnImage -> Box {
            Column { content() }
            BpkBottomSheetHandle(modifier = Modifier.align(Alignment.TopCenter), dragHandleStyle)
        }
    }
}
