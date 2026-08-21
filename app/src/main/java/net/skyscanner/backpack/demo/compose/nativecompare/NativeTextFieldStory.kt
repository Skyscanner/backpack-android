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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.textfield.BpkTextField
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.NativeCompareComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.meta.StoryKind

/** BpkTextField next to Material 3's OutlinedTextField, themed with Backpack's semantic tokens only. */
@Composable
@NativeCompareComponent
@ComposeStory("Text field", kind = StoryKind.DemoOnly)
fun NativeTextFieldStory(modifier: Modifier = Modifier) {
    ComparisonStory(modifier = modifier) {
        TextFieldComparison.entries.forEach { pair ->
            NativeTextFieldComparison(pair)
        }
    }
}

@Composable
internal fun NativeTextFieldComparison(pair: TextFieldComparison, modifier: Modifier = Modifier) {
    val filledStates = if (pair.filledOnly) listOf(true) else listOf(false, true)
    ComparisonPair(
        label = pair.label,
        difference = pair.difference,
        rows = filledStates.map { filled ->
            ComparisonRow(
                backpack = {
                    BpkTextField(
                        value = if (filled) stringResource(R.string.native_compare_text_field_value) else "",
                        onValueChange = {},
                        placeholder = stringResource(R.string.native_compare_text_field_placeholder),
                        status = pair.status(),
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                native = {
                    OutlinedTextField(
                        value = if (filled) stringResource(R.string.native_compare_text_field_value) else "",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        enabled = pair.enabled,
                        isError = pair.isError,
                        placeholder = { Text(stringResource(R.string.native_compare_text_field_placeholder)) },
                        singleLine = true,
                        shape = MaterialTheme.shapes.small,
                        textStyle = MaterialTheme.typography.bodyMedium,
                    )
                },
            )
        },
        backpack = pair.bpkSwatches,
        native = pair.nativeSwatches,
        modifier = modifier,
    )
}

internal enum class TextFieldComparison(
    @StringRes val label: Int,
    @StringRes val difference: Int,
    val status: @Composable () -> BpkFieldStatus,
    val enabled: Boolean,
    val isError: Boolean,
    val filledOnly: Boolean,
    val bpkSwatches: List<Swatch>,
    val nativeSwatches: List<Swatch>,
) {
    Default(
        label = R.string.native_compare_text_field_default,
        difference = R.string.native_compare_text_field_default_difference,
        status = { BpkFieldStatus.Default },
        enabled = true,
        isError = false,
        filledOnly = false,
        bpkSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_surface_default) { BpkTheme.colors.surfaceDefault },
            Swatch.token(R.string.native_compare_state_border, R.string.native_compare_token_line) { BpkTheme.colors.line },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_placeholder, R.string.native_compare_token_text_secondary) { BpkTheme.colors.textSecondary },
            Swatch.token(R.string.native_compare_state_focused, R.string.native_compare_token_core_accent) { BpkTheme.colors.coreAccent },
            Swatch.token(R.string.native_compare_state_cursor, R.string.native_compare_token_core_accent) { BpkTheme.colors.coreAccent },
        ),
        nativeSwatches = listOf(
            Swatch.none(R.string.native_compare_state_fill),
            Swatch.token(R.string.native_compare_state_border, R.string.native_compare_token_line) { BpkTheme.colors.line },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_placeholder, R.string.native_compare_token_text_secondary) { BpkTheme.colors.textSecondary },
            Swatch.token(R.string.native_compare_state_focused, R.string.native_compare_token_core_primary) { BpkTheme.colors.corePrimary },
            Swatch.token(R.string.native_compare_state_cursor, R.string.native_compare_token_core_primary) { BpkTheme.colors.corePrimary },
        ),
    ),
    Error(
        label = R.string.native_compare_text_field_error,
        difference = R.string.native_compare_text_field_error_difference,
        status = { BpkFieldStatus.Error(stringResource(R.string.native_compare_text_field_error_message)) },
        enabled = true,
        isError = true,
        filledOnly = true,
        bpkSwatches = listOf(
            Swatch.token(R.string.native_compare_state_border, R.string.native_compare_token_text_error) { BpkTheme.colors.textError },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_icon, R.string.native_compare_token_status_danger_spot) { BpkTheme.colors.statusDangerSpot },
        ),
        nativeSwatches = listOf(
            Swatch.token(R.string.native_compare_state_border, R.string.native_compare_token_status_danger_spot) { BpkTheme.colors.statusDangerSpot },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.none(R.string.native_compare_state_icon),
        ),
    ),
    Disabled(
        label = R.string.native_compare_text_field_disabled,
        difference = R.string.native_compare_text_field_disabled_difference,
        status = { BpkFieldStatus.Disabled },
        enabled = false,
        isError = false,
        filledOnly = true,
        bpkSwatches = listOf(
            Swatch.token(R.string.native_compare_state_border, R.string.native_compare_token_surface_highlight) { BpkTheme.colors.surfaceHighlight },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_disabled) { BpkTheme.colors.textDisabled },
            Swatch.token(R.string.native_compare_state_placeholder, R.string.native_compare_token_text_disabled) { BpkTheme.colors.textDisabled },
        ),
        nativeSwatches = listOf(
            Swatch.platform(R.string.native_compare_state_border),
            Swatch.platform(R.string.native_compare_state_text),
            Swatch.platform(R.string.native_compare_state_placeholder),
        ),
    ),
}
