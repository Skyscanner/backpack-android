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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.nudger.BpkNudger
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
@Preview
fun NudgerStory() {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base, Alignment.CenterVertically),
  ) {

    NudgerExample(name = stringResource(R.string.generic_default)) {
      NudgerExample()
    }

    NudgerExample(name = stringResource(R.string.nudger_minus_disabled)) {
      NudgerExample(initialValue = NudgerStoryMin)
    }

    NudgerExample(name = stringResource(R.string.nudger_plus_disabled)) {
      NudgerExample(initialValue = NudgerStoryMax)
    }

    NudgerExample(name = stringResource(R.string.generic_disabled)) {
      NudgerExample(enabled = false)
    }
  }
}

@Composable
@Preview
fun NudgerExample(
  modifier: Modifier = Modifier,
  initialValue: Int = NudgerStoryAvg,
  enabled: Boolean = true,
) {
  var value by remember { mutableStateOf(initialValue) }

  BpkNudger(
    value = value,
    onValueChange = { value = it },
    min = NudgerStoryMin,
    max = NudgerStoryMax,
    enabled = enabled,
  )
}

@Composable
private fun NudgerExample(
  name: String,
  modifier: Modifier = Modifier,
  content: @Composable ColumnScope.() -> Unit,
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
  ) {
    BpkText(text = name, style = BpkTheme.typography.footnote)
    content()
  }
}

internal val NudgerStoryMin = 0
internal val NudgerStoryAvg = 5
internal val NudgerStoryMax = 10
