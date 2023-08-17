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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.bottomsheet.internal.BottomSheetContent
import net.skyscanner.backpack.compose.bottomsheet.internal.BpkBottomSheetHandle
import net.skyscanner.backpack.compose.bottomsheet.internal.BpkDragHandleStyle
import net.skyscanner.backpack.compose.bottomsheet.internal.HandleHeight
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkElevation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BpkBottomSheet(
    sheetContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    state: BpkBottomSheetState = rememberBpkBottomSheetState(),
    sheetGesturesEnabled: Boolean = true,
    peekHeight: Dp = DefaultSheetPeekHeight,
    dragHandleStyle: BpkDragHandleStyle = BpkDragHandleStyle.Default,
    content: @Composable (PaddingValues) -> Unit,
) {
    BottomSheetScaffold(
        sheetContent = { BottomSheetContent(dragHandleStyle = dragHandleStyle, content = sheetContent) },
        modifier = modifier,
        scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = state.delegate,
        ),
        sheetPeekHeight = peekHeight + HandleHeight,
        sheetShape = RoundedCornerShape(topStart = BpkBorderRadius.Lg, topEnd = BpkBorderRadius.Lg),
        sheetContainerColor = BpkTheme.colors.surfaceElevated,
        sheetContentColor = BpkTheme.colors.textPrimary,
        sheetTonalElevation = 0.dp,
        sheetShadowElevation = BpkElevation.Lg,
        sheetDragHandle = { if (dragHandleStyle == BpkDragHandleStyle.Default) BpkBottomSheetHandle() },
        sheetSwipeEnabled = sheetGesturesEnabled,
        topBar = null,
        snackbarHost = { Box(Modifier) },
        containerColor = BpkTheme.colors.surfaceDefault,
        contentColor = BpkTheme.colors.textPrimary,
        content = content,
    )
}

private val DefaultSheetPeekHeight = 56.dp
