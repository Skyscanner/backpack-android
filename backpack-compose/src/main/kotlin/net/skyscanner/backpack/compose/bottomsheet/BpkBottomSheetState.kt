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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun rememberBpkBottomSheetState(
    initialValue: BpkBottomSheetValue,
    confirmStateChange: (BpkBottomSheetValue) -> Boolean = { true },
): BpkBottomSheetState {
    val delegate = rememberSaveable(
        confirmStateChange,
        saver = SheetState.Saver(
            skipPartiallyExpanded = true,
            confirmValueChange = { confirmStateChange(it.toBpkBottomSheetValue()) },
        ),
    ) {
        SheetState(
            skipPartiallyExpanded = true,
            initialValue = initialValue.toBottomSheetValue(),
            confirmValueChange = { confirmStateChange(it.toBpkBottomSheetValue()) },
            skipHiddenState = false,
        )
    }
    return remember { BpkBottomSheetState(delegate) }
}

enum class BpkBottomSheetValue {
    Collapsed,
    Expanded,
}

@Stable
@OptIn(ExperimentalMaterial3Api::class)
class BpkBottomSheetState internal constructor(
    internal val delegate: SheetState,
) {

    constructor(
        initialValue: BpkBottomSheetValue = BpkBottomSheetValue.Collapsed,
        confirmValueChange: (BpkBottomSheetValue) -> Boolean = { true },
    ) : this(
        SheetState(
            skipPartiallyExpanded = true,
            skipHiddenState = false,
            initialValue = initialValue.toBottomSheetValue(),
            confirmValueChange = { confirmValueChange(it.toBpkBottomSheetValue()) },
        ),
    )

    val currentValue: BpkBottomSheetValue
        get() = delegate.currentValue.toBpkBottomSheetValue()

    val targetValue: BpkBottomSheetValue
        get() = delegate.targetValue.toBpkBottomSheetValue()

    suspend fun expand() = delegate.expand()

    suspend fun collapse() = delegate.hide()
}

@OptIn(ExperimentalMaterial3Api::class)
private fun SheetValue.toBpkBottomSheetValue(): BpkBottomSheetValue =
    when (this) {
        SheetValue.Hidden -> BpkBottomSheetValue.Collapsed
        SheetValue.Expanded -> BpkBottomSheetValue.Expanded
        SheetValue.PartiallyExpanded -> error("PartiallyExpanded is not supported")
    }

@OptIn(ExperimentalMaterial3Api::class)
private fun BpkBottomSheetValue.toBottomSheetValue(): SheetValue =
    when (this) {
        BpkBottomSheetValue.Collapsed -> SheetValue.Hidden
        BpkBottomSheetValue.Expanded -> SheetValue.Expanded
    }
