/*
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import net.skyscanner.backpack.compose.LocalContentColor
import net.skyscanner.backpack.compose.spinner.BpkSpinner
import net.skyscanner.backpack.compose.spinner.BpkSpinnerSize
import net.skyscanner.backpack.compose.spinner.BpkSpinnerStyle
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.components.SpinnerComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@SpinnerComponent
@ComposeStory
fun SpinnerStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        BpkSpinnerStyle.values().forEach { style ->
            SpinnersRow(style = style)
        }
    }
}

@Composable
private fun SpinnersRow(
    style: BpkSpinnerStyle,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {

        BpkText(style.name)

        Row(
            modifier = Modifier.background(
                if (style == BpkSpinnerStyle.OnDarkSurface) BpkTheme.colors.surfaceContrast else Color.Transparent,
            ),
        ) {
            BpkSpinnerSize.values().forEach { size ->

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Box(Modifier.height(BpkSpacing.Xxl), contentAlignment = Alignment.Center) {
                        BpkSpinner(size = size, style = style)
                    }

                    BpkText(
                        text = size.name,
                        style = BpkTheme.typography.caption,
                        color = if (style == BpkSpinnerStyle.OnDarkSurface) BpkTheme.colors.textOnDark else LocalContentColor.current,
                    )
                }
            }
        }
    }
}
