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
 *
 */

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.data.Token
import net.skyscanner.backpack.demo.data.values

@Composable
@Preview
fun ColorsComposeStory() {
  LazyColumn() {
    val data: List<Token<Color>> = BpkColor.values
    items(data) { item ->
      ColorSampleRow(token = item)
    }
  }
}

@Composable
private fun ColorSampleRow(token: Token<Color>) {
  Row(
    modifier = Modifier.height(56.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    BpkText(
      text = token.name,
      modifier = Modifier
        .weight(1f)
        .padding(BpkSpacing.Base),
    )
    Box(
      modifier = Modifier
        .height(56.dp)
        .width(112.dp)
        .padding(1.dp)
        .background(token.value),
      contentAlignment = Alignment.Center,
    ) {
      BpkText(
        text = colorToHex(color = token.value),
        color = if (token.value.luminance() > 0.5) Color.Black else Color.White,
      )
    }
  }
}

private fun colorToHex(color: Color): String {
  val argb = color.toArgb()
  val hexString = Integer.toHexString(argb)
  val truncatedHexString = hexString.removeRange(0..1)
  return "#$truncatedHexString".uppercase()
}
