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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.text.style.TextAlign
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
 * target (icons are marked as decorative - the accessible label comes from [text] or [contentDescription] if
 * provided).
 *
 * [clearAndSetSemantics] (rather than `semantics(mergeDescendants = true)`) is used to build that accessible
 * target: `mergeDescendants` *merges* descendant semantics (including the child [BpkText]'s own text) into this
 * node *in addition to* any contentDescription set here, so a screen reader would announce both the custom
 * [contentDescription] and [text]. `clearAndSetSemantics` instead replaces the subtree's semantics outright,
 * guaranteeing a single, clean announcement.
 *
 * [leadingIconBackgroundColor], when set, renders [leadingIcon] inside a circular badge of that color. Purely
 * decorative - it has no effect on [trailingIcon], and does not change the clickable touch/semantics target when
 * [onClick] is set.
 */
@Composable
internal fun BpkPriceLeadingText(
    text: String,
    size: BpkPriceSize,
    style: BpkPriceStyle,
    modifier: Modifier = Modifier,
    leadingIcon: BpkIcon? = null,
    trailingIcon: BpkIcon? = null,
    leadingIconBackgroundColor: Color? = null,
    contentDescription: String? = null,
    textAlign: TextAlign? = null,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .let { base ->
                if (onClick != null) {
                    base
                        .clickableWithRipple(
                            role = Role.Button,
                            onClick = onClick,
                        )
                        .clearAndSetSemantics {
                            this.contentDescription = contentDescription ?: text
                            this.role = Role.Button
                            this.onClick(label = null) {
                                onClick()
                                true
                            }
                        }
                } else {
                    base
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
    ) {
        leadingIcon?.let {
            if (leadingIconBackgroundColor != null) {
                Box(
                    modifier = Modifier
                        .background(color = leadingIconBackgroundColor, shape = CircleShape)
                        .padding(BpkSpacing.Xxs),
                    contentAlignment = Alignment.Center,
                ) {
                    BpkIcon(
                        icon = it,
                        contentDescription = null,
                        size = BpkIconSize.Small,
                        tint = style.mainTextColor(),
                        modifier = Modifier.scale(LeadingIconBadgeIconScale),
                    )
                }
            } else {
                BpkIcon(
                    icon = it,
                    contentDescription = null,
                    size = BpkIconSize.Small,
                    tint = style.mainTextColor(),
                )
            }
        }
        BpkText(
            text = text,
            color = style.mainTextColor(),
            style = size.secondaryTextStyle(),
            textAlign = textAlign,
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

/** Scale applied to [leadingIcon] when rendered inside a [leadingIconBackgroundColor] badge - see usage above. */
private const val LeadingIconBadgeIconScale = 0.75f
