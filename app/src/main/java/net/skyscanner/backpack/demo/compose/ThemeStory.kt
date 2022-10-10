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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.BottomAppBar
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Slider
import androidx.compose.material.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkSpacing

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

    LinearProgressIndicator(Modifier.width(144.dp))

    var sliderValue by remember { mutableStateOf(0.5f) }
    Slider(modifier = Modifier.width(144.dp), value = sliderValue, onValueChange = { sliderValue = it })

    Snackbar {
      BpkText(text = "Snackbar")
    }

    BottomAppBar {
      BpkText(text = "Bottom app bar")
    }
  }
}
