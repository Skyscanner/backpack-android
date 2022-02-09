package net.skyscanner.backpack.compose.button.internal

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import net.skyscanner.backpack.compose.button.BpkButtonColors
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.utils.animateAsColor
import net.skyscanner.backpack.compose.utils.dynamicColorOf

@Composable
internal fun BpkButtonColors.disabledBackgroundColor() =
  when (this) {
    BpkButtonColors.Link -> Color.Transparent
    else -> dynamicColorOf(BpkColor.SkyGrayTint06, BpkColor.BlackTint01)
  }

@Composable
internal fun BpkButtonColors.disabledContentColor() =
  dynamicColorOf(BpkColor.SkyGrayTint04, BpkColor.BlackTint03)

@Composable
internal fun BpkButtonColors.rippleColor() =
  when (this) {
    BpkButtonColors.Destructive -> dynamicColorOf(Color.Black, Color.White).copy(alpha = 0.1f)
    BpkButtonColors.Featured -> Color.Black.copy(alpha = 0.1f)
    BpkButtonColors.Link -> dynamicColorOf(Color.Black, dark = Color.White).copy(alpha = 0.2f)
    BpkButtonColors.PrimaryOnLight -> Color.White.copy(alpha = 0.2f)
    else -> Color.Black.copy(alpha = 0.2f)
  }

@Composable
internal fun BpkButtonColors.backgroundColor(interactionSource: InteractionSource) =
  when (this) {
    BpkButtonColors.Destructive -> interactionSource.animateAsColor(
      default = dynamicColorOf(BpkColor.SkyGrayTint06, BpkColor.BlackTint02),
      pressed = dynamicColorOf(Color(0xFFB22E45), Color(0xFFF85C76)),
    )
    BpkButtonColors.Featured -> interactionSource.animateAsColor(
      default = BpkTheme.colors.primary,
      pressed = dynamicColorOf(BpkColor.SkyBlueShade01, dark = BpkColor.SkyBlue),
    )
    BpkButtonColors.Link -> Color.Transparent
    BpkButtonColors.Primary -> BpkColor.Monteverde
    BpkButtonColors.PrimaryOnDark -> BpkColor.White
    BpkButtonColors.PrimaryOnLight -> BpkColor.SkyGray
    BpkButtonColors.Secondary -> dynamicColorOf(BpkColor.SkyGrayTint06, BpkColor.BlackTint02)
  }


@Composable
internal fun BpkButtonColors.contentColor(interactionSource: InteractionSource) =
  when (this) {
    BpkButtonColors.Destructive -> interactionSource.animateAsColor(
      default = dynamicColorOf(Color(0xFFB22E45), Color(0xFFF85C76)),
      pressed = dynamicColorOf(BpkColor.White, BpkColor.Black),
    )
    BpkButtonColors.Featured -> dynamicColorOf(BpkColor.White, BpkColor.SkyGray)
    BpkButtonColors.Link -> interactionSource.animateAsColor(
      default = BpkTheme.colors.primary,
      pressed = dynamicColorOf(BpkColor.SkyBlueShade01, BpkColor.SkyBlue),
    )
    BpkButtonColors.Primary -> dynamicColorOf(BpkColor.White, BpkColor.Black)
    BpkButtonColors.PrimaryOnDark -> BpkColor.SkyGray
    BpkButtonColors.PrimaryOnLight -> BpkColor.White
    BpkButtonColors.Secondary -> dynamicColorOf(BpkColor.SkyBlueShade01, BpkColor.SkyBlueTint01)
  }
