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

package net.skyscanner.backpack.compose.card

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.card.internal.BpkCardImpl

enum class BpkCardCorner {
    Small,
    Large,
}

enum class BpkCardPadding {
    None,
    Small,
}

enum class BpkCardElevation {
    None,
    Default,
    Focus,
}

enum class BpkCardStyle {
    onDefault,
    onContrast,
}

@Composable
fun BpkCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    corner: BpkCardCorner = BpkCardCorner.Small,
    padding: BpkCardPadding = BpkCardPadding.Small,
    cardStyle: BpkCardStyle = BpkCardStyle.onContrast,
    elevation: BpkCardElevation = BpkCardElevation.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    BpkCardImpl(
        modifier = modifier,
        corner = corner,
        padding = padding,
        cardStyle = cardStyle,
        elevation = elevation,
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
fun BpkCard(
    modifier: Modifier = Modifier,
    corner: BpkCardCorner = BpkCardCorner.Small,
    padding: BpkCardPadding = BpkCardPadding.Small,
    cardStyle: BpkCardStyle = BpkCardStyle.onContrast,
    elevation: BpkCardElevation = BpkCardElevation.Default,
    content: @Composable ColumnScope.() -> Unit,
) {
    BpkCardImpl(
        modifier = modifier,
        corner = corner,
        padding = padding,
        cardStyle = cardStyle,
        elevation = elevation,
        content = content,
    )
}
