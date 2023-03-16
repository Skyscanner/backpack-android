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
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.collapse
import androidx.compose.ui.semantics.expand
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkElevation
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.nestedScrollFixedSwipeable
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
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
        val progress = state.wrapped.progress
        val openingPercent = when (progress.to) {
            BpkBottomSheetValue.Expanded -> (1f - progress.fraction)
            BpkBottomSheetValue.Collapsed -> progress.fraction
        }

        val radius = BpkBorderRadius.Lg * openingPercent

        content(PaddingValues(bottom = peekHeightWithHandle))

        Surface(
            modifier = Modifier
                .bottomSheetSwipeable(state, fullHeight, peekHeightPx, bottomSheetHeight, sheetGesturesEnabled)
                .bottomSheetSemantics(state, peekHeightPx, bottomSheetHeight)
                .fillMaxWidth()
                .requiredHeightIn(min = peekHeightWithHandle)
                .onGloballyPositioned { bottomSheetHeight = it.size.height.toFloat() }
                .offset { IntOffset(0, state.wrapped.offset.value.roundToInt()) },
            shape = RoundedCornerShape(topStart = radius, topEnd = radius),
            elevation = BpkElevation.Lg,
            color = BpkTheme.colors.surfaceElevated,
            contentColor = BpkTheme.colors.textPrimary,
            content = {
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
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
private fun Modifier.bottomSheetSwipeable(
    state: BpkBottomSheetState,
    fullHeight: Float,
    peekHeightPx: Float,
    bottomSheetHeight: Float,
    sheetGesturesEnabled: Boolean,
): Modifier =
    nestedScrollFixedSwipeable(
        state = state.wrapped,
        anchors = mapOf(
            fullHeight - peekHeightPx to BpkBottomSheetValue.Collapsed,
            fullHeight - bottomSheetHeight to BpkBottomSheetValue.Expanded,
        ),
        orientation = Orientation.Vertical,
        enabled = sheetGesturesEnabled,
        resistance = null,
    )

private fun Modifier.bottomSheetSemantics(
    state: BpkBottomSheetState,
    peekHeightPx: Float,
    bottomSheetHeight: Float,
): Modifier = composed {
    val scope = rememberCoroutineScope()
    semantics {
        if (peekHeightPx != bottomSheetHeight) {
            if (state.isCollapsed) {
                expand {
                    if (state.confirmStateChange(BpkBottomSheetValue.Expanded)) {
                        scope.launch { state.expand() }
                    }
                    true
                }
            } else {
                collapse {
                    if (state.confirmStateChange(BpkBottomSheetValue.Collapsed)) {
                        scope.launch { state.collapse() }
                    }
                    true
                }
            }
        }
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
