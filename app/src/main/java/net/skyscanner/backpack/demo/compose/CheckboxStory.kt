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
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import net.skyscanner.backpack.compose.checkbox.BpkCheckbox
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
fun CheckboxStory() {
  Column(
    modifier = Modifier.padding(horizontal = BpkSpacing.Md, vertical = BpkSpacing.Md),
  ) {

    var checked by remember { mutableStateOf(true) }

    BpkCheckbox(
      text = "Default",
      checked = checked,
      onCheckedChange = { checked = it },
    )

    AndroidView(
      modifier = Modifier.padding(start = 8.dp),
      factory = {
      net.skyscanner.backpack.checkbox.BpkCheckbox(it).apply {
        text = "Default"
        checked = true
      }
    })

    BpkCheckbox(
      text = "Indeterminate",
      state = ToggleableState.Indeterminate,
      onClick = { },
    )

    BpkCheckbox(
      text = "Default Unchecked",
      checked = false,
      onCheckedChange = {},
    )

    BpkCheckbox(
      text = "Default Checked",
      checked = true,
      onCheckedChange = {},
    )

    BpkCheckbox(
      text = "Disabled Unchecked",
      checked = false,
      enabled = false,
      onCheckedChange = {},
    )

    BpkCheckbox(
      text = "Disabled Checked",
      checked = true,
      enabled = false,
      onCheckedChange = {},
    )
  }
}
