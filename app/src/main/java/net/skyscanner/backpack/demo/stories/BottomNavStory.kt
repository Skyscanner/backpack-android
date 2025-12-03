/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import net.skyscanner.backpack.bottomnav.BpkBottomNav
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification
import net.skyscanner.backpack.compose.floatingnotification.rememberBpkFloatingNotificationState
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.BottomNavComponent
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout

@Composable
@BottomNavComponent
@ViewStory
fun BottomNavStory(modifier: Modifier = Modifier) {
    val notificationState = rememberBpkFloatingNotificationState()
    val scope = rememberCoroutineScope()

    Box(modifier.navigationBarsPadding().fillMaxSize()) {
        AndroidLayout<BpkBottomNav>(R.layout.fragment_bottom_nav, R.id.bottom_nav) {
            addItem(1, R.string.bottom_nav_home, R.drawable.bpk_hotels)
            addItem(2, R.string.bottom_nav_explore, R.drawable.bpk_navigation)
            addItem(3, R.string.bottom_nav_trips, R.drawable.bpk_trips)
            addItem(4, R.string.bottom_nav_profile, R.drawable.bpk_account_circle)
            addOnNavigationItemReselectedListener { item, index ->
                scope.launch {
                    notificationState.show(text = "${item.title} #$index is reselected!")
                }
            }
            addOnNavigationItemSelectedListener { item, index ->
                scope.launch {
                    notificationState.show(text = "${item.title} #$index is selected!")
                }
            }
        }
        BpkFloatingNotification(notificationState)
    }
}
