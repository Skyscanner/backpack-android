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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.max
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkElevation
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.data.Token
import net.skyscanner.backpack.demo.data.values

@Composable
@Preview
fun ElevationComposeStory() {
  Column(modifier = Modifier.padding(vertical = BpkSpacing.Base)) {
    val data = BpkElevation.values.sortedBy { it.value }
    for (item in data) {
      ElevationSample(token = item)
    }
  }
}

@Composable
private fun ElevationCard(token: Token<Dp>) {
  Surface(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(BpkBorderRadius.Md),
    elevation = token.value,
  ) {
    Box(contentAlignment = Alignment.Center) {
      BpkText(
        text = "${token.name} = ${token.value}",
        modifier = Modifier.padding(BpkSpacing.Base),
      )
    }
  }
}

@Composable
private fun ElevationSample(token: Token<Dp>) {
  Box(Modifier.padding(horizontal = max(BpkSpacing.Base, token.value), vertical = max(BpkSpacing.Base, token.value))) {
    ElevationCard(token)
  }
}
