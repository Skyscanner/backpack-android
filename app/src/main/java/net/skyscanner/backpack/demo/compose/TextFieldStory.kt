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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.LocalTextStyle
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.textfield.BpkTextField
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Accessibility
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.TextFieldComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.demo.meta.StoryKind
import net.skyscanner.backpack.demo.ui.FieldStatusSwitcher

@Composable
@TextFieldComponent
@ComposeStory
fun TextFieldStory(
  modifier: Modifier = Modifier,
  initialStatus: BpkFieldStatus = BpkFieldStatus.Default,
) =
  FieldStatusSwitcher(
    initialStatus = initialStatus,
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    modifier = modifier
      .verticalScroll(rememberScrollState())
      .padding(BpkSpacing.Base),
  ) { status ->
    CompositionLocalProvider(LocalTextStyle provides BpkTheme.typography.label2) {

      BpkText(text = stringResource(R.string.generic_default))
      TextFieldDefaultExample(status = status)

      BpkText(stringResource(R.string.generic_read_only))
      TextFieldReadOnlyExample(status = status)

      BpkText(stringResource(R.string.generic_with_leading_icon))
      TextFieldLeadingIconExample(status = status)

      BpkText(stringResource(R.string.generic_single_line))
      TextFieldSingleLineExample(status = status)

      BpkText(stringResource(R.string.generic_multiline))
      TextFieldMultilineExample(status = status)
    }
  }

@Composable
@TextFieldComponent
@ComposeStory("Disabled", StoryKind.ScreenshotOnly)
internal fun TextFieldScreenshotDisabled(modifier: Modifier = Modifier) =
  TextFieldStory(modifier, BpkFieldStatus.Disabled)

@Composable
@TextFieldComponent
@ComposeStory("Validated", StoryKind.ScreenshotOnly)
internal fun TextFieldScreenshotValidated(modifier: Modifier = Modifier) =
  TextFieldStory(modifier, BpkFieldStatus.Validated)

@Composable
@TextFieldComponent
@ComposeStory("Error", StoryKind.ScreenshotOnly)
internal fun TextFieldScreenshotError(modifier: Modifier = Modifier) =
  TextFieldStory(modifier, BpkFieldStatus.Error(stringResource(R.string.generic_error_text)))

@Composable
private fun TextFieldDefaultExample(
  modifier: Modifier = Modifier,
  status: BpkFieldStatus = BpkFieldStatus.Default,
) {
  var value by remember { mutableStateOf("") }
  BpkTextField(
    modifier = modifier,
    value = value,
    status = status,
    onValueChange = { value = it },
    placeholder = stringResource(R.string.generic_placeholder),
  )
}

@Composable
private fun TextFieldReadOnlyExample(
  modifier: Modifier = Modifier,
  status: BpkFieldStatus = BpkFieldStatus.Default,
) {
  BpkTextField(
    modifier = modifier,
    readOnly = true,
    value = stringResource(R.string.generic_read_only_value),
    onValueChange = { },
    placeholder = stringResource(R.string.generic_placeholder),
    status = status,
  )
}

@Composable
private fun TextFieldLeadingIconExample(
  modifier: Modifier = Modifier,
  status: BpkFieldStatus = BpkFieldStatus.Default,
) {
  var value by remember { mutableStateOf("") }
  BpkTextField(
    modifier = modifier,
    value = value,
    onValueChange = { value = it },
    placeholder = stringResource(R.string.generic_placeholder),
    icon = BpkIcon.Accessibility,
    status = status,
  )
}

@Composable
private fun TextFieldSingleLineExample(
  modifier: Modifier = Modifier,
  status: BpkFieldStatus = BpkFieldStatus.Default,
) {
  val loremIpsum = stringResource(R.string.stub_sm)
  var value by remember { mutableStateOf("") }
  BpkTextField(
    modifier = modifier,
    value = value,
    onValueChange = { value = it },
    placeholder = loremIpsum,
    status = status,
  )
}

@Composable
private fun TextFieldMultilineExample(
  modifier: Modifier = Modifier,
  status: BpkFieldStatus = BpkFieldStatus.Default,
) {
  val loremIpsum = stringResource(R.string.stub_sm)
  var value by remember { mutableStateOf(loremIpsum) }
  BpkTextField(
    modifier = modifier,
    value = value,
    onValueChange = { value = it },
    placeholder = stringResource(R.string.generic_placeholder),
    icon = BpkIcon.Accessibility,
    status = status,
    maxLines = Int.MAX_VALUE,
  )
}
