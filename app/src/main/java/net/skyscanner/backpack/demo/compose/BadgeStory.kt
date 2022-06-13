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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.badge.BpkBadge
import net.skyscanner.backpack.compose.badge.BpkBadgeType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.CloseCircle
import net.skyscanner.backpack.compose.tokens.HelpCircle
import net.skyscanner.backpack.compose.tokens.TickCircle

@Composable
@Preview
fun BadgeStory() {
  Row(
    modifier = Modifier
      .background(if (BpkTheme.colors.isLight) Color(0xFFEBEBEB) else BpkColor.Black)
      .padding(BpkSpacing.Base),
    horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
  ) {

    BadgeColumn { type ->
      BpkBadge(
        text = type.toString(),
        type = type,
      )
    }

    BadgeColumn { type ->
      BpkBadge(
        text = type.toString(),
        type = type,
        icon = when (type) {
          BpkBadgeType.Warning -> BpkIcon.HelpCircle
          BpkBadgeType.Destructive -> BpkIcon.CloseCircle
          else -> BpkIcon.TickCircle
        }
      )
    }
  }
}

@Composable
private fun BadgeColumn(
  modifier: Modifier = Modifier,
  content: @Composable (BpkBadgeType) -> Unit,
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base)
  ) {
    BpkBadgeType.values().forEach { type ->
      content(type)
    }
  }
}
