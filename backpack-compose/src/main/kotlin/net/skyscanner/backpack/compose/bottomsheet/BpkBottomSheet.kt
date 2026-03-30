/*
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

@file:OptIn(ExperimentalMaterial3Api::class)

package net.skyscanner.backpack.compose.bottomsheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.bottomsheet.internal.BpkBottomSheetImpl
import net.skyscanner.backpack.compose.bottomsheet.internal.BpkDragHandleStyle

@Composable
fun BpkBottomSheet(
    sheetContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    state: BpkBottomSheetState = rememberBpkBottomSheetState(),
    sheetGesturesEnabled: Boolean = true,
    peekHeight: Dp = 56.dp,
    dragHandleStyle: BpkDragHandleStyle = BpkDragHandleStyle.Default,
    content: @Composable (PaddingValues) -> Unit,
) {
    BpkBottomSheetImpl(
        sheetContent = sheetContent,
        modifier = modifier,
        delegate = state.delegate,
        sheetGesturesEnabled = sheetGesturesEnabled,
        peekHeight = peekHeight,
        dragHandleStyle = dragHandleStyle,
        content = content,
    )
}
