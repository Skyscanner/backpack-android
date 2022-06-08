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

package net.skyscanner.backpack.compose.utils

import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkColor

@Composable
internal fun BpkToggleableContent(
  enabled: Boolean,
  content: @Composable () -> Unit,
) {

  val contentColor = when {
    enabled -> BpkTheme.colors.textPrimary
    else -> dynamicColorOf(BpkColor.SkyGrayTint04, BpkColor.BlackTint06)
  }

  CompositionLocalProvider(
    LocalContentColor provides contentColor,
    LocalTextStyle provides BpkTheme.typography.footnote,
    content = content,
  )

}
