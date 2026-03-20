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

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.SwapVertical

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
    var isRotated by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (isRotated) 180f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "swap_button_rotation",
    )

    val backgroundColor = when (style) {
        BpkSwapButtonStyle.CanvasDefault -> BpkTheme.colors.surfaceDefault
        BpkSwapButtonStyle.CanvasContrast -> BpkTheme.colors.canvasContrast
        BpkSwapButtonStyle.SurfaceContrast -> BpkTheme.colors.surfaceDefault
    }

    val borderColor = when (style) {
        BpkSwapButtonStyle.CanvasDefault -> BpkTheme.colors.canvasContrast
        BpkSwapButtonStyle.CanvasContrast -> BpkTheme.colors.canvas
        BpkSwapButtonStyle.SurfaceContrast -> BpkTheme.colors.textPrimary
    }

    Box(
        modifier = modifier
            .size(OuterBoxTouchTargetSize)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = {
                    isRotated = !isRotated
                    onClick()
                },
            )
            .semantics(mergeDescendants = true) {
                this.contentDescription = contentDescription
            },
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(SwapButtonSize)
                .clip(CircleShape)
                .background(backgroundColor)
                .border(
                    width = BorderWidth,
                    color = borderColor,
                    shape = CircleShape,
                )
                .rotate(rotation),
            contentAlignment = Alignment.Center,
        ) {
            BpkIcon(
                icon = BpkIcon.SwapVertical,
                contentDescription = null,
            )
        }
    }
}

private val OuterBoxTouchTargetSize = 48.dp
private val SwapButtonSize = 36.dp
private val BorderWidth = 2.dp
