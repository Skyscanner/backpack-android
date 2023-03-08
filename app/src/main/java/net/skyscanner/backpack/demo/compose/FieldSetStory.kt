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
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.fieldset.BpkFieldSet
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.textfield.BpkTextField
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.FieldSetComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.demo.meta.StoryKind
import net.skyscanner.backpack.demo.ui.FieldStatusSwitcher

@Composable
@FieldSetComponent
@ComposeStory(kind = StoryKind.StoryOnly)
fun FieldSetStory(modifier: Modifier = Modifier) =
  FieldStatusSwitcher(
    modifier = modifier.padding(BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    content = { FieldSetDemo(it) },
  )

@Composable
@FieldSetComponent
@ComposeStory("Default", StoryKind.ScreenshotOnly)
internal fun FieldSetScreenshotDefault(modifier: Modifier = Modifier) =
  FieldSetDemo(BpkFieldStatus.Default, modifier.padding(BpkSpacing.Base))

@Composable
@FieldSetComponent
@ComposeStory("Disabled", StoryKind.ScreenshotOnly)
internal fun FieldSetScreenshotDisabled(modifier: Modifier = Modifier) =
  FieldSetDemo(BpkFieldStatus.Disabled, modifier.padding(BpkSpacing.Base))

@Composable
@FieldSetComponent
@ComposeStory("Validated", StoryKind.ScreenshotOnly)
internal fun FieldSetScreenshotValidated(modifier: Modifier = Modifier) =
  FieldSetDemo(BpkFieldStatus.Validated, modifier.padding(BpkSpacing.Base))

@Composable
@FieldSetComponent
@ComposeStory("Error", StoryKind.ScreenshotOnly)
internal fun FieldSetScreenshotError(modifier: Modifier = Modifier) =
  FieldSetDemo(BpkFieldStatus.Error(stringResource(R.string.generic_error_text)), modifier.padding(BpkSpacing.Base))

@Composable
private fun FieldSetDemo(
  status: BpkFieldStatus,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
  ) {
    FieldSetWithDescriptionExample(status)
    BpkFieldSetWithoutDescriptionExample(status)
    BpkFieldSetNoLabelExample(status)
  }
}

@Composable
internal fun FieldSetWithDescriptionExample(status: BpkFieldStatus = BpkFieldStatus.Default) {
  BpkFieldSet(
    label = stringResource(R.string.generic_with_description),
    description = stringResource(R.string.generic_description),
    status = status,
  ) {
    var value by remember { mutableStateOf("") }
    BpkTextField(
      value = value,
      onValueChange = { value = it },
      placeholder = stringResource(R.string.generic_placeholder),
    )
  }
}

@Composable
internal fun BpkFieldSetWithoutDescriptionExample(status: BpkFieldStatus = BpkFieldStatus.Default) {
  BpkFieldSet(
    label = stringResource(R.string.generic_no_description),
    status = status,
  ) {
    var value by remember { mutableStateOf("") }
    BpkTextField(
      value = value,
      onValueChange = { value = it },
      placeholder = stringResource(R.string.generic_placeholder),
    )
  }
}

@Composable
internal fun BpkFieldSetNoLabelExample(status: BpkFieldStatus = BpkFieldStatus.Default) {
  BpkFieldSet(
    status = status,
  ) {
    var value by remember { mutableStateOf("") }
    BpkTextField(
      value = value,
      onValueChange = { value = it },
      placeholder = stringResource(R.string.generic_no_label),
    )
  }
}
