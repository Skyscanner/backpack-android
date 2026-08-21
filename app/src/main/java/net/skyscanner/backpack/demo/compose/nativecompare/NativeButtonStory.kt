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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.common.R as BpkRes
import net.skyscanner.backpack.demo.components.NativeCompareComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.meta.StoryKind

/**
 * Side by side: BpkButton (left) against the stock Material 3 button for the same job (right),
 * the Material one themed with Backpack's semantic tokens only via [BackpackMaterialTheme].
 *
 * Lives under the "Native compare" component in the demo app, one story per Backpack component.
 * Demo-only. Not exported, not part of the docs screenshot run.
 */
@Composable
@NativeCompareComponent
@ComposeStory("Button", kind = StoryKind.DemoOnly)
fun NativeButtonStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(vertical = BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        ButtonComparison.entries.forEach { pair ->
            NativeButtonComparison(pair)
        }
    }
}

/** One comparison: the difference in a sentence, the two buttons, then the colour table. */
@Composable
internal fun NativeButtonComparison(pair: ButtonComparison, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
    ) {
        BpkText(text = stringResource(pair.label), style = BpkTheme.typography.heading5)
        BpkText(
            text = stringResource(pair.difference),
            style = BpkTheme.typography.footnote,
            color = BpkTheme.colors.textSecondary,
        )
        TwoColumns(
            backpack = { BpkText(text = stringResource(R.string.native_compare_backpack), style = BpkTheme.typography.label2) },
            native = { BpkText(text = stringResource(R.string.native_compare_native_material), style = BpkTheme.typography.label2) },
        )
        listOf(true, false).forEach { enabled ->
            TwoColumns(
                verticalAlignment = Alignment.CenterVertically,
                backpack = { BpkButton(text = stringResource(R.string.button), type = pair.bpkType, enabled = enabled, onClick = {}) },
                native = { BackpackMaterialTheme { pair.material(enabled) } },
            )
        }
        ComparisonTable(backpack = pair.bpkSwatches, native = pair.nativeSwatches)
    }
}

/** Backpack at the start, the native component at the end, a fixed gap between. */
@Composable
private fun TwoColumns(
    backpack: @Composable () -> Unit,
    native: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Lg),
        verticalAlignment = verticalAlignment,
    ) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) { backpack() }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) { native() }
    }
}

internal enum class ButtonComparison(
    @StringRes val label: Int,
    /** The difference between the two, in one sentence. */
    @StringRes val difference: Int,
    val bpkType: BpkButtonType,
    /** What BpkButton paints with today (no button configuration, as the app runs). */
    val bpkSwatches: List<Swatch>,
    /** What the native column paints with: semantic tokens only, via BackpackMaterialTheme. */
    val nativeSwatches: List<Swatch>,
    val material: @Composable (enabled: Boolean) -> Unit,
) {
    Primary(
        label = R.string.native_compare_primary,
        difference = R.string.native_compare_primary_difference,
        bpkType = BpkButtonType.Primary,
        bpkSwatches = listOf(
            Swatch.buttonOnly(
                R.string.native_compare_state_fill,
                BpkRes.color.__privateButtonPrimaryNormalBackground,
                note = R.string.native_compare_note_light_equals_core_primary,
            ),
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_on_dark) { BpkTheme.colors.textOnDark },
            Swatch.buttonOnly(R.string.native_compare_state_pressed, BpkRes.color.__privateButtonPrimaryPressedBackground),
            Swatch.buttonOnly(R.string.native_compare_state_disabled, BpkRes.color.__privateButtonDisabledBackground),
        ),
        nativeSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_core_primary) { BpkTheme.colors.corePrimary },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_on_dark) { BpkTheme.colors.textOnDark },
            Swatch.platform(R.string.native_compare_state_pressed),
            Swatch.platform(R.string.native_compare_state_disabled),
        ),
        material = { enabled ->
            Button(onClick = {}, enabled = enabled, shape = MaterialTheme.shapes.small) { Text(stringResource(R.string.button)) }
        },
    ),
    Secondary(
        label = R.string.native_compare_secondary,
        difference = R.string.native_compare_secondary_difference,
        bpkType = BpkButtonType.Secondary,
        bpkSwatches = listOf(
            Swatch.buttonOnly(
                R.string.native_compare_state_fill,
                BpkRes.color.__privateButtonSecondaryNormalBackground,
                note = R.string.native_compare_note_equals_surface_highlight,
            ),
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.buttonOnly(R.string.native_compare_state_pressed, BpkRes.color.__privateButtonSecondaryPressedBackground),
            Swatch.buttonOnly(R.string.native_compare_state_disabled, BpkRes.color.__privateButtonDisabledBackground),
        ),
        nativeSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_surface_highlight) { BpkTheme.colors.surfaceHighlight },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.platform(R.string.native_compare_state_pressed),
            Swatch.platform(R.string.native_compare_state_disabled),
        ),
        material = { enabled ->
            FilledTonalButton(onClick = {}, enabled = enabled, shape = MaterialTheme.shapes.small) { Text(stringResource(R.string.button)) }
        },
    ),
    Destructive(
        label = R.string.native_compare_destructive,
        difference = R.string.native_compare_destructive_difference,
        bpkType = BpkButtonType.Destructive,
        bpkSwatches = listOf(
            Swatch.buttonOnly(
                R.string.native_compare_state_fill,
                BpkRes.color.__privateButtonDestructiveNormalBackground,
                note = R.string.native_compare_note_equals_surface_highlight,
            ),
            Swatch.buttonOnly(
                R.string.native_compare_state_text,
                BpkRes.color.__privateButtonDestructiveNormalForeground,
                note = R.string.native_compare_note_equals_text_error,
            ),
            Swatch.buttonOnly(R.string.native_compare_state_pressed, BpkRes.color.__privateButtonDestructivePressedBackground),
            Swatch.buttonOnly(R.string.native_compare_state_disabled, BpkRes.color.__privateButtonDisabledBackground),
        ),
        nativeSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_surface_highlight) { BpkTheme.colors.surfaceHighlight },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_error) { BpkTheme.colors.textError },
            Swatch.platform(R.string.native_compare_state_pressed),
            Swatch.platform(R.string.native_compare_state_disabled),
        ),
        material = { enabled ->
            FilledTonalButton(
                onClick = {},
                enabled = enabled,
                shape = MaterialTheme.shapes.small,
                colors = errorTonalColors(),
            ) { Text(stringResource(R.string.button)) }
        },
    ),
    Link(
        label = R.string.native_compare_link,
        difference = R.string.native_compare_link_difference,
        bpkType = BpkButtonType.Link,
        bpkSwatches = listOf(
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_primary_underlined) { BpkTheme.colors.textPrimary },
            Swatch.overlay(R.string.native_compare_state_pressed, R.string.native_compare_ripple_text_primary),
            Swatch.token(R.string.native_compare_state_disabled, R.string.native_compare_token_text_disabled) { BpkTheme.colors.textDisabled },
        ),
        nativeSwatches = listOf(
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.platform(R.string.native_compare_state_pressed),
            Swatch.platform(R.string.native_compare_state_disabled),
        ),
        material = { enabled ->
            TextButton(
                onClick = {},
                enabled = enabled,
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
            ) { Text(stringResource(R.string.button)) }
        },
    ),
}

@Composable
private fun errorTonalColors(): ButtonColors = ButtonDefaults.filledTonalButtonColors(
    containerColor = MaterialTheme.colorScheme.errorContainer,
    contentColor = MaterialTheme.colorScheme.onErrorContainer,
)
