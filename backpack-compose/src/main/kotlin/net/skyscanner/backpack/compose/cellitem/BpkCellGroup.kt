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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import net.skyscanner.backpack.compose.annotation.BpkPreviews
import net.skyscanner.backpack.compose.divider.BpkDivider
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Account
import net.skyscanner.backpack.compose.tokens.BpkSpacing

/**
 * CompositionLocal to indicate whether a BpkCellItem is inside a BpkCellGroup.
 * When true, the item should not apply its own background as the group handles it.
 */
internal val LocalCellGroupContext = compositionLocalOf { false }

/**
 * Data class representing a cell item within a [BpkCellGroup].
 *
 * @param title The title text for the cell.
 * @param body Optional body/description text for the cell.
 * @param icon Optional composable for the leading icon.
 * @param slot Optional composable for the trailing accessory (e.g., chevron, switch, badge).
 * @param onClick Optional click handler for the cell. If null, the cell is not clickable.
 */
data class BpkCellGroupItem(
    val title: String,
    val body: String? = null,
    val icon: (@Composable () -> Unit)? = null,
    val slot: (@Composable () -> Unit)? = null,
    val onClick: (() -> Unit)? = null,
)

/**
 * A container component that groups multiple cell items together with automatic dividers.
 * The group always uses rounded corners and default surface styling as per design specifications.
 *
 * @param items The list of [BpkCellGroupItem]s to display in the group.
 * @param modifier Optional modifier for the group container.
 */
@Composable
fun BpkCellGroup(
    items: List<BpkCellGroupItem>,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(BpkSpacing.Md)
    val backgroundColor = BpkTheme.colors.surfaceDefault

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(backgroundColor),
    ) {
        CompositionLocalProvider(LocalCellGroupContext provides true) {
            items.forEachIndexed { index, item ->
                BpkCellItem(
                    title = item.title,
                    body = item.body,
                    icon = item.icon,
                    slot = item.slot,
                    onClick = item.onClick,
                )
                if (index < items.lastIndex) {
                    BpkDivider()
                }
            }
        }
    }
}

@BpkPreviews
@Composable
private fun BpkCellGroupPreview() {
    BpkCellGroup(
        items = listOf(
            BpkCellGroupItem(
                title = "Title",
                body = "Description",
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
                title = "Title",
                body = "Description",
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
                title = "Title",
                body = "Description",
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
        ),
    )
}
