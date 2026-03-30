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

package net.skyscanner.backpack.compose.swapbutton

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.swapbutton.internal.BpkSwapButtonImpl

enum class BpkSwapButtonStyle {
    CanvasDefault,
    CanvasContrast,
    SurfaceContrast,
}

/**
 * BpkSwapButton is a round button component that rotates 180 degrees when clicked.
 * It's typically used for swapping between two states or reversing an action.
 *
 * @param onClick Callback invoked when the button is clicked
 * @param contentDescription Content description for accessibility
 * @param modifier Modifier to be applied to the button
 * @param style The visual style of the button
 * @param interactionSource MutableInteractionSource to observe interactions
 */
@Composable
fun BpkSwapButton(
    onClick: () -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier,
    style: BpkSwapButtonStyle = BpkSwapButtonStyle.CanvasDefault,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    BpkSwapButtonImpl(
        onClick = onClick,
        contentDescription = contentDescription,
        modifier = modifier,
        style = style,
        interactionSource = interactionSource,
    )
}
