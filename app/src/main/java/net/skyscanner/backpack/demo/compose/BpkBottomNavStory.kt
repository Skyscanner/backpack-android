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
 *
 */

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import net.skyscanner.backpack.compose.bottomnav.BpkBottomNav
import net.skyscanner.backpack.compose.bottomnav.TabIcon
import net.skyscanner.backpack.compose.bottomnav.TabItem
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.AccountCircle
import net.skyscanner.backpack.compose.tokens.Trips
import net.skyscanner.backpack.demo.R

@Composable
fun BpkBottomNavStory() {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Bottom,
  ) {
    BpkBottomNavSample()
  }
}

@Composable
fun BpkBottomNavSample(defaultItemId: Int = 1) {
  var selectedItemId by remember { mutableStateOf(defaultItemId) }
  BpkBottomNav(
    tabItems = listOf(
      TabItem(icon = TabIcon.Custom(painter = painterResource(id = R.drawable.bpk_explore)), title = "Explore", id = 1),
      TabItem(icon = TabIcon.Bpk(icon = BpkIcon.Trips), title = "Trips", id = 2),
      TabItem(icon = TabIcon.Bpk(icon = BpkIcon.AccountCircle), title = "Profile", id = 3),
    ),
    selectedItemId = selectedItemId,
    onTabClicked = { selectedItemId = it },
  )
}
