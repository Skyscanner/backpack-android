package net.skyscanner.backpack.compose.bottomsheet.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.bottomsheet.BpkModalBottomSheetCloseAction
import net.skyscanner.backpack.compose.bottomsheet.BpkModalBottomSheetState
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationbar.IconAction
import net.skyscanner.backpack.compose.navigationbar.internal.IconAction
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkElevation
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.NativeAndroidClose

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BpkModalBottomSheetImpl(
    onDismissRequest: () -> Unit,
    state: BpkModalBottomSheetState,
    dragHandleStyle: BpkDragHandleStyle,
    title: String?,
    isClosable: BpkModalBottomSheetCloseAction,
    content: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        sheetState = state.delegate,
        content = {
            BottomSheetContent(dragHandleStyle = dragHandleStyle, content = {
                BpkModalBottomSheetContent(
                    dragHandleStyle = dragHandleStyle,
                    title = title,
                    isClosable = isClosable,
                    state = state,
                    onDismissRequest = onDismissRequest,
                    content = content,
                )
            })
        },
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

@Composable
fun ColumnScope.BpkModalBottomSheetContent(
    dragHandleStyle: BpkDragHandleStyle,
    title: String?,
    isClosable: BpkModalBottomSheetCloseAction,
    state: BpkModalBottomSheetState,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    if (isClosable is BpkModalBottomSheetCloseAction.Default || !title.isNullOrEmpty()) {
        when (dragHandleStyle) {
            BpkDragHandleStyle.Default -> {
                Column(modifier = modifier,
                    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Lg),
                ) {
                    BpkModalBottomSheetHeader(
                        modifier = Modifier.height(BpkSpacing.Lg),
                        title = title,
                        state = state,
                        dragHandleStyle = dragHandleStyle,
                        isClosable = isClosable,
                        onDismissRequest = onDismissRequest,
                    )
                    content()
                }
            }

            is BpkDragHandleStyle.OnImage -> {
                Box(modifier = modifier) {
                    Column { content() }
                    BpkModalBottomSheetHeader(
                        title = title,
                        state = state,
                        dragHandleStyle = dragHandleStyle,
                        isClosable = isClosable,
                        onDismissRequest = onDismissRequest,
                    )
                }
            }
        }
    } else {
        content()
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BpkModalBottomSheetHeader(
    title: String?,
    isClosable: BpkModalBottomSheetCloseAction,
    state: BpkModalBottomSheetState,
    onDismissRequest: () -> Unit,
    dragHandleStyle: BpkDragHandleStyle,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val backgroundColor = when (dragHandleStyle) {
        BpkDragHandleStyle.Default -> BpkTheme.colors.surfaceElevated
        is BpkDragHandleStyle.OnImage -> Color.Transparent
    }
    val textColor = when (dragHandleStyle) {
        BpkDragHandleStyle.Default -> BpkTheme.colors.textPrimary
        is BpkDragHandleStyle.OnImage -> BpkTheme.colors.textOnDark
    }
    CenterAlignedTopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = BpkSpacing.Base),
        title = {
            title?.let {
                BpkText(
                    text = it,
                    style = BpkTheme.typography.heading5,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.semantics { heading() },
                )
            }
        },
        navigationIcon = {
            when (isClosable) {
                is BpkModalBottomSheetCloseAction.Default -> {
                    IconAction(
                        action = IconAction(
                            BpkIcon.NativeAndroidClose,
                            onClick = {
                                coroutineScope.launch { state.hide() }.invokeOnCompletion {
                                    onDismissRequest()
                                }
                            },
                            contentDescription = isClosable.contentDescription,
                        ),
                    )
                }

                BpkModalBottomSheetCloseAction.None -> {}
            }
        },
        windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp),
        colors = TopAppBarColors(
            containerColor = backgroundColor,
            scrolledContainerColor = backgroundColor,
            navigationIconContentColor = textColor,
            titleContentColor = textColor,
            actionIconContentColor = BpkTheme.colors.textLink,
        ),
    )
}
