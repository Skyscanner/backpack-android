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

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.tokens.BpkColors
import net.skyscanner.backpack.compose.tokens.BpkTypography

val LocalBpkTypography = staticCompositionLocalOf<BpkTypography> {
  error("Wrap you content with BpkTheme {} to get access to LocalBpkTypography")
}
val LocalBpkColors = staticCompositionLocalOf<BpkColors> {
  error("Wrap you content with BpkTheme {} to get access to LocalBpkColors")
}

@Composable
fun BpkTheme(
  fontFamily: FontFamily = FontFamily.SansSerif,
  content: @Composable () -> Unit,
) {
  val typography = BpkTypography(defaultFontFamily = fontFamily)
  val colors = if (isSystemInDarkTheme()) BpkColors.dark() else BpkColors.light()

  CompositionLocalProvider(
    LocalBpkTypography provides typography,
    LocalBpkColors provides colors,
  ) {
    MaterialTheme(
      typography = typography.toMaterialTypography(fontFamily),
      colors = colors.toMaterialColors(),
      content = content,
    )
  }
}

object BpkTheme {

  val typography: BpkTypography
    @Composable
    get() = LocalBpkTypography.current

  val colors: BpkColors
    @Composable
    get() = LocalBpkColors.current

}

private fun BpkTypography.toMaterialTypography(fontFamily: FontFamily) : Typography =
  Typography(
    defaultFontFamily = fontFamily,
    h1 = hero2,
    h2 = hero4,
    h3 = hero5,
    h4 = heading2,
    h5 = heading3,
    h6 = heading4,
    subtitle1 = bodyDefault,
    subtitle2 = footnote,
    body1 = bodyDefault,
    body2 = footnote,
    button = label2,
    caption = caption,
    overline = caption.copy(fontWeight = FontWeight.Bold),
  )

private fun BpkColors.toMaterialColors(): Colors =
  Colors(
    primary = primary,
    primaryVariant = if (isLight) BpkColor.SkyBlueShade01 else BpkColor.SkyBlueTint01,
    secondary = BpkColor.Monteverde,
    secondaryVariant = BpkColor.Glencoe,
    background = background,
    surface = backgroundElevation01,
    error = if (isLight) BpkColor.Panjin else BpkColor.Hillier,
    onPrimary = BpkColor.White,
    onSecondary = BpkColor.White,
    onBackground = textPrimary,
    onSurface = textPrimary,
    onError = BpkColor.White,
    isLight = isLight,
  )
