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

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryChevron
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryLogo
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessorySwitch
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryText
import net.skyscanner.backpack.compose.cellitem.BpkCellGroup
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellItemStyle
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Account
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.CellItemComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@CellItemComponent
@ComposeStory
fun CellItemDefaultStory(modifier: Modifier = Modifier) {
    CellItemSample(modifier)
}

@Composable
@CellItemComponent
@ComposeStory("With Chevron Accessory")
fun CellItemWithChevronStory(modifier: Modifier = Modifier) {
    CellItemWithChevron(modifier)
}

@Composable
@CellItemComponent
@ComposeStory("With Switch Accessory")
fun CellItemWithSwitchStory(modifier: Modifier = Modifier) {
    CellItemWithSwitch(modifier)
}

@Composable
@CellItemComponent
@ComposeStory("With Text Accessory")
fun CellItemWithTextStory(modifier: Modifier = Modifier) {
    CellItemWithText(modifier)
}

@Composable
@CellItemComponent
@ComposeStory("With Logo Accessory")
fun CellItemWithLogoStory(modifier: Modifier = Modifier) {
    CellItemWithLogo(modifier)
}

@Composable
@CellItemComponent
@ComposeStory("With Padded Style")
fun CellItemWithPaddedStyleStory(modifier: Modifier = Modifier) {
    CellItemWithPaddedStyle(modifier)
}

@Composable
@CellItemComponent
@ComposeStory("Cell Group")
fun CellItemGroupStory(modifier: Modifier = Modifier) {
    CellGroup(modifier)
}

@Composable
internal fun CellItemSample(modifier: Modifier = Modifier) {
    BpkCellItem(
        icon = BpkIcon.Account,
        iconContentDescription = "Account",
        title = "Title",
        description = "Description",
        style = BpkCellItemStyle.Divider,
        onClick = {},
        modifier = modifier,
    )
}

@Composable
internal fun CellItemWithChevron(modifier: Modifier = Modifier) {
    BpkCellItem(
        icon = BpkIcon.Account,
        iconContentDescription = "Account",
        title = "Profile Settings",
        description = "Manage your account",
        style = BpkCellItemStyle.Divider,
        onClick = {},
        modifier = modifier,
        accessory = {
            BpkCellAccessoryChevron()
        },
    )
}

@Composable
internal fun CellItemWithSwitch(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(true) }
    BpkCellItem(
        icon = BpkIcon.Account,
        iconContentDescription = "Account",
        title = "Notifications",
        description = "Enable push notifications",
        style = BpkCellItemStyle.Divider,
        modifier = modifier,
        accessory = {
            BpkCellAccessorySwitch(
                checked = checked,
                onCheckedChange = { checked = it },
            )
        },
    )
}

@Composable
internal fun CellItemWithText(modifier: Modifier = Modifier) {
    BpkCellItem(
        icon = BpkIcon.Account,
        iconContentDescription = "Account",
        title = "Language",
        description = "App display language",
        style = BpkCellItemStyle.Divider,
        onClick = {},
        modifier = modifier,
        accessory = {
            BpkCellAccessoryText("English")
        },
    )
}

@Composable
internal fun CellItemWithLogo(modifier: Modifier = Modifier) {
    BpkCellItem(
        icon = BpkIcon.Account,
        iconContentDescription = "Account",
        title = "Partner Program",
        description = "Backpack Demo",
        style = BpkCellItemStyle.Divider,
        onClick = {},
        modifier = modifier,
        accessory = {
            BpkCellAccessoryLogo(R.drawable.ic_launcher_foreground)
        },
    )
}

@Composable
internal fun CellItemWithPaddedStyle(modifier: Modifier = Modifier) {
    BpkCellItem(
        icon = BpkIcon.Account,
        iconContentDescription = "Account",
        title = "Padded Cell",
        description = "No divider, just padding",
        style = BpkCellItemStyle.Padded,
        onClick = {},
        modifier = modifier,
        accessory = {
            BpkCellAccessoryChevron()
        },
    )
}

@Composable
internal fun CellGroup(modifier: Modifier = Modifier) {
    BpkCellGroup(modifier = modifier) {
        Column {
            BpkCellItem(
                icon = BpkIcon.Account,
                iconContentDescription = "Account",
                title = "Profile Settings",
                description = "Manage your account",
                style = BpkCellItemStyle.Divider,
                onClick = {},
                accessory = {
                    BpkCellAccessoryChevron()
                },
            )
            var notificationsEnabled by remember { mutableStateOf(true) }
            BpkCellItem(
                icon = BpkIcon.Account,
                iconContentDescription = "Notifications",
                title = "Notifications",
                description = "Push notifications",
                style = BpkCellItemStyle.Divider,
                accessory = {
                    BpkCellAccessorySwitch(
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it },
                    )
                },
            )
            BpkCellItem(
                icon = BpkIcon.Account,
                iconContentDescription = "Language",
                title = "Language",
                description = "App display language",
                onClick = {},
                accessory = {
                    BpkCellAccessoryText("English")
                },
            )
        }
    }
}
