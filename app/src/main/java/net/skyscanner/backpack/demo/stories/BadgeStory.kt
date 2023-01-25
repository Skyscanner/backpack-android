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

package net.skyscanner.backpack.demo.stories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.viewinterop.AndroidView
import net.skyscanner.backpack.badge.BpkBadge
import net.skyscanner.backpack.compose.badge.BpkBadge
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.meta.Component
import net.skyscanner.backpack.demo.meta.EnumProvider
import net.skyscanner.backpack.demo.meta.Kind
import net.skyscanner.backpack.demo.meta.Story

@Component(
  name = "Badge",
  link = "badge",
  kind = Kind.View,
)
annotation class BadgeComponent

class BadgeTypeProvider : EnumProvider<BpkBadge.Type>(BpkBadge.Type::class)

@Composable
@Story
fun BadgeStory(
  @PreviewParameter(BadgeTypeProvider::class) type: BpkBadge.Type,
  modifier: Modifier = Modifier,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .fillMaxWidth()
      .background(
        when (type) {
          BpkBadge.Type.Outline, BpkBadge.Type.Inverse -> BpkTheme.colors.corePrimary
          else -> Color.Transparent
        },
      )
      .padding(vertical = BpkSpacing.Sm)
      .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Md),
  ) {

    AndroidView(
      factory = {
        BpkBadge(it).apply {
          this.type = type
          this.text = type.toString()
        }
      },
      modifier = Modifier
        .weight(1f)
        .wrapContentWidth(align = Alignment.CenterHorizontally),
    )
  }
}

@Composable
@Story(name = "Test")
fun BadgeStory1(
  @PreviewParameter(BadgeTypeProvider::class) type: BpkBadge.Type,
  modifier: Modifier = Modifier,
) {
  BadgeStory(type = type, modifier)
}
