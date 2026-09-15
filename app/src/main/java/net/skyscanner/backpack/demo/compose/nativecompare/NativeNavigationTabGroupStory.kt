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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.navigationtabgroup.BpkNavigationTabGroup
import net.skyscanner.backpack.compose.navigationtabgroup.BpkNavigationTabGroupStyle
import net.skyscanner.backpack.compose.navigationtabgroup.BpkNavigationTabItem
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.common.R as BpkRes
import net.skyscanner.backpack.demo.components.NativeCompareComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.meta.StoryKind

/** BpkNavigationTabGroup next to Material 3's PrimaryTabRow, themed with Backpack's semantic tokens only. */
@Composable
@NativeCompareComponent
@ComposeStory("Navigation tab group", kind = StoryKind.DemoOnly)
fun NativeNavigationTabGroupStory(modifier: Modifier = Modifier) {
    ComparisonStory(modifier = modifier) {
        NavigationTabGroupComparison.entries.forEach { pair ->
            NativeNavigationTabGroupComparison(pair)
        }
    }
}

@Composable
internal fun NativeNavigationTabGroupComparison(pair: NavigationTabGroupComparison, modifier: Modifier = Modifier) {
    ComparisonPair(
        label = pair.label,
        difference = pair.difference,
        rows = listOf(
            ComparisonRow(
                backpack = {
                    ContrastBackground(contrast = pair.contrast) {
                        BpkNavigationTabGroup(
                            tabs = tabLabels().map { BpkNavigationTabItem(text = it) },
                            selectedIndex = 0,
                            onItemClicked = {},
                            style = pair.style,
                        )
                    }
                },
                native = {
                    ContrastBackground(contrast = pair.contrast) {
                        MaterialTabRow(contrast = pair.contrast)
                    }
                },
            ),
        ),
        backpack = pair.bpkSwatches,
        native = pair.nativeSwatches,
        modifier = modifier,
    )
}

@Composable
private fun tabLabels(): List<String> = listOf(
    stringResource(R.string.native_compare_tab_first),
    stringResource(R.string.native_compare_tab_second),
    stringResource(R.string.native_compare_tab_third),
)

/**
 * Stock tab row. Selected and unselected colours are the Material 3 spec roles (primary and
 * onSurfaceVariant); on a contrast surface everything takes the inverse content colour.
 */
@Composable
private fun MaterialTabRow(contrast: Boolean) {
    val scheme = MaterialTheme.colorScheme
    PrimaryTabRow(
        selectedTabIndex = 0,
        containerColor = if (contrast) scheme.inverseSurface else scheme.surface,
        contentColor = if (contrast) scheme.inverseOnSurface else scheme.onSurface,
    ) {
        tabLabels().forEachIndexed { index, label ->
            Tab(
                selected = index == 0,
                onClick = {},
                text = { Text(label, style = MaterialTheme.typography.labelMedium) },
                selectedContentColor = if (contrast) scheme.inverseOnSurface else scheme.primary,
                unselectedContentColor = if (contrast) scheme.inverseOnSurface else scheme.onSurfaceVariant,
            )
        }
    }
}

internal enum class NavigationTabGroupComparison(
    @StringRes val label: Int,
    @StringRes val difference: Int,
    val style: BpkNavigationTabGroupStyle,
    val contrast: Boolean,
    val bpkSwatches: List<Swatch>,
    val nativeSwatches: List<Swatch>,
) {
    CanvasDefault(
        label = R.string.native_compare_canvas_default,
        difference = R.string.native_compare_tab_group_canvas_default_difference,
        style = BpkNavigationTabGroupStyle.CanvasDefault,
        contrast = false,
        bpkSwatches = listOf(
            Swatch.none(R.string.native_compare_state_fill),
            Swatch.token(R.string.native_compare_state_selected, R.string.native_compare_token_core_accent) { BpkTheme.colors.coreAccent },
            Swatch.token(R.string.native_compare_state_selected_text, R.string.native_compare_token_text_primary_inverse) { BpkTheme.colors.textPrimaryInverse },
            Swatch.token(R.string.native_compare_state_border, R.string.native_compare_token_line) { BpkTheme.colors.line },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_pressed, R.string.native_compare_token_text_primary_border) { BpkTheme.colors.textPrimary },
        ),
        nativeSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_surface_default) { BpkTheme.colors.surfaceDefault },
            Swatch.token(R.string.native_compare_state_selected, R.string.native_compare_token_core_primary_indicator) { BpkTheme.colors.corePrimary },
            Swatch.token(R.string.native_compare_state_selected_text, R.string.native_compare_token_core_primary) { BpkTheme.colors.corePrimary },
            Swatch.none(R.string.native_compare_state_border),
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_secondary) { BpkTheme.colors.textSecondary },
            Swatch.platform(R.string.native_compare_state_pressed),
        ),
    ),
    SurfaceContrast(
        label = R.string.native_compare_surface_contrast,
        difference = R.string.native_compare_tab_group_surface_contrast_difference,
        style = BpkNavigationTabGroupStyle.SurfaceContrast,
        contrast = true,
        bpkSwatches = listOf(
            Swatch.none(R.string.native_compare_state_fill),
            Swatch.token(R.string.native_compare_state_selected, R.string.native_compare_token_core_accent) { BpkTheme.colors.coreAccent },
            Swatch.token(R.string.native_compare_state_selected_text, R.string.native_compare_token_text_primary_inverse) { BpkTheme.colors.textPrimaryInverse },
            Swatch.componentOnly(R.string.native_compare_state_border, BpkRes.color.__privateNavigationTabOnDarkOutline, R.string.native_compare_tab_only),
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_on_dark) { BpkTheme.colors.textOnDark },
            Swatch.componentOnly(R.string.native_compare_state_pressed, BpkRes.color.__privateNavigationTabHover, R.string.native_compare_tab_only),
        ),
        nativeSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_surface_contrast) { BpkTheme.colors.surfaceContrast },
            Swatch.token(R.string.native_compare_state_selected, R.string.native_compare_token_core_primary_indicator) { BpkTheme.colors.corePrimary },
            Swatch.token(R.string.native_compare_state_selected_text, R.string.native_compare_token_text_on_dark) { BpkTheme.colors.textOnDark },
            Swatch.none(R.string.native_compare_state_border),
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_on_dark) { BpkTheme.colors.textOnDark },
            Swatch.platform(R.string.native_compare_state_pressed),
        ),
    ),
}
