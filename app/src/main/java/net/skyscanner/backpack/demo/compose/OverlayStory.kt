/**
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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.overlay.BpkOverlay
import net.skyscanner.backpack.compose.overlay.BpkOverlayType
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.OverlayComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@OverlayComponent
@ComposeStory
fun OverlayStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        OverlaySampleRow {
            val childModifier = Modifier.weight(1f)
            DefaultOverlaySample(overlayType = BpkOverlayType.SolidLow, modifier = childModifier)
            DefaultOverlaySample(overlayType = BpkOverlayType.SolidMedium, modifier = childModifier)
            DefaultOverlaySample(overlayType = BpkOverlayType.SolidHigh, modifier = childModifier)
        }
        OverlaySampleRow {
            val childModifier = Modifier.weight(1f)
            DefaultOverlaySample(overlayType = BpkOverlayType.TopLow, modifier = childModifier)
            DefaultOverlaySample(overlayType = BpkOverlayType.TopMedium, modifier = childModifier)
            DefaultOverlaySample(overlayType = BpkOverlayType.TopHigh, modifier = childModifier)
        }
        OverlaySampleRow {
            val childModifier = Modifier.weight(1f)
            DefaultOverlaySample(overlayType = BpkOverlayType.BottomLow, modifier = childModifier)
            DefaultOverlaySample(overlayType = BpkOverlayType.BottomMedium, modifier = childModifier)
            DefaultOverlaySample(overlayType = BpkOverlayType.BottomHigh, modifier = childModifier)
        }
        OverlaySampleRow {
            val childModifier = Modifier.weight(1f)
            DefaultOverlaySample(overlayType = BpkOverlayType.LeftLow, modifier = childModifier)
            DefaultOverlaySample(overlayType = BpkOverlayType.LeftMedium, modifier = childModifier)
            DefaultOverlaySample(overlayType = BpkOverlayType.LeftHigh, modifier = childModifier)
        }
        OverlaySampleRow {
            val childModifier = Modifier.weight(1f)
            DefaultOverlaySample(overlayType = BpkOverlayType.RightLow, modifier = childModifier)
            DefaultOverlaySample(overlayType = BpkOverlayType.RightMedium, modifier = childModifier)
            DefaultOverlaySample(overlayType = BpkOverlayType.RightHigh, modifier = childModifier)
        }
        OverlaySampleRow {
            val childModifier = Modifier.weight(1f)
            DefaultOverlaySample(overlayType = BpkOverlayType.Vignette, modifier = childModifier)
            WithTextOverlaySample(overlayType = BpkOverlayType.SolidHigh, modifier = childModifier)
            Spacer(modifier = childModifier)
        }
        Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
    }
}

@Composable
fun OverlaySampleRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        content = content,
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
fun DefaultOverlaySample(
    overlayType: BpkOverlayType,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        BpkOverlay(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f),
            overlayType = overlayType,
        ) {
            Image(
                painter = painterResource(R.drawable.sea),
                contentDescription = stringResource(R.string.image_sea_content_description),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
        BpkText(text = overlayType.toString())
    }
}

@Composable
fun WithTextOverlaySample(
    overlayType: BpkOverlayType,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        BpkOverlay(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f),
            overlayType = overlayType,
            foregroundContent = {
                BpkText(
                    modifier = Modifier.align(Alignment.Center),
                    text = "With text",
                    color = BpkTheme.colors.textOnDark,
                    style = BpkTheme.typography.bodyDefault,
                )
            },
        ) {
            Image(
                painter = painterResource(R.drawable.sea),
                contentDescription = stringResource(R.string.image_sea_content_description),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        }
        BpkText(text = overlayType.toString())
    }
}
