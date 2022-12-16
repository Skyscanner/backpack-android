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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.price.BpkPrice
import net.skyscanner.backpack.compose.price.BpkPriceAlign
import net.skyscanner.backpack.compose.price.BpkPriceSize
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
@Preview
fun PriceStory(
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = Modifier
      .padding(BpkSpacing.Base)
      .fillMaxSize(),
    verticalArrangement = Arrangement.SpaceEvenly,
  ) {
    PriceRow(
      price = "£1,830",
      size = BpkPriceSize.Small,
    )
    PriceRow(
      price = "£1,830",
      trailingText = "per day",
      size = BpkPriceSize.Small,
    )
    PriceRow(
      price = "£1,830",
      lineThroughText = "£2,033",
      trailingText = "per day",
      size = BpkPriceSize.Small,
    )
    PriceRow(
      price = "£1,830",
      lineThroughText = "£2,033",
      leadingText = "App only deal",
      trailingText = "per day",
      size = BpkPriceSize.Small,
    )

    Spacer(modifier = Modifier.height(BpkSpacing.Xxl))

    PriceRow(
      price = "£1,830",
      size = BpkPriceSize.Large,
    )
    PriceRow(
      price = "£1,830",
      trailingText = "per day",
      size = BpkPriceSize.Large,
    )
    PriceRow(
      price = "£1,830",
      lineThroughText = "£2,033",
      trailingText = "per day",
      size = BpkPriceSize.Large,
    )
    PriceRow(
      price = "£1,830",
      lineThroughText = "£2,033",
      leadingText = "App only deal",
      trailingText = "per day",
      size = BpkPriceSize.Large,
    )
  }
}

@Composable
private fun PriceRow(
  price: String,
  size: BpkPriceSize,
  lineThroughText: String? = null,
  leadingText: String? = null,
  trailingText: String? = null,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceEvenly,
  ) {
    BpkPrice(
      price = price,
      lineThroughText = lineThroughText,
      leadingText = leadingText,
      trailingText = trailingText,
      size = size,
      align = BpkPriceAlign.Start,
    )
    BpkPrice(
      price = price,
      lineThroughText = lineThroughText,
      leadingText = leadingText,
      trailingText = trailingText,
      size = size,
      align = BpkPriceAlign.End,
    )
  }
}
