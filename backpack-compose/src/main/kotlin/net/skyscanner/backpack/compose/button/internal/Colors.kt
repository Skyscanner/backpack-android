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
import net.skyscanner.backpack.compose.tokens.internal.BpkButtonColors
import net.skyscanner.backpack.compose.utils.animateAsColor
import net.skyscanner.backpack.compose.utils.dynamicColorOf

@Composable
internal fun BpkButtonType.disabledBackgroundColor(): Color =
  when (this) {
    BpkButtonType.PrimaryOnDark -> BpkButtonColors.primaryOnDarkDisabledBackground
    BpkButtonType.PrimaryOnLight -> BpkButtonColors.primaryOnLightDisabledBackground
    BpkButtonType.SecondaryOnDark -> BpkButtonColors.secondaryOnDarkDisabledBackground
    BpkButtonType.Link, BpkButtonType.LinkOnDark -> Color.Transparent
    else -> BpkButtonColors.disabledBackground
  }

@Composable
internal fun BpkButtonType.disabledContentColor(): Color =
  when (this) {
    BpkButtonType.PrimaryOnDark -> BpkButtonColors.primaryOnDarkDisabledForeground
    BpkButtonType.PrimaryOnLight -> BpkButtonColors.primaryOnLightDisabledForeground
    BpkButtonType.SecondaryOnDark -> BpkButtonColors.secondaryOnDarkDisabledForeground
    BpkButtonType.LinkOnDark -> BpkButtonColors.linkOnDarkDisabledForeground
    else -> BpkTheme.colors.textDisabled
  }

@Composable
internal fun BpkButtonType.rippleColor(): Color =
  when (this) {
    BpkButtonType.PrimaryOnLight -> Color.White.copy(alpha = 0.1f)
    BpkButtonType.PrimaryOnDark, BpkButtonType.SecondaryOnDark -> Color.Black.copy(alpha = 0.1f)
    BpkButtonType.Link -> dynamicColorOf(Color.Black, dark = Color.White).copy(alpha = 0.2f)
    BpkButtonType.LinkOnDark -> Color.White.copy(alpha = 0.2f)
    else -> dynamicColorOf(Color.Black, Color.White).copy(alpha = 0.1f)
  }

@Composable
internal fun BpkButtonType.backgroundColor(interactionSource: InteractionSource): Color =
  interactionSource.animateAsColor(
    default = defaultBackgroundColor(),
    pressed = pressedBackgroundColor(),
  )

@Composable
private fun BpkButtonType.defaultBackgroundColor(): Color =
  when (this) {
    BpkButtonType.Primary -> BpkButtonColors.primaryNormalBackground
    BpkButtonType.Secondary -> BpkButtonColors.secondaryNormalBackground
    BpkButtonType.Featured -> BpkButtonColors.featuredNormalBackground
    BpkButtonType.PrimaryOnDark -> BpkButtonColors.primaryOnDarkNormalBackground
    BpkButtonType.PrimaryOnLight -> BpkButtonColors.primaryOnLightNormalBackground
    BpkButtonType.SecondaryOnDark -> BpkButtonColors.secondaryOnDarkNormalBackground
    BpkButtonType.Destructive -> BpkButtonColors.destructiveNormalBackground
    BpkButtonType.Link, BpkButtonType.LinkOnDark -> Color.Transparent
  }

@Composable
private fun BpkButtonType.pressedBackgroundColor(): Color =
  when (this) {
    BpkButtonType.Primary -> BpkButtonColors.primaryPressedBackground
    BpkButtonType.Secondary -> BpkButtonColors.secondaryPressedBackground
    BpkButtonType.Featured -> BpkButtonColors.featuredPressedBackground
    BpkButtonType.PrimaryOnDark -> BpkButtonColors.primaryOnDarkPressedBackground
    BpkButtonType.PrimaryOnLight -> BpkButtonColors.primaryOnLightPressedBackground
    BpkButtonType.SecondaryOnDark -> BpkButtonColors.secondaryOnDarkPressedBackground
    BpkButtonType.Destructive -> BpkButtonColors.destructivePressedBackground
    BpkButtonType.Link, BpkButtonType.LinkOnDark -> Color.Transparent
  }

@Composable
internal fun BpkButtonType.contentColor(interactionSource: InteractionSource): Color =
  interactionSource.animateAsColor(
    default = defaultContentColor(),
    pressed = pressedContentColor(),
  )

@Composable
private fun BpkButtonType.defaultContentColor(): Color =
  when (this) {
    BpkButtonType.Primary -> BpkTheme.colors.textOnDark
    BpkButtonType.Secondary -> BpkTheme.colors.textPrimary
    BpkButtonType.Featured -> BpkTheme.colors.textPrimaryInverse
    BpkButtonType.PrimaryOnDark -> BpkTheme.colors.textOnLight
    BpkButtonType.PrimaryOnLight, BpkButtonType.SecondaryOnDark -> BpkTheme.colors.textOnDark
    BpkButtonType.Destructive -> BpkButtonColors.destructiveNormalForeground
    BpkButtonType.Link -> BpkTheme.colors.textLink
    BpkButtonType.LinkOnDark -> BpkTheme.colors.textOnDark
  }

@Composable
private fun BpkButtonType.pressedContentColor(): Color =
  when (this) {
    BpkButtonType.Primary -> BpkTheme.colors.textOnDark
    BpkButtonType.Secondary -> BpkTheme.colors.textPrimary
    BpkButtonType.Featured -> BpkTheme.colors.textPrimaryInverse
    BpkButtonType.PrimaryOnDark -> BpkTheme.colors.textOnLight
    BpkButtonType.PrimaryOnLight, BpkButtonType.SecondaryOnDark -> BpkTheme.colors.textOnDark
    BpkButtonType.Destructive -> BpkTheme.colors.textPrimaryInverse
    BpkButtonType.Link -> BpkButtonColors.linkPressedForeground
    BpkButtonType.LinkOnDark -> BpkButtonColors.linkOnDarkPressedForeground
  }

@Composable
internal fun BpkButtonType.loadingBackgroundColor(): Color = pressedBackgroundColor()

@Composable
internal fun BpkButtonType.loadingContentColor(): Color = pressedContentColor()
