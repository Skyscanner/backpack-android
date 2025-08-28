/*
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import net.skyscanner.backpack.compose.badge.BpkBadge
import net.skyscanner.backpack.compose.badge.BpkBadgeType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.PriceTag
import net.skyscanner.backpack.compose.tokens.TickCircle
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.BadgeComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@BadgeComponent
@ComposeStory
fun BadgeStory(modifier: Modifier = Modifier) {
    Column(modifier) {
        BpkBadgeType.entries.forEach { type ->
            BadgeRow(type = type)
        }
    }
}

@Composable
private fun BadgeRow(
    type: BpkBadgeType,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(
                when (type) {
                    BpkBadgeType.Outline, BpkBadgeType.Inverse -> BpkTheme.colors.corePrimary
                    else -> Color.Transparent
                },
            )
            .padding(vertical = BpkSpacing.Sm)
            .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Md),
    ) {

        if (type !in listOf(BpkBadgeType.Success, BpkBadgeType.Warning, BpkBadgeType.Destructive)) {
            BpkBadge(
                text = type.toString(),
                type = type,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(align = Alignment.CenterHorizontally),
            )
        } else {
            Spacer(modifier = Modifier.weight(1F))
        }

        BpkBadge(
            text = type.toString(),
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(align = Alignment.CenterHorizontally),
            type = type,
            icon = when (type) {
                BpkBadgeType.Success,
                BpkBadgeType.Warning,
                BpkBadgeType.Destructive,
                -> null
                BpkBadgeType.Brand -> BpkIcon.PriceTag
                else -> BpkIcon.TickCircle
            },
        )

        BpkBadge(
            text = type.toString(),
            icon = painterResource(id = R.drawable.sample_icon),
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(align = Alignment.CenterHorizontally),
            type = type,
        )
    }
}
