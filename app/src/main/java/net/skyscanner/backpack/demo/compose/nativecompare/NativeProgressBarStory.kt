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
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import net.skyscanner.backpack.compose.progressbar.BpkProgressBar
import net.skyscanner.backpack.compose.progressbar.BpkProgressBarSize
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.NativeCompareComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.meta.StoryKind

/** BpkProgressBar next to Material 3's LinearProgressIndicator, themed with Backpack's semantic tokens only. */
@Composable
@NativeCompareComponent
@ComposeStory("Progress bar", kind = StoryKind.DemoOnly)
fun NativeProgressBarStory(modifier: Modifier = Modifier) {
    ComparisonStory(modifier = modifier) {
        ProgressBarComparison.entries.forEach { pair ->
            NativeProgressBarComparison(pair)
        }
    }
}

@Composable
internal fun NativeProgressBarComparison(pair: ProgressBarComparison, modifier: Modifier = Modifier) {
    ComparisonPair(
        label = pair.label,
        difference = pair.difference,
        rows = listOf(
            ComparisonRow(
                backpack = {
                    BpkProgressBar(
                        value = pair.fraction * pair.steps,
                        size = pair.size,
                        max = pair.steps,
                        stepped = pair.steps > 1,
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                native = {
                    LinearProgressIndicator(
                        progress = { pair.fraction },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(pair.height),
                    )
                },
            ),
        ),
        backpack = pair.bpkSwatches,
        native = pair.nativeSwatches,
        modifier = modifier,
    )
}

internal enum class ProgressBarComparison(
    @StringRes val label: Int,
    @StringRes val difference: Int,
    val size: BpkProgressBarSize,
    /** Same track height for both sides so the comparison is about paint, not size. */
    val height: Dp,
    val steps: Int,
    val bpkSwatches: List<Swatch>,
    val nativeSwatches: List<Swatch>,
) {
    Small(
        label = R.string.native_compare_progress_small,
        difference = R.string.native_compare_progress_small_difference,
        size = BpkProgressBarSize.Small,
        height = BpkSpacing.Md,
        steps = 1,
        bpkSwatches = listOf(
            Swatch.token(R.string.native_compare_state_indicator, R.string.native_compare_token_core_accent) { BpkTheme.colors.coreAccent },
            Swatch.token(R.string.native_compare_state_track, R.string.native_compare_token_surface_highlight) { BpkTheme.colors.surfaceHighlight },
            Swatch.none(R.string.native_compare_state_stop),
        ),
        nativeSwatches = listOf(
            Swatch.token(R.string.native_compare_state_indicator, R.string.native_compare_token_core_primary) { BpkTheme.colors.corePrimary },
            Swatch.token(R.string.native_compare_state_track, R.string.native_compare_token_surface_highlight) { BpkTheme.colors.surfaceHighlight },
            Swatch.token(R.string.native_compare_state_stop, R.string.native_compare_token_core_primary) { BpkTheme.colors.corePrimary },
        ),
    ),
    LargeStepped(
        label = R.string.native_compare_progress_large_stepped,
        difference = R.string.native_compare_progress_large_stepped_difference,
        size = BpkProgressBarSize.Large,
        height = BpkSpacing.Base,
        steps = STEPS,
        bpkSwatches = listOf(
            Swatch.token(R.string.native_compare_state_indicator, R.string.native_compare_token_core_accent) { BpkTheme.colors.coreAccent },
            Swatch.token(R.string.native_compare_state_track, R.string.native_compare_token_surface_highlight) { BpkTheme.colors.surfaceHighlight },
            Swatch.token(R.string.native_compare_state_divider, R.string.native_compare_token_surface_default) { BpkTheme.colors.surfaceDefault },
            Swatch.none(R.string.native_compare_state_stop),
        ),
        nativeSwatches = listOf(
            Swatch.token(R.string.native_compare_state_indicator, R.string.native_compare_token_core_primary) { BpkTheme.colors.corePrimary },
            Swatch.token(R.string.native_compare_state_track, R.string.native_compare_token_surface_highlight) { BpkTheme.colors.surfaceHighlight },
            Swatch.none(R.string.native_compare_state_divider),
            Swatch.token(R.string.native_compare_state_stop, R.string.native_compare_token_core_primary) { BpkTheme.colors.corePrimary },
        ),
    ),
    ;

    val fraction: Float get() = PROGRESS
}

private const val PROGRESS = 0.6f
private const val STEPS = 5
