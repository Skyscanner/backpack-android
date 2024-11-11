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

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.divider.BpkDivider
import net.skyscanner.backpack.compose.segmentedcontrol.BpkSegmentedControl
import net.skyscanner.backpack.compose.segmentedcontrol.BpkSegmentedControlStyle
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.SegmentedControlComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@SegmentedControlComponent
@ComposeStory("Canvas Default")
fun SegmentedControlCanvasDefaultStory(modifier: Modifier = Modifier) {
    SegmentedControlsSample(
        type = BpkSegmentedControlStyle.CanvasDefault,
        modifier = modifier,
    )
}

@Composable
@SegmentedControlComponent
@ComposeStory("Canvas Contrast")
fun SegmentedControlCanvasContrastStory(modifier: Modifier = Modifier) {
    SegmentedControlsSample(
        type = BpkSegmentedControlStyle.CanvasContrast,
        modifier = modifier,
    )
}

@Composable
@SegmentedControlComponent
@ComposeStory("Surface Default")
fun SegmentedControlSurfaceDefaultStory(modifier: Modifier = Modifier) {
    SegmentedControlsSample(
        type = BpkSegmentedControlStyle.SurfaceDefault,
        modifier = modifier,
    )
}

@Composable
@SegmentedControlComponent
@ComposeStory("Surface Contrast")
fun SegmentedControlSurfaceContrastStory(modifier: Modifier = Modifier) {
    SegmentedControlsSample(
        type = BpkSegmentedControlStyle.SurfaceContrast,
        modifier = modifier,
    )
}

@Composable
private fun SegmentedControlsSample(
    type: BpkSegmentedControlStyle,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(type.toColor())
            .padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Lg),
    ) {
        SegmentedControlCase(
            numberOfButtons = 2,
            type = type,
        )
        BpkDivider()
        SegmentedControlCase(
            numberOfButtons = 3,
            type = type,
        )
        BpkDivider()
        SegmentedControlCase(
            numberOfButtons = 4,
            type = type,
        )
    }
}

@Composable
private fun SegmentedControlCase(
    numberOfButtons: Int,
    type: BpkSegmentedControlStyle,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {

        SegmentedControlWithShadowStatus(
            numberOfButtons = numberOfButtons,
            type = type,
        )
        SegmentedControlWithShadowStatus(
            shadow = true,
            numberOfButtons = numberOfButtons,
            type = type,
        )
    }
}

@Composable
private fun SegmentedControlWithShadowStatus(
    numberOfButtons: Int,
    type: BpkSegmentedControlStyle,
    modifier: Modifier = Modifier,
    shadow: Boolean = false,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
    ) {
        BpkText(
            color = type.getTextColor(),
            style = BpkTheme.typography.bodyDefault,
            text = stringResource(
                id = R.string.shadow_status,
                stringResource(if (shadow) R.string.on else R.string.off),
            ),
        )
        SegmentedControlsSample(
            numberOfButtons = numberOfButtons,
            type = type,
            shadow = shadow,
        )
    }
}

@Composable
internal fun SegmentedControlsSample(
    numberOfButtons: Int,
    type: BpkSegmentedControlStyle,
    modifier: Modifier = Modifier,
    selectedInt: Int = 0,
    content: String = stringResource(R.string.value),
    shadow: Boolean = false,
) {
    var selectedIndex by remember { mutableIntStateOf(selectedInt) }
    val buttonContents = List(numberOfButtons) { content }
    BpkSegmentedControl(
        modifier = modifier,
        type = type,
        shadow = shadow,
        buttonContents = buttonContents,
        onItemClick = { selectedIndex = it },
        selectedInt = selectedIndex,
    )
}

@Composable
private fun BpkSegmentedControlStyle.getTextColor(): Color {
    return animateColorAsState(
        if (this == BpkSegmentedControlStyle.SurfaceContrast) BpkTheme.colors.textOnDark
        else BpkTheme.colors.textPrimary,
    ).value
}

@Composable
internal fun BpkSegmentedControlStyle.toColor() = when (this) {
    BpkSegmentedControlStyle.CanvasDefault -> BpkTheme.colors.canvas
    BpkSegmentedControlStyle.CanvasContrast -> BpkTheme.colors.canvasContrast
    BpkSegmentedControlStyle.SurfaceDefault -> BpkTheme.colors.surfaceDefault
    BpkSegmentedControlStyle.SurfaceContrast -> BpkTheme.colors.surfaceContrast
}
