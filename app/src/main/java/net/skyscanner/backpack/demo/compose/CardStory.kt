/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    val cardModifier = Modifier
      .fillMaxWidth()
      .weight(1f)

    BpkCard(
      modifier = cardModifier,
      onClick = {},
    ) {
      Box(contentAlignment = Alignment.Center) {
        BpkText("Small corners")
      }
    }

    BpkCard(
      modifier = cardModifier,
      onClick = {},
      corner = BpkCardCorner.Large,
    ) {
      Box(contentAlignment = Alignment.Center) {
        BpkText("Large corners")
      }
    }

    BpkCard(cardModifier) {
      Box(contentAlignment = Alignment.Center) {
        BpkText("Non clickable")
      }
    }

    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    val focus = remember { FocusInteraction.Focus() }

    BpkCard(
      modifier = cardModifier,
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
