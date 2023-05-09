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

package net.skyscanner.backpack.compose.card.internal

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.card.BpkCardCorner
import net.skyscanner.backpack.compose.card.BpkCardElevation
import net.skyscanner.backpack.compose.card.BpkCardPadding
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkElevation
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal inline fun CardContent(
    padding: BpkCardPadding,
    contentAlignment: Alignment,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = Modifier.padding(
            all = when (padding) {
                BpkCardPadding.None -> 0.dp
                BpkCardPadding.Small -> BpkSpacing.Base
            },
        ),
        contentAlignment = contentAlignment,
        content = content,
    )
}

@Composable
internal fun cardColors(elevation: BpkCardElevation): CardColors {
    val value by animateColorAsState(
        targetValue = when (elevation) {
            BpkCardElevation.Focus -> BpkTheme.colors.surfaceElevated
            BpkCardElevation.None, BpkCardElevation.Default -> BpkTheme.colors.surfaceDefault
        },
        label = "BpkCard background color",
    )
    return CardDefaults.cardColors(
        containerColor = value,
        contentColor = BpkTheme.colors.textPrimary,
        disabledContainerColor = BpkTheme.colors.surfaceDefault,
        disabledContentColor = BpkTheme.colors.textPrimary,
    )
}

@Composable
internal fun cardElevation(elevation: BpkCardElevation): CardElevation {
    val value by animateDpAsState(
        targetValue = when (elevation) {
            BpkCardElevation.None -> 0.dp
            BpkCardElevation.Focus -> BpkElevation.Xl
            BpkCardElevation.Default -> BpkElevation.Sm
        },
        label = "BpkCard elevation",
    )
    return CardDefaults.cardElevation(
        defaultElevation = value,
        pressedElevation = value,
        focusedElevation = value,
        hoveredElevation = value,
        draggedElevation = value,
        disabledElevation = value,
    )
}

internal fun cardShape(corner: BpkCardCorner) =
    RoundedCornerShape(
        size = when (corner) {
            BpkCardCorner.Small -> BpkBorderRadius.Md
            BpkCardCorner.Large -> BpkBorderRadius.Lg
        },
    )
