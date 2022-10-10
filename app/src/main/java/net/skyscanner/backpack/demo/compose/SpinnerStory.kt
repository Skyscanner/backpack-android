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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.spinner.BpkSpinner
import net.skyscanner.backpack.compose.spinner.BpkSpinnerSize
import net.skyscanner.backpack.compose.spinner.BpkSpinnerStyle
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
@Preview
fun SpinnerStory() {
  Column(Modifier.padding(BpkSpacing.Base), verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base)) {
    BpkSpinnerStyle.values().forEach { style ->
      SpinnersRow(style = style)
    }
  }
}

@Composable
private fun SpinnersRow(
  style: BpkSpinnerStyle,
  modifier: Modifier = Modifier,
) {
  Column(modifier) {

    BpkText(style.name)

    Surface(
      color = if (style == BpkSpinnerStyle.OnDarkSurface) BpkTheme.colors.surfaceContrast else Color.Transparent,
      contentColor = if (style == BpkSpinnerStyle.OnDarkSurface) BpkColor.White else LocalContentColor.current,
    ) {

      Row {
        BpkSpinnerSize.values().forEach { size ->

          Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
          ) {

            Box(Modifier.height(BpkSpacing.Xxl), contentAlignment = Alignment.Center) {
              BpkSpinner(size = size, style = style)
            }

            BpkText(text = size.name, style = BpkTheme.typography.caption)
          }
        }
      }
    }
  }
}
