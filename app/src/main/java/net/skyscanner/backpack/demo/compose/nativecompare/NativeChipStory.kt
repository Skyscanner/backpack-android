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
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.chip.BpkChip
import net.skyscanner.backpack.compose.chip.BpkDismissibleChip
import net.skyscanner.backpack.compose.chip.BpkDropdownChip
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.ChevronDown
import net.skyscanner.backpack.compose.tokens.Close
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.common.R as BpkRes
import net.skyscanner.backpack.demo.components.NativeCompareComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.meta.StoryKind

/** BpkChip next to Material 3's FilterChip, AssistChip and InputChip, themed with Backpack's semantic tokens only. */
@Composable
@NativeCompareComponent
@ComposeStory("Chip", kind = StoryKind.DemoOnly)
fun NativeChipStory(modifier: Modifier = Modifier) {
    ComparisonStory(modifier = modifier) {
        ChipComparison.entries.forEach { pair ->
            NativeChipComparison(pair)
        }
    }
}

@Composable
internal fun NativeChipComparison(pair: ChipComparison, modifier: Modifier = Modifier) {
    ComparisonPair(
        label = pair.label,
        difference = pair.difference,
        rows = pair.selectedStates.map { selected ->
            ComparisonRow(
                backpack = { pair.backpack(selected) },
                native = { pair.native(selected) },
            )
        },
        backpack = pair.bpkSwatches,
        native = pair.nativeSwatches,
        modifier = modifier,
    )
}

internal enum class ChipComparison(
    @StringRes val label: Int,
    @StringRes val difference: Int,
    val selectedStates: List<Boolean>,
    val bpkSwatches: List<Swatch>,
    val nativeSwatches: List<Swatch>,
    val backpack: @Composable (selected: Boolean) -> Unit,
    val native: @Composable (selected: Boolean) -> Unit,
) {
    Selectable(
        label = R.string.native_compare_chip_selectable,
        difference = R.string.native_compare_chip_selectable_difference,
        selectedStates = listOf(false, true),
        bpkSwatches = listOf(
            Swatch.none(R.string.native_compare_state_fill),
            Swatch.token(R.string.native_compare_state_border, R.string.native_compare_token_line) { BpkTheme.colors.line },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_selected, R.string.native_compare_token_core_primary) { BpkTheme.colors.corePrimary },
            Swatch.token(R.string.native_compare_state_selected_text, R.string.native_compare_token_text_on_dark) { BpkTheme.colors.textOnDark },
            Swatch.token(R.string.native_compare_state_pressed, R.string.native_compare_token_core_primary_border) { BpkTheme.colors.corePrimary },
            Swatch.componentOnly(
                R.string.native_compare_state_disabled,
                BpkRes.color.__privateChipDisabledBackground,
                R.string.native_compare_chip_only,
                note = R.string.native_compare_note_equals_surface_highlight,
            ),
        ),
        nativeSwatches = listOf(
            Swatch.none(R.string.native_compare_state_fill),
            Swatch.token(R.string.native_compare_state_border, R.string.native_compare_token_line) { BpkTheme.colors.line },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_secondary) { BpkTheme.colors.textSecondary },
            Swatch.token(R.string.native_compare_state_selected, R.string.native_compare_token_surface_highlight) { BpkTheme.colors.surfaceHighlight },
            Swatch.token(R.string.native_compare_state_selected_text, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.platform(R.string.native_compare_state_pressed),
            Swatch.platform(R.string.native_compare_state_disabled),
        ),
        backpack = { selected ->
            BpkChip(text = stringResource(R.string.native_compare_chip_label), selected = selected, onSelectedChange = {})
        },
        native = { selected ->
            FilterChip(
                selected = selected,
                onClick = {},
                label = { Text(stringResource(R.string.native_compare_chip_label)) },
                shape = MaterialTheme.shapes.small,
            )
        },
    ),
    Dropdown(
        label = R.string.native_compare_chip_dropdown,
        difference = R.string.native_compare_chip_dropdown_difference,
        selectedStates = listOf(false),
        bpkSwatches = listOf(
            Swatch.none(R.string.native_compare_state_fill),
            Swatch.token(R.string.native_compare_state_border, R.string.native_compare_token_line) { BpkTheme.colors.line },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_icon, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_pressed, R.string.native_compare_token_core_primary_border) { BpkTheme.colors.corePrimary },
        ),
        nativeSwatches = listOf(
            Swatch.none(R.string.native_compare_state_fill),
            Swatch.token(R.string.native_compare_state_border, R.string.native_compare_token_line) { BpkTheme.colors.line },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_icon, R.string.native_compare_token_core_primary) { BpkTheme.colors.corePrimary },
            Swatch.platform(R.string.native_compare_state_pressed),
        ),
        backpack = { _ ->
            BpkDropdownChip(text = stringResource(R.string.native_compare_chip_label), onSelectedChange = {})
        },
        native = { _ ->
            AssistChip(
                onClick = {},
                label = { Text(stringResource(R.string.native_compare_chip_label)) },
                trailingIcon = { MaterialIcon(BpkIcon.ChevronDown) },
                shape = MaterialTheme.shapes.small,
            )
        },
    ),
    Dismissible(
        label = R.string.native_compare_chip_dismissible,
        difference = R.string.native_compare_chip_dismissible_difference,
        selectedStates = listOf(false),
        bpkSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_core_primary) { BpkTheme.colors.corePrimary },
            Swatch.none(R.string.native_compare_state_border),
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_on_dark) { BpkTheme.colors.textOnDark },
            Swatch.token(R.string.native_compare_state_icon, R.string.native_compare_token_text_disabled_on_dark) { BpkTheme.colors.textDisabledOnDark },
        ),
        nativeSwatches = listOf(
            Swatch.none(R.string.native_compare_state_fill),
            Swatch.token(R.string.native_compare_state_border, R.string.native_compare_token_line) { BpkTheme.colors.line },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_secondary) { BpkTheme.colors.textSecondary },
            Swatch.token(R.string.native_compare_state_icon, R.string.native_compare_token_text_secondary) { BpkTheme.colors.textSecondary },
        ),
        backpack = { _ ->
            BpkDismissibleChip(text = stringResource(R.string.native_compare_chip_label), onClick = {})
        },
        native = { _ ->
            InputChip(
                selected = false,
                onClick = {},
                label = { Text(stringResource(R.string.native_compare_chip_label)) },
                trailingIcon = { MaterialIcon(BpkIcon.Close) },
                shape = MaterialTheme.shapes.small,
            )
        },
    ),
}
