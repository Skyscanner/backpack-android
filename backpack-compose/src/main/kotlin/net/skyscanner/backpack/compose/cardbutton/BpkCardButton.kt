package net.skyscanner.backpack.compose.cardbutton

import androidx.compose.runtime.Composable
import net.skyscanner.backpack.compose.cardbutton.internal.BpkSaveCardButtonImpl
import net.skyscanner.backpack.compose.cardbutton.internal.BpkShareCardButtonImpl

sealed class BpkCardButtonType {
  data class Save(val checked: Boolean, val onCheckedChange: (Boolean) -> Unit) : BpkCardButtonType()
  data class Share(val onClick: () -> Unit) : BpkCardButtonType()
}

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
fun BpkCardButton(
  type: BpkCardButtonType,
  contentDescription: String,
  style: BpkCardButtonStyle = BpkCardButtonStyle.Default,
  size: BpkCardButtonSize = BpkCardButtonSize.Default,
) {
  when (type) {
    is BpkCardButtonType.Save -> BpkSaveCardButtonImpl(
      checked = type.checked,
      contentDescription = contentDescription,
      style = style,
      size = size,
      onCheckedChange = type.onCheckedChange
    )
    is BpkCardButtonType.Share -> BpkShareCardButtonImpl(
      contentDescription = contentDescription,
      style = style,
      size = size,
      onClick = type.onClick
    )
  }
}
