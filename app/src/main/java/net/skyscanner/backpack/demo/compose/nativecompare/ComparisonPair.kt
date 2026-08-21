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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

/**
 * One row of the side-by-side: the Backpack component on the left, the stock Material 3 component
 * on the right. The native side is always composed inside [BackpackMaterialTheme], so the only
 * Backpack input it gets is the global semantic tokens.
 */
internal class ComparisonRow(
    val backpack: @Composable () -> Unit,
    val native: @Composable () -> Unit,
)

/** The scrolling list of pairs every native-compare story is made of. */
@Composable
internal fun ComparisonStory(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(vertical = BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        content = content,
    )
}

/**
 * One variant of a component: name, the one-sentence difference, the two columns, then the table
 * of what each side paints with. Each story crops one of these per screenshot.
 */
@Composable
internal fun ComparisonPair(
    @StringRes label: Int,
    @StringRes difference: Int,
    rows: List<ComparisonRow>,
    backpack: List<Swatch>,
    native: List<Swatch>,
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
    ) {
        BpkText(text = stringResource(label), style = BpkTheme.typography.heading5)
        BpkText(
            text = stringResource(difference),
            style = BpkTheme.typography.footnote,
            color = BpkTheme.colors.textSecondary,
        )
        TwoColumns(
            backpack = { BpkText(text = stringResource(R.string.native_compare_backpack), style = BpkTheme.typography.label2) },
            native = { BpkText(text = stringResource(R.string.native_compare_native_material), style = BpkTheme.typography.label2) },
        )
        rows.forEach { row ->
            TwoColumns(
                verticalAlignment = verticalAlignment,
                backpack = row.backpack,
                native = { BackpackMaterialTheme { row.native() } },
            )
        }
        ComparisonTable(backpack = backpack, native = native)
    }
}

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

/**
 * A Backpack icon glyph drawn inside a Material 3 slot. It takes Material's content colour, so the
 * native column shows what the platform component tints its icons with, not what Backpack would.
 */
@Composable
internal fun MaterialIcon(icon: BpkIcon) {
    BpkIcon(icon = icon, contentDescription = null, tint = LocalContentColor.current)
}

/** Wraps a row in Surface / Contrast when the variant is meant to sit on a dark surface. */
@Composable
internal fun ContrastBackground(contrast: Boolean, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .then(if (contrast) Modifier.background(BpkTheme.colors.surfaceContrast).padding(BpkSpacing.Md) else Modifier),
        contentAlignment = Alignment.CenterStart,
    ) {
        content()
    }
}
