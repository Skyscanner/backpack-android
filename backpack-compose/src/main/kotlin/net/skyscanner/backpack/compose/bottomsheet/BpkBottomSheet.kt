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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkElevation

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BpkBottomSheet(
  sheetContent: @Composable ColumnScope.() -> Unit,
  modifier: Modifier = Modifier,
  state: BpkBottomSheetState = rememberBpkBottomSheetState(BpkBottomSheetValue.Collapsed),
  sheetGesturesEnabled: Boolean = true,
  sheetPeekHeight: Dp = BottomSheetScaffoldDefaults.SheetPeekHeight,
  content: @Composable (PaddingValues) -> Unit
) {
  BottomSheetScaffold(
    sheetContent = sheetContent,
    modifier = modifier,
    scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = state.delegate),
    topBar = null,
    snackbarHost = {},
    floatingActionButton = null,
    sheetGesturesEnabled = sheetGesturesEnabled,
    sheetShape = RoundedCornerShape(BpkBorderRadius.Lg),
    sheetElevation = BpkElevation.Lg,
    sheetBackgroundColor = BpkTheme.colors.surfaceElevated,
    sheetContentColor = BpkTheme.colors.textPrimary,
    sheetPeekHeight = sheetPeekHeight,
    drawerContent = null,
    drawerGesturesEnabled = false,
    drawerShape = RectangleShape,
    drawerElevation = 0.dp,
    drawerBackgroundColor = Color.Transparent,
    drawerContentColor = Color.Transparent,
    drawerScrimColor = Color.Transparent,
    backgroundColor = BpkTheme.colors.surfaceDefault,
    contentColor = BpkTheme.colors.textPrimary,
    content = content,
  )
}
