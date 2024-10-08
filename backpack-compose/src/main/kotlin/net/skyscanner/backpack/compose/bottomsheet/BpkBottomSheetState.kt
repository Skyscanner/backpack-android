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
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun rememberBpkBottomSheetState(
    initialValue: BpkBottomSheetValue = BpkBottomSheetValue.Collapsed,
    confirmStateChange: (BpkBottomSheetValue) -> Boolean = { true },
): BpkBottomSheetState {
    val delegate = rememberStandardBottomSheetState(
        initialValue = initialValue.toBottomSheetValue(),
        confirmValueChange = { confirmStateChange(it.toBpkBottomSheetValue()) },
        skipHiddenState = true,
    )
    return remember(delegate) { BpkBottomSheetState(delegate) }
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

    val currentValue: BpkBottomSheetValue
        get() = delegate.currentValue.toBpkBottomSheetValue()

    val targetValue: BpkBottomSheetValue
        get() = delegate.targetValue.toBpkBottomSheetValue()

    suspend fun expand() = delegate.expand()

    suspend fun collapse() = delegate.partialExpand()
}

@OptIn(ExperimentalMaterial3Api::class)
private fun SheetValue.toBpkBottomSheetValue(): BpkBottomSheetValue =
    when (this) {
        SheetValue.PartiallyExpanded -> BpkBottomSheetValue.Collapsed
        SheetValue.Expanded -> BpkBottomSheetValue.Expanded
        SheetValue.Hidden -> error("Hidden state is not supported")
    }

@OptIn(ExperimentalMaterial3Api::class)
private fun BpkBottomSheetValue.toBottomSheetValue(): SheetValue =
    when (this) {
        BpkBottomSheetValue.Collapsed -> SheetValue.PartiallyExpanded
        BpkBottomSheetValue.Expanded -> SheetValue.Expanded
    }
