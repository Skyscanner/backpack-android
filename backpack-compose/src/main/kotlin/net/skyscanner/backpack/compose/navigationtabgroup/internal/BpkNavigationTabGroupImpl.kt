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

package net.skyscanner.backpack.compose.navigationtabgroup.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.navigationtabgroup.BpkNavigationTabGroupStyle
import net.skyscanner.backpack.compose.navigationtabgroup.BpkNavigationTabItem
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.BpkBehaviouralEventWrapper

@Composable
internal fun BpkNavigationTabGroupImpl(
    tabs: List<BpkNavigationTabItem>,
    selectedIndex: Int,
    onItemClicked: (BpkNavigationTabItem) -> Unit,
    style: BpkNavigationTabGroupStyle,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    behaviouralEventWrapper: BpkBehaviouralEventWrapper? = null,
) {
    LazyRow(
        modifier = modifier.selectableGroup(),
        contentPadding = contentPadding,
        state = rememberLazyListState(),
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
    ) {
        itemsIndexed(items = tabs) { index, tab ->
            if (behaviouralEventWrapper != null) {
                behaviouralEventWrapper(tab, Modifier) {
                    NavigationTabItem(
                        tab = tab,
                        selected = index == selectedIndex,
                        style = style,
                    ) {
                        onItemClicked.invoke(tabs[index])
                        notifyClick()
                    }
                }
            } else {
                NavigationTabItem(
                    tab = tab,
                    selected = index == selectedIndex,
                    style = style,
                ) {
                    onItemClicked.invoke(tabs[index])
                }
            }
        }
    }
}

@Composable
private fun NavigationTabItem(
    tab: BpkNavigationTabItem,
    selected: Boolean,
    style: BpkNavigationTabGroupStyle,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val tabStyle = when (style) {
        BpkNavigationTabGroupStyle.SurfaceContrast -> BpkNavigationTabStyle.SurfaceContrast
        BpkNavigationTabGroupStyle.CanvasDefault -> BpkNavigationTabStyle.CanvasDefault
    }
    BpkNavigationTab(
        modifier = modifier.semantics { role = Role.RadioButton },
        text = tab.text,
        icon = tab.icon,
        selected = selected,
        style = tabStyle,
        onClick = onClick,
    )
}
