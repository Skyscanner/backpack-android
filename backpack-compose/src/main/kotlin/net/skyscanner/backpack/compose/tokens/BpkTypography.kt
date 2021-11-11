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

package net.skyscanner.backpack.compose.tokens

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

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
      fontSize = 120.sp, // TODO - replace with token when available
      letterSpacing = (-0.02).em, // TODO - replace with token when available
      fontFamily = defaultFontFamily,
    ),
    hero2 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = 96.sp, // TODO - replace with token when available
      letterSpacing = (-0.02).em, // TODO - replace with token when available
      fontFamily = defaultFontFamily,
    ),
    hero3 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = 76.sp, // TODO - replace with token when available
      letterSpacing = (-0.02).em, // TODO - replace with token when available
      fontFamily = defaultFontFamily,
    ),
    hero4 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = 64.sp, // TODO - replace with token when available
      letterSpacing = (-0.02).em, // TODO - replace with token when available
      fontFamily = defaultFontFamily,
    ),
    hero5 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = 48.sp, // TODO - replace with token when available
      letterSpacing = (-0.02).em, // TODO - replace with token when available
      fontFamily = defaultFontFamily,
    ),
    heading1 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Xxxl,
      fontFamily = defaultFontFamily,
    ),
    heading2 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Xxl,
      fontFamily = defaultFontFamily,
    ),
    heading3 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Xl,
      fontFamily = defaultFontFamily,
    ),
    heading4 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Lg,
      fontFamily = defaultFontFamily,
    ),
    heading5 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Base,
      fontFamily = defaultFontFamily,
    ),
    subheading = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = BpkFontSize.Xl,
      fontFamily = defaultFontFamily,
    ),
    bodyLongform = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = BpkFontSize.Lg,
      fontFamily = defaultFontFamily,
    ),
    bodyDefault = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = BpkFontSize.Base,
      fontFamily = defaultFontFamily,
    ),
    footnote = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = BpkFontSize.Sm,
      fontFamily = defaultFontFamily,
    ),
    caption = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = BpkFontSize.Xs,
      fontFamily = defaultFontFamily,
    ),
    label1 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Base,
      fontFamily = defaultFontFamily,
    ),
    label2 = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Sm,
      fontFamily = defaultFontFamily,
    ),
  )
}
