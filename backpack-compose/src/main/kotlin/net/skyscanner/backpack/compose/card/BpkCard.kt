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

package net.skyscanner.backpack.compose.card

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import net.skyscanner.backpack.compose.card.internal.CardContent
import net.skyscanner.backpack.compose.card.internal.cardBackgroundColor
import net.skyscanner.backpack.compose.card.internal.cardElevation
import net.skyscanner.backpack.compose.card.internal.cardShape
import net.skyscanner.backpack.compose.theme.BpkTheme

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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BpkCard(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  corner: BpkCardCorner = BpkCardCorner.Small,
  padding: BpkCardPadding = BpkCardPadding.Small,
  elevation: BpkCardElevation = BpkCardElevation.Default,
  contentAlignment: Alignment = Alignment.TopStart,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  enabled: Boolean = true,
  onClickLabel: String? = null,
  role: Role? = null,
  content: @Composable BoxScope.() -> Unit,
) {
  Card(onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    shape = cardShape(corner),
    backgroundColor = cardBackgroundColor(elevation),
    contentColor = BpkTheme.colors.textPrimary,
    elevation = cardElevation(elevation),
    interactionSource = interactionSource,
    content = { CardContent(padding, contentAlignment, content) })
}

@Composable
fun BpkCard(
  modifier: Modifier = Modifier,
  corner: BpkCardCorner = BpkCardCorner.Small,
  padding: BpkCardPadding = BpkCardPadding.Small,
  contentAlignment: Alignment = Alignment.TopStart,
  elevation: BpkCardElevation = BpkCardElevation.Default,
  content: @Composable BoxScope.() -> Unit,
) {
  Card(
    modifier = modifier,
    shape = cardShape(corner),
    backgroundColor = cardBackgroundColor(elevation),
    contentColor = BpkTheme.colors.textPrimary,
    elevation = cardElevation(elevation),
    content = { CardContent(padding, contentAlignment, content) },
  )
}
