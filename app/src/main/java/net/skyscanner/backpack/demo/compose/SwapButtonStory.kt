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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.swapbutton.BpkSwapButton
import net.skyscanner.backpack.compose.swapbutton.BpkSwapButtonStyle
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.components.SwapButtonComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@SwapButtonComponent
@ComposeStory
fun SwapButtonStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        CanvasDefaultSwapButtonExample()
        CanvasContrastSwapButtonExample()
        SurfaceContrastSwapButtonExample()
    }
}

@Composable
internal fun CanvasDefaultSwapButtonExample(modifier: Modifier = Modifier) {
    SwapButtonSampleRow(
        label = "Canvas Default",
        style = BpkSwapButtonStyle.CanvasDefault,
        backgroundColor = { BpkTheme.colors.surfaceDefault },
        modifier = modifier,
    )
}

@Composable
internal fun CanvasContrastSwapButtonExample(modifier: Modifier = Modifier) {
    SwapButtonSampleRow(
        label = "Canvas Contrast",
        style = BpkSwapButtonStyle.CanvasContrast,
        backgroundColor = { BpkTheme.colors.canvasContrast },
        modifier = modifier,
    )
}

@Composable
internal fun SurfaceContrastSwapButtonExample(modifier: Modifier = Modifier) {
    SwapButtonSampleRow(
        label = "Surface Contrast",
        style = BpkSwapButtonStyle.SurfaceContrast,
        backgroundColor = { BpkTheme.colors.surfaceDefault },
        modifier = modifier,
    )
}

@Composable
private fun SwapButtonSampleRow(
    label: String,
    style: BpkSwapButtonStyle,
    backgroundColor: @Composable () -> androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
    ) {
        BpkText(text = label, style = BpkTheme.typography.label2)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor())
                .padding(BpkSpacing.Base),
            contentAlignment = Alignment.Center,
        ) {
            BpkSwapButton(
                onClick = {},
                contentDescription = label,
                style = style,
            )
        }
    }
}
