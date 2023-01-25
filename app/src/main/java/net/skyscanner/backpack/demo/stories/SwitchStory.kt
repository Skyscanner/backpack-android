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

package net.skyscanner.backpack.demo.stories

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import net.skyscanner.backpack.compose.switch.BpkSwitch
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.meta.Component
import net.skyscanner.backpack.demo.meta.Kind
import net.skyscanner.backpack.demo.meta.Story
import net.skyscanner.backpack.toggle.BpkSwitch

@Component(
  name = "Switch",
  link = "switch",
  kind = Kind.View,
)
annotation class SwitchComponent

@Composable
@Story
fun SwitchStory(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier.padding(BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
  ) {

    DefaultUncheckedSwitchExample()
    DefaultCheckedSwitchExample()

    DisabledUncheckedSwitchExample()
    DisabledCheckedSwitchExample()
  }
}

@Preview
@Composable
fun DefaultUncheckedSwitchExample() {
  var checked by remember { mutableStateOf(false) }
  AndroidView(
    modifier = Modifier.fillMaxWidth(),
    factory = {
      BpkSwitch(it).apply {
        this.isChecked = checked
        this.text = it.getText(R.string.toggle_default_unchecked)
        this.setOnCheckedChangeListener { _, isChecked ->
          checked = isChecked
        }
      }
    },
  )
}

@Preview
@Composable
fun DefaultCheckedSwitchExample() {
  var checked by remember { mutableStateOf(true) }
  AndroidView(
    modifier = Modifier.fillMaxWidth(),
    factory = {
      BpkSwitch(it).apply {
        this.isChecked = checked
        this.text = it.getText(R.string.toggle_default_checked)
        this.setOnCheckedChangeListener { _, isChecked ->
          checked = isChecked
        }
      }
    },
  )
}

@Preview
@Composable
fun DisabledUncheckedSwitchExample() {
  AndroidView(
    modifier = Modifier.fillMaxWidth(),
    factory = {
      BpkSwitch(it).apply {
        this.isChecked = false
        this.isEnabled = false
        this.text = it.getText(R.string.toggle_disabled_unchecked)
      }
    },
  )
}

@Preview
@Composable
fun DisabledCheckedSwitchExample() {
  AndroidView(
    modifier = Modifier.fillMaxWidth(),
    factory = {
      BpkSwitch(it).apply {
        this.isChecked = true
        this.isEnabled = false
        this.text = it.getText(R.string.toggle_disabled_checked)
      }
    },
  )
}
