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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.price.internal.BpkPriceImpl

enum class BpkPriceAlign {
    Start,
    End,
    Row,
}

enum class BpkPriceSize {
    Large,
    Small,
    ExtraSmall,
}

enum class BpkPriceStyle {
    default,
    onContrast,
}

/**
 * @param leadingIcon optional icon shown before [leadingText]. Has no effect when [leadingText] is null.
 * @param leadingTextContentDescription optional accessibility description for [leadingText] when [onLeadingTextClicked] is set.
 * When null and [onLeadingTextClicked] is set, a default description based on [leadingText] is used.
 * @param trailingIcon optional icon shown after [leadingText]. Has no effect when [leadingText] is null.
 * @param onLeadingTextClicked optional callback invoked when [leadingText] (and [leadingIcon]/[trailingIcon], if
 * present) is tapped. When set, [leadingText] and its icons are exposed as a single clickable, accessible target.
 */
@Composable
fun BpkPrice(
    price: String,
    modifier: Modifier = Modifier,
    leadingText: String? = null,
    previousPrice: String? = null,
    trailingText: String? = null,
    align: BpkPriceAlign = BpkPriceAlign.Start,
    size: BpkPriceSize = BpkPriceSize.Small,
    style: BpkPriceStyle = BpkPriceStyle.default,
    icon: BpkIcon? = null,
    onPriceClicked: (() -> Unit)? = null,
    leadingIcon: BpkIcon? = null,
    leadingTextContentDescription: String? = null,
    trailingIcon: BpkIcon? = null,
    onLeadingTextClicked: (() -> Unit)? = null,
) {
    BpkPriceImpl(
        price = price,
        modifier = modifier,
        leadingText = leadingText,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        leadingTextContentDescription = leadingTextContentDescription,
        previousPrice = previousPrice,
        trailingText = trailingText,
        align = align,
        size = size,
        style = style,
        icon = icon,
        onPriceClicked = onPriceClicked,
        onLeadingTextClicked = onLeadingTextClicked,
    )
}
