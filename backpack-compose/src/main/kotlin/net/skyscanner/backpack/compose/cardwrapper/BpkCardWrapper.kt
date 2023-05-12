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

package net.skyscanner.backpack.compose.cardwrapper

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.card.BpkCardCorner
import net.skyscanner.backpack.compose.card.BpkCardElevation
import net.skyscanner.backpack.compose.card.internal.cardShape
import net.skyscanner.backpack.compose.tokens.BpkBorderSize

@Composable
fun BpkCardWrapper(
    backgroundColor: Color,
    headerContent: @Composable () -> Unit,
    cardContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    corner: BpkCardCorner = BpkCardCorner.Small,
    elevation: BpkCardElevation = BpkCardElevation.Default,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = cardShape(corner),
        color = backgroundColor,
        shadowElevation = 0.dp,
        tonalElevation = 0.dp,
        border = BorderStroke(width = BpkBorderSize.Lg, color = backgroundColor),
    ) {
        Column {
            headerContent.invoke()
            BpkCard(
                modifier = Modifier
                    .background(color = backgroundColor, shape = cardShape(corner))
                    .border(width = BpkBorderSize.Lg, color = backgroundColor, shape = cardShape(corner))
                    .padding(BpkBorderSize.Lg),
                elevation = elevation,
            ) {
                cardContent.invoke()
            }
        }
    }
}
