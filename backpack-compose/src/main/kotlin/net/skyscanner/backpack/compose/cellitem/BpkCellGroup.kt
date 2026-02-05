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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.annotation.BpkPreviews
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.card.BpkCardPadding
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Account
import net.skyscanner.backpack.compose.tokens.Settings

/**
 * Cell group container for [BpkCellItem] components.
 * Groups multiple cell items together in a card with no padding.
 * Typically used to create lists of related settings or navigation options.
 *
 * @param modifier Optional modifier for the card container.
 * @param content The cell items to display within the group.
 */
@Composable
fun BpkCellGroup(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BpkCard(
        modifier = modifier.fillMaxWidth(),
        padding = BpkCardPadding.None,
    ) {
        content()
    }
}

@BpkPreviews
@Composable
private fun BpkCellGroupPreview() {
    BpkCellGroup {
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
            BpkCellItem(
                icon = BpkIcon.Settings,
                iconContentDescription = "Settings",
                title = "App Settings",
                description = "Configure preferences",
                style = BpkCellItemStyle.Divider,
                onClick = {},
                accessory = {
                    BpkCellAccessoryChevron()
                },
            )
        }
    }
}
