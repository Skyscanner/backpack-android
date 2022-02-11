package net.skyscanner.backpack.compose.button.internal

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.button.BpkButtonSize
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.utils.hideContentIf

@Composable
internal fun BpkButtonImpl(
  modifier: Modifier = Modifier,
  size: BpkButtonSize = BpkButtonSize.Default,
  type: BpkButtonType = BpkButtonType.Primary,
  enabled: Boolean = true,
  loading: Boolean = false,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  onClick: () -> Unit,
  content: @Composable RowScope.() -> Unit,
) {
  CompositionLocalProvider(LocalRippleTheme provides ButtonRippleTheme(type.rippleColor())) {
    Button(
      onClick = onClick,
      enabled = enabled && !loading,
      modifier = modifier.requiredHeight(size.minHeight),
      interactionSource = interactionSource,
      colors = ButtonDefaults.buttonColors(
        backgroundColor = type.backgroundColor(interactionSource),
        contentColor = type.contentColor(interactionSource),
        disabledBackgroundColor = type.disabledBackgroundColor(),
        disabledContentColor = type.disabledContentColor(),
      ),
      shape = ButtonShape,
      contentPadding = PaddingValues(horizontal = size.horizontalPadding),
      elevation = null,
      content = {
        CompositionLocalProvider(LocalTextStyle provides size.textStyle()) {
          Box {
            Row(
              modifier = Modifier.hideContentIf(loading),
              horizontalArrangement = Arrangement.spacedBy(size.horizontalSpacing),
              verticalAlignment = Alignment.CenterVertically,
              content = content,
            )

            if (loading) {
              ButtonProgress(size, Modifier.align(Alignment.Center))
            }
          }
        }
      },
    )
  }
}

@Composable
internal fun ButtonIcon(
  icon: Painter,
  contentDescription: String?,
  size: BpkButtonSize,
  modifier: Modifier = Modifier,
) {
  Icon(icon, contentDescription, modifier.requiredSize(size.iconSize))
}

@Composable
internal fun ButtonText(text: String, modifier: Modifier = Modifier) {
  BpkText(text, modifier)
}

@Composable
internal fun ButtonProgress(size: BpkButtonSize, modifier: Modifier = Modifier) {
  CircularProgressIndicator(
    modifier = modifier.requiredSize(size.iconSize),
    strokeWidth = 2.dp,
    color = LocalContentColor.current,
  )
}

private class ButtonRippleTheme(
  private val color: Color = Color.Black,
) : RippleTheme {

  private val alpha = RippleAlpha(color.alpha, color.alpha, color.alpha, color.alpha)

  @Composable
  override fun defaultColor(): Color =
    color

  @Composable
  override fun rippleAlpha(): RippleAlpha =
    alpha

}

private val ButtonShape = RoundedCornerShape(BpkBorderRadius.Sm)
