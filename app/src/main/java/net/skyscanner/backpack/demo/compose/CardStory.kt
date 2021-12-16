package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.card.BpkCardCorner
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
fun CardStory() {
  Column(
    modifier = Modifier.padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Xxl),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
  ) {

    BpkCard(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f),
      onClick = {},
    ) {
      Box(contentAlignment = Alignment.Center) {
        BpkText("Small corners")
      }
    }

    BpkCard(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f),
      onClick = {},
      corner = BpkCardCorner.Large,
    ) {
      Box(contentAlignment = Alignment.Center) {
        BpkText("Large corners")
      }
    }

    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    val focus = remember { FocusInteraction.Focus() }

    BpkCard(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f),
      interactionSource = interactionSource,
      onClick = {
        if (!focused) {
          interactionSource.tryEmit(focus)
        } else {
          interactionSource.tryEmit(FocusInteraction.Unfocus(focus))
        }
      },
    ) {
      Box(contentAlignment = Alignment.Center) {
        BpkText(if (focused) "Tap to unfocus" else "Tap to focus")
      }
    }
  }
}
