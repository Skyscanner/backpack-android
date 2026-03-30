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

package net.skyscanner.backpack.compose.cellitem.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import net.skyscanner.backpack.compose.cellitem.BpkCellItemCorner
import net.skyscanner.backpack.compose.cellitem.BpkCellItemData
import net.skyscanner.backpack.compose.cellitem.BpkCellItemStyle
import net.skyscanner.backpack.compose.divider.BpkDivider
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BpkCellGroupImpl(
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
            BpkCellItemImpl(
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
