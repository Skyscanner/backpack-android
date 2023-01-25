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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.viewinterop.AndroidView
import net.skyscanner.backpack.badge.BpkBadge
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
  AndroidView(
    factory = {
      BpkBadge(it).apply {
        this.type = type
        this.text = type.toString()
      }
    },
  )
}
