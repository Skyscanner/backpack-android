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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.price.BpkPriceSize
import net.skyscanner.backpack.compose.price.BpkPriceStyle
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.clickableWithRipple

/**
 * Renders [text] (the [net.skyscanner.backpack.compose.price.BpkPrice] `leadingText`) with optional decorative
 * icons either side. When [onClick] is set, the icons and text are exposed as a single clickable, accessible
 * target (icons are marked as decorative - the accessible label comes from [text]).
 */
@Composable
internal fun BpkPriceLeadingText(
    text: String,
    size: BpkPriceSize,
    style: BpkPriceStyle,
    modifier: Modifier = Modifier,
    leadingIcon: BpkIcon? = null,
    trailingIcon: BpkIcon? = null,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .let { base ->
                if (onClick != null) {
                    base
                        .clickableWithRipple(
                            bounded = false,
                            role = Role.Button,
                            onClick = onClick,
                        )
                        .semantics(mergeDescendants = true) { }
                } else {
                    base
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
    ) {
        leadingIcon?.let {
            BpkIcon(
                icon = it,
                contentDescription = null,
                size = BpkIconSize.Small,
                tint = style.secondaryTextColor(),
            )
        }
        BpkText(
            text = text,
            color = style.secondaryTextColor(),
            style = size.secondaryTextStyle(),
        )
        trailingIcon?.let {
            BpkIcon(
                icon = it,
                contentDescription = null,
                size = BpkIconSize.Small,
                tint = style.secondaryTextColor(),
            )
        }
    }
}
