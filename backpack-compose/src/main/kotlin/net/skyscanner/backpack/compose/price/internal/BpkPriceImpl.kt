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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.price.BpkPriceSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.applyIf

@Composable
internal fun BpkPriceAlignStart(
    price: String,
    modifier: Modifier = Modifier,
    leadingText: String? = null,
    previousPrice: String? = null,
    trailingText: String? = null,
    size: BpkPriceSize = BpkPriceSize.Small,
) {
    Column(modifier = modifier) {
        Row {
            previousPrice?.let {
                BpkText(
                    text = it,
                    color = BpkTheme.colors.textSecondary,
                    style = when (size) {
                        BpkPriceSize.Large -> BpkTheme.typography.footnote
                        BpkPriceSize.Small -> BpkTheme.typography.caption
                        BpkPriceSize.ExtraSmall -> BpkTheme.typography.caption
                    },
                    textDecoration = TextDecoration.LineThrough,
                )
            }
            leadingText?.let {
                val builder = StringBuilder()
                previousPrice?.let { builder.append(" • ") }
                builder.append(it)
                BpkText(
                    text = builder.toString(),
                    color = BpkTheme.colors.textSecondary,
                    style = when (size) {
                        BpkPriceSize.Large -> BpkTheme.typography.footnote
                        BpkPriceSize.Small -> BpkTheme.typography.caption
                        BpkPriceSize.ExtraSmall -> BpkTheme.typography.caption
                    },
                )
            }
        }
        Row(verticalAlignment = Alignment.Bottom) {
            BpkText(
                modifier = Modifier.alignByBaseline(),
                text = price,
                color = BpkTheme.colors.textPrimary,
                style = when (size) {
                    BpkPriceSize.Large -> BpkTheme.typography.heading2
                    BpkPriceSize.Small -> BpkTheme.typography.heading4
                    BpkPriceSize.ExtraSmall -> BpkTheme.typography.heading5
                },
            )
            trailingText?.let {
                Box(
                    modifier = Modifier
                        .padding(start = BpkSpacing.Sm)
                        .alignByBaseline(),
                ) {
                    BpkText(
                        text = it,
                        color = BpkTheme.colors.textSecondary,
                        style = when (size) {
                            BpkPriceSize.Large -> BpkTheme.typography.footnote
                            BpkPriceSize.Small -> BpkTheme.typography.caption
                            BpkPriceSize.ExtraSmall -> BpkTheme.typography.caption
                        },
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
    previousPrice: String? = null,
    trailingText: String? = null,
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
                    style = when (size) {
                        BpkPriceSize.Large -> BpkTheme.typography.footnote
                        BpkPriceSize.Small -> BpkTheme.typography.caption
                        BpkPriceSize.ExtraSmall -> BpkTheme.typography.caption
                    },
                )
            }
            previousPrice?.let {
                BpkText(
                    text = it,
                    color = BpkTheme.colors.textSecondary,
                    style = when (size) {
                        BpkPriceSize.Large -> BpkTheme.typography.footnote
                        BpkPriceSize.Small -> BpkTheme.typography.caption
                        BpkPriceSize.ExtraSmall -> BpkTheme.typography.caption
                    },
                    textDecoration = TextDecoration.LineThrough,
                )
            }
        }
        BpkText(
            text = price,
            color = BpkTheme.colors.textPrimary,
            style = when (size) {
                BpkPriceSize.Large -> BpkTheme.typography.heading2
                BpkPriceSize.Small -> BpkTheme.typography.heading4
                BpkPriceSize.ExtraSmall -> BpkTheme.typography.heading5
            },
        )
        trailingText?.let {
            BpkText(
                text = it,
                color = BpkTheme.colors.textSecondary,
                style = when (size) {
                    BpkPriceSize.Large -> BpkTheme.typography.footnote
                    BpkPriceSize.Small -> BpkTheme.typography.caption
                    BpkPriceSize.ExtraSmall -> BpkTheme.typography.caption
                },
                modifier = Modifier.applyIf(size == BpkPriceSize.ExtraSmall) {
                    offset(y = (-2).dp)
                },
            )
        }
    }
}
