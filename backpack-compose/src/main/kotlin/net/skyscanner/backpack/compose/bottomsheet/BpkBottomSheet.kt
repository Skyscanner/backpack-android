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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkElevation
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BpkBottomSheet(
    sheetContent: @Composable ColumnScope.(PaddingValues) -> Unit,
    modifier: Modifier = Modifier,
    state: BpkBottomSheetState = rememberBpkBottomSheetState(),
    sheetGesturesEnabled: Boolean = true,
    peekHeight: Dp = BottomSheetScaffoldDefaults.SheetPeekHeight,
    content: @Composable (PaddingValues) -> Unit,
) {
    var contentHeight by remember { mutableStateOf(0) }
    val totalSheetHeight = peekHeight + HandleHeight

    var openingPercent = if (state.currentValue == BpkBottomSheetValue.Collapsed) 1f else 0f
    if (contentHeight > 0) {
        val total = contentHeight - with(LocalDensity.current) { totalSheetHeight.toPx() }
        val currentPosition = try {
            state.delegate.requireOffset()
        } catch (e: Exception) {
            0f
        }
        openingPercent = (currentPosition / total).coerceIn(0f, 1f)
    }

    val radius = BpkBorderRadius.Lg * openingPercent

    BottomSheetScaffold(
        sheetContent = {
            Box {
                BpkBottomSheetHandle(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .alpha(openingPercent),
                )
                Column {
                    sheetContent(PaddingValues(top = HandleHeight * openingPercent))
                }
            }
        },
        modifier = modifier,
        scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = state.delegate,
        ),
        sheetPeekHeight = totalSheetHeight,
        sheetShape = RoundedCornerShape(topStart = radius, topEnd = radius),
        sheetContainerColor = BpkTheme.colors.surfaceElevated,
        sheetContentColor = BpkTheme.colors.textPrimary,
        sheetTonalElevation = 0.dp,
        sheetShadowElevation = BpkElevation.Lg,
        sheetDragHandle = null,
        sheetSwipeEnabled = sheetGesturesEnabled,
        topBar = null,
        snackbarHost = { Box(Modifier) },
        containerColor = BpkTheme.colors.surfaceDefault,
        contentColor = BpkTheme.colors.textPrimary,
    ) {
        Box(
            modifier = Modifier.onGloballyPositioned { contentHeight = it.size.height },
            content = { content(it) },
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
