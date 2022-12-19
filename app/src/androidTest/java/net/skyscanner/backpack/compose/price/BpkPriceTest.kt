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

package net.skyscanner.backpack.compose.price

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BpkPriceTest(flavor: Flavor) : BpkSnapshotTest() {

  private val size = flavor.size
  private val align = flavor.align

  @Test
  fun priceOnly() {
    capture {
      BpkPrice(
        price = stringResource(id = R.string.price_price),
        size = size,
        align = align
      )
    }
  }

  @Test
  fun priceTrailing() {
    capture {
      BpkPrice(
        price = stringResource(id = R.string.price_price),
        trailingText = stringResource(id = R.string.price_trailing_text),
        size = size,
        align = align
      )
    }
  }

  @Test
  fun priceLineThroughTrailing() {
    capture {
      BpkPrice(
        price = stringResource(id = R.string.price_price),
        trailingText = stringResource(id = R.string.price_trailing_text),
        lineThroughText = stringResource(id = R.string.price_line_through_text),
        size = size,
        align = align
      )
    }
  }

  @Test
  fun priceFull() {
    capture {
      BpkPrice(
        price = stringResource(id = R.string.price_price),
        trailingText = stringResource(id = R.string.price_trailing_text),
        lineThroughText = stringResource(id = R.string.price_line_through_text),
        leadingText = stringResource(id = R.string.price_leading_text),
        size = size,
        align = align
      )
    }
  }

  private fun capture(
    background: @Composable () -> Color = { Color.Unspecified },
    content: @Composable () -> Unit,
  ) {
    snap(
      size = IntSize(200, 200),
      tags = listOf(size, align),
    ) {
      Box(
        Modifier
          .fillMaxSize()
          .background(background())
          .padding(BpkSpacing.Md),
        contentAlignment = Alignment.TopStart,
      ) {
        content()
      }
    }
  }

  companion object {
    @JvmStatic
    @Parameterized.Parameters(name = "{0} Screenshot")
    fun flavours(): List<Flavor> = BpkPriceSize.values().flatMap { size ->
      BpkPriceAlign.values().map { align ->
        Flavor(size = size, align = align)
      }
    }
  }
}

data class Flavor(
  val size: BpkPriceSize,
  val align: BpkPriceAlign
)
