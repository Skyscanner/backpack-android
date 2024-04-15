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

package net.skyscanner.backpack.compose.navigationtabgroup

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationtabgroup.internal.BpkNavigationTabGroupImpl
import net.skyscanner.backpack.compose.utils.BpkBehaviouralEventWrapper

data class BpkNavigationTabItem(val text: String, val icon: BpkIcon? = null)

enum class BpkNavigationTabGroupStyle {
    CanvasDefault,
    SurfaceContrast,
}

@Composable
fun BpkNavigationTabGroup(
    tabs: List<BpkNavigationTabItem>,
    selectedIndex: Int,
    onItemClicked: (BpkNavigationTabItem) -> Unit,
    modifier: Modifier = Modifier,
    style: BpkNavigationTabGroupStyle = BpkNavigationTabGroupStyle.CanvasDefault,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    behaviouralEventWrapper: BpkBehaviouralEventWrapper? = null,
) {
    BpkNavigationTabGroupImpl(
        tabs = tabs,
        selectedIndex = selectedIndex,
        onItemClicked = onItemClicked,
        modifier = modifier,
        style = style,
        contentPadding = contentPadding,
        behaviouralEventWrapper = behaviouralEventWrapper,
    )
}
