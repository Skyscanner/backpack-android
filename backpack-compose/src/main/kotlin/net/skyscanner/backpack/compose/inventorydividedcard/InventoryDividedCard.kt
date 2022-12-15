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
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.card.BpkCardCorner
import net.skyscanner.backpack.compose.card.BpkCardPadding
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.utils.clickable

@Composable
fun InventoryDividedCard(
  primaryContent: @Composable () -> Unit,
  secondaryContent: @Composable () -> Unit,
  onClick: (() -> Unit)? = null,
) {
  BpkCard(
    modifier = Modifier
      .clickable { onClick?.invoke() },
    corner = BpkCardCorner.Small,
    padding = BpkCardPadding.None,
  ) {
    Column(
      modifier = Modifier
        .requiredWidthIn(min = 240.dp)
    ) {
      primaryContent.invoke()
      Spacer(
        modifier = Modifier
          .height(1.dp)
          .width(IntrinsicSize.Max)
          .background(color = BpkTheme.colors.surfaceHighlight)
      )
      secondaryContent.invoke()
    }
  }
}
