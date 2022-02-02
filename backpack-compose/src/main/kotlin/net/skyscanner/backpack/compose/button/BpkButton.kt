package net.skyscanner.backpack.compose.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import net.skyscanner.backpack.compose.button.internal.BpkButtonContent
import net.skyscanner.backpack.compose.button.internal.BpkButtonImpl

@Composable
fun BpkButton(
  text: String,
  modifier: Modifier = Modifier,
  size: BpkButtonSize = BpkButtonSize.Default,
  colors: BpkButtonColors = BpkButtonColors.Primary,
  enabled: Boolean = true,
  loading: Boolean = false,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  onClick: () -> Unit,
) {
  BpkButtonImpl(
    content = BpkButtonContent.Text(text),
    size = size,
    colors = colors,
    enabled = enabled,
    loading = loading,
    interactionSource = interactionSource,
    modifier = modifier,
    onClick = onClick,
  )
}

@Composable
fun BpkButton(
  icon: Painter,
  contentDescription: String,
  modifier: Modifier = Modifier,
  size: BpkButtonSize = BpkButtonSize.Default,
  colors: BpkButtonColors = BpkButtonColors.Primary,
  enabled: Boolean = true,
  loading: Boolean = false,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  onClick: () -> Unit,
) {
  BpkButtonImpl(
    content = BpkButtonContent.Icon(icon, contentDescription),
    size = size,
    colors = colors,
    enabled = enabled,
    loading = loading,
    interactionSource = interactionSource,
    modifier = modifier.requiredWidth(size.minHeight),
    onClick = onClick,
  )
}

@Composable
fun BpkButton(
  text: String,
  icon: Painter,
  position: BpkButtonIconPosition,
  modifier: Modifier = Modifier,
  size: BpkButtonSize = BpkButtonSize.Default,
  colors: BpkButtonColors = BpkButtonColors.Primary,
  enabled: Boolean = true,
  loading: Boolean = false,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  onClick: () -> Unit,
) {
  BpkButtonImpl(
    content = BpkButtonContent.IconAndText(icon, text, position),
    size = size,
    colors = colors,
    enabled = enabled,
    loading = loading,
    interactionSource = interactionSource,
    modifier = modifier,
    onClick = onClick,
  )
}
