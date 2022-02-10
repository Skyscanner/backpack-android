package net.skyscanner.backpack.compose.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import net.skyscanner.backpack.compose.button.internal.BpkButtonImpl
import net.skyscanner.backpack.compose.button.internal.ButtonIcon
import net.skyscanner.backpack.compose.button.internal.ButtonText
import net.skyscanner.backpack.compose.button.internal.minHeight

enum class BpkButtonIconPosition {
  Start,
  End,
}

enum class BpkButtonSize {
  Default,
  Large,
}

enum class BpkButtonType {
  Primary,
  Secondary,
  PrimaryOnDark,
  PrimaryOnLight,
  Featured,
  Destructive,
  Link,
}


@Composable
fun BpkButton(
  text: String,
  modifier: Modifier = Modifier,
  size: BpkButtonSize = DefaultSize,
  type: BpkButtonType = DefaultType,
  enabled: Boolean = DefaultEnabled,
  loading: Boolean = DefaultLoading,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  onClick: () -> Unit,
) {
  BpkButtonImpl(
    size = size,
    type = type,
    enabled = enabled,
    loading = loading,
    interactionSource = interactionSource,
    modifier = modifier,
    onClick = onClick,
    content = { ButtonText(text) },
  )
}

@Composable
fun BpkButton(
  icon: Painter,
  contentDescription: String,
  modifier: Modifier = Modifier,
  size: BpkButtonSize = DefaultSize,
  type: BpkButtonType = DefaultType,
  enabled: Boolean = DefaultEnabled,
  loading: Boolean = DefaultLoading,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  onClick: () -> Unit,
) {
  BpkButtonImpl(
    size = size,
    type = type,
    enabled = enabled,
    loading = loading,
    interactionSource = interactionSource,
    modifier = modifier.requiredWidth(size.minHeight),
    onClick = onClick,
    content = { ButtonIcon(icon, contentDescription, size) },
  )
}

@Composable
fun BpkButton(
  text: String,
  icon: Painter,
  position: BpkButtonIconPosition,
  modifier: Modifier = Modifier,
  size: BpkButtonSize = DefaultSize,
  type: BpkButtonType = DefaultType,
  enabled: Boolean = DefaultEnabled,
  loading: Boolean = DefaultLoading,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
  onClick: () -> Unit,
) {
  BpkButtonImpl(
    size = size,
    type = type,
    enabled = enabled,
    loading = loading,
    interactionSource = interactionSource,
    modifier = modifier,
    onClick = onClick,
  ) {
    when (position) {
      BpkButtonIconPosition.Start -> {
        ButtonIcon(icon, null, size)
        ButtonText(text)
      }
      BpkButtonIconPosition.End -> {
        ButtonText(text)
        ButtonIcon(icon, null, size)
      }
    }
  }
}

private val DefaultSize = BpkButtonSize.Default
private val DefaultType = BpkButtonType.Primary
private const val DefaultEnabled = true
private const val DefaultLoading = false
