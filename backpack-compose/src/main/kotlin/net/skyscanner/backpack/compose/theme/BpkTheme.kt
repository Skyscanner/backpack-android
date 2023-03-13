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

package net.skyscanner.backpack.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontFamily
import net.skyscanner.backpack.compose.tokens.BpkColors
import net.skyscanner.backpack.compose.tokens.BpkShapes
import net.skyscanner.backpack.compose.tokens.BpkTypography

private val LocalBpkTypography = staticCompositionLocalOf<BpkTypography> {
  error("Wrap you content with BpkTheme {} to get access to Backpack typography")
}
private val LocalBpkColors = staticCompositionLocalOf<BpkColors> {
  error("Wrap you content with BpkTheme {} to get access to Backpack colors")
}
private val LocalBpkShapes = staticCompositionLocalOf<BpkShapes> {
  error("Wrap you content with BpkTheme {} to get access to Backpack shapes")
}

@Composable
fun BpkTheme(
  fontFamily: FontFamily = FontFamily.SansSerif,
  content: @Composable () -> Unit,
) {
  val typography = BpkTypography(defaultFontFamily = fontFamily)
  val colors = if (isSystemInDarkTheme()) BpkColors.dark() else BpkColors.light()
  val shapes = BpkShapes()

  CompositionLocalProvider(
    LocalBpkTypography provides typography,
    LocalBpkColors provides colors,
    LocalBpkShapes provides shapes,
    LocalContentColor provides colors.textPrimary,
    LocalElevationOverlay provides null,
    LocalTextStyle provides typography.bodyDefault,
    LocalContentAlpha provides 1f,
    content = content,
  )
}

object BpkTheme {

  val typography: BpkTypography
    @Composable
    @ReadOnlyComposable
    get() = if (LocalInspectionMode.current) {
      // when in preview mode return a default typography object to ensure previews work
      // without wrapping it in another composable
      BpkTypography(defaultFontFamily = FontFamily.SansSerif)
    } else {
      LocalBpkTypography.current
    }

  val colors: BpkColors
    @Composable
    @ReadOnlyComposable
    get() = if (LocalInspectionMode.current) {
      // when in preview mode return a default colour object to ensure previews work
      // without wrapping it in another composable
      if (isSystemInDarkTheme()) BpkColors.dark() else BpkColors.light()
    } else {
      LocalBpkColors.current
    }

  val shapes: BpkShapes
    @Composable
    @ReadOnlyComposable
    get() = if (LocalInspectionMode.current) {
      // when in preview mode return a default typography object to ensure previews work
      // without wrapping it in another composable
      BpkShapes()
    } else {
      LocalBpkShapes.current
    }
}
