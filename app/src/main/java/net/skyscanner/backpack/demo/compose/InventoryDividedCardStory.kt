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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.inventorydividedcard.InventoryDividedCard
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
@Preview
fun InventoryDividedCardStory() {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(BpkSpacing.Base)
  ) {
    InventoryDividedCard(
      primaryContent = {
        Image(
          modifier = Modifier
            .fillMaxWidth()
            .height(BpkSpacing.Xxl * 2),
          painter = painterResource(id = R.drawable.canadian_rockies_canada),
          contentDescription = "",
          contentScale = ContentScale.Crop
        )
      },
      secondaryContent = {
        Box(
          modifier = Modifier.fillMaxWidth(),
          contentAlignment = Alignment.Center
        ) {
          BpkText(
            text = stringResource(id = R.string.inventory_divided_card_with_more_than_minimum),
            style = BpkTheme.typography.bodyDefault
          )
        }
      },
      onClick = {}
    )

    Spacer(modifier = Modifier.height(BpkSpacing.Xxl))

    InventoryDividedCard(
      primaryContent = {
        Image(
          modifier = Modifier
            .width(BpkSpacing.Xxl * 4)
            .height(BpkSpacing.Xxl * 4),
          painter = painterResource(id = R.drawable.canadian_rockies_canada),
          contentDescription = "",
          contentScale = ContentScale.Crop
        )
      },
      secondaryContent = {
        Box(
          modifier = Modifier.width(BpkSpacing.Xxl * 4),
          contentAlignment = Alignment.Center
        ) {
          BpkText(
            text = stringResource(id = R.string.inventory_divided_card_with_less_than_minimum),
            style = BpkTheme.typography.bodyDefault
          )
        }
      },
      onClick = {}
    )
  }
}
