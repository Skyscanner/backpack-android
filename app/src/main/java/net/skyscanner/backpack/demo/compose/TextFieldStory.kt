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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.textfield.BpkTextField
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Accessibility
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
fun TextFiledStory() {

  val loremIpsum = stringResource(R.string.stub_sm)

  FieldStatusSwitcher(
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    modifier = Modifier
      .verticalScroll(rememberScrollState())
      .padding(BpkSpacing.Base),
  ) { status ->
    CompositionLocalProvider(LocalTextStyle provides BpkTheme.typography.label2) {

      BpkText(text = "Default")
      var defaultValue by remember { mutableStateOf("") }
      BpkTextField(
        value = defaultValue,
        status = status,
        onValueChange = { defaultValue = it },
        placeholder = "Placeholder",
      )

      BpkText("Read only")
      BpkTextField(
        readOnly = true,
        value = "Read only value",
        onValueChange = { },
        placeholder = "Placeholder",
        status = status,
      )

      BpkText("Forced single line")
      var forcedSingleLine by remember { mutableStateOf("") }
      BpkTextField(
        maxLines = 1,
        value = forcedSingleLine,
        onValueChange = { forcedSingleLine = it },
        placeholder = loremIpsum,
        status = status,
      )

      BpkText("With leading icon")
      var withLeadingIconValue by remember { mutableStateOf("") }
      BpkTextField(
        value = withLeadingIconValue,
        onValueChange = { withLeadingIconValue = it },
        placeholder = "Placeholder",
        icon = BpkIcon.Accessibility,
        status = status,
      )

      BpkText("With long text")
      var withLongTextValue by remember { mutableStateOf(loremIpsum) }
      BpkTextField(
        value = withLongTextValue,
        onValueChange = { withLongTextValue = it },
        placeholder = "Placeholder",
        icon = BpkIcon.Accessibility,
        status = status,
      )
    }
  }
}
