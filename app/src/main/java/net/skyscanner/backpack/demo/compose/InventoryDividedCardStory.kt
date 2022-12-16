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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.inventorydividedcard.BpkInventoryDividedCard
import net.skyscanner.backpack.compose.inventorydividedcard.inventoryDividedCardWidth
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
@Preview
fun InventoryDividedCardStory(
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(BpkSpacing.Base),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    BpkInventoryDividedCard(
      modifier = Modifier.fillMaxWidth(),
      primaryContent = {
        Image(
          modifier = Modifier
            .height(BpkSpacing.Xxl * 2)
            .fillMaxWidth(),
          painter = painterResource(id = R.drawable.canadian_rockies_canada),
          contentDescription = "",
          contentScale = ContentScale.Crop
        )
      },
      secondaryContent = {
        BpkText(
          modifier = Modifier.fillMaxWidth(),
          text = stringResource(id = R.string.inventory_divided_card_with_more_than_minimum),
          style = BpkTheme.typography.bodyDefault,
          textAlign = TextAlign.Center
        )
      },
      onClick = {}
    )

    Spacer(modifier = Modifier.height(BpkSpacing.Base))

    BpkInventoryDividedCard(
      modifier = Modifier.inventoryDividedCardWidth(BpkSpacing.Xxl),
      primaryContent = {
        Image(
          modifier = Modifier
            .height(BpkSpacing.Xxl * 2)
            .fillMaxWidth(),
          painter = painterResource(id = R.drawable.canadian_rockies_canada),
          contentDescription = "",
          contentScale = ContentScale.Crop
        )
      },
      secondaryContent = {
        BpkText(
          modifier = Modifier.fillMaxWidth(),
          text = stringResource(id = R.string.inventory_divided_card_with_less_than_minimum),
          style = BpkTheme.typography.bodyDefault,
          textAlign = TextAlign.Center
        )
      },
      onClick = {}
    )

    Spacer(modifier = Modifier.height(BpkSpacing.Base))

    BpkInventoryDividedCard(
      modifier = Modifier.fillMaxWidth(),
      primaryContent = {
        Spacer(modifier = Modifier.height(BpkSpacing.Xxl * 4))
      },
      secondaryContent = {
        Spacer(modifier = Modifier.height(BpkSpacing.Xxl * 2))
        BpkText(
          modifier = Modifier.fillMaxWidth(),
          text = stringResource(id = R.string.inventory_divided_card_empty_card),
          style = BpkTheme.typography.bodyDefault,
          textAlign = TextAlign.Center
        )
      },
      onClick = {}
    )
  }
}
