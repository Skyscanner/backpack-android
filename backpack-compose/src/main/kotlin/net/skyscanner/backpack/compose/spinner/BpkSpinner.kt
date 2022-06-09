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

package net.skyscanner.backpack.compose.spinner

import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.dynamicColorOf

enum class BpkSpinnerSize {
  Small,
  Medium,
  Large,
}

enum class BpkSpinnerStyle {
  TextPrimary,
  Disabled,
  OnDarkSurface,
}

@Composable
fun BpkSpinner(
  size: BpkSpinnerSize,
  modifier: Modifier = Modifier,
  style: BpkSpinnerStyle = findBestSpinnerStyleFor(LocalContentColor.current, BpkTheme.colors.isLight),
) {
  CircularProgressIndicator(
    modifier = modifier.requiredSize(size.dp, size.dp),
    strokeWidth = 2.dp,
    color = when (style) {
      BpkSpinnerStyle.TextPrimary -> BpkTheme.colors.textPrimary
      BpkSpinnerStyle.Disabled -> dynamicColorOf(BpkColor.SkyGrayTint04, BpkColor.BlackTint06)
      BpkSpinnerStyle.OnDarkSurface -> Color.White
    }
  )
}

private val BpkSpinnerSize.dp: Dp
  get() = when (this) {
    BpkSpinnerSize.Small -> BpkSpacing.Base
    BpkSpinnerSize.Medium -> BpkSpacing.Lg
    BpkSpinnerSize.Large -> BpkSpacing.Xl
  }

private fun findBestSpinnerStyleFor(contentColor: Color, lightMode: Boolean): BpkSpinnerStyle =
  if (contentColor.luminance() > 0.5f && lightMode) {
    BpkSpinnerStyle.OnDarkSurface
  } else {
    BpkSpinnerStyle.TextPrimary
  }
