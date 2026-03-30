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

package net.skyscanner.backpack.compose.cardwrapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import net.skyscanner.backpack.compose.card.BpkCardCorner
import net.skyscanner.backpack.compose.card.BpkCardElevation
import net.skyscanner.backpack.compose.card.BpkCardPadding
import net.skyscanner.backpack.compose.card.BpkCardStyle
import net.skyscanner.backpack.compose.cardwrapper.internal.BpkCardWrapperImpl

@Composable
fun BpkCardWrapper(
    backgroundColor: Color,
    headerContent: @Composable () -> Unit,
    cardContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    cardPadding: BpkCardPadding = BpkCardPadding.Small,
    corner: BpkCardCorner = BpkCardCorner.Small,
    cardStyle: BpkCardStyle = BpkCardStyle.onContrast,
    elevation: BpkCardElevation = BpkCardElevation.Default,
) {
    BpkCardWrapperImpl(
        backgroundColor = backgroundColor,
        headerContent = headerContent,
        cardContent = cardContent,
        modifier = modifier,
        cardPadding = cardPadding,
        corner = corner,
        cardStyle = cardStyle,
        elevation = elevation,
    )
}
