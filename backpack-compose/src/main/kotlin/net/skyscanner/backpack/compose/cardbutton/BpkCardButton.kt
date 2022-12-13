package net.skyscanner.backpack.compose.cardbutton

import androidx.compose.runtime.Composable
import net.skyscanner.backpack.compose.cardbutton.internal.BpkSaveCardButtonImpl
import net.skyscanner.backpack.compose.cardbutton.internal.BpkShareCardButtonImpl

enum class BpkCardButtonStyle {
  Default,
  Contained,
  OnDark
}

enum class BpkCardButtonSize {
  Default,
  Small
}

@Composable
fun BpkSaveButton(
  checked: Boolean,
  contentDescription: String,
  onCheckedChange: (Boolean) -> Unit,
  size: BpkCardButtonSize = BpkCardButtonSize.Default,
  style: BpkCardButtonStyle = BpkCardButtonStyle.Default,
) {
  BpkSaveCardButtonImpl(
    checked = checked,
    contentDescription = contentDescription,
    style = style,
    size = size,
    onCheckedChange = onCheckedChange
  )
}

@Composable
fun BpkShareButton(
  contentDescription: String,
  onClick: () -> Unit,
  size: BpkCardButtonSize = BpkCardButtonSize.Default,
  style: BpkCardButtonStyle = BpkCardButtonStyle.Default,
) {
  BpkShareCardButtonImpl(
    contentDescription = contentDescription,
    style = style,
    size = size,
    onClick = onClick
  )
}
