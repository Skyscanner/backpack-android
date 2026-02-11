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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import net.skyscanner.backpack.compose.annotation.BpkPreviews
import net.skyscanner.backpack.compose.divider.BpkDivider
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

/**
 * A container component that groups multiple composable items together with automatic dividers.
 * The group always uses rounded corners and default surface styling as per design specifications.
 *
 * @param modifier Optional modifier for the group container.
 * @param content The composable content to display within the group. Use [item] to add items.
 */
@Composable
fun BpkCellGroup(
    modifier: Modifier = Modifier,
    content: BpkCellGroupScope.() -> Unit,
) {
    val shape = RoundedCornerShape(BpkSpacing.Md)
    val backgroundColor = BpkTheme.colors.surfaceDefault

    val scope = BpkCellGroupScopeImpl()
    scope.content()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(backgroundColor),
    ) {
        scope.items.forEachIndexed { index, item ->
            item()
            if (index < scope.items.lastIndex) {
                BpkDivider()
            }
        }
    }
}

/**
 * Scope for adding items to a [BpkCellGroup].
 */
interface BpkCellGroupScope {
    /**
     * Adds an item to the group.
     *
     * @param content The composable content for this item.
     */
    fun item(content: @Composable () -> Unit)
}

private class BpkCellGroupScopeImpl : BpkCellGroupScope {
    val items = mutableListOf<@Composable () -> Unit>()

    override fun item(content: @Composable () -> Unit) {
        items.add(content)
    }
}

@BpkPreviews
@Composable
private fun BpkCellGroupPreview() {
    BpkCellGroup {
        item {
            BpkText(
                text = "Example Item 1",
                style = BpkTheme.typography.label1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(BpkSpacing.Base),
            )
        }
        item {
            BpkText(
                text = "Example Item 2",
                style = BpkTheme.typography.label1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(BpkSpacing.Base),
            )
        }
        item {
            BpkText(
                text = "Example Item 3",
                style = BpkTheme.typography.label1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(BpkSpacing.Base),
            )
        }
    }
}
