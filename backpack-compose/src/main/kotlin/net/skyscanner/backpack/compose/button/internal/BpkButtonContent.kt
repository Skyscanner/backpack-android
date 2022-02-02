package net.skyscanner.backpack.compose.button.internal

import androidx.compose.ui.graphics.painter.Painter
import net.skyscanner.backpack.compose.button.BpkButtonIconPosition

internal sealed interface BpkButtonContent {

  data class Icon(val icon: Painter, val contentDescription: String) : BpkButtonContent

  @JvmInline
  value class Text(val text: String) : BpkButtonContent

  data class IconAndText(val icon: Painter, val text: String, val position: BpkButtonIconPosition) : BpkButtonContent

}
