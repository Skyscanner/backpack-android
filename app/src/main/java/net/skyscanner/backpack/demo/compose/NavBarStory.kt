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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.divider.BpkDivider
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.IconAction
import net.skyscanner.backpack.compose.navigationbar.NavBarStyle
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.navigationbar.TextAction
import net.skyscanner.backpack.compose.navigationbar.TopNavBarStatus
import net.skyscanner.backpack.compose.navigationbar.nestedScroll
import net.skyscanner.backpack.compose.navigationbar.rememberTopAppBarState
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.Accessibility
import net.skyscanner.backpack.compose.tokens.Account
import net.skyscanner.backpack.compose.tokens.AccountIdCard
import net.skyscanner.backpack.compose.tokens.BpkDimension
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.NavBarComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.demo.ui.ListItem

@Composable
@NavBarComponent
@ComposeStory("Default")
fun NavBarStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(vertical = BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {

        val childModifier = Modifier.fillMaxWidth()
        NoNavIconTopNavBar(childModifier)
        BackTopNavBar(childModifier)
        CloseTopNavBar(childModifier)
        ActionsTopNavBar(childModifier)
        TextActionTopNavBar(childModifier)
    }
}

@Composable
@NavBarComponent
@ComposeStory("Collapsible")
fun CollapsibleNavBarStory(
    modifier: Modifier = Modifier,
    initialStatus: TopNavBarStatus = TopNavBarStatus.Expanded,
    showList: Boolean = true,
    showActions: Boolean = true,
    showNav: Boolean = true,
    insets: WindowInsets? = null,
) {
    val state = rememberTopAppBarState(initialStatus)
    Column(modifier.nestedScroll(state)) {
        BpkTopNavBar(
            state = state,
            title = stringResource(R.string.navigation_bar_title),
            insets = insets,
            navIcon = when {
                showNav -> NavIcon.Back(contentDescription = stringResource(R.string.navigation_back)) {}
                else -> NavIcon.None
            },
            actions = if (showActions) listOf(
                IconAction(icon = BpkIcon.AccountIdCard, contentDescription = stringResource(R.string.navigation_id_card)) {},
                IconAction(icon = BpkIcon.Accessibility, contentDescription = stringResource(R.string.navigation_accessibility)) {},
                IconAction(icon = BpkIcon.Account, contentDescription = stringResource(R.string.navigation_account)) {},
            ) else emptyList(),
        )
        NavBarSampleBody(showList)
    }
}

@Composable
@NavBarComponent
@ComposeStory("Transparent")
fun TransparentNavBarStory(
    modifier: Modifier = Modifier,
    initialStatus: TopNavBarStatus = TopNavBarStatus.Expanded,
    showList: Boolean = true,
    showActions: Boolean = true,
    showNav: Boolean = true,
    insets: WindowInsets? = null,
    style: NavBarStyle = NavBarStyle.OnImage,
) {
    val state = rememberTopAppBarState(initialStatus)
    Column(modifier.nestedScroll(state)) {
        Box(modifier = Modifier.padding(vertical = BpkSpacing.Base)) {
            Image(
                painter = painterResource(id = R.drawable.swimming),
                modifier = Modifier.matchParentSize(),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
            )
            BpkTopNavBar(
                state = state,
                title = stringResource(R.string.navigation_bar_title),
                insets = insets,
                navIcon = when {
                    showNav -> NavIcon.Back(contentDescription = stringResource(R.string.navigation_back)) {}
                    else -> NavIcon.None
                },
                actions = if (showActions) listOf(
                    IconAction(
                        icon = BpkIcon.Accessibility,
                        contentDescription = stringResource(R.string.navigation_accessibility),
                    ) {},
                    IconAction(icon = BpkIcon.Account, contentDescription = stringResource(R.string.navigation_account)) {},
                ) else emptyList(),
                style = style,
            )
        }
        NavBarSampleBody(showList)
    }
}

@Composable
@NavBarComponent
@ComposeStory("SurfaceContrast")
fun SurfaceContrastNavBarStory(
    modifier: Modifier = Modifier,
    initialStatus: TopNavBarStatus = TopNavBarStatus.Expanded,
    showList: Boolean = true,
    showActions: Boolean = true,
    showNav: Boolean = true,
    insets: WindowInsets? = null,
    style: NavBarStyle = NavBarStyle.SurfaceContrast,
) {
    val state = rememberTopAppBarState(initialStatus)
    Column(modifier.nestedScroll(state)) {
        Box(modifier = Modifier.padding(vertical = BpkSpacing.Base)) {
            Image(
                painter = painterResource(id = R.drawable.swimming),
                modifier = Modifier.matchParentSize(),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
            )
            BpkTopNavBar(
                state = state,
                title = stringResource(R.string.navigation_bar_title),
                insets = insets,
                navIcon = when {
                    showNav -> NavIcon.Back(contentDescription = stringResource(R.string.navigation_back)) {}
                    else -> NavIcon.None
                },
                actions = if (showActions) listOf(
                    IconAction(
                        icon = BpkIcon.Accessibility,
                        contentDescription = stringResource(R.string.navigation_accessibility),
                    ) {},
                    IconAction(icon = BpkIcon.Account, contentDescription = stringResource(R.string.navigation_account)) {},
                ) else emptyList(),
                style = style,
            )
        }
        NavBarSampleBody(showList)
    }
}

@Composable
private fun NavBarSampleBody(showList: Boolean) {
    if (showList) {
        LazyColumn {
            items(count = 2) {
                BpkCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = BpkDimension.Spacing.Lg),
                ) {
                    Column {
                        BpkText(text = stringResource(R.string.generic_scroll_the_list))
                    }
                }
                BpkDivider(Modifier.alpha(0f))
            }
            items(100) {
                ListItem(title = stringResource(R.string.generic_scroll_the_list))
            }
        }
    }
}

@Composable
internal fun NoNavIconTopNavBar(modifier: Modifier = Modifier) {
    BpkTopNavBar(
        navIcon = NavIcon.None,
        title = stringResource(R.string.navigation_bar_title),
        modifier = modifier,
        insets = null,
    )
}

@Composable
internal fun BackTopNavBar(modifier: Modifier = Modifier) {
    BpkTopNavBar(
        title = stringResource(R.string.navigation_bar_title),
        navIcon = NavIcon.Back(contentDescription = stringResource(R.string.navigation_back)) {},
        modifier = modifier,
        insets = null,
    )
}

@Composable
internal fun CloseTopNavBar(modifier: Modifier = Modifier) {
    BpkTopNavBar(
        title = stringResource(R.string.navigation_bar_title),
        navIcon = NavIcon.Close(contentDescription = stringResource(R.string.navigation_close)) {},
        modifier = modifier,
        insets = null,
    )
}

@Composable
internal fun ActionsTopNavBar(modifier: Modifier = Modifier) {
    BpkTopNavBar(
        title = stringResource(R.string.navigation_bar_title),
        navIcon = NavIcon.Back(contentDescription = stringResource(R.string.navigation_back)) {},
        actions = listOf(
            IconAction(icon = BpkIcon.AccountIdCard, contentDescription = stringResource(R.string.navigation_id_card)) {},
            IconAction(icon = BpkIcon.Accessibility, contentDescription = stringResource(R.string.navigation_accessibility)) {},
            IconAction(icon = BpkIcon.Account, contentDescription = stringResource(R.string.navigation_account)) {},
        ),
        modifier = modifier,
        insets = null,
    )
}

@Composable
internal fun TextActionTopNavBar(modifier: Modifier = Modifier) {
    BpkTopNavBar(
        navIcon = NavIcon.None,
        title = stringResource(R.string.navigation_bar_title),
        action = TextAction(text = stringResource(R.string.navigation_text_action)) {},
        modifier = modifier,
        insets = null,
    )
}
