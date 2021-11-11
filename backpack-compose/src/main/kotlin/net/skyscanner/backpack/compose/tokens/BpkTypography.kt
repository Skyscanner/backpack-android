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
    defaultFontFamily: FontFamily = FontFamily.Default,
    hero1: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = 120.sp, // TODO - replace with token when available
      letterSpacing = (-0.02).em, // TODO - replace with token when available
    ),
    hero2: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = 96.sp, // TODO - replace with token when available
      letterSpacing = (-0.02).em, // TODO - replace with token when available
    ),
    hero3: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = 76.sp, // TODO - replace with token when available
      letterSpacing = (-0.02).em, // TODO - replace with token when available
    ),
    hero4: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = 64.sp, // TODO - replace with token when available
      letterSpacing = (-0.02).em, // TODO - replace with token when available
    ),
    hero5: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = 48.sp, // TODO - replace with token when available
      letterSpacing = (-0.02).em, // TODO - replace with token when available
    ),
    heading1: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Xxxl,
    ),
    heading2: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Xxl,
    ),
    heading3: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Xl,
    ),
    heading4: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Lg,
    ),
    heading5: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Base,
    ),
    subheading: TextStyle = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = BpkFontSize.Xl,
    ),
    bodyLongform: TextStyle = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = BpkFontSize.Lg,
    ),
    bodyDefault: TextStyle = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = BpkFontSize.Base,
    ),
    footnote: TextStyle = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = BpkFontSize.Sm,
    ),
    caption12: TextStyle = TextStyle(
      fontWeight = FontWeight.Normal,
      fontSize = BpkFontSize.Xs,
    ),
    label1: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Base,
    ),
    label2: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Sm,
    ),
  ) : this(
    hero1 = hero1.copy(fontFamily = defaultFontFamily),
    hero2 = hero2.copy(fontFamily = defaultFontFamily),
    hero3 = hero3.copy(fontFamily = defaultFontFamily),
    hero4 = hero4.copy(fontFamily = defaultFontFamily),
    hero5 = hero5.copy(fontFamily = defaultFontFamily),
    heading1 = heading1.copy(fontFamily = defaultFontFamily),
    heading2 = heading2.copy(fontFamily = defaultFontFamily),
    heading3 = heading3.copy(fontFamily = defaultFontFamily),
    heading4 = heading4.copy(fontFamily = defaultFontFamily),
    heading5 = heading5.copy(fontFamily = defaultFontFamily),
    subheading = subheading.copy(fontFamily = defaultFontFamily),
    bodyLongform = bodyLongform.copy(fontFamily = defaultFontFamily),
    bodyDefault = bodyDefault.copy(fontFamily = defaultFontFamily),
    footnote = footnote.copy(fontFamily = defaultFontFamily),
    caption = caption12.copy(fontFamily = defaultFontFamily),
    label1 = label1.copy(fontFamily = defaultFontFamily),
    label2 = label2.copy(fontFamily = defaultFontFamily),
  )
}
