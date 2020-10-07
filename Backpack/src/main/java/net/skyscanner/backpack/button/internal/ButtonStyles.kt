package net.skyscanner.backpack.button.internal

import android.content.Context
import net.skyscanner.backpack.R

internal sealed class ButtonStyles : (Context) -> ButtonStyle {

  object Primary : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle(
      context = context,
      style = R.attr.bpkButtonPrimaryStyle,
      bgColorRes = R.color.bpkMonteverde,
      textColorRes = R.color.bpkWhite,
      strokeColorRes = android.R.color.transparent,
      strokeColorPressedRes = android.R.color.transparent,
      disabledBgColorRes = R.color.__buttonDisabledBackground,
      disabledTextColorRes = R.color.__buttonDisabledText,
      stateListAnimatorRes = R.drawable.bpk_button_state_animator
    )
  }

  object Secondary : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle(
      context = context,
      style = R.attr.bpkButtonSecondaryStyle,
      bgColorRes = R.color.__buttonSecondaryBackground,
      textColorRes = R.color.bpkPrimary,
      strokeColorRes = R.color.__buttonSecondaryBorder,
      strokeColorPressedRes = R.color.bpkPrimary,
      disabledBgColorRes = R.color.__buttonDisabledBackground,
      disabledTextColorRes = R.color.__buttonDisabledText,
      strokeWidthRes = R.dimen.bpkBorderSizeLg
    )
  }

  object Featured : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle(
      context = context,
      style = R.attr.bpkButtonFeaturedStyle,
      bgColorRes = R.color.bpkSkyBlue,
      textColorRes = R.color.bpkWhite,
      strokeColorRes = android.R.color.transparent,
      strokeColorPressedRes = android.R.color.transparent,
      disabledBgColorRes = R.color.__buttonDisabledBackground,
      disabledTextColorRes = R.color.__buttonDisabledText,
      stateListAnimatorRes = R.drawable.bpk_button_state_animator
    )
  }

  object Destructive : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle(
      context = context,
      style = R.attr.bpkButtonDestructiveStyle,
      bgColorRes = R.color.__buttonSecondaryBackground,
      textColorRes = R.color.bpkPanjin,
      strokeColorRes = R.color.__buttonSecondaryBorder,
      strokeColorPressedRes = R.color.bpkPanjin,
      disabledBgColorRes = R.color.__buttonDisabledBackground,
      disabledTextColorRes = R.color.__buttonDisabledText,
      strokeWidthRes = R.dimen.bpkBorderSizeLg
    )
  }

  object Outline : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle(
      context = context,
      style = R.attr.bpkButtonOutlineStyle,
      bgColorRes = android.R.color.transparent,
      textColorRes = R.color.bpkWhite,
      strokeColorRes = R.color.bpkWhite,
      strokeColorPressedRes = R.color.bpkWhite,
      disabledBgColorRes = R.color.bpkSkyGrayTint06,
      disabledTextColorRes = R.color.bpkSkyGrayTint04,
      strokeWidthRes = R.dimen.bpkBorderSizeLg
    )
  }

  object Link : ButtonStyles() {
    override fun invoke(context: Context) = ButtonStyle(
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
