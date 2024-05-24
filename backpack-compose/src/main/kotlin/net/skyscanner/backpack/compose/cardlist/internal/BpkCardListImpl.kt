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

package net.skyscanner.backpack.compose.cardlist.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.cardlist.BpkCardListButtonAccessory
import net.skyscanner.backpack.compose.cardlist.BpkCardListLayout
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeader
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderButton
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun <T> BpkCardListImpl(
    title: String,
    description: String,
    layout: BpkCardListLayout,
    dataList: List<T>,
    modifier: Modifier = Modifier,
    elements: @Composable (LazyItemScope.(Int) -> Unit),
) {
    Column(
        modifier = modifier,
    ) {
        BpkSectionHeader(
            title = title,
            description = description,
            modifier = Modifier.padding(BpkSpacing.Base),
            button = bpkSectionHeaderButton(layout.button),
        )

        Spacer(modifier = Modifier.height(BpkSpacing.Base))

        when (layout) {
            is BpkCardListLayout.Rail -> RailLayout(dataList = dataList, elements = elements)
            else -> {}
        }
    }
}

@Composable
private fun bpkSectionHeaderButton(button: BpkCardListButtonAccessory?) = when (button) {
    is BpkCardListButtonAccessory.SectionHeaderButton -> {
        BpkSectionHeaderButton(
            text = button.text,
            onClick = button.onClick,
        )
    }

    else -> null
}

@Composable
fun <T> RailLayout(
    dataList: List<T>,
    modifier: Modifier = Modifier,
    elements: @Composable (LazyItemScope.(Int) -> Unit),
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
    ) {
        items(
            count = dataList.size,
            key = { position ->
                dataList[position].hashCode()
            },
            itemContent = elements,
        )
    }
}
