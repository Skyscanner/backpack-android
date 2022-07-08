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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
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
internal fun cardBackgroundColor(elevation: BpkCardElevation): Color =
  animateColorAsState(
    when(elevation) {
      BpkCardElevation.Focus -> BpkTheme.colors.backgroundElevation02
      BpkCardElevation.None, BpkCardElevation.Default -> BpkTheme.colors.backgroundElevation01
    }
  ).value


@Composable
internal fun cardElevation(elevation: BpkCardElevation): Dp =
  animateDpAsState(
    when(elevation) {
      BpkCardElevation.None -> 0.dp
      BpkCardElevation.Focus -> BpkElevation.Xl
      BpkCardElevation.Default -> BpkElevation.Sm
    }
  ).value

internal fun cardShape(corner: BpkCardCorner) =
  RoundedCornerShape(
    size = when (corner) {
      BpkCardCorner.Small -> BpkBorderRadius.Md
      BpkCardCorner.Large -> BpkBorderRadius.Lg
    }
  )
