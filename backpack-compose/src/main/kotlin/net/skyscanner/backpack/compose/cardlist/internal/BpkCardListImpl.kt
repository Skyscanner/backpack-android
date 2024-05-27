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

import androidx.compose.foundation.ExperimentalFoundationApi
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
import net.skyscanner.backpack.compose.cardlist.BpkCardListLayout
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeader
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderButton
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BpkCardListImpl(
    title: String,
    description: String,
    layout: BpkCardListLayout,
    headerButton: BpkSectionHeaderButton?,
    initiallyShownCards: Int,
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
        )

        Spacer(modifier = Modifier.height(BpkSpacing.Base))

        when (layout) {
            is BpkCardListLayout.Rail -> RailLayout(content = content, initiallyShownCards = initiallyShownCards)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RailLayout(
    initiallyShownCards: Int,
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
            count = initiallyShownCards,
            itemContent = content,
        )
    }
}
