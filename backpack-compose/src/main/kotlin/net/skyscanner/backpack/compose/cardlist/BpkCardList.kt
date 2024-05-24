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

package net.skyscanner.backpack.compose.cardlist

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.cardlist.internal.BpkCardListImpl

@Composable
fun <T> BpkCardList(
    title: String,
    description: String,
    layout: BpkCardListLayout,
    dataList: List<T>,
    modifier: Modifier = Modifier,
    elements: @Composable (LazyItemScope.(Int) -> Unit),
) {
    BpkCardListImpl(
        title = title,
        description = description,
        layout = layout,
        modifier = modifier,
        dataList = dataList,
        elements = elements,
    )
}

sealed class BpkCardListLayout(
    open val button: BpkCardListButtonAccessory?,
) {
    data class Rail(override val button: BpkCardListButtonAccessory? = null) : BpkCardListLayout(button)
    data class Stack(override val button: BpkCardListButtonAccessory? = null) : BpkCardListLayout(button)
}

sealed class BpkCardListButtonAccessory {
    class SectionHeaderButton(
        val text: String,
        val onClick: (() -> Unit),
    ) : BpkCardListButtonAccessory()
}
