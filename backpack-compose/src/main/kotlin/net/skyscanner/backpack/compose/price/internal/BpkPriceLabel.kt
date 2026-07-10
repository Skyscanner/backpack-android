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

package net.skyscanner.backpack.compose.price.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.link.BpkLink
import net.skyscanner.backpack.compose.price.BpkPriceSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme

/**
 * Renders the main price value shared by every [net.skyscanner.backpack.compose.price.BpkPriceAlign] variant.
 *
 * When [onPriceClicked] is null the price is a plain [BpkText] with a stable [String] input, so it can be skipped on
 * recomposition and re-used cheaply inside lazy lists. Only when a click handler is supplied do we render a [BpkLink],
 * using the action-only overload which remembers its annotated string rather than rebuilding it (and parsing markdown)
 * on every recomposition.
 */
@Composable
internal fun BpkPriceLabel(
    price: String,
    size: BpkPriceSize,
    modifier: Modifier = Modifier,
    onPriceClicked: (() -> Unit)? = null,
) {
    if (onPriceClicked != null) {
        BpkLink(
            text = price,
            onClick = onPriceClicked,
            textStyle = size.mainTextStyle(),
            modifier = modifier,
        )
    } else {
        BpkText(
            text = price,
            color = BpkTheme.colors.textPrimary,
            style = size.mainTextStyle(),
            modifier = modifier,
        )
    }
}
