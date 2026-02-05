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

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.switch.BpkSwitch
import net.skyscanner.backpack.compose.tokens.Account
import net.skyscanner.backpack.demo.R
import org.junit.Test

class BpkCellItemTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        BpkCellItem(
            title = "Title",
        )
    }

    @Test
    fun withDescription() = snap {
        BpkCellItem(
            title = "Title",
            description = "Description",
        )
    }

    @Test
    fun withIconAndDescription() = snap {
        BpkCellItem(
            icon = BpkIcon.Account,
            iconContentDescription = "Account",
            title = "Title",
            description = "Description",
        )
    }

    @Test
    fun withAccessoryAndDivider() = snap {
        BpkCellItem(
            icon = BpkIcon.Account,
            iconContentDescription = "Account",
            title = "Title",
            description = "Description",
            showDivider = true,
            accessory = {
                BpkSwitch(
                    checked = true,
                    onCheckedChange = {},
                    content = {},
                )
            },
        )
    }

    @Test
    fun clickable() = snap {
        BpkCellItem(
            title = "Title",
            onClick = {},
        )
    }

    @Test
    fun withChevronAccessory() = snap {
        BpkCellItem(
            icon = BpkIcon.Account,
            iconContentDescription = "Account",
            title = "Title",
            description = "Description",
            onClick = {},
            accessory = {
                BpkCellAccessoryChevron()
            },
        )
    }

    @Test
    fun withSwitchAccessory() = snap {
        BpkCellItem(
            icon = BpkIcon.Account,
            iconContentDescription = "Account",
            title = "Title",
            description = "Description",
            accessory = {
                BpkCellAccessorySwitch(
                    checked = true,
                    onCheckedChange = {},
                )
            },
        )
    }

    @Test
    fun withTextAccessory() = snap {
        BpkCellItem(
            icon = BpkIcon.Account,
            iconContentDescription = "Account",
            title = "Title",
            description = "Description",
            accessory = {
                BpkCellAccessoryText("Value")
            },
        )
    }

    @Test
    fun withLogoAccessory() = snap {
        BpkCellItem(
            icon = BpkIcon.Account,
            iconContentDescription = "Account",
            title = "Partner Program",
            description = "Skyland Airlines",
            accessory = {
                BpkCellAccessoryLogo(R.drawable.skyairlines)
            },
        )
    }
}
