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
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.price.BpkPrice
import net.skyscanner.backpack.compose.price.BpkPriceAlign
import net.skyscanner.backpack.compose.price.BpkPriceSize
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

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

    PriceRowWithSize(size = BpkPriceSize.Small)

    Spacer(modifier = Modifier.height(BpkSpacing.Xxl))

    PriceRowWithSize(size = BpkPriceSize.Large)
  }
}

@Composable
private fun ColumnScope.PriceRowWithSize(size: BpkPriceSize) {
  PriceRow(
    price = stringResource(id = R.string.price_price),
    size = size,
  )
  PriceRow(
    price = stringResource(id = R.string.price_price),
    trailingText = stringResource(id = R.string.price_trailing_text),
    size = size,
  )
  PriceRow(
    price = stringResource(id = R.string.price_price),
    lineThroughText = stringResource(id = R.string.price_line_through_text),
    trailingText = stringResource(id = R.string.price_trailing_text),
    size = size,
  )
  PriceRow(
    price = stringResource(id = R.string.price_price),
    lineThroughText = stringResource(id = R.string.price_line_through_text),
    leadingText = stringResource(id = R.string.price_leading_text),
    trailingText = stringResource(id = R.string.price_trailing_text),
    size = size,
  )
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
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = BpkSpacing.Base, end = BpkSpacing.Base),
    horizontalArrangement = Arrangement.SpaceBetween,
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
