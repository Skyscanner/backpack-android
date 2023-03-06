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

import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BpkPriceTest(flavor: Flavor) : BpkSnapshotTest(listOf(flavor.size, flavor.align)) {

  private val size = flavor.size
  private val align = flavor.align

  @Test
  fun priceOnly() {
    snap {
      BpkPrice(
        price = stringResource(id = R.string.price_price),
        size = size,
        align = align,
      )
    }
  }

  @Test
  fun priceTrailing() {
    snap {
      BpkPrice(
        price = stringResource(id = R.string.price_price),
        trailingText = stringResource(id = R.string.price_trailing_text),
        size = size,
        align = align,
      )
    }
  }

  @Test
  fun priceLineThroughTrailing() {
    snap {
      BpkPrice(
        price = stringResource(id = R.string.price_price),
        trailingText = stringResource(id = R.string.price_trailing_text),
        previousPrice = stringResource(id = R.string.price_line_through_text),
        size = size,
        align = align,
      )
    }
  }

  @Test
  fun priceFull() {
    snap {
      BpkPrice(
        price = stringResource(id = R.string.price_price),
        trailingText = stringResource(id = R.string.price_trailing_text),
        previousPrice = stringResource(id = R.string.price_line_through_text),
        leadingText = stringResource(id = R.string.price_leading_text),
        size = size,
        align = align,
      )
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
  val align: BpkPriceAlign,
)
