/*
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

package net.skyscanner.backpack.compose.iconbullet.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.iconbullet.BpkIconBulletSize
import net.skyscanner.backpack.compose.iconbullet.BpkIconBulletType
import net.skyscanner.backpack.compose.theme.BpkTheme

@Composable
internal fun BpkIconBulletImpl(
    icon: BpkIcon,
    size: BpkIconBulletSize,
    type: BpkIconBulletType,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(size.containerSize)
            .background(color = type.backgroundColor, shape = CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        BpkIcon(
            icon = icon,
            contentDescription = null,
            size = size.iconSize,
            tint = type.contentColor,
            modifier = Modifier.scale(size.iconScale),
        )
    }
}

private val BpkIconBulletSize.containerSize: Dp
    get() = when (this) {
        BpkIconBulletSize.Small -> SmallContainerSize
        BpkIconBulletSize.Medium -> MediumContainerSize
        BpkIconBulletSize.Large -> LargeContainerSize
    }

// The icon is always half the size of its container, mapped onto the closest BpkIconSize and
// scaled down further where no exact match exists (Small: 8dp icon in a 16dp container).
private val BpkIconBulletSize.iconSize: BpkIconSize
    get() = when (this) {
        BpkIconBulletSize.Small -> BpkIconSize.Small
        BpkIconBulletSize.Medium -> BpkIconSize.Small
        BpkIconBulletSize.Large -> BpkIconSize.Large
    }

private val BpkIconBulletSize.iconScale: Float
    get() = when (this) {
        BpkIconBulletSize.Small -> HalfScale
        BpkIconBulletSize.Medium, BpkIconBulletSize.Large -> FullScale
    }

private val BpkIconBulletType.backgroundColor: Color
    @Composable
    get() = when (this) {
        BpkIconBulletType.Loyalty -> BpkTheme.colors.statusLoyaltySpot
        BpkIconBulletType.Strong -> BpkTheme.colors.corePrimary
        BpkIconBulletType.Brand -> BpkTheme.colors.coreAccent
    }

private val BpkIconBulletType.contentColor: Color
    @Composable
    get() = when (this) {
        BpkIconBulletType.Loyalty -> BpkTheme.colors.textOnLight
        BpkIconBulletType.Strong -> BpkTheme.colors.textOnDark
        BpkIconBulletType.Brand -> BpkTheme.colors.textPrimaryInverse
    }

private val SmallContainerSize = 16.dp
private val MediumContainerSize = 32.dp
private val LargeContainerSize = 48.dp
private const val HalfScale = 0.5f
private const val FullScale = 1f
