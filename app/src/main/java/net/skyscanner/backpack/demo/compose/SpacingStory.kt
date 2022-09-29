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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.data.Token
import net.skyscanner.backpack.demo.data.values

@Composable
@Preview
fun SpacingComposeStory() {
  LazyColumn() {
    val data: List<Token<Dp>> = BpkSpacing.values.sortedBy { it.value }
    items(data) { item ->
      SpacingSampleRow(token = item)
    }
  }
}

@Composable
private fun SpacingSampleRow(token: Token<Dp>) {
  Row(
    modifier = Modifier
      .height(56.dp)
      .padding(BpkSpacing.Base),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    BpkText(
      text = stringResource(R.string.token_placeholder, token.name, token.value),
      modifier = Modifier.weight(1f),
    )
    Box(
      modifier = Modifier
        .height(BpkSpacing.Base)
        .width(token.value)
        .background(color = BpkTheme.colors.coreAccent),
    )
  }
}
