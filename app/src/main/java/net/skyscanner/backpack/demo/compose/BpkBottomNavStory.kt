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
import net.skyscanner.backpack.compose.bottomnav.BpkBottomNav
import net.skyscanner.backpack.compose.bottomnav.BpkBottomNavIcon
import net.skyscanner.backpack.compose.bottomnav.TabItem
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.AccountCircle
import net.skyscanner.backpack.compose.tokens.Explore
import net.skyscanner.backpack.compose.tokens.Hotels
import net.skyscanner.backpack.compose.tokens.Trips

@Composable
fun BpkBottomNavStory() {
  var selectedItemId by remember { mutableStateOf(1) }
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Bottom,
  ) {
    BpkBottomNav(
      tabItems = listOf(
        TabItem(icon = { BpkBottomNavIcon(icon = BpkIcon.Hotels) }, title = "Home", id = 1),
        TabItem(icon = { BpkIcon(icon = BpkIcon.Explore, contentDescription = null) }, title = "Explore", id = 2),
        TabItem(icon = { BpkIcon(icon = BpkIcon.Trips, contentDescription = null) }, title = "Trips", id = 3),
        TabItem(icon = { BpkIcon(icon = BpkIcon.AccountCircle, contentDescription = null) }, title = "Profile", id = 4),
      ),
      selectedItemId = selectedItemId,
      onTabClicked = { selectedItemId = it },
    )
  }
}
