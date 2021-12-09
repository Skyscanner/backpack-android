package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.card.BpkCardCorner
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
fun CardStory() {
  Column(Modifier.padding(BpkSpacing.Base), verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base)) {

    CardWithElevation(elevation = "1") {
      CardWithElevation(elevation = "2") {
        CardWithElevation(elevation = "3") {
          CardWithElevation(elevation = "Max") {}
        }
      }
    }

    BpkCard(
      modifier = Modifier.size(144.dp, 96.dp),
      onClick = {},
      corner = BpkCardCorner.Large,
    ) {
      Box(contentAlignment = Alignment.Center) {
        Text("Large corners")
      }
    }

    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    val focus = remember { FocusInteraction.Focus() }

    BpkCard(
      modifier = Modifier.size(144.dp, 96.dp),
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
        Text(if (focused) "Tap to unfocus" else "Tap to focus")
      }
    }
  }
}

@Composable
private fun CardWithElevation(
  elevation: String,
  content: @Composable () -> Unit,
) {
  BpkCard(onClick = {}) {
    Column(Modifier.padding(BpkSpacing.Base), verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base)) {
      Text("Elevation $elevation")
      content()
    }
  }
}
