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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.data.Token
import net.skyscanner.backpack.demo.data.values

@Composable
@Preview
fun RadiiComposeStory() {
  LazyColumn() {
    val data: List<Token<Dp>> = BpkBorderRadius.values.sortedBy { it.value }
    items(data) { item ->
      RadiiSample(token = item)
    }
  }
}

@Composable
private fun RadiiSample(token: Token<Dp>) {
  Box(
    modifier = Modifier.padding(BpkSpacing.Base),
  ) {
    RadiiCard(token = token)
  }
}

@Composable
private fun RadiiCard(token: Token<Dp>) {
  Surface(
    modifier = Modifier.sizeIn(minHeight = token.value * 2),
    color = BpkTheme.colors.coreAccent,
    shape = RoundedCornerShape(token.value),
  ) {
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier
        .fillMaxWidth()
        .padding(BpkSpacing.Base),
    ) {
      BpkText(
        text = stringResource(R.string.token_placeholder, token.name, token.value),
        style = BpkTheme.typography.bodyLongform,
        color = BpkTheme.colors.textPrimaryInverse
      )
    }
  }
}
