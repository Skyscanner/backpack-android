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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationtabgroup.BpkNavigationTabGroup
import net.skyscanner.backpack.compose.navigationtabgroup.BpkNavigationTabGroupStyle
import net.skyscanner.backpack.compose.navigationtabgroup.BpkNavigationTabItem
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Cars
import net.skyscanner.backpack.compose.tokens.Explore
import net.skyscanner.backpack.compose.tokens.Flight
import net.skyscanner.backpack.compose.tokens.Hotels
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.NavigationTabGroupComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@NavigationTabGroupComponent
@ComposeStory("Navigation Tab Group")
fun NavigationTabGroupStory(modifier: Modifier = Modifier) {
    NavigationTabGroupDemo(
        modifier,
    ) { style -> NavigationTabGroupSample(style = style) }
}

@Composable
internal fun NavigationTabGroupSample(
    modifier: Modifier = Modifier,
    style: BpkNavigationTabGroupStyle = BpkNavigationTabGroupStyle.CanvasDefault,
) {
    val tabs = listOf(
        BpkNavigationTabItem(stringResource(R.string.navigation_tabs_explore), BpkIcon.Explore),
        BpkNavigationTabItem(stringResource(R.string.navigation_tabs_flights), BpkIcon.Flight),
        BpkNavigationTabItem(stringResource(R.string.navigation_tabs_hotels), BpkIcon.Hotels),
        BpkNavigationTabItem(stringResource(R.string.navigation_tabs_carhire), BpkIcon.Cars),
    )
    var selectedIndex by remember { mutableStateOf(0) }

    BpkNavigationTabGroup(
        modifier = modifier,
        tabs = tabs,
        selectedIndex = selectedIndex,
        onItemClicked = { selectedIndex = tabs.indexOf(it) },
        style = style,
    )
}

@Composable
private fun NavigationTabGroupDemo(
    modifier: Modifier = Modifier,
    content: @Composable (BpkNavigationTabGroupStyle) -> Unit,
) {
    Column(modifier) {
        NavigationTabGroupBox(
            modifier = Modifier
                .weight(1f)
                .background(Color.Transparent),
            content = { content.invoke(BpkNavigationTabGroupStyle.CanvasDefault) },
        )
        NavigationTabGroupBox(
            modifier = Modifier
                .weight(1f)
                .background(BpkTheme.colors.surfaceContrast),
            content = { content.invoke(BpkNavigationTabGroupStyle.SurfaceContrast) },
        )
    }
}

@Composable
private fun NavigationTabGroupBox(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(BpkSpacing.Base),
            verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        ) {
            content()
        }
    }
}
