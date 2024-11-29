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

package net.skyscanner.backpack.compose.badge.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import net.skyscanner.backpack.compose.badge.BpkBadgeType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkBorderSize
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.internal.BpkBadgeColors

@Composable
internal fun BpkBadgeImpl(
    text: String,
    modifier: Modifier = Modifier,
    type: BpkBadgeType = BpkBadgeType.Normal,
    icon: @Composable (RowScope.() -> Unit)?,
) {
    Row(
        modifier = modifier
            .semantics(mergeDescendants = true) { }
            .border(BpkBorderSize.Sm, type.borderColor, BadgeShape)
            .background(type.backgroundColor, BadgeShape)
            .padding(horizontal = BpkSpacing.Md, vertical = BpkSpacing.Sm),
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val contentColor = type.contentColor

        icon?.let { it() }

        BpkText(
            text = text,
            color = contentColor,
            style = BpkTheme.typography.footnote,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
internal fun BadgeIcon(
    icon: BpkIcon,
    type: BpkBadgeType,
    modifier: Modifier = Modifier,
) {
    BpkIcon(
        icon = icon,
        contentDescription = null,
        modifier = modifier,
        tint = type.iconColor,
    )
}

@Composable
internal fun BadgeDrawable(
    icon: Painter,
    type: BpkBadgeType,
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = icon,
        contentDescription = null,
        tint = type.iconColor,
        modifier = modifier.requiredSize(BpkSpacing.Base, BpkSpacing.Base),
    )
}

private val BpkBadgeType.iconColor: Color
    @Composable
    get() = when (this) {
        BpkBadgeType.Warning -> BpkTheme.colors.statusWarningSpot
        BpkBadgeType.Destructive -> BpkTheme.colors.statusDangerSpot
        BpkBadgeType.Success -> BpkTheme.colors.statusSuccessSpot
        else -> {
            contentColor
        }
    }

private val BpkBadgeType.contentColor: Color
    @Composable
    get() = when (this) {
        BpkBadgeType.Normal -> BpkTheme.colors.textPrimary
        BpkBadgeType.Strong -> BpkTheme.colors.textOnDark
        BpkBadgeType.Success -> BpkTheme.colors.textPrimary
        BpkBadgeType.Warning -> BpkTheme.colors.textPrimary
        BpkBadgeType.Destructive -> BpkTheme.colors.textPrimary
        BpkBadgeType.Inverse -> BpkTheme.colors.textPrimary
        BpkBadgeType.Outline -> BpkTheme.colors.textOnDark
        BpkBadgeType.Brand -> BpkTheme.colors.textPrimaryInverse
    }

private val BpkBadgeType.backgroundColor: Color
    @Composable
    get() = when (this) {
        BpkBadgeType.Normal -> BpkBadgeColors.backgroundNormal
        BpkBadgeType.Strong -> BpkTheme.colors.corePrimary
        BpkBadgeType.Success -> BpkBadgeColors.backgroundNormal
        BpkBadgeType.Warning -> BpkBadgeColors.backgroundNormal
        BpkBadgeType.Destructive -> BpkBadgeColors.backgroundNormal
        BpkBadgeType.Inverse -> BpkTheme.colors.surfaceDefault
        BpkBadgeType.Outline -> Color.Transparent
        BpkBadgeType.Brand -> BpkTheme.colors.coreAccent
    }

private val BpkBadgeType.borderColor: Color
    @Composable
    get() = when (this) {
        BpkBadgeType.Outline -> BpkTheme.colors.textOnDark
        else -> Color.Transparent
    }

private val BadgeShape = RoundedCornerShape(BpkBorderRadius.Xs)
