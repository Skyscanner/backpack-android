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

package net.skyscanner.backpack.compose.segmentedcontrol

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.segmentedcontrol.internal.BpkSegmentedControlImpl

enum class BpkSegmentedControlStyle {
    CanvasDefault,
    CanvasContrast,
    SurfaceDefault,
    SurfaceContrast,
}

private const val maxNumberOfButtons = 4

@Composable
fun BpkSegmentedControl(
    buttonContents: List<String>,
    onItemClick: (Int) -> Unit,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    shadow: Boolean = false,
    type: BpkSegmentedControlStyle = BpkSegmentedControlStyle.CanvasDefault,
) {
    val limitedButtonContents = buttonContents.take(maxNumberOfButtons)
    BpkSegmentedControlImpl(
        buttonContents = limitedButtonContents,
        onItemClick = onItemClick,
        selectedIndex = selectedIndex,
        modifier = modifier,
        shadow = shadow,
        type = type,
    )
}
