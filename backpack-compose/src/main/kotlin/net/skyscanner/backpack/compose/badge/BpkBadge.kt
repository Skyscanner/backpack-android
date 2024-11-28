/*
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

package net.skyscanner.backpack.compose.badge

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import net.skyscanner.backpack.compose.badge.internal.BadgeDrawable
import net.skyscanner.backpack.compose.badge.internal.BadgeIcon
import net.skyscanner.backpack.compose.badge.internal.BpkBadgeImpl
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.tokens.Exclamation
import net.skyscanner.backpack.compose.tokens.InformationCircle
import net.skyscanner.backpack.compose.tokens.TickCircle

enum class BpkBadgeType {
    Normal,
    Strong,
    Success,
    Warning,
    Destructive,
    Inverse,
    Outline,
    Brand,
}

@Composable
fun BpkBadge(
    text: String,
    modifier: Modifier = Modifier,
    type: BpkBadgeType = BpkBadgeType.Normal,
    icon: BpkIcon? = null,
) {
    val badgeIcon = icon ?: when (type) {
        BpkBadgeType.Warning -> BpkIcon.InformationCircle
        BpkBadgeType.Destructive -> BpkIcon.Exclamation
        BpkBadgeType.Success -> BpkIcon.TickCircle
        else -> icon
    }
    BpkBadgeImpl(
        text = text,
        modifier = modifier,
        type = type,
        icon = badgeIcon?.let {
            {
                BadgeIcon(
                    icon = badgeIcon,
                    size = BpkIconSize.Small,
                    type = type,
                )
            }
        },
    )
}

@Composable
fun BpkBadge(
    text: String,
    icon: Painter,
    modifier: Modifier = Modifier,
    type: BpkBadgeType = BpkBadgeType.Normal,
) {
    BpkBadgeImpl(
        text = text,
        modifier = modifier,
        type = type,
        icon = {
            BadgeDrawable(
                icon = icon,
                size = BpkIconSize.Small,
                type = type,
            )
        },
    )
}
