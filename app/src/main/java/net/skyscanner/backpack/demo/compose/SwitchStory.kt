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
import net.skyscanner.backpack.compose.switch.BpkSwitch
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.SwitchComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@SwitchComponent
@ComposeStory
fun SwitchStory(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier.padding(BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
  ) {

    DefaultUncheckedSwitchExample()
    DefaultCheckedSwitchExample()

    DisabledUncheckedSwitchExample()
    DisabledCheckedSwitchExample()
    CustomContentSwitchExample()
  }
}

@Composable
internal fun DefaultUncheckedSwitchExample(modifier: Modifier = Modifier) {
  var checked by remember { mutableStateOf(false) }
  BpkSwitch(
    modifier = modifier.fillMaxWidth(),
    text = stringResource(id = R.string.toggle_default_unchecked),
    checked = checked,
    onCheckedChange = { checked = it },
  )
}

@Composable
internal fun DefaultCheckedSwitchExample(modifier: Modifier = Modifier) {
  var checked by remember { mutableStateOf(true) }
  BpkSwitch(
    modifier = modifier.fillMaxWidth(),
    text = stringResource(id = R.string.toggle_default_checked),
    checked = checked,
    onCheckedChange = { checked = it },
  )
}

@Composable
internal fun DisabledUncheckedSwitchExample(modifier: Modifier = Modifier) {
  BpkSwitch(
    modifier = modifier.fillMaxWidth(),
    text = stringResource(id = R.string.toggle_disabled_unchecked),
    enabled = false,
    checked = false,
    onCheckedChange = null,
  )
}

@Composable
internal fun DisabledCheckedSwitchExample(modifier: Modifier = Modifier) {
  BpkSwitch(
    modifier = modifier.fillMaxWidth(),
    text = stringResource(id = R.string.toggle_disabled_checked),
    enabled = false,
    checked = true,
    onCheckedChange = null,
  )
}

@Composable
internal fun CustomContentSwitchExample(modifier: Modifier = Modifier) {
  var checked by remember { mutableStateOf(false) }
  BpkSwitch(
    modifier = modifier.fillMaxWidth(),
    checked = checked,
    onCheckedChange = { checked = it },
  ) {
    Column {
      BpkText(text = stringResource(id = R.string.toggle_custom_title), style = BpkTheme.typography.heading5)
      BpkText(text = stringResource(id = R.string.toggle_custom_subtitle))
    }
  }
}
