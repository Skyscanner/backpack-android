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

package net.skyscanner.backpack.compose.flare.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.flare.BpkFlarePointerDirection
import net.skyscanner.backpack.compose.flare.BpkFlareRadius
import net.skyscanner.backpack.compose.tokens.BpkDimension
import net.skyscanner.backpack.compose.utils.FlareShape

@Composable
internal fun BpkFlareImpl(
    modifier: Modifier = Modifier,
    radius: BpkFlareRadius = BpkFlareRadius.None,
    pointerDirection: BpkFlarePointerDirection = BpkFlarePointerDirection.Down,
    background: Color = Color.Unspecified,
    insetContent: Boolean = false,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = true,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .clip(FlareRectShape(radius)) // this exists to improve anti-aliasing on < sdk 30. remove when dropping support
            .clip(FlareShape(radius, pointerDirection))
            .background(background)
            .padding(
                top = when (pointerDirection) {
                    BpkFlarePointerDirection.Up -> FlareContentPadding(insetContent)
                    BpkFlarePointerDirection.Down -> 0.dp
                },
                bottom = when (pointerDirection) {
                    BpkFlarePointerDirection.Up -> 0.dp
                    BpkFlarePointerDirection.Down -> FlareContentPadding(insetContent)
                },
            ),
        propagateMinConstraints = propagateMinConstraints,
        contentAlignment = contentAlignment,
        content = content,
    )
}

internal fun FlareShape(
    radius: BpkFlareRadius,
    pointerDirection: BpkFlarePointerDirection,
): Shape =
    FlareShape(
        flareHeight = FlareHeight,
        pointerDirection = pointerDirection,
        applyAntialiasFix = true,
        borderRadius = when (radius) {
            BpkFlareRadius.None -> 0.dp
            BpkFlareRadius.Medium -> BpkDimension.BorderRadius.Md
        },
    )

internal fun FlareRectShape(radius: BpkFlareRadius): Shape = when (radius) {
    BpkFlareRadius.None -> RectangleShape
    BpkFlareRadius.Medium -> RoundedCornerShape(BpkDimension.BorderRadius.Md)
}

internal fun FlareContentPadding(insetContent: Boolean = false) =
    when (insetContent) {
        true -> FlareHeight
        false -> 0.dp
    }

private val FlareHeight = 11.dp
