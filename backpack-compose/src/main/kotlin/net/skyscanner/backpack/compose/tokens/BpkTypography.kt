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

package net.skyscanner.backpack.compose.tokens

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

data class BpkTypography internal constructor(
  val hero1: TextStyle,
  val hero2: TextStyle,
  val hero3: TextStyle,
  val hero4: TextStyle,
  val hero5: TextStyle,
  val heading1: TextStyle,
  val heading2: TextStyle,
  val heading3: TextStyle,
  val heading4: TextStyle,
  val heading5: TextStyle,
  val subheading: TextStyle,
  val bodyLongform: TextStyle,
  val bodyDefault: TextStyle,
  val footnote: TextStyle,
  val caption: TextStyle,
  val label1: TextStyle,
  val label2: TextStyle,
) {
  constructor(
    defaultFontFamily: FontFamily = FontFamily.SansSerif,
  ) : this(
    hero1 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.`8xl`,
      lineHeight = BpkLineHeight.`8xl`,
      letterSpacing = BpkLetterSpacing.Tight,
      fontFamily = defaultFontFamily,
    ),
    hero2 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.`7xl`,
      lineHeight = BpkLineHeight.`7xl`,
      letterSpacing = BpkLetterSpacing.Tight,
      fontFamily = defaultFontFamily,
    ),
    hero3 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.`6xl`,
      lineHeight = BpkLineHeight.`6xl`,
      letterSpacing = BpkLetterSpacing.Tight,
      fontFamily = defaultFontFamily,
    ),
    hero4 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.`5xl`,
      lineHeight = BpkLineHeight.`5xl`,
      letterSpacing = BpkLetterSpacing.Tight,
      fontFamily = defaultFontFamily,
    ),
    hero5 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Xxxxl,
      lineHeight = BpkLineHeight.Xxxxl,
      letterSpacing = BpkLetterSpacing.Tight,
      fontFamily = defaultFontFamily,
    ),
    heading1 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Xxxl,
      lineHeight = BpkLineHeight.Xxxl,
      fontFamily = defaultFontFamily,
    ),
    heading2 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Xxl,
      lineHeight = BpkLineHeight.Xxl,
      fontFamily = defaultFontFamily,
    ),
    heading3 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Xl,
      lineHeight = BpkLineHeight.XlTight,
      fontFamily = defaultFontFamily,
    ),
    heading4 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Lg,
      lineHeight = BpkLineHeight.LgTight,
      fontFamily = defaultFontFamily,
    ),
    heading5 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Base,
      lineHeight = BpkLineHeight.BaseTight,
      fontFamily = defaultFontFamily,
    ),
    subheading = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = BpkFontSize.Xl,
      lineHeight = BpkLineHeight.Xl,
      fontFamily = defaultFontFamily,
    ),
    bodyLongform = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = BpkFontSize.Lg,
      lineHeight = BpkLineHeight.Lg,
      fontFamily = defaultFontFamily,
    ),
    bodyDefault = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = BpkFontSize.Base,
      lineHeight = BpkLineHeight.Base,
      fontFamily = defaultFontFamily,
    ),
    footnote = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = BpkFontSize.Sm,
      lineHeight = BpkLineHeight.Sm,
      fontFamily = defaultFontFamily,
    ),
    caption = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = BpkFontSize.Xs,
      lineHeight = BpkLineHeight.Xs,
      fontFamily = defaultFontFamily,
    ),
    label1 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Base,
      lineHeight = BpkLineHeight.Base,
      fontFamily = defaultFontFamily,
    ),
    label2 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Sm,
      lineHeight = BpkLineHeight.Sm,
      fontFamily = defaultFontFamily,
    ),
  )
}
