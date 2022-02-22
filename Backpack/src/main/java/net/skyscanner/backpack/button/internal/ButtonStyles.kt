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
      bgColorRes = R.color.bpk_button_bg_primary,
      contentColorRes = R.color.bpk_button_content_primary,
      rippleColorRes = R.color.__buttonDefaultRipple,
    )
  }

  object Secondary : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonSecondaryStyle,
      bgColorRes = R.color.bpk_button_bg_secondary,
      contentColorRes = R.color.bpk_button_content_secondary,
      rippleColorRes = R.color.__buttonDefaultRipple,
    )
  }

  object Featured : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonFeaturedStyle,
      bgColorRes = R.color.bpk_button_bg_featured,
      contentColorRes = R.color.bpk_button_content_featured,
      rippleColorRes = R.color.__buttonFeaturedRipple,
    )
  }

  object Destructive : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonDestructiveStyle,
      bgColorRes = R.color.bpk_button_bg_destructive,
      contentColorRes = R.color.bpk_button_content_destructive,
      rippleColorRes = R.color.__buttonDestructiveRipple,
    )
  }

  object Outline : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonOutlineStyle,
      bgColorRes = android.R.color.transparent,
      contentColorRes = R.color.bpkWhite,
      rippleColorRes = R.color.__buttonDefaultRipple,
    )
  }

  object Link : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonLinkStyle,
      bgColorRes = R.color.bpk_button_bg_link,
      contentColorRes = R.color.bpk_button_content_link,
      rippleColorRes = R.color.__buttonLinkRipple,
    )
  }
}
