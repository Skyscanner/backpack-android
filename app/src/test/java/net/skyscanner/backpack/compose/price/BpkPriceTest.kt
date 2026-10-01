/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.InformationCircle
import net.skyscanner.backpack.compose.tokens.NewWindow
import net.skyscanner.backpack.demo.R
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner

@RunWith(ParameterizedRobolectricTestRunner::class)
class BpkPriceTest(flavor: Flavor) : BpkSnapshotTest(listOf(flavor.size, flavor.align, flavor.style)) {

    private val size = flavor.size
    private val align = flavor.align
    private val style = flavor.style

    @Test
    fun priceOnly() {
        // Also guards against the price rendering blank when a BpkPrice node is re-used in a lazy
        // list (regression: the price value must always be present in the semantics tree).
        snap(assertion = { onNodeWithText(testContext.getString(R.string.price_price)).assertIsDisplayed() }) {
            BpkPrice(
                price = stringResource(id = R.string.price_price),
                size = size,
                align = align,
                style = style,
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
                style = style,
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
                style = style,
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
                style = style,
            )
        }
    }

    @Test
    fun priceWithIcon() {
        snap {
            BpkPrice(
                price = stringResource(id = R.string.price_price),
                trailingText = stringResource(id = R.string.price_trailing_text),
                previousPrice = stringResource(id = R.string.price_line_through_text),
                leadingText = stringResource(id = R.string.price_leading_text),
                size = size,
                align = align,
                style = style,
                icon = BpkIcon.NewWindow,
            )
        }
    }

    @Test
    fun priceClickable() {
        // Same regression guard for the clickable path, which renders via BpkLink.
        snap(assertion = { onNodeWithText(testContext.getString(R.string.price_price)).assertIsDisplayed() }) {
            BpkPrice(
                price = stringResource(id = R.string.price_price),
                size = size,
                align = align,
                style = style,
                onPriceClicked = {},
            )
        }
    }

    @Test
    fun priceClickableTrailing() {
        snap {
            BpkPrice(
                price = stringResource(id = R.string.price_price),
                trailingText = stringResource(id = R.string.price_trailing_text),
                size = size,
                align = align,
                style = style,
                onPriceClicked = {},
            )
        }
    }

    @Test
    fun priceClickableFull() {
        snap {
            BpkPrice(
                price = stringResource(id = R.string.price_price),
                trailingText = stringResource(id = R.string.price_trailing_text),
                previousPrice = stringResource(id = R.string.price_line_through_text),
                leadingText = stringResource(id = R.string.price_leading_text),
                size = size,
                align = align,
                style = style,
                onPriceClicked = {},
            )
        }
    }

    @Test
    fun priceClickableWithIcon() {
        snap {
            BpkPrice(
                price = stringResource(id = R.string.price_price),
                trailingText = stringResource(id = R.string.price_trailing_text),
                previousPrice = stringResource(id = R.string.price_line_through_text),
                leadingText = stringResource(id = R.string.price_leading_text),
                size = size,
                align = align,
                style = style,
                icon = BpkIcon.NewWindow,
                onPriceClicked = {},
            )
        }
    }

    @Test
    fun priceLeadingIconOnly() {
        snap {
            BpkPrice(
                price = stringResource(id = R.string.price_price),
                leadingText = stringResource(id = R.string.price_leading_text_cheaper),
                leadingIcon = BpkIcon.InformationCircle,
                size = size,
                align = align,
                style = style,
            )
        }
    }

    @Test
    fun priceTrailingIconOnly() {
        snap {
            BpkPrice(
                price = stringResource(id = R.string.price_price),
                leadingText = stringResource(id = R.string.price_leading_text_cheaper),
                trailingIcon = BpkIcon.InformationCircle,
                size = size,
                align = align,
                style = style,
            )
        }
    }

    @Test
    fun priceLeadingAndTrailingIcon() {
        snap {
            BpkPrice(
                price = stringResource(id = R.string.price_price),
                leadingText = stringResource(id = R.string.price_leading_text_cheaper),
                leadingIcon = BpkIcon.InformationCircle,
                trailingIcon = BpkIcon.InformationCircle,
                size = size,
                align = align,
                style = style,
            )
        }
    }

    @Test
    fun priceLeadingTextClickable() {
        // Guards the merged clickable/accessible target: leadingText + icons must expose a single
        // clickable node and the leadingText value must remain present in the semantics tree.
        snap(
            assertion = {
                onNodeWithText(testContext.getString(R.string.price_leading_text_cheaper))
                    .assertIsDisplayed()
                    .assert(hasClickAction())
            },
        ) {
            BpkPrice(
                price = stringResource(id = R.string.price_price),
                leadingText = stringResource(id = R.string.price_leading_text_cheaper),
                leadingIcon = BpkIcon.InformationCircle,
                trailingIcon = BpkIcon.InformationCircle,
                size = size,
                align = align,
                style = style,
                onLeadingTextClicked = {},
            )
        }
    }

    @Test
    fun priceLongWrapping() {
        // Long, digits-only price (and trailingText) forced to wrap onto multiple lines by constraining
        // the available width - locks in the End-align textAlign fix (price/trailingText must stay
        // right-aligned line-to-line) and documents the known Start-align limitation (trailingText can
        // collapse to zero width when the price's longest wrapped line fills the available space).
        snap(width = 160.dp) {
            BpkPrice(
                price = stringResource(id = R.string.price_long),
                trailingText = stringResource(id = R.string.price_trailing_text_two_people),
                size = size,
                align = align,
                style = style,
            )
        }
    }

    companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "{0} Screenshot")
        fun flavours(): List<Flavor> = BpkPriceSize.entries.flatMap { size ->
            BpkPriceAlign.entries.flatMap { align ->
                BpkPriceStyle.entries.map { style ->
                    Flavor(size = size, align = align, style = style)
                }
            }
        }
    }
}

data class Flavor(
    val size: BpkPriceSize,
    val align: BpkPriceAlign,
    val style: BpkPriceStyle,
)
