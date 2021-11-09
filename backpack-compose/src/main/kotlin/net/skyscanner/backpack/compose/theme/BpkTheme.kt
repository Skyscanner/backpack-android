/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontWeight
import net.skyscanner.backpack.compose.tokens.BpkTypography

val LocalBpkTypography = staticCompositionLocalOf {
  BpkTypography()
}

@Composable
fun BpkTheme(
  typography: BpkTypography = BpkTheme.typography,
  content: @Composable () -> Unit
) {
  CompositionLocalProvider(
    LocalBpkTypography provides typography,
  ) {
    val materialTypography = Typography(
      h2 = typography.hero64,
      h3 = typography.hero48,
      h4 = typography.heading32,
      h5 = typography.heading24,
      h6 = typography.heading20,
      subtitle1 = typography.bodyDefault,
      subtitle2 = typography.footnote,
      body1 = typography.bodyDefault,
      body2 = typography.footnote,
      button = typography.label14,
      caption = typography.caption,
      overline = typography.caption.copy(fontWeight = FontWeight.Bold)
    )
    MaterialTheme(
      typography = materialTypography,
      content = content
    )
  }
}

object BpkTheme {
  val typography: BpkTypography
    @Composable
    get() = LocalBpkTypography.current
}
