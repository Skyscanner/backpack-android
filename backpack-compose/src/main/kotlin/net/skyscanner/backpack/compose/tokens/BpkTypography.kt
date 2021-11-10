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
  val hero64: TextStyle,
  val hero48: TextStyle,
  val heading40: TextStyle,
  val heading32: TextStyle,
  val heading24: TextStyle,
  val heading20: TextStyle,
  val heading16: TextStyle,
  val subheading: TextStyle,
  val bodyLongform: TextStyle,
  val bodyDefault: TextStyle,
  val footnote: TextStyle,
  val caption: TextStyle,
  val label16: TextStyle,
  val label14: TextStyle,
) {
  constructor(
    defaultFontFamily: FontFamily = FontFamily.Default,
    hero64: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = 64.sp, // TODO - replace with token when available
      letterSpacing = (-0.02).em, // TODO - replace with token when available
    ),
    hero48: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = 48.sp, // TODO - replace with token when available
      letterSpacing = (-0.02).em, // TODO - replace with token when available
    ),
    heading40: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Xxxl,
    ),
    heading32: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Xxl,
    ),
    heading24: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Xl,
    ),
    heading20: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Lg,
    ),
    heading16: TextStyle = TextStyle(
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
    label16: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Base,
    ),
    label14: TextStyle = TextStyle(
      fontWeight = FontWeight.Bold,
      fontSize = BpkFontSize.Sm,
    ),
  ) : this(
    hero64 = hero64.copy(fontFamily = defaultFontFamily),
    hero48 = hero48.copy(fontFamily = defaultFontFamily),
    heading40 = heading40.copy(fontFamily = defaultFontFamily),
    heading32 = heading32.copy(fontFamily = defaultFontFamily),
    heading24 = heading24.copy(fontFamily = defaultFontFamily),
    heading20 = heading20.copy(fontFamily = defaultFontFamily),
    heading16 = heading16.copy(fontFamily = defaultFontFamily),
    subheading = subheading.copy(fontFamily = defaultFontFamily),
    bodyLongform = bodyLongform.copy(fontFamily = defaultFontFamily),
    bodyDefault = bodyDefault.copy(fontFamily = defaultFontFamily),
    footnote = footnote.copy(fontFamily = defaultFontFamily),
    caption = caption12.copy(fontFamily = defaultFontFamily),
    label16 = label16.copy(fontFamily = defaultFontFamily),
    label14 = label14.copy(fontFamily = defaultFontFamily),
  )
}
