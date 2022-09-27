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

package net.skyscanner.backpack.button.internal

import android.content.Context
import net.skyscanner.backpack.R

internal sealed class ButtonStyles : (Context) -> ButtonStyle {

  object Primary : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonPrimaryStyle,
      bgColorRes = R.color.__privateButtonPrimaryNormalBackground,
      bgPressedColorRes = R.color.__privateButtonPrimaryPressedBackground,
      contentColorRes = R.color.bpkTextOnDark,
    )
  }

  object Secondary : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonSecondaryStyle,
      bgColorRes = R.color.__privateButtonSecondaryNormalBackground,
      bgPressedColorRes = R.color.__privateButtonSecondaryPressedBackground,
      contentColorRes = R.color.bpkTextPrimary,
    )
  }

  object Featured : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonFeaturedStyle,
      bgColorRes = R.color.__privateButtonFeaturedNormalBackground,
      bgPressedColorRes = R.color.__privateButtonFeaturedPressedBackground,
      contentColorRes = R.color.bpkTextPrimaryInverse,
    )
  }

  object Destructive : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonDestructiveStyle,
      bgColorRes = R.color.__privateButtonDestructiveNormalBackground,
      bgPressedColorRes = R.color.__privateButtonDestructivePressedBackground,
      contentColorRes = R.color.__privateButtonDestructiveNormalForeground,
      contentPressedColorRes = R.color.bpkTextPrimaryInverse,
    )
  }

  object PrimaryOnDark : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonPrimaryOnDarkStyle,
      bgColorRes = R.color.__privateButtonPrimaryOnDarkNormalBackground,
      bgPressedColorRes = R.color.__privateButtonPrimaryOnDarkPressedBackground,
      bgDisabledColorRes = R.color.__privateButtonPrimaryOnDarkDisabledBackground,
      contentColorRes = R.color.bpkTextOnLight,
      contentDisabledColorRes = R.color.__privateButtonPrimaryOnDarkDisabledForeground,
      rippleColorRes = R.color.__buttonOnDarkRipple,
    )
  }

  object PrimaryOnLight : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonPrimaryOnLightStyle,
      bgColorRes = R.color.__privateButtonPrimaryOnLightNormalBackground,
      bgPressedColorRes = R.color.__privateButtonPrimaryOnLightPressedBackground,
      bgDisabledColorRes = R.color.__privateButtonPrimaryOnLightDisabledBackground,
      contentColorRes = R.color.bpkTextOnDark,
      contentDisabledColorRes = R.color.__privateButtonPrimaryOnLightDisabledForeground,
      rippleColorRes = R.color.__buttonOnLightRipple,
    )
  }

  object SecondaryOnDark : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonSecondaryOnDarkStyle,
      bgColorRes = R.color.__privateButtonSecondaryOnDarkNormalBackground,
      bgPressedColorRes = R.color.__privateButtonSecondaryOnDarkPressedBackground,
      bgDisabledColorRes = R.color.__privateButtonSecondaryOnDarkDisabledBackground,
      contentColorRes = R.color.bpkTextOnDark,
      contentDisabledColorRes = R.color.__privateButtonSecondaryOnDarkDisabledForeground,
      rippleColorRes = R.color.__buttonOnDarkRipple,
    )
  }

  object Link : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonLinkStyle,
      bgColorRes = android.R.color.transparent,
      bgDisabledColorRes = android.R.color.transparent,
      contentColorRes = R.color.bpkTextLink,
      contentPressedColorRes = R.color.__privateButtonLinkPressedForeground,
      rippleColorRes = R.color.__buttonLinkRipple,
    )
  }

  object LinkOnDark : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonLinkOnDarkStyle,
      bgColorRes = android.R.color.transparent,
      bgDisabledColorRes = android.R.color.transparent,
      contentColorRes = R.color.bpkTextOnDark,
      contentPressedColorRes = R.color.__privateButtonLinkOnDarkPressedForeground,
      contentDisabledColorRes = R.color.__privateButtonLinkOnDarkDisabledForeground,
      rippleColorRes = R.color.__buttonLinkOnDarkRipple,
    )
  }
}
