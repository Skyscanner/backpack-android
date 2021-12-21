/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkElevation

enum class BpkCardCorner {
  Small,
  Large,
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BpkCard(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  corner: BpkCardCorner = BpkCardCorner.Small,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  enabled: Boolean = true,
  onClickLabel: String? = null,
  role: Role? = null,
  content: @Composable () -> Unit,
) {

  val focused by interactionSource.collectIsFocusedAsState()
  val pressed by interactionSource.collectIsPressedAsState()
  val elevated = focused || pressed

  val elevation by animateDpAsState(
    when {
      elevated -> BpkElevation.Xl
      else -> BpkElevation.Base
    }
  )

  val backgroundColor by animateColorAsState(
    when {
      elevated -> BpkTheme.colors.backgroundElevation02
      else -> BpkTheme.colors.backgroundElevation01
    }
  )

  val size = when (corner) {
    BpkCardCorner.Small -> BpkBorderRadius.Md
    BpkCardCorner.Large -> BpkBorderRadius.Lg
  }

  Card(
    modifier = modifier,
    shape = RoundedCornerShape(size = size),
    backgroundColor = backgroundColor,
    contentColor = BpkTheme.colors.textPrimary,
    elevation = elevation,
    onClick = onClick,
    onClickLabel = onClickLabel,
    interactionSource = interactionSource,
    enabled = enabled,
    role = role,
    content = content,
  )

}

@Composable
fun BpkCard(
  modifier: Modifier = Modifier,
  corner: BpkCardCorner = BpkCardCorner.Small,
  content: @Composable () -> Unit,
) {

  val size = when (corner) {
    BpkCardCorner.Small -> BpkBorderRadius.Md
    BpkCardCorner.Large -> BpkBorderRadius.Lg
  }

  Card(
    modifier = modifier,
    shape = RoundedCornerShape(size = size),
    backgroundColor = BpkTheme.colors.backgroundElevation01,
    contentColor = BpkTheme.colors.textPrimary,
    elevation = BpkElevation.Base,
    content = content,
  )

}
