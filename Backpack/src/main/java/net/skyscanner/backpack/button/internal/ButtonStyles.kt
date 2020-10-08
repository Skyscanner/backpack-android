/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2020 Skyscanner Ltd
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
      textColorRes = R.color.bpkWhite,
      strokeColorRes = R.color.bpkMonteverde,
      strokeColorPressedRes = R.color.bpkMonteverde,
      disabledBgColorRes = R.color.__buttonDisabledBackground,
      disabledTextColorRes = R.color.__buttonDisabledText,
      stateListAnimatorRes = R.drawable.bpk_button_state_animator
    )
  }

  object Secondary : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonSecondaryStyle,
      bgColorRes = R.color.__buttonSecondaryBackground,
      textColorRes = R.color.bpkPrimary,
      strokeColorRes = R.color.__buttonSecondaryBorder,
      strokeColorPressedRes = R.color.bpkPrimary,
      disabledBgColorRes = R.color.__buttonDisabledBackground,
      disabledTextColorRes = R.color.__buttonDisabledText
    )
  }

  object Featured : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonFeaturedStyle,
      bgColorRes = R.color.bpkSkyBlue,
      textColorRes = R.color.bpkWhite,
      strokeColorRes = R.color.bpkSkyBlue,
      strokeColorPressedRes = R.color.bpkSkyBlue,
      disabledBgColorRes = R.color.__buttonDisabledBackground,
      disabledTextColorRes = R.color.__buttonDisabledText,
      stateListAnimatorRes = R.drawable.bpk_button_state_animator
    )
  }

  object Destructive : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonDestructiveStyle,
      bgColorRes = R.color.__buttonSecondaryBackground,
      textColorRes = R.color.bpkPanjin,
      strokeColorRes = R.color.__buttonSecondaryBorder,
      strokeColorPressedRes = R.color.bpkPanjin,
      disabledBgColorRes = R.color.__buttonDisabledBackground,
      disabledTextColorRes = R.color.__buttonDisabledText
    )
  }

  object Outline : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonOutlineStyle,
      bgColorRes = android.R.color.transparent,
      textColorRes = R.color.bpkWhite,
      strokeColorRes = R.color.bpkWhite,
      strokeColorPressedRes = R.color.bpkWhite,
      disabledBgColorRes = R.color.bpkSkyGrayTint06,
      disabledTextColorRes = R.color.bpkSkyGrayTint04
    )
  }

  object Link : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle.fromTheme(
      context = context,
      style = R.attr.bpkButtonLinkStyle,
      bgColorRes = android.R.color.transparent,
      textColorRes = R.color.bpkPrimary,
      strokeColorRes = android.R.color.transparent,
      strokeColorPressedRes = android.R.color.transparent,
      disabledBgColorRes = android.R.color.transparent,
      disabledTextColorRes = R.color.__buttonDisabledText
    )
  }
}
