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

package net.skyscanner.backpack.demo.compose.nativecompare

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellItemSlot
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Account
import net.skyscanner.backpack.compose.tokens.ChevronRight
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.NativeCompareComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.meta.StoryKind

/** BpkCellItem next to Material 3's ListItem, themed with Backpack's semantic tokens only. */
@Composable
@NativeCompareComponent
@ComposeStory("Cell item", kind = StoryKind.DemoOnly)
fun NativeCellItemStory(modifier: Modifier = Modifier) {
    ComparisonStory(modifier = modifier) {
        CellItemComparison.entries.forEach { pair ->
            NativeCellItemComparison(pair)
        }
    }
}

@Composable
internal fun NativeCellItemComparison(pair: CellItemComparison, modifier: Modifier = Modifier) {
    ComparisonPair(
        label = pair.label,
        difference = pair.difference,
        rows = listOf(
            ComparisonRow(
                backpack = {
                    BpkCellItem(
                        title = stringResource(R.string.native_compare_cell_title),
                        body = stringResource(R.string.native_compare_cell_body),
                        icon = BpkIcon.Account,
                        onClick = {},
                        slot = if (pair.switch) BpkCellItemSlot.Switch(checked = true, onCheckedChange = {}) else BpkCellItemSlot.Chevron,
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                native = {
                    ListItem(
                        headlineContent = {
                            Text(stringResource(R.string.native_compare_cell_title), style = MaterialTheme.typography.titleMedium)
                        },
                        supportingContent = {
                            Text(stringResource(R.string.native_compare_cell_body), style = MaterialTheme.typography.bodySmall)
                        },
                        leadingContent = { MaterialIcon(BpkIcon.Account) },
                        trailingContent = {
                            if (pair.switch) Switch(checked = true, onCheckedChange = {}) else MaterialIcon(BpkIcon.ChevronRight)
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
            ),
        ),
        backpack = pair.bpkSwatches,
        native = pair.nativeSwatches,
        modifier = modifier,
    )
}

internal enum class CellItemComparison(
    @StringRes val label: Int,
    @StringRes val difference: Int,
    val switch: Boolean,
    val bpkSwatches: List<Swatch>,
    val nativeSwatches: List<Swatch>,
) {
    Chevron(
        label = R.string.native_compare_cell_chevron,
        difference = R.string.native_compare_cell_chevron_difference,
        switch = false,
        bpkSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_surface_default) { BpkTheme.colors.surfaceDefault },
            Swatch.token(R.string.native_compare_state_title, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_body, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_icon, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_trailing, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
        ),
        nativeSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_surface_default) { BpkTheme.colors.surfaceDefault },
            Swatch.token(R.string.native_compare_state_title, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_body, R.string.native_compare_token_text_secondary) { BpkTheme.colors.textSecondary },
            Swatch.token(R.string.native_compare_state_icon, R.string.native_compare_token_text_secondary) { BpkTheme.colors.textSecondary },
            Swatch.token(R.string.native_compare_state_trailing, R.string.native_compare_token_text_secondary) { BpkTheme.colors.textSecondary },
        ),
    ),
    WithSwitch(
        label = R.string.native_compare_cell_switch,
        difference = R.string.native_compare_cell_switch_difference,
        switch = true,
        bpkSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_surface_default) { BpkTheme.colors.surfaceDefault },
            Swatch.token(R.string.native_compare_state_title, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_body, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_switch_on, R.string.native_compare_token_core_accent) { BpkTheme.colors.coreAccent },
        ),
        nativeSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_surface_default) { BpkTheme.colors.surfaceDefault },
            Swatch.token(R.string.native_compare_state_title, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_body, R.string.native_compare_token_text_secondary) { BpkTheme.colors.textSecondary },
            Swatch.token(R.string.native_compare_state_switch_on, R.string.native_compare_token_core_primary) { BpkTheme.colors.corePrimary },
        ),
    ),
}
