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

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import net.skyscanner.backpack.compose.divider.BpkDivider
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkBorderSize
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

/**
 * One colour in the comparison table: which state it paints, the colour itself, and what design
 * knows it as. [name] resolves to the Figma variable name for a semantic token, or "button-only"
 * for a value that exists only inside the button. No literal text or hex: states and names are
 * string resources, colours are tokens or Backpack's own private colour resources.
 */
internal data class Swatch(
    @StringRes val state: Int,
    val name: @Composable () -> String,
    val color: @Composable () -> Color?,
) {
    companion object {
        /** A button-only value, read from Backpack's private colour resource so light and dark stay in sync. */
        fun buttonOnly(@StringRes state: Int, @ColorRes color: Int, @StringRes note: Int = 0) = Swatch(
            state = state,
            name = {
                if (note == 0) {
                    stringResource(R.string.native_compare_button_only)
                } else {
                    stringResource(R.string.native_compare_button_only_note, stringResource(note))
                }
            },
            color = { colorResource(color) },
        )

        /** A semantic token, named as it appears in Figma. */
        fun token(@StringRes state: Int, @StringRes figmaName: Int, color: @Composable () -> Color) =
            Swatch(state = state, name = { stringResource(figmaName) }, color = color)

        /** No colour of ours: the platform decides this state. */
        fun platform(@StringRes state: Int) =
            Swatch(state = state, name = { stringResource(R.string.native_compare_from_platform) }, color = { null })

        /** A state drawn as an overlay rather than a colour of its own, described in words. */
        fun overlay(@StringRes state: Int, @StringRes description: Int) =
            Swatch(state = state, name = { stringResource(description) }, color = { null })
    }
}

/**
 * One row per state: Backpack today, the native component, and whether they match.
 * States are paired by name; a state present on one side only still gets a row.
 */
@Composable
internal fun ComparisonTable(
    backpack: List<Swatch>,
    native: List<Swatch>,
    modifier: Modifier = Modifier,
) {
    val states = (backpack.map { it.state } + native.map { it.state }).distinct()
    Column(modifier = modifier.fillMaxWidth()) {
        TableRow(
            state = { Header(stringResource(R.string.native_compare_state)) },
            backpack = { Header(stringResource(R.string.native_compare_backpack)) },
            native = { Header(stringResource(R.string.native_compare_native)) },
            same = { Header(stringResource(R.string.native_compare_same)) },
        )
        BpkDivider()
        states.forEach { state ->
            val left = backpack.firstOrNull { it.state == state }
            val right = native.firstOrNull { it.state == state }
            val leftColor = left?.color?.invoke()
            val rightColor = right?.color?.invoke()
            TableRow(
                state = { Secondary(stringResource(state)) },
                backpack = { if (left != null) SwatchCell(left, leftColor) },
                native = { if (right != null) SwatchCell(right, rightColor) },
                same = {
                    Secondary(
                        stringResource(
                            when {
                                leftColor == null || rightColor == null -> R.string.native_compare_same_na
                                leftColor == rightColor -> R.string.native_compare_same_yes
                                else -> R.string.native_compare_same_no
                            },
                        ),
                    )
                },
            )
        }
    }
}

@Composable
private fun TableRow(
    state: @Composable () -> Unit,
    backpack: @Composable () -> Unit,
    native: @Composable () -> Unit,
    same: @Composable () -> Unit,
) {
    val em = emPx()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = BpkSpacing.Sm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
    ) {
        Box(modifier = Modifier.width(em * STATE_WIDTH_EM)) { state() }
        Cell { backpack() }
        Cell { native() }
        Box(modifier = Modifier.width(em * SAME_WIDTH_EM)) { same() }
    }
}

@Composable
private fun RowScope.Cell(content: @Composable () -> Unit) {
    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) { content() }
}

/**
 * Swatch, hex in the current mode, and the name. One line at normal text size; at large text sizes
 * the name drops under the hex so the row stays readable instead of wrapping mid-word.
 */
@Composable
private fun SwatchCell(swatch: Swatch, color: Color?) {
    val stacked = LocalDensity.current.fontScale > STACK_ABOVE_FONT_SCALE
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Sm)) {
        if (color != null) {
            Box(
                modifier = Modifier
                    .size(emPx() * SWATCH_SIZE_EM)
                    .background(color, RoundedCornerShape(BpkBorderRadius.Xs))
                    .border(BpkBorderSize.Sm, BpkTheme.colors.line, RoundedCornerShape(BpkBorderRadius.Xs)),
            )
            if (stacked) {
                Column {
                    BpkText(text = color.toHex(), style = BpkTheme.typography.caption)
                    Secondary(swatch.name())
                }
            } else {
                BpkText(text = color.toHex(), style = BpkTheme.typography.caption)
                Secondary(swatch.name())
            }
        } else {
            Secondary(swatch.name())
        }
    }
}

@Composable
private fun Header(text: String) = BpkText(text = text, style = BpkTheme.typography.label2)

@Composable
private fun Secondary(text: String) =
    BpkText(text = text, style = BpkTheme.typography.caption, color = BpkTheme.colors.textSecondary)

/**
 * One em of the caption style as a Dp. Converted through pixels so it scales exactly as the text does:
 * sp-to-px is non-linear on Android 14+, and a pre-multiplied sp width would not keep up at 200%.
 */
@Composable
private fun emPx(): Dp = with(LocalDensity.current) { BpkTheme.typography.caption.fontSize.toPx().toDp() }

/** Hex of the colour; translucent colours also show their opacity, since the hex alone would mislead. */
private fun Color.toHex(): String {
    val rgb = "#%06X".format(toArgb() and 0xFFFFFF)
    val alphaPercent = (alpha * PERCENT).toInt()
    return if (alphaPercent < PERCENT.toInt()) "$rgb $alphaPercent%" else rgb
}

private const val PERCENT = 100f

private const val STATE_WIDTH_EM = 5f
private const val SAME_WIDTH_EM = 4f
private const val STACK_ABOVE_FONT_SCALE = 1.3f
private const val SWATCH_SIZE_EM = 1.2f
