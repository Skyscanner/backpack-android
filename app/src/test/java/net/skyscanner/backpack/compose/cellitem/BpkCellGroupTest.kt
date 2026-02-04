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

package net.skyscanner.backpack.compose.cellitem

import androidx.compose.foundation.layout.Column
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Account
import net.skyscanner.backpack.compose.tokens.Settings
import org.junit.Test

class BpkCellGroupTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        BpkCellGroup {
            Column {
                BpkCellItem(
                    icon = BpkIcon.Account,
                    iconContentDescription = "Account",
                    title = "Profile Settings",
                    showDivider = true,
                    onClick = {},
                    accessory = {
                        BpkCellAccessoryChevron()
                    },
                )
                BpkCellItem(
                    icon = BpkIcon.Settings,
                    iconContentDescription = "Settings",
                    title = "App Settings",
                    onClick = {},
                    accessory = {
                        BpkCellAccessoryChevron()
                    },
                )
            }
        }
    }

    @Test
    fun withMultipleItems() = snap {
        BpkCellGroup {
            Column {
                BpkCellItem(
                    icon = BpkIcon.Account,
                    iconContentDescription = "Account",
                    title = "Profile",
                    description = "View your profile",
                    showDivider = true,
                    onClick = {},
                    accessory = {
                        BpkCellAccessoryChevron()
                    },
                )
                BpkCellItem(
                    icon = BpkIcon.Settings,
                    iconContentDescription = "Settings",
                    title = "Settings",
                    description = "App preferences",
                    showDivider = true,
                    onClick = {},
                    accessory = {
                        BpkCellAccessoryChevron()
                    },
                )
                BpkCellItem(
                    icon = BpkIcon.Account,
                    iconContentDescription = "Language",
                    title = "Language",
                    description = "English",
                    onClick = {},
                    accessory = {
                        BpkCellAccessoryText("EN")
                    },
                )
            }
        }
    }

    @Test
    fun withMixedAccessories() = snap {
        BpkCellGroup {
            Column {
                BpkCellItem(
                    title = "Notifications",
                    showDivider = true,
                    accessory = {
                        BpkCellAccessorySwitch(
                            checked = true,
                            onCheckedChange = {},
                        )
                    },
                )
                BpkCellItem(
                    title = "Language",
                    showDivider = true,
                    onClick = {},
                    accessory = {
                        BpkCellAccessoryText("English")
                    },
                )
                BpkCellItem(
                    title = "About",
                    onClick = {},
                    accessory = {
                        BpkCellAccessoryChevron()
                    },
                )
            }
        }
    }
}
