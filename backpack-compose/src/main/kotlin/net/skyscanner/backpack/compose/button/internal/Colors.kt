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
internal fun BpkButtonType.disabledBackgroundColor() =
  when (this) {
    BpkButtonType.Link -> Color.Transparent
    else -> dynamicColorOf(BpkColor.SkyGrayTint06, BpkColor.BlackTint01)
  }

@Composable
internal fun BpkButtonType.disabledContentColor() =
  dynamicColorOf(BpkColor.SkyGrayTint04, BpkColor.BlackTint03)

@Composable
internal fun BpkButtonType.rippleColor() =
  when (this) {
    BpkButtonType.Destructive -> dynamicColorOf(Color.Black, Color.White).copy(alpha = 0.1f)
    BpkButtonType.Featured -> Color.Black.copy(alpha = 0.1f)
    BpkButtonType.Link -> dynamicColorOf(Color.Black, dark = Color.White).copy(alpha = 0.2f)
    BpkButtonType.PrimaryOnLight -> Color.White.copy(alpha = 0.2f)
    else -> Color.Black.copy(alpha = 0.2f)
  }

@Composable
internal fun BpkButtonType.backgroundColor(interactionSource: InteractionSource) =
  when (this) {
    BpkButtonType.Destructive -> interactionSource.animateAsColor(
      default = dynamicColorOf(BpkColor.SkyGrayTint06, BpkColor.BlackTint02),
      pressed = dynamicColorOf(Color(0xFFB22E45), Color(0xFFF85C76)),
    )
    BpkButtonType.Featured -> interactionSource.animateAsColor(
      default = BpkTheme.colors.primary,
      pressed = dynamicColorOf(BpkColor.SkyBlueShade01, dark = BpkColor.SkyBlue),
    )
    BpkButtonType.Link -> Color.Transparent
    BpkButtonType.Primary -> BpkColor.Monteverde
    BpkButtonType.PrimaryOnDark -> BpkColor.White
    BpkButtonType.PrimaryOnLight -> BpkColor.SkyGray
    BpkButtonType.Secondary -> dynamicColorOf(BpkColor.SkyGrayTint06, BpkColor.BlackTint02)
  }


@Composable
internal fun BpkButtonType.contentColor(interactionSource: InteractionSource) =
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
    BpkButtonType.Primary -> dynamicColorOf(BpkColor.White, BpkColor.Black)
    BpkButtonType.PrimaryOnDark -> BpkColor.SkyGray
    BpkButtonType.PrimaryOnLight -> BpkColor.White
    BpkButtonType.Secondary -> dynamicColorOf(BpkColor.SkyBlueShade01, BpkColor.SkyBlueTint01)
  }
