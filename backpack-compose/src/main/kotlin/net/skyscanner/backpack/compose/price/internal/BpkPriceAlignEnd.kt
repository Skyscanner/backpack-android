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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.price.BpkPriceSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.utils.applyIf

@Composable
internal fun BpkPriceAlignEnd(
    price: String,
    modifier: Modifier = Modifier,
    leadingText: String? = null,
    previousPrice: String? = null,
    trailingText: String? = null,
    previousPriceColor: Color = BpkTheme.colors.textSecondary,
    size: BpkPriceSize = BpkPriceSize.Small,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
    ) {
        Row {
            leadingText?.let {
                val builder = StringBuilder()
                builder.append(it)
                previousPrice?.let { builder.append(" • ") }
                BpkText(
                    text = builder.toString(),
                    color = BpkTheme.colors.textSecondary,
                    style = size.secondaryTextStyle(),
                )
            }
            previousPrice?.let {
                BpkText(
                    text = it,
                    color = previousPriceColor,
                    style = size.secondaryTextStyle(),
                    textDecoration = TextDecoration.LineThrough,
                )
            }
        }
        BpkText(
            text = price,
            color = BpkTheme.colors.textPrimary,
            style = size.mainTextStyle(),
        )
        trailingText?.let {
            BpkText(
                text = it,
                color = BpkTheme.colors.textSecondary,
                style = size.secondaryTextStyle(),
                modifier = Modifier.applyIf(size == BpkPriceSize.ExtraSmall) {
                    offset(y = (-2).dp)
                },
            )
        }
    }
}
