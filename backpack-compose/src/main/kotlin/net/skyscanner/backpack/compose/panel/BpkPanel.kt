package net.skyscanner.backpack.compose.panel

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing

sealed interface BpkPanelPadding {

  object None : BpkPanelPadding

  object Base : BpkPanelPadding

}

@Composable
fun BpkPanel(
  modifier: Modifier = Modifier,
  contentAlignment: Alignment = Alignment.TopStart,
  propagateMinConstraints: Boolean = false,
  padding: BpkPanelPadding = BpkPanelPadding.Base,
  content: @Composable BoxScope.() -> Unit
) {
  Box(
    contentAlignment = contentAlignment,
    propagateMinConstraints = propagateMinConstraints,
    content = content,
    modifier = modifier
      .clip(PanelShape)
      .border(1.dp, BpkTheme.colors.line, PanelShape)
      .padding(
        all = when (padding) {
          BpkPanelPadding.None -> 0.dp
          BpkPanelPadding.Base -> BpkSpacing.Base
        },
      ),
  )
}

private val PanelShape = RoundedCornerShape(BpkBorderRadius.Md)
