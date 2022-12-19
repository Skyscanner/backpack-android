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

package net.skyscanner.backpack.compose.price.internal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import net.skyscanner.backpack.compose.price.BpkPriceSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkFontSize
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BpkPriceAlignStart(
  price: String,
  modifier: Modifier = Modifier,
  leadingText: String? = null,
  lineThroughText: String? = null,
  trailingText: String? = null,
  size: BpkPriceSize = BpkPriceSize.Small,
) {
  Column(modifier = modifier) {
    Row {
      lineThroughText?.let {
        BpkText(
          text = it,
          color = BpkTheme.colors.textSecondary,
          style = BpkTheme.typography.caption.copy(
            fontSize = if (size == BpkPriceSize.Large) BpkFontSize.Sm else BpkFontSize.Xs,
          ),
          textDecoration = TextDecoration.LineThrough,
        )
      }
      leadingText?.let {
        BpkText(
          text = StringBuilder().append(" • ").append(it).toString(),
          color = BpkTheme.colors.textSecondary,
          style = BpkTheme.typography.caption.copy(
            fontSize = if (size == BpkPriceSize.Large) BpkFontSize.Sm else BpkFontSize.Xs,
          ),
        )
      }
    }
    Row(verticalAlignment = Alignment.Bottom) {
      BpkText(
        text = price,
        color = BpkTheme.colors.textPrimary,
        style = if (size == BpkPriceSize.Large) BpkTheme.typography.heading2 else BpkTheme.typography.heading4,
      )
      Box(modifier = Modifier.padding(start = BpkSpacing.Sm, bottom = BpkSpacing.Sm)) {
        trailingText?.let {
          BpkText(
            text = it,
            color = BpkTheme.colors.textSecondary,
            style = BpkTheme.typography.footnote.copy(
              fontSize = if (size == BpkPriceSize.Large) BpkFontSize.Sm else BpkFontSize.Xs,
            ),
          )
        }
      }
    }
  }
}

@Composable
internal fun BpkPriceAlignEnd(
  price: String,
  modifier: Modifier = Modifier,
  leadingText: String? = null,
  lineThroughText: String? = null,
  trailingText: String? = null,
  size: BpkPriceSize = BpkPriceSize.Small,
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.End,
  ) {
    Row {
      leadingText?.let {
        BpkText(
          text = StringBuilder().append(it).append(" • ").toString(),
          color = BpkTheme.colors.textSecondary,
          style = BpkTheme.typography.caption.copy(
            fontSize = if (size == BpkPriceSize.Large) BpkFontSize.Sm else BpkFontSize.Xs,
          ),
        )
      }
      lineThroughText?.let {
        BpkText(
          text = it,
          color = BpkTheme.colors.textSecondary,
          style = BpkTheme.typography.caption.copy(
            fontSize = if (size == BpkPriceSize.Large) BpkFontSize.Sm else BpkFontSize.Xs,
          ),
          textDecoration = TextDecoration.LineThrough,
        )
      }
    }
    BpkText(
      text = price,
      color = BpkTheme.colors.textPrimary,
      style = if (size == BpkPriceSize.Large) BpkTheme.typography.heading2 else BpkTheme.typography.heading4,
    )
    trailingText?.let {
      BpkText(
        text = it,
        color = BpkTheme.colors.textSecondary,
        style = BpkTheme.typography.footnote.copy(
          fontSize = if (size == BpkPriceSize.Large) BpkFontSize.Sm else BpkFontSize.Xs,
        ),
      )
    }
  }
}
