/*
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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellItemCorner
import net.skyscanner.backpack.compose.cellitem.BpkCellItemSlot
import net.skyscanner.backpack.compose.cellitem.BpkCellItemStyle
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Account
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.CellItemComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@CellItemComponent
@ComposeStory
fun CellItemDefaultStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(BpkTheme.colors.line)
            .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Xxl),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        BpkCellItem(
            icon = BpkIcon.Account,
            title = "Title",
            body = "Description",
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
@CellItemComponent
@ComposeStory("With Chevron")
fun CellItemWithChevronStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(BpkTheme.colors.line)
            .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Xxl),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        BpkCellItem(
            icon = BpkIcon.Account,
            title = "Profile Settings",
            body = "Manage your account",
            onClick = {},
            slot = BpkCellItemSlot.Chevron,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
@CellItemComponent
@ComposeStory("With Switch")
fun CellItemWithSwitchStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(BpkTheme.colors.line)
            .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Xxl),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        var checked by remember { mutableStateOf(true) }
        BpkCellItem(
            icon = BpkIcon.Account,
            title = "Notifications",
            body = "Enable push notifications",
            slot = BpkCellItemSlot.Switch(
                checked = checked,
                onCheckedChange = { checked = it },
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
@CellItemComponent
@ComposeStory("With Text")
fun CellItemWithTextStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(BpkTheme.colors.line)
            .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Xxl),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        BpkCellItem(
            icon = BpkIcon.Account,
            title = "Language",
            body = "App display language",
            onClick = {},
            slot = BpkCellItemSlot.Text("English"),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
@CellItemComponent
@ComposeStory("With Link")
fun CellItemWithLinkStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(BpkTheme.colors.line)
            .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Xxl),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        BpkCellItem(
            icon = BpkIcon.Account,
            title = "Privacy Policy",
            body = "Read our privacy policy",
            slot = BpkCellItemSlot.Link(
                text = "Link",
                url = "https://example.com/privacy",
                onClick = {},
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
@CellItemComponent
@ComposeStory("With Image")
fun CellItemWithImageStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(BpkTheme.colors.line)
            .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Xxl),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        BpkCellItem(
            icon = BpkIcon.Account,
            title = "Partner Program",
            body = "Skyland Airlines",
            onClick = {},
            slot = BpkCellItemSlot.Image(R.drawable.sample_icon),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
@CellItemComponent
@ComposeStory("Surface Low Contrast")
fun CellItemSurfaceLowContrastStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(BpkTheme.colors.line)
            .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Xxl),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        BpkCellItem(
            icon = BpkIcon.Account,
            title = "Title",
            body = "Description",
            style = BpkCellItemStyle.SurfaceLowContrast,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
@CellItemComponent
@ComposeStory("Rounded Corner")
fun CellItemRoundedCornerStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(BpkTheme.colors.line)
            .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Xxl),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        BpkCellItem(
            icon = BpkIcon.Account,
            title = "Profile Settings",
            body = "Manage your account",
            onClick = {},
            corner = BpkCellItemCorner.Rounded,
            slot = BpkCellItemSlot.Chevron,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
@CellItemComponent
@ComposeStory("Surface Low Contrast + Rounded")
fun CellItemSurfaceLowContrastAndRoundedStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(BpkTheme.colors.line)
            .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Xxl),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        var checked by remember { mutableStateOf(true) }
        BpkCellItem(
            icon = BpkIcon.Account,
            title = "Notifications",
            body = "Enable push notifications",
            style = BpkCellItemStyle.SurfaceLowContrast,
            corner = BpkCellItemCorner.Rounded,
            slot = BpkCellItemSlot.Switch(
                checked = checked,
                onCheckedChange = { checked = it },
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
