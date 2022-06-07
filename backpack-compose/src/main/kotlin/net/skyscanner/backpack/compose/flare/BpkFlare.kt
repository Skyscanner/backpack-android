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

package net.skyscanner.backpack.compose.flare

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import net.skyscanner.backpack.compose.flare.internal.FlareContent
import net.skyscanner.backpack.compose.flare.internal.FlareShape

enum class BpkFlareRadius {
  None,
  Medium,
}

enum class BpkFlarePointerDirection {
  Up,
  Down,
}

@Composable
fun BpkFlare(
  modifier: Modifier = Modifier,
  radius: BpkFlareRadius = BpkFlareRadius.None,
  pointerDirection: BpkFlarePointerDirection = BpkFlarePointerDirection.Down,
  background: Color = Color.Unspecified,
  insetContent: Boolean = false,
  contentAlignment: Alignment = Alignment.TopStart,
  content: @Composable BoxScope.() -> Unit,
) {
  Box(
    modifier = modifier
      .clip(FlareShape(radius, pointerDirection))
      .background(background),
    propagateMinConstraints = true,
    content = { FlareContent(pointerDirection, insetContent, contentAlignment, content) }
  )
}
