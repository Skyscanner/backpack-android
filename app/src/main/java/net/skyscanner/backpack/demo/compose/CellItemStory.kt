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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryChevron
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryLogo
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessorySwitch
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryText
import net.skyscanner.backpack.compose.cellitem.BpkCellGroup
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.Account
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.CellItemComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@CellItemComponent
@ComposeStory
fun CellItemStory(modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base)) {

        Column {
            BpkText(
                text = stringResource(R.string.generic_default),
                modifier = Modifier.padding(BpkSpacing.Base),
            )
            CellItemSample()
        }

        Column {
            BpkText(
                text = "With Chevron Accessory",
                modifier = Modifier.padding(BpkSpacing.Base),
            )
            CellItemWithChevron()
        }

        Column {
            BpkText(
                text = "With Switch Accessory",
                modifier = Modifier.padding(BpkSpacing.Base),
            )
            CellItemWithSwitch()
        }

        Column {
            BpkText(
                text = "With Text Accessory",
                modifier = Modifier.padding(BpkSpacing.Base),
            )
            CellItemWithText()
        }

        Column {
            BpkText(
                text = "With Logo Accessory",
                modifier = Modifier.padding(BpkSpacing.Base),
            )
            CellItemWithLogo()
        }

        Column {
            BpkText(
                text = "Cell Group",
                modifier = Modifier.padding(BpkSpacing.Base),
            )
            CellGroup()
        }
    }
}

@Composable
internal fun CellItemSample(modifier: Modifier = Modifier) {
    BpkCellItem(
        icon = BpkIcon.Account,
        iconContentDescription = "Account",
        title = "Title",
        description = "Description",
        showDivider = true,
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
        showDivider = true,
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
        showDivider = true,
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
        showDivider = true,
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
        description = "Skyland Airlines",
        showDivider = true,
        onClick = {},
        modifier = modifier,
        accessory = {
            BpkCellAccessoryLogo(R.drawable.skyairlines)
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
                showDivider = true,
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
                showDivider = true,
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
