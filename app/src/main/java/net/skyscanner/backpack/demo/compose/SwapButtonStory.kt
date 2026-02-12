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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.swapbutton.BpkSwapButton
import net.skyscanner.backpack.compose.swapbutton.BpkSwapButtonStyle
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkDimension
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.components.SwapButtonComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@SwapButtonComponent
@ComposeStory("Default")
fun SwapButtonDefaultStory(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(BpkDimension.Spacing.Md),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Lg),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
            ) {
                BpkText(
                    text = "Canvas Default",
                    style = BpkTheme.typography.heading4,
                )
                BpkSwapButton(
                    onClick = { },
                    style = BpkSwapButtonStyle.CanvasDefault,
                    contentDescription = "Swap",
                )
            }
        }

        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
            ) {
                BpkText(
                    text = "Canvas Contrast",
                    style = BpkTheme.typography.heading4,
                )
                BpkSwapButton(
                    onClick = { },
                    style = BpkSwapButtonStyle.CanvasContrast,
                    contentDescription = "Swap",
                )
            }
        }

        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
            ) {
                BpkText(
                    text = "Surface Contrast",
                    style = BpkTheme.typography.heading4,
                )
                BpkSwapButton(
                    onClick = { },
                    style = BpkSwapButtonStyle.SurfaceContrast,
                    contentDescription = "Swap",
                )
            }
        }

        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
            ) {
                BpkText(
                    text = "Disabled",
                    style = BpkTheme.typography.heading4,
                )
                BpkSwapButton(
                    onClick = { },
                    enabled = false,
                    contentDescription = "Swap",
                )
            }
        }
    }
}
