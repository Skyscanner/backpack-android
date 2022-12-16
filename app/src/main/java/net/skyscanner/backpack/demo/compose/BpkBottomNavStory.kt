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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.bottomnav.BpkBottomNav
import net.skyscanner.backpack.compose.bottomnav.BpkBottomNavItem
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.AccountCircle
import net.skyscanner.backpack.compose.tokens.Trips
import net.skyscanner.backpack.demo.R

@Composable
fun BpkBottomNavStory(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.BottomEnd,
  ) {
    BpkBottomNavSample()
  }
}

@Composable
fun BpkBottomNavSample(
  modifier: Modifier = Modifier,
  defaultItemId: Int = 1,
) {
  var selectedItemId by remember { mutableStateOf(defaultItemId) }
  BpkBottomNav(
    modifier = modifier,
    items = listOf(
      BpkBottomNavItem(
        painter = painterResource(id = R.drawable.sample_icon),
        title = stringResource(R.string.bottom_nav_explore),
        id = 1,
      ),
      BpkBottomNavItem(
        icon = BpkIcon.Trips,
        title = stringResource(R.string.bottom_nav_trips),
        id = 2,
      ),
      BpkBottomNavItem(
        icon = BpkIcon.AccountCircle,
        title = stringResource(R.string.navigation_account),
        id = 3,
        showBadge = true,
      ),
    ),
    selectedItemId = selectedItemId,
    onTabClicked = { selectedItemId = it },
  )
}
