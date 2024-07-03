package net.skyscanner.backpack.compose.bottomsheet.internal

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.bottomsheet.BpkModalBottomSheetCloseAction
import net.skyscanner.backpack.compose.bottomsheet.BpkModalBottomSheetState
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationbar.IconAction
import net.skyscanner.backpack.compose.navigationbar.TextAction
import net.skyscanner.backpack.compose.navigationbar.internal.IconAction
import net.skyscanner.backpack.compose.navigationbar.internal.TextAction
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkElevation
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.NativeAndroidClose
import net.skyscanner.backpack.compose.utils.applyIf

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
    action: TextAction?,
    closeButton: BpkModalBottomSheetCloseAction,
    modifier: Modifier = Modifier,
    titleContentDescription: String? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        sheetState = state.delegate,
        content = {
            ModalBottomSheetContent(
                dragHandleStyle = dragHandleStyle,
                title = title,
                titleContentDescription = titleContentDescription,
                closeButton = closeButton,
                state = state,
                action = action,
                onDismissRequest = onDismissRequest,
                content = content,
            )
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
private fun ModalBottomSheetContent(
    dragHandleStyle: BpkDragHandleStyle,
    title: String?,
    action: TextAction?,
    closeButton: BpkModalBottomSheetCloseAction,
    state: BpkModalBottomSheetState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    titleContentDescription: String? = null,
    content: @Composable() (ColumnScope.() -> Unit),
) {
    if (closeButton is BpkModalBottomSheetCloseAction.Close || !title.isNullOrEmpty() || action != null) {
        when (dragHandleStyle) {
            BpkDragHandleStyle.Default -> {
                Column(
                    modifier = modifier,
                ) {
                    BpkModalBottomSheetHeader(
                        modifier = Modifier.height(BpkSpacing.Lg),
                        title = title,
                        titleContentDescription = titleContentDescription,
                        action = action,
                        state = state,
                        dragHandleStyle = dragHandleStyle,
                        closeButton = closeButton,
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
                        closeButton = closeButton,
                        action = action,
                        state = state,
                        onDismissRequest = onDismissRequest,
                        dragHandleStyle = dragHandleStyle,
                    )
                    BpkBottomSheetHandle(modifier = Modifier.align(Alignment.TopCenter), dragHandleStyle)
                }
            }
        }
    } else {
        BottomSheetContent(dragHandleStyle = dragHandleStyle, content = content)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BpkModalBottomSheetHeader(
    title: String?,
    action: TextAction?,
    closeButton: BpkModalBottomSheetCloseAction,
    state: BpkModalBottomSheetState,
    onDismissRequest: () -> Unit,
    dragHandleStyle: BpkDragHandleStyle,
    modifier: Modifier = Modifier,
    titleContentDescription: String? = null,
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
                    modifier = Modifier
                        .semantics {
                            heading()
                        }
                        .applyIf(titleContentDescription != null) {
                            semantics {
                                contentDescription = titleContentDescription ?: ""
                            }
                        },
                )
            }
        },
        navigationIcon = {
            when (closeButton) {
                is BpkModalBottomSheetCloseAction.Close -> {
                    IconAction(
                        action = IconAction(
                            BpkIcon.NativeAndroidClose,
                            onClick = {
                                coroutineScope.launch { state.hide() }.invokeOnCompletion {
                                    onDismissRequest()
                                }
                            },
                            contentDescription = closeButton.contentDescription,
                        ),
                    )
                }

                BpkModalBottomSheetCloseAction.None -> {}
            }
        },
        actions = {
            action?.let { TextAction(action = it) }
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
