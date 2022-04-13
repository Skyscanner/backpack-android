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

package net.skyscanner.backpack.compose.button.internal

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.utils.animateAsColor
import net.skyscanner.backpack.compose.utils.dynamicColorOf

@Composable
internal fun BpkButtonType.disabledBackgroundColor(): Color =
  when (this) {
    BpkButtonType.Link, BpkButtonType.LinkOnDark -> Color.Transparent
    else -> dynamicColorOf(BpkColor.SkyGrayTint06, BpkColor.BlackTint01)
  }

@Composable
internal fun BpkButtonType.disabledContentColor(): Color =
  when (this) {
    BpkButtonType.LinkOnDark -> Color.White.copy(alpha = 0.2f)
    else -> dynamicColorOf(BpkColor.SkyGrayTint04, BpkColor.BlackTint03)
  }

@Composable
internal fun BpkButtonType.rippleColor(): Color =
  when (this) {
    BpkButtonType.Destructive -> dynamicColorOf(Color.Black, Color.White).copy(alpha = 0.1f)
    BpkButtonType.Featured -> Color.Black.copy(alpha = 0.1f)
    BpkButtonType.Link -> dynamicColorOf(Color.Black, dark = Color.White).copy(alpha = 0.2f)
    BpkButtonType.PrimaryOnLight, BpkButtonType.SecondaryOnDark, BpkButtonType.LinkOnDark -> Color.White.copy(alpha = 0.2f)
    else -> Color.Black.copy(alpha = 0.2f)
  }

@Composable
internal fun BpkButtonType.backgroundColor(interactionSource: InteractionSource): Color =
  when (this) {
    BpkButtonType.Destructive -> interactionSource.animateAsColor(
      default = dynamicColorOf(BpkColor.SkyGrayTint06, BpkColor.BlackTint02),
      pressed = dynamicColorOf(Color(0xFFB22E45), Color(0xFFF85C76)),
    )
    BpkButtonType.Featured -> interactionSource.animateAsColor(
      default = BpkTheme.colors.primary,
      pressed = dynamicColorOf(BpkColor.SkyBlueShade01, dark = BpkColor.SkyBlue),
    )
    BpkButtonType.Link, BpkButtonType.LinkOnDark -> Color.Transparent
    BpkButtonType.Primary -> BpkColor.Monteverde
    BpkButtonType.PrimaryOnDark -> BpkColor.White
    BpkButtonType.PrimaryOnLight -> BpkColor.SkyGray
    BpkButtonType.Secondary -> dynamicColorOf(BpkColor.SkyGrayTint06, BpkColor.BlackTint02)
    BpkButtonType.SecondaryOnDark -> Color.White.copy(alpha = 0.1f)
  }


@Composable
internal fun BpkButtonType.contentColor(interactionSource: InteractionSource): Color =
  when (this) {
    BpkButtonType.Destructive -> interactionSource.animateAsColor(
      default = dynamicColorOf(Color(0xFFB22E45), Color(0xFFF85C76)),
      pressed = dynamicColorOf(BpkColor.White, BpkColor.Black),
    )
    BpkButtonType.Featured -> dynamicColorOf(BpkColor.White, BpkColor.SkyGray)
    BpkButtonType.Link -> interactionSource.animateAsColor(
      default = BpkTheme.colors.primary,
      pressed = dynamicColorOf(BpkColor.SkyBlueShade01, BpkColor.SkyBlue),
    )
    BpkButtonType.LinkOnDark -> interactionSource.animateAsColor(
      default = Color.White,
      pressed = Color.White.copy(alpha = 0.6f),
    )
    BpkButtonType.Primary -> dynamicColorOf(BpkColor.White, BpkColor.Black)
    BpkButtonType.PrimaryOnDark -> BpkColor.SkyGray
    BpkButtonType.PrimaryOnLight, BpkButtonType.SecondaryOnDark -> BpkColor.White
    BpkButtonType.Secondary -> dynamicColorOf(BpkColor.SkyBlueShade01, BpkColor.SkyBlueTint01)
  }

@Composable
internal fun loadingSpinnerColor(): Color =
  dynamicColorOf(BpkColor.SkyGrayTint04, BpkColor.BlackTint03)
