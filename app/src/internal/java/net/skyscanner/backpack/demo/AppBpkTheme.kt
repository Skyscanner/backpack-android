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

package net.skyscanner.backpack.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.theme.LocalBpkTypography
import net.skyscanner.backpack.compose.tokens.BpkTypography

@Composable
fun AppBpkTheme(
  content: @Composable () -> Unit
) {
  val typography = BpkTypography(
    defaultFontFamily = FontFamily(
      Font(R.font.skyscanner_relative_android_book),
      Font(R.font.skyscanner_relative_android_bold, weight = FontWeight.Bold)
    )
  )
  CompositionLocalProvider(
    LocalBpkTypography provides typography,
  ) {
    BpkTheme(
      typography = typography,
      content = content
    )
  }
}
