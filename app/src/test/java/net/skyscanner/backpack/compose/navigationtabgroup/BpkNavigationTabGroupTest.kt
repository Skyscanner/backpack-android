/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.isSelectable
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.demo.compose.NavigationTabGroupSample
import org.junit.Test

class BpkNavigationTabGroupTest : BpkSnapshotTest() {

    @Test
    fun default() = snap(background = { Color.Transparent }) {
        NavigationTabGroupSample()
    }

    @Test
    fun onDark() = snap(background = { BpkTheme.colors.surfaceContrast }) {
        NavigationTabGroupSample(style = BpkNavigationTabGroupStyle.SurfaceContrast)
    }

    @Test
    fun withoutIcons() = snap(background = { Color.Transparent }) {
        BpkNavigationTabGroup(
            tabs = listOf(
                BpkNavigationTabItem("Tab 1"),
                BpkNavigationTabItem("Tab 2"),
                BpkNavigationTabItem("Tab 3"),
            ),
            selectedIndex = 0,
            onItemClicked = {},
        )
    }

    @Test
    fun semanticsSelectedState() = snap(
        background = { BpkTheme.colors.canvas },
        assertion = {
            onAllNodes(isSelectable()).assertCountEquals(4)
            onAllNodes(isSelectable())[0].assertIsSelected()
            onAllNodes(isSelectable())[1].assertIsNotSelected()
            onAllNodes(isSelectable())[2].assertIsNotSelected()
            onAllNodes(isSelectable())[3].assertIsNotSelected()
        },
    ) {
        NavigationTabGroupSample()
    }
}
