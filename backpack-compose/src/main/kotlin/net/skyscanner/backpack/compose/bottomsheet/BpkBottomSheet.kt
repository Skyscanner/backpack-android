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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BpkBottomSheet(
    sheetContent: @Composable ColumnScope.(PaddingValues) -> Unit,
    modifier: Modifier = Modifier,
    state: BpkBottomSheetState = rememberBpkBottomSheetState(BpkBottomSheetValue.Collapsed),
    sheetGesturesEnabled: Boolean = true,
    peekHeight: Dp = BottomSheetScaffoldDefaults.SheetPeekHeight,
    content: @Composable (PaddingValues) -> Unit,
) {

    BoxWithConstraints(modifier) {
        val fullHeight = constraints.maxHeight.toFloat()
        val peekHeightWithHandle = peekHeight + HandleHeight
        val peekHeightPx = with(LocalDensity.current) { peekHeightWithHandle.toPx() }
        var bottomSheetHeight by remember { mutableStateOf(fullHeight) }
//        val progress = state.wrapped.progress
//        val openingPercent = when (progress.to) {
//            BpkBottomSheetValue.Expanded -> (1f - progress.fraction)
//            BpkBottomSheetValue.Collapsed -> progress.fraction
//        }

//        val radius = BpkBorderRadius.Lg * openingPercent
        val radius = BpkBorderRadius.Lg

        content(PaddingValues(bottom = peekHeightWithHandle))

        ModalBottomSheet(
            onDismissRequest = {},
            modifier = Modifier.fillMaxWidth(),
            sheetState = state.delegate,
            shape = RoundedCornerShape(topStart = radius, topEnd = radius),
            containerColor = BpkTheme.colors.surfaceElevated,
            contentColor = BpkTheme.colors.textPrimary,
            tonalElevation = 0.dp,
            scrimColor = Color.Transparent,
            dragHandle = { BpkBottomSheetHandle() },
            content = { sheetContent(PaddingValues.Absolute(0.dp)) },
        )
    }
}
@Composable
private fun BpkBottomSheetHandle(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .height(HandleHeight)
            .padding(BpkSpacing.Md)
            .width(HandleWidth)
            .background(BpkTheme.colors.line, CircleShape),
    )
}

private val HandleWidth = 36.dp
private val HandleHeight = 20.dp
