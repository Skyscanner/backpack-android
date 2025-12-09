/*
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.demo.figma.price

import androidx.compose.runtime.Composable
import com.figma.code.connect.Figma
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.price.BpkPrice
import net.skyscanner.backpack.compose.price.BpkPriceAlign
import net.skyscanner.backpack.compose.price.BpkPriceSize
import net.skyscanner.backpack.compose.tokens.Share

@FigmaConnect("FIGMA_PRICE")
class BpkPriceDoc {

    @FigmaProperty(FigmaType.Text, "Price")
    val price: String = "£1,830"

    @FigmaProperty(FigmaType.Text, "Trailing text")
    val trailingText: String = "per day"

    @FigmaProperty(FigmaType.Text, "Previous price")
    val previousPrice: String = "£2,033"

    @FigmaProperty(FigmaType.Boolean, "Icon?")
    val icon: BpkIcon? = Figma.mapping(
        true to BpkIcon.Share,
        false to null,
    )

    @FigmaProperty(FigmaType.Enum, "Size")
    val size: BpkPriceSize = Figma.mapping(
        "Large" to BpkPriceSize.Large,
        "Small" to BpkPriceSize.Small,
        "X-Small" to BpkPriceSize.ExtraSmall,
    )

    @FigmaProperty(FigmaType.Enum, "Alignment")
    val align: BpkPriceAlign = Figma.mapping(
        "Left" to BpkPriceAlign.Start,
        "Right" to BpkPriceAlign.End,
    )

    @Composable
    fun PriceExample() {
        BpkPrice(
            price = price,
            size = size,
            align = align,
            icon = icon,
            leadingText = "App only deal",
            previousPrice = previousPrice,
            trailingText = trailingText,
        )
    }
}
