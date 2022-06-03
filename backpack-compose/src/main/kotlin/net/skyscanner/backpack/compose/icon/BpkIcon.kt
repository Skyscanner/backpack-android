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

package net.skyscanner.backpack.compose.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.autoMirror

enum class BpkIconSize {
  Small,
  Large,
}

data class BpkIcon(
  @DrawableRes internal val small: Int,
  @DrawableRes internal val large: Int,
)

@Composable
fun BpkIcon(
  icon: BpkIcon,
  contentDescription: String?,
  modifier: Modifier = Modifier,
  size: BpkIconSize = LocalBpkIconSize.current,
  tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
  autoMirror: Boolean = true,
) {
  Icon(
    painter = painterResource(id = icon[size]),
    contentDescription = contentDescription,
    tint = tint,
    modifier = modifier
      .defaultMinSize(size)
      .autoMirror(autoMirror),
  )
}

@Composable
fun ProvideIconSize(size: BpkIconSize, content: @Composable () -> Unit) {
  CompositionLocalProvider(LocalBpkIconSize provides size, content = content)
}

private val LocalBpkIconSize = staticCompositionLocalOf { BpkIconSize.Small }

private operator fun BpkIcon.get(size: BpkIconSize): Int =
  when (size) {
    BpkIconSize.Small -> small
    BpkIconSize.Large -> large
  }

private fun Modifier.defaultMinSize(size: BpkIconSize): Modifier =
  when (size) {
    BpkIconSize.Small -> defaultMinSize(BpkSpacing.Base, BpkSpacing.Base)
    BpkIconSize.Large -> defaultMinSize(BpkSpacing.Lg, BpkSpacing.Lg)
  }
