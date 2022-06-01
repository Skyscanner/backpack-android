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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.switch.BpkSwitch
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
fun SwitchStory() {
  Column(
    modifier = Modifier.padding(BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
  ) {

    DefaultUncheckedSwitchExample()
    DefaultCheckedSwitchExample()

    DisabledUncheckedSwitchExample()
    DisabledCheckedSwitchExample()
    CustomContentSwitchExample()
  }
}

@Preview
@Composable
fun DefaultUncheckedSwitchExample() {
  var checked by remember { mutableStateOf(false) }
  BpkSwitch(
    modifier = Modifier.fillMaxWidth(),
    text = stringResource(id = R.string.toggle_default_unchecked),
    checked = checked,
    onCheckedChange = { checked = it },
  )
}

@Preview
@Composable
fun DefaultCheckedSwitchExample() {
  var checked by remember { mutableStateOf(true) }
  BpkSwitch(
    modifier = Modifier.fillMaxWidth(),
    text = stringResource(id = R.string.toggle_default_checked),
    checked = checked,
    onCheckedChange = { checked = it },
  )
}

@Preview
@Composable
fun DisabledUncheckedSwitchExample() {
  BpkSwitch(
    modifier = Modifier.fillMaxWidth(),
    text = stringResource(id = R.string.toggle_disabled_unchecked),
    enabled = false,
    checked = false,
    onCheckedChange = null
  )
}

@Preview
@Composable
fun DisabledCheckedSwitchExample() {
  BpkSwitch(
    modifier = Modifier.fillMaxWidth(),
    text = stringResource(id = R.string.toggle_disabled_checked),
    enabled = false,
    checked = true,
    onCheckedChange = null,
  )
}

@Preview
@Composable
fun CustomContentSwitchExample() {
  var checked by remember { mutableStateOf(false) }
  BpkSwitch(
    modifier = Modifier.fillMaxWidth(),
    checked = checked,
    onCheckedChange = { checked = it },
  ) {
    Column {
      BpkText(text = stringResource(id = R.string.toggle_custom_title), style = BpkTheme.typography.heading5)
      BpkText(text = stringResource(id = R.string.toggle_custom_subtitle))
    }
  }
}
