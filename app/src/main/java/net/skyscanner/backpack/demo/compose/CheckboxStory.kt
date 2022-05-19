/**
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
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.checkbox.BpkCheckbox
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
fun CheckboxStory() {
  Column(
    modifier = Modifier.padding(BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
  ) {

    DefaultCheckboxSample()
    IntermediateCheckboxSample()
    UncheckedCheckboxSample()
    CheckedCheckboxSample()
    DisabledUncheckedCheckboxSample()
    DisabledCheckedCheckboxSample()
    CustomContentCheckboxSample()
  }

}

@Composable
@Preview
private fun DefaultCheckboxSample() {
  BackpackPreview {
    var checked by remember { mutableStateOf(true) }

    BpkCheckbox(
      text = stringResource(id = R.string.toggle_default),
      checked = checked,
      onCheckedChange = { checked = it },
    )
  }
}


@Composable
@Preview
private fun IntermediateCheckboxSample() {
  BackpackPreview {
    BpkCheckbox(
      text = stringResource(id = R.string.toggle_indeterminate),
      state = ToggleableState.Indeterminate,
      onClick = { },
    )
  }
}

@Composable
private fun UncheckedCheckboxSample() {
  BackpackPreview {
    BpkCheckbox(
      text = stringResource(id = R.string.toggle_default_unchecked),
      checked = false,
      onCheckedChange = {},
    )
  }
}

@Composable
@Preview
private fun CheckedCheckboxSample() {
  BackpackPreview {
    BpkCheckbox(
      text = stringResource(id = R.string.toggle_default_checked),
      checked = true,
      onCheckedChange = {},
    )
  }
}

@Composable
@Preview
private fun DisabledUncheckedCheckboxSample() {
  BackpackPreview {
    BpkCheckbox(
      text = stringResource(id = R.string.toggle_disabled_unchecked),
      checked = false,
      enabled = false,
      onCheckedChange = {},
    )
  }
}

@Composable
@Preview
private fun DisabledCheckedCheckboxSample() {
  BackpackPreview {
    BpkCheckbox(
      text = stringResource(id = R.string.toggle_disabled_checked),
      checked = true,
      enabled = false,
      onCheckedChange = {},
    )
  }
}

@Composable
@Preview
private fun CustomContentCheckboxSample() {
  BackpackPreview {
    BpkCheckbox(
      checked = true,
      onCheckedChange = {},
    ) {
      Column {
        BpkText(text = stringResource(id = R.string.toggle_custom_title), style = BpkTheme.typography.heading5)
        BpkText(text = stringResource(id = R.string.toggle_custom_subtitle))
      }
    }
  }
}
