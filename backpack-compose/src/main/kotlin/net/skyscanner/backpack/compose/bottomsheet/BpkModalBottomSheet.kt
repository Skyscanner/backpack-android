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

package net.skyscanner.backpack.compose.bottomsheet

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.bottomsheet.internal.BpkBottomSheetHandle
import net.skyscanner.backpack.compose.bottomsheet.internal.BpkDragHandleStyle
import net.skyscanner.backpack.compose.bottomsheet.internal.bottomSheetContent
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkElevation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BpkModalBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    state: BpkModalBottomSheetState = rememberBpkModalBottomSheetState(),
    dragHandleStyle: BpkDragHandleStyle = BpkDragHandleStyle.Default,
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        sheetState = state.delegate,
        content = bottomSheetContent(dragHandleStyle, content),
        modifier = modifier,
        dragHandle = { if (dragHandleStyle == BpkDragHandleStyle.Default) BpkBottomSheetHandle() },
        shape = RoundedCornerShape(topStart = BpkBorderRadius.Lg, topEnd = BpkBorderRadius.Lg),
        containerColor = BpkTheme.colors.surfaceElevated,
        contentColor = BpkTheme.colors.textPrimary,
        scrimColor = BpkTheme.colors.scrim,
        tonalElevation = BpkElevation.Lg,
        onDismissRequest = onDismissRequest,
    )
}
