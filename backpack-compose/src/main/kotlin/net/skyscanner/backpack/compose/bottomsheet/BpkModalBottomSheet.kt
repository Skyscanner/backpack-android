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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.bottomsheet.internal.BpkDragHandleStyle
import net.skyscanner.backpack.compose.bottomsheet.internal.BpkModalBottomSheetImpl

sealed class BpkModalBottomSheetCloseAction {
    data object None : BpkModalBottomSheetCloseAction()
    data class Default(val contentDescription: String) : BpkModalBottomSheetCloseAction()
}

@Composable
fun BpkModalBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    state: BpkModalBottomSheetState = rememberBpkModalBottomSheetState(),
    dragHandleStyle: BpkDragHandleStyle = BpkDragHandleStyle.Default,
    title: String? = null,
    isClosable: BpkModalBottomSheetCloseAction = BpkModalBottomSheetCloseAction.None,
    content: @Composable ColumnScope.() -> Unit,
) {
    BpkModalBottomSheetImpl(
        content = content,
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        state = state,
        dragHandleStyle = dragHandleStyle,
        title = title,
        isClosable = isClosable,
    )
}
