/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.inventorydividedcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.card.BpkCardCorner
import net.skyscanner.backpack.compose.card.BpkCardPadding
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.utils.clickable

@Composable
fun BpkInventoryDividedCard(
  primaryContent: @Composable () -> Unit,
  secondaryContent: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  onClick: (() -> Unit)? = null,
) {
  BpkCard(
    modifier = modifier
      .then(onClick?.let { Modifier.clickable { it.invoke() } } ?: Modifier),
    corner = BpkCardCorner.Small,
    padding = BpkCardPadding.None,
  ) {
    Column {
      primaryContent.invoke()
      Spacer(
        modifier = Modifier
          .height(1.dp)
          .fillMaxWidth()
          .background(color = BpkTheme.colors.surfaceHighlight),
      )
      secondaryContent.invoke()
    }
  }
}

fun Modifier.inventoryDividedCardWidth(cardWidth: Dp): Modifier {
  val minWidth = 240.dp
  val width = if (cardWidth < minWidth) minWidth else cardWidth
  return requiredWidth(width)
}
