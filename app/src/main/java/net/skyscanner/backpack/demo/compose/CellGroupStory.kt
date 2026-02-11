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
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryChevron
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessorySwitch
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryText
import net.skyscanner.backpack.compose.cellitem.BpkCellGroup
import net.skyscanner.backpack.compose.cellitem.BpkCellGroupItem
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Account
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.components.CellGroupComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@CellGroupComponent
@ComposeStory
fun CellGroupDefaultStory(modifier: Modifier = Modifier) {
    var notificationsEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .background(BpkTheme.colors.line)
            .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Xxl),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        BpkCellGroup(
            items = listOf(
                BpkCellGroupItem(
                    title = "Profile Settings",
                    body = "Manage your account",
                    icon = {
                        BpkIcon(
                            icon = BpkIcon.Account,
                            contentDescription = "Account",
                            size = BpkIconSize.Large,
                        )
                    },
                    slot = {
                        BpkCellAccessoryChevron()
                    },
                    onClick = {},
                ),
                BpkCellGroupItem(
                    title = "Notifications",
                    body = "Enable push notifications",
                    icon = {
                        BpkIcon(
                            icon = BpkIcon.Account,
                            contentDescription = "Account",
                            size = BpkIconSize.Large,
                        )
                    },
                    slot = {
                        BpkCellAccessorySwitch(
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it },
                        )
                    },
                ),
                BpkCellGroupItem(
                    title = "Language",
                    body = "App display language",
                    icon = {
                        BpkIcon(
                            icon = BpkIcon.Account,
                            contentDescription = "Account",
                            size = BpkIconSize.Large,
                        )
                    },
                    slot = {
                        BpkCellAccessoryText("English")
                    },
                    onClick = {},
                ),
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
