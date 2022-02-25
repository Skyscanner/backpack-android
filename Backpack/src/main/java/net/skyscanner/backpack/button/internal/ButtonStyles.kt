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
      bgColorRes = R.color.bpkMonteverde,
      contentColorRes = R.color.bpk_button_content_primary,
    )
  }

  object Secondary : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonSecondaryStyle,
      bgColorRes = R.color.__buttonSecondaryBackground,
      contentColorRes = R.color.bpk_button_content_secondary,
    )
  }

  object Featured : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonFeaturedStyle,
      bgColorRes = R.color.bpkPrimary,
      bgPressedColorRes = R.color.__buttonFeaturedPressedBackground,
      contentColorRes = R.color.bpk_button_content_featured,
      rippleColorRes = R.color.__buttonFeaturedRipple,
    )
  }

  object Destructive : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonDestructiveStyle,
      bgColorRes = R.color.__buttonDestructiveBackground,
      bgPressedColorRes = R.color.__buttonDestructivePressedBackground,
      contentColorRes = R.color.bpk_button_content_destructive,
      rippleColorRes = R.color.__buttonDestructiveRipple,
    )
  }

  object PrimaryOnDark : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonPrimaryOnDarkStyle,
      bgColorRes = R.color.bpkWhite,
      contentColorRes = R.color.bpk_button_content_primary_on_dark,
    )
  }

  object PrimaryOnLight : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonPrimaryOnLightStyle,
      bgColorRes = R.color.bpkSkyGray,
      contentColorRes = R.color.bpk_button_content_primary_on_light,
      rippleColorRes = R.color.__buttonPrimaryOnLightRipple,
    )
  }

  object Link : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonLinkStyle,
      bgColorRes = android.R.color.transparent,
      bgDisabledColorRes = android.R.color.transparent,
      contentColorRes = R.color.bpk_button_content_link,
      rippleColorRes = R.color.__buttonLinkRipple,
    )
  }
}
