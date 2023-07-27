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

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun rememberBpkModalBottomSheetState(
    skipPartiallyExpanded: Boolean = false,
    confirmStateChange: (BpkModalBottomSheetValue) -> Boolean = { true },
): BpkModalBottomSheetState {
    val delegate = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded,
        confirmValueChange = { confirmStateChange(it.toBpkBottomSheetValue()) },
    )
    return remember(delegate) { BpkModalBottomSheetState(delegate) }
}

enum class BpkModalBottomSheetValue {
    Collapsed,
    Expanded,
    Hidden,
}

@Stable
@OptIn(ExperimentalMaterial3Api::class)
class BpkModalBottomSheetState internal constructor(
    internal val delegate: SheetState,
) {

    constructor(
        initialValue: BpkModalBottomSheetValue = BpkModalBottomSheetValue.Hidden,
        confirmValueChange: (BpkModalBottomSheetValue) -> Boolean = { true },
    ) : this(
        SheetState(
            skipPartiallyExpanded = false,
            skipHiddenState = false,
            initialValue = initialValue.toBottomSheetValue(),
            confirmValueChange = { confirmValueChange(it.toBpkBottomSheetValue()) },
        ),
    )

    val currentValue: BpkModalBottomSheetValue
        get() = delegate.currentValue.toBpkBottomSheetValue()

    val targetValue: BpkModalBottomSheetValue
        get() = delegate.targetValue.toBpkBottomSheetValue()

    val isVisible: Boolean
        get() = delegate.isVisible

    suspend fun expand() = delegate.expand()

    suspend fun collapse() = delegate.partialExpand()

    suspend fun show() = delegate.show()

    suspend fun hide() = delegate.hide()
}

@OptIn(ExperimentalMaterial3Api::class)
private fun SheetValue.toBpkBottomSheetValue(): BpkModalBottomSheetValue =
    when (this) {
        SheetValue.PartiallyExpanded -> BpkModalBottomSheetValue.Collapsed
        SheetValue.Expanded -> BpkModalBottomSheetValue.Expanded
        SheetValue.Hidden -> BpkModalBottomSheetValue.Hidden
    }

@OptIn(ExperimentalMaterial3Api::class)
private fun BpkModalBottomSheetValue.toBottomSheetValue(): SheetValue =
    when (this) {
        BpkModalBottomSheetValue.Collapsed -> SheetValue.PartiallyExpanded
        BpkModalBottomSheetValue.Expanded -> SheetValue.Expanded
        BpkModalBottomSheetValue.Hidden -> SheetValue.Hidden
    }
