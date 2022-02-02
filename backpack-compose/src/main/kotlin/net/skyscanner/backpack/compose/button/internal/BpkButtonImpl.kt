package net.skyscanner.backpack.compose.button.internal

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import net.skyscanner.backpack.compose.button.BpkButtonColors
import net.skyscanner.backpack.compose.button.BpkButtonIconPosition
import net.skyscanner.backpack.compose.button.BpkButtonSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius

@Composable
internal fun BpkButtonImpl(
  content: BpkButtonContent,
  modifier: Modifier = Modifier,
  size: BpkButtonSize = BpkButtonSize.Default,
  colors: BpkButtonColors = BpkButtonColors.Primary,
  enabled: Boolean = true,
  loading: Boolean = false,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  onClick: () -> Unit,
) {
  Button(
    onClick = onClick,
    enabled = enabled && !loading,
    modifier = modifier.requiredHeight(size.minHeight),
    interactionSource = interactionSource,
    colors = ButtonDefaults.buttonColors(
      backgroundColor = colors.backgroundColor(interactionSource),
      contentColor = colors.contentColor(interactionSource),
      disabledBackgroundColor = colors.disabledBackgroundColor(),
      disabledContentColor = colors.disabledContentColor(),
    ),
    shape = ButtonShape,
    contentPadding = PaddingValues(horizontal = size.horizontalPadding),
    elevation = null,
    content = {
      CompositionLocalProvider(LocalTextStyle provides size.textStyle()) {
        ButtonContent(content, size, loading)
      }
    },
  )
}

@Composable
private fun ButtonContent(content: BpkButtonContent, size: BpkButtonSize, loading: Boolean) {

  @Composable
  fun Progress() {
    CircularProgressIndicator(Modifier.requiredSize(size.iconSize))
  }

  @Composable
  fun Text(text: String) {
    BpkText(text)
  }


  @Composable
  fun Icon(icon: Painter, contentDescription: String?) {
    Icon(icon, contentDescription, Modifier.requiredSize(size.iconSize))
  }

  @Composable
  fun Spacer() {
    Spacer(Modifier.requiredWidth(size.horizontalSpacing))
  }


  return when (loading) {
    true -> Progress()
    else -> when (content) {
      is BpkButtonContent.Text -> {
        Text(content.text)
      }
      is BpkButtonContent.Icon -> {
        Icon(content.icon, content.contentDescription)
      }
      is BpkButtonContent.IconAndText -> when (content.position) {
        BpkButtonIconPosition.Start -> {
          Icon(content.icon, null)
          Spacer()
          Text(content.text)
        }
        BpkButtonIconPosition.End -> {
          Text(content.text)
          Spacer()
          Icon(content.icon, null)
        }
      }
    }
  }
}

private val ButtonShape = RoundedCornerShape(BpkBorderRadius.Sm)
