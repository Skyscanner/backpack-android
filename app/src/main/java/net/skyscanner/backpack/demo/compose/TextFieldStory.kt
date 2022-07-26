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
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.textfield.BpkTextField
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Accessibility
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
fun TextFiledStory() {

  FieldStatusSwitcher(
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    modifier = Modifier
      .verticalScroll(rememberScrollState())
      .padding(BpkSpacing.Base),
  ) { status ->
    CompositionLocalProvider(LocalTextStyle provides BpkTheme.typography.label2) {

      BpkText(text = stringResource(R.string.default_text))
      TextFieldDefaultExample(status)

      BpkText(stringResource(R.string.read_only))
      TextFieldReadOnlyExample(status)

      BpkText(stringResource(R.string.with_leading_icon))
      TextFieldLeadingIconExample(status)

      BpkText(stringResource(R.string.single_line))
      TextFieldSingleLineExample(status)

      BpkText(stringResource(R.string.multiline))
      TextFieldMultilineExample(status)
    }
  }
}

@Preview
@Composable
fun TextFieldDefaultExample(status: BpkFieldStatus = BpkFieldStatus.Default) {
  var value by remember { mutableStateOf("") }
  BpkTextField(
    value = value,
    status = status,
    onValueChange = { value = it },
    placeholder = stringResource(R.string.placeholder),
  )
}

@Preview
@Composable
fun TextFieldReadOnlyExample(status: BpkFieldStatus = BpkFieldStatus.Default) {
  BpkTextField(
    readOnly = true,
    value = stringResource(R.string.read_only_value),
    onValueChange = { },
    placeholder = stringResource(R.string.placeholder),
    status = status,
  )
}

@Preview
@Composable
fun TextFieldLeadingIconExample(status: BpkFieldStatus = BpkFieldStatus.Default) {
  var value by remember { mutableStateOf("") }
  BpkTextField(
    value = value,
    onValueChange = { value = it },
    placeholder = stringResource(R.string.placeholder),
    icon = BpkIcon.Accessibility,
    status = status,
  )
}

@Preview
@Composable
fun TextFieldSingleLineExample(status: BpkFieldStatus = BpkFieldStatus.Default) {
  val loremIpsum = stringResource(R.string.stub_sm)
  var value by remember { mutableStateOf("") }
  BpkTextField(
    value = value,
    onValueChange = { value = it },
    placeholder = loremIpsum,
    status = status,
  )
}

@Preview
@Composable
fun TextFieldMultilineExample(status: BpkFieldStatus = BpkFieldStatus.Default) {
  val loremIpsum = stringResource(R.string.stub_sm)
  var value by remember { mutableStateOf(loremIpsum) }
  BpkTextField(
    value = value,
    onValueChange = { value = it },
    placeholder = stringResource(R.string.placeholder),
    icon = BpkIcon.Accessibility,
    status = status,
    maxLines = Int.MAX_VALUE,
  )
}
