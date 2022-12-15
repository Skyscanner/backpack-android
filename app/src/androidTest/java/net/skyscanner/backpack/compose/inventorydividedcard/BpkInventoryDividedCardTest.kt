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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkInventoryDividedCardTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(height = 450, width = 450)
  }

  @Test
  fun cardWidthSmallerThanMinimum() {
    composed {
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
    }
  }

  @Test
  fun cardWidthGreaterThanMinimum() {
    composed {
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
    }
  }
}
