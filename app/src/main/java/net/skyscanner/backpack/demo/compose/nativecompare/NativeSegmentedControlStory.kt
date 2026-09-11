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
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.segmentedcontrol.BpkSegmentedControl
import net.skyscanner.backpack.compose.segmentedcontrol.BpkSegmentedControlStyle
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.common.R as BpkRes
import net.skyscanner.backpack.demo.components.NativeCompareComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.meta.StoryKind

/** BpkSegmentedControl next to Material 3's SingleChoiceSegmentedButtonRow, themed with Backpack's semantic tokens only. */
@Composable
@NativeCompareComponent
@ComposeStory("Segmented control", kind = StoryKind.DemoOnly)
fun NativeSegmentedControlStory(modifier: Modifier = Modifier) {
    ComparisonStory(modifier = modifier) {
        SegmentedControlComparison.entries.forEach { pair ->
            NativeSegmentedControlComparison(pair)
        }
    }
}

@Composable
internal fun NativeSegmentedControlComparison(pair: SegmentedControlComparison, modifier: Modifier = Modifier) {
    ComparisonPair(
        label = pair.label,
        difference = pair.difference,
        rows = listOf(
            ComparisonRow(
                backpack = {
                    ContrastBackground(contrast = pair.contrast) {
                        BpkSegmentedControl(
                            buttonContents = segmentLabels(),
                            onItemClick = {},
                            selectedIndex = 0,
                            type = pair.style,
                        )
                    }
                },
                native = {
                    ContrastBackground(contrast = pair.contrast) {
                        MaterialSegmentedRow(contrast = pair.contrast)
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
private fun segmentLabels(): List<String> = listOf(
    stringResource(R.string.native_compare_segment_first),
    stringResource(R.string.native_compare_segment_second),
    stringResource(R.string.native_compare_segment_third),
)

@Composable
private fun MaterialSegmentedRow(contrast: Boolean) {
    val labels = segmentLabels()
    SingleChoiceSegmentedButtonRow {
        labels.forEachIndexed { index, label ->
            SegmentedButton(
                selected = index == 0,
                onClick = {},
                shape = SegmentedButtonDefaults.itemShape(index = index, count = labels.size, baseShape = MaterialTheme.shapes.small),
                colors = if (contrast) contrastSegmentColors() else SegmentedButtonDefaults.colors(),
                label = { Text(label, style = MaterialTheme.typography.labelMedium) },
            )
        }
    }
}

/** The stock colours moved onto a contrast surface: inverse surface roles for the track, primary for the active segment. */
@Composable
private fun contrastSegmentColors(): SegmentedButtonColors = SegmentedButtonDefaults.colors(
    activeContainerColor = MaterialTheme.colorScheme.primary,
    activeContentColor = MaterialTheme.colorScheme.onPrimary,
    inactiveContainerColor = MaterialTheme.colorScheme.inverseSurface,
    inactiveContentColor = MaterialTheme.colorScheme.inverseOnSurface,
)

internal enum class SegmentedControlComparison(
    @StringRes val label: Int,
    @StringRes val difference: Int,
    val style: BpkSegmentedControlStyle,
    val contrast: Boolean,
    val bpkSwatches: List<Swatch>,
    val nativeSwatches: List<Swatch>,
) {
    CanvasDefault(
        label = R.string.native_compare_canvas_default,
        difference = R.string.native_compare_segmented_canvas_default_difference,
        style = BpkSegmentedControlStyle.CanvasDefault,
        contrast = false,
        bpkSwatches = listOf(
            Swatch.componentOnly(R.string.native_compare_state_fill, BpkRes.color.__privateSegmentedControlCanvasDefault, R.string.native_compare_segmented_only),
            Swatch.token(R.string.native_compare_state_selected, R.string.native_compare_token_core_primary) { BpkTheme.colors.corePrimary },
            Swatch.token(R.string.native_compare_state_selected_text, R.string.native_compare_token_text_on_dark) { BpkTheme.colors.textOnDark },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_divider, R.string.native_compare_token_line) { BpkTheme.colors.line },
            Swatch.none(R.string.native_compare_state_border),
        ),
        nativeSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_surface_default) { BpkTheme.colors.surfaceDefault },
            Swatch.token(R.string.native_compare_state_selected, R.string.native_compare_token_surface_highlight) { BpkTheme.colors.surfaceHighlight },
            Swatch.token(R.string.native_compare_state_selected_text, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_divider, R.string.native_compare_token_line) { BpkTheme.colors.line },
            Swatch.token(R.string.native_compare_state_border, R.string.native_compare_token_line) { BpkTheme.colors.line },
        ),
    ),
    SurfaceContrast(
        label = R.string.native_compare_surface_contrast,
        difference = R.string.native_compare_segmented_surface_contrast_difference,
        style = BpkSegmentedControlStyle.SurfaceContrast,
        contrast = true,
        bpkSwatches = listOf(
            Swatch.componentOnly(R.string.native_compare_state_fill, BpkRes.color.__privateSegmentedControlSurfaceContrast, R.string.native_compare_segmented_only),
            Swatch.componentOnly(R.string.native_compare_state_selected, BpkRes.color.__privateSegmentedControlSurfaceContrastOn, R.string.native_compare_segmented_only),
            Swatch.token(R.string.native_compare_state_selected_text, R.string.native_compare_token_text_on_dark) { BpkTheme.colors.textOnDark },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_on_dark) { BpkTheme.colors.textOnDark },
            Swatch.token(R.string.native_compare_state_divider, R.string.native_compare_token_line) { BpkTheme.colors.line },
            Swatch.none(R.string.native_compare_state_border),
        ),
        nativeSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_surface_contrast) { BpkTheme.colors.surfaceContrast },
            Swatch.token(R.string.native_compare_state_selected, R.string.native_compare_token_core_primary) { BpkTheme.colors.corePrimary },
            Swatch.token(R.string.native_compare_state_selected_text, R.string.native_compare_token_text_on_dark) { BpkTheme.colors.textOnDark },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_on_dark) { BpkTheme.colors.textOnDark },
            Swatch.token(R.string.native_compare_state_divider, R.string.native_compare_token_line) { BpkTheme.colors.line },
            Swatch.token(R.string.native_compare_state_border, R.string.native_compare_token_line) { BpkTheme.colors.line },
        ),
    ),
}
