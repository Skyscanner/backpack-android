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

package net.skyscanner.backpack.compose.cardlist.rail.internal

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeader
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderButton
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BpkRailCardListImpl(
    title: String,
    description: String?,
    headerButton: BpkSectionHeaderButton?,
    totalCards: Int,
    accessibilityHeaderTagEnabled: Boolean?,
    modifier: Modifier = Modifier,
    content: @Composable (LazyItemScope.(Int) -> Unit),
) {
    Column(
        modifier = modifier,
    ) {
        BpkSectionHeader(
            title = title,
            description = description,
            modifier = Modifier.padding(horizontal = BpkSpacing.Base),
            button = headerButton,
            accessibilityHeaderTagEnabled = accessibilityHeaderTagEnabled,
        )

        Spacer(modifier = Modifier.height(BpkSpacing.Base))

        RailLayout(content = content, totalCards = totalCards)
    }
}

@Composable
fun RailLayout(
    totalCards: Int,
    modifier: Modifier = Modifier,
    content: @Composable (LazyItemScope.(Int) -> Unit),
) {
    val lazyListState = rememberLazyListState()
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        contentPadding = PaddingValues(start = BpkSpacing.Base, end = BpkSpacing.Base),
        state = lazyListState,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState),
    ) {
        items(
            count = totalCards,
            itemContent = content,
        )
    }
}
