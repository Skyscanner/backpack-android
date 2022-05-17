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

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Slider
import androidx.compose.material.Snackbar
import androidx.compose.material.Switch
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.icons.BpkIcons
import net.skyscanner.backpack.compose.icons.lg.Flight
import net.skyscanner.backpack.compose.radiobutton.BpkRadioButton
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
fun ThemeStory() {
  FlowRow(
    modifier = Modifier
      .scrollable(state = rememberScrollState(), orientation = Orientation.Horizontal)
      .padding(all = BpkSpacing.Base),
    mainAxisSpacing = BpkSpacing.Base,
    mainAxisAlignment = FlowMainAxisAlignment.Center,
    crossAxisSpacing = BpkSpacing.Base,
    crossAxisAlignment = FlowCrossAxisAlignment.Center,
  ) {

    TopAppBar {
      BpkText(
        modifier = Modifier.padding(horizontal = BpkSpacing.Base),
        text = "Top app bar",
        style = BpkTheme.typography.heading5,
      )
    }

    var selectedTabIndex by remember { mutableStateOf(1) }
    TabRow(modifier = Modifier.height(BpkSpacing.Xxl), selectedTabIndex = selectedTabIndex) {
      for (i in 0 until 3) {
        Tab(
          selected = i == selectedTabIndex,
          onClick = { selectedTabIndex = i },
        ) {
          BpkText(text = "Tab ${i + 1}")
        }
      }
    }

    BpkButton(text = "Primary button", onClick = { })

    BpkButton(text = "Secondary button", type = BpkButtonType.Secondary, onClick = { })

    var showAlertDialog by remember { mutableStateOf(false) }
    BpkButton(text = "Alert dialog", onClick = { showAlertDialog = true })
    if (showAlertDialog) {
      AlertDialog(
        onDismissRequest = { showAlertDialog = false },
        title = { BpkText(text = "Alert dialog") },
        confirmButton = {
          BpkButton(text = "OK", onClick = { showAlertDialog = false })
        },
        dismissButton = {
          BpkButton(text = "Cancel", type = BpkButtonType.Secondary, onClick = { showAlertDialog = false })
        },
        text = {
          BpkText(text = stringResource(id = R.string.stub))
        },
      )
    }

    var radioSelected by remember { mutableStateOf(true) }
    BpkRadioButton(selected = radioSelected, onClick = { radioSelected = !radioSelected })

    var checkboxChecked by remember { mutableStateOf(true) }
    Checkbox(checked = checkboxChecked, onCheckedChange = { checkboxChecked = it })

    var switchChecked by remember { mutableStateOf(true) }
    Switch(checked = switchChecked, onCheckedChange = { switchChecked = it })

    CircularProgressIndicator()

    LinearProgressIndicator(Modifier.width(144.dp))

    var sliderValue by remember { mutableStateOf(0.5f) }
    Slider(modifier = Modifier.width(144.dp), value = sliderValue, onValueChange = { sliderValue = it })

    FloatingActionButton(onClick = { }) {
      Icon(painter = BpkIcons.Lg.Flight, contentDescription = "Flight")
    }

    ExtendedFloatingActionButton(
      text = { BpkText(text = "FloatingActionButton") },
      onClick = { },
    )

    BpkCard(focused = true) {
      BpkText(modifier = Modifier.padding(all = BpkSpacing.Base), text = "Card")
    }

    Snackbar {
      BpkText(text = "Snackbar")
    }

    BottomAppBar {
      BpkText(text = "Bottom app bar")
    }
  }
}
