package net.skyscanner.backpack.compose.button

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.tokens.BpkColors
import net.skyscanner.backpack.compose.utils.animateAsColor
import net.skyscanner.backpack.compose.utils.dynamicColorOf

sealed class BpkButtonColors {

  @Composable
  internal open fun disabledBackgroundColor(): Color =
    dynamicColorOf(BpkColor.SkyGrayTint06, BpkColor.BlackTint01)

  @Composable
  internal open fun disabledContentColor(): Color =
    dynamicColorOf(BpkColor.SkyGrayTint04, BpkColor.BlackTint03)

  @Composable
  internal open fun rippleColor(): Color =
    Color.Black.copy(alpha = 0.2f)

  @Composable
  internal abstract fun backgroundColor(interactionSource: InteractionSource): Color

  @Composable
  internal abstract fun contentColor(interactionSource: InteractionSource): Color

  object Primary : BpkButtonColors() {

    @Composable
    override fun backgroundColor(interactionSource: InteractionSource): Color =
      interactionSource.animateAsColor(
        default = BpkColor.Monteverde,
      )

    @Composable
    override fun contentColor(interactionSource: InteractionSource): Color =
      dynamicColorOf(BpkColor.White, BpkColor.Black)

  }

  object Secondary : BpkButtonColors() {

    @Composable
    override fun backgroundColor(interactionSource: InteractionSource): Color =
      interactionSource.animateAsColor(
        default = dynamicColorOf(BpkColor.SkyGrayTint06, BpkColor.BlackTint02),
      )

    @Composable
    override fun contentColor(interactionSource: InteractionSource): Color =
      dynamicColorOf(BpkColor.SkyBlueShade01, BpkColor.SkyBlueTint01)

  }

  object PrimaryOnDark : BpkButtonColors() {

    @Composable
    override fun backgroundColor(interactionSource: InteractionSource): Color =
      interactionSource.animateAsColor(
        default = BpkColor.White,
      )

    @Composable
    override fun contentColor(interactionSource: InteractionSource): Color =
      BpkColor.SkyGray

  }

  object PrimaryOnLight : BpkButtonColors() {

    @Composable
    override fun backgroundColor(interactionSource: InteractionSource): Color =
      interactionSource.animateAsColor(
        default = BpkColor.SkyGray,
      )

    @Composable
    override fun rippleColor(): Color =
      Color.White.copy(alpha = 0.2f)

    @Composable
    override fun contentColor(interactionSource: InteractionSource): Color =
      BpkColor.White

  }

  object Featured : BpkButtonColors() {

    @Composable
    override fun backgroundColor(interactionSource: InteractionSource): Color =
      interactionSource.animateAsColor(
        default = BpkTheme.colors.primary,
        pressed = dynamicColorOf(BpkColor.SkyBlueShade01, dark = BpkColor.SkyBlue),
      )

    @Composable
    override fun rippleColor() : Color =
      Color.Black.copy(alpha = 0.1f)

    @Composable
    override fun contentColor(interactionSource: InteractionSource): Color =
      dynamicColorOf(BpkColor.White, BpkColor.SkyGray)

  }

  object Destructive : BpkButtonColors() {

    @Composable
    override fun backgroundColor(interactionSource: InteractionSource): Color =
      interactionSource.animateAsColor(
        default = dynamicColorOf(BpkColor.SkyGrayTint06, BpkColor.BlackTint02),
        pressed = dynamicColorOf(Color(0xFFB22E45), Color(0xFFF85C76)),
      )

    @Composable
    override fun rippleColor() : Color =
      dynamicColorOf(Color.Black, Color.White).copy(alpha = 0.1f)

    @Composable
    override fun contentColor(interactionSource: InteractionSource): Color =
      interactionSource.animateAsColor(
        default = dynamicColorOf(Color(0xFFB22E45), Color(0xFFF85C76)),
        pressed = dynamicColorOf(BpkColor.White, BpkColor.Black),
      )

  }

  object Link : BpkButtonColors() {

    @Composable
    override fun disabledBackgroundColor(): Color =
      Color.Transparent

    @Composable
    override fun backgroundColor(interactionSource: InteractionSource): Color =
      Color.Transparent

    @Composable
    override fun rippleColor() : Color =
      dynamicColorOf(Color.Black, dark = Color.White).copy(alpha = 0.2f)

    @Composable
    override fun contentColor(interactionSource: InteractionSource): Color =
      interactionSource.animateAsColor(
        default = BpkTheme.colors.primary,
        pressed = dynamicColorOf(BpkColor.SkyBlueShade01, BpkColor.SkyBlue),
      )

  }

}
