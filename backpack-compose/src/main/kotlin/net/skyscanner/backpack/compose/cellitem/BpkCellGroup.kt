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

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import net.skyscanner.backpack.compose.annotation.BpkPreviews
import net.skyscanner.backpack.compose.divider.BpkDivider
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Account
import net.skyscanner.backpack.compose.tokens.BpkSpacing

/**
 * Data class representing a cell item in a [BpkCellGroup].
 * This class holds all the parameters needed to construct a [BpkCellItem].
 *
 * @param title The primary text to display in the cell item.
 * @param body Optional secondary text to display below the title.
 * @param icon Optional icon to display at the leading edge of the cell item.
 * @param onClick Optional click handler for the cell item.
 * @param slot Optional accessory content to display at the trailing edge of the cell item.
 */
data class BpkCellItemData(
    val title: String,
    val body: String? = null,
    val icon: BpkIcon? = null,
    val onClick: (() -> Unit)? = null,
    val slot: BpkCellItemSlot? = null,
)

/**
 * A container component that groups multiple [BpkCellItem]s together with automatic dividers.
 * The group always uses rounded corners and default surface styling as per design specifications.
 *
 * @param items List of [BpkCellItemData] to display in the group.
 * @param modifier Optional modifier for the group container.
 */
@Composable
fun BpkCellGroup(
    items: List<BpkCellItemData>,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(BpkSpacing.Md)
    val backgroundColor = BpkTheme.colors.surfaceDefault

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(backgroundColor),
    ) {
        itemsIndexed(items) { index, itemData ->
            BpkCellItem(
                title = itemData.title,
                body = itemData.body,
                icon = itemData.icon,
                onClick = itemData.onClick,
                slot = itemData.slot,
                style = BpkCellItemStyle.SurfaceDefault,
                corner = BpkCellItemCorner.Default,
            )
            if (index < items.lastIndex) {
                BpkDivider()
            }
        }
    }
}

@BpkPreviews
@Composable
private fun BpkCellGroupPreview() {
    BpkCellGroup(
        items = listOf(
            BpkCellItemData(
                title = "Profile Settings",
                body = "Manage your account",
                icon = BpkIcon.Account,
            ),
            BpkCellItemData(
                title = "Notifications",
                body = "Enable push notifications",
            ),
            BpkCellItemData(
                title = "Language",
                body = "App display language",
            ),
        ),
    )
}
