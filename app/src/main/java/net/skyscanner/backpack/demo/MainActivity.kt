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

package net.skyscanner.backpack.demo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import net.skyscanner.backpack.demo.meta.Component
import net.skyscanner.backpack.demo.meta.Story
import net.skyscanner.backpack.demo.ui.CasesScreen
import net.skyscanner.backpack.demo.ui.ComponentsScreen
import net.skyscanner.backpack.demo.ui.DemoScreen

/**
 * An activity representing a list of Components. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ComponentDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MainActivity : BpkBaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      BackpackDemoTheme {

        var currentComponent by remember { mutableStateOf<Component?>(null) }
        var currentCase by remember { mutableStateOf<Story?>(null) }

        when {
          currentCase != null -> DemoScreen(
            case = currentCase!!,
            onBack = { currentCase = null },
          )
          currentComponent != null -> CasesScreen(
            component = currentComponent!!,
            onBack = { currentComponent = null },
            onClick = { currentCase = it },
          )
          else -> ComponentsScreen(
            onClick = { currentComponent = it },
          )
        }
      }
    }
  }
}
