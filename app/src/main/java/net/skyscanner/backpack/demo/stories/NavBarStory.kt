/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

import android.view.View
import androidx.annotation.LayoutRes
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification
import net.skyscanner.backpack.compose.floatingnotification.rememberBpkFloatingNotificationState
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.NavBarComponent
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout
import net.skyscanner.backpack.meta.StoryKind
import net.skyscanner.backpack.navbar.BpkNavBar
import net.skyscanner.backpack.navbar.NavBarStyle

@Composable
@NavBarComponent
@ViewStory("Default")
fun NavBarStoryDefault(modifier: Modifier = Modifier) =
    NavBarDemo(R.layout.fragment_nav_bar, modifier)

@Composable
@NavBarComponent
@ViewStory("Collapsed", StoryKind.ScreenshotOnly)
fun NavBarStoryCollapsed(modifier: Modifier = Modifier) =
    NavBarDemo(R.layout.fragment_nav_bar, modifier) {
        setExpanded(false)
    }

@Composable
@NavBarComponent
@ViewStory("With Icon", StoryKind.DemoOnly)
fun NavBarStoryWithIcon(modifier: Modifier = Modifier) =
    NavBarDemo(R.layout.fragment_nav_bar_with_icon, modifier)

@Composable
@NavBarComponent
@ViewStory("With Menu")
fun NavBarStoryWithMenu(modifier: Modifier = Modifier) =
    NavBarDemo(R.layout.fragment_nav_bar_with_menu, modifier) {
        setExpanded(false)
    }

@Composable
@NavBarComponent
@ViewStory("SurfaceContrast Style", StoryKind.DemoOnly)
fun NavBarStoryWithSurfaceContrast(modifier: Modifier = Modifier) =
    NavBarDemo(R.layout.fragment_nav_bar_with_menu, modifier) {
        style = NavBarStyle.SurfaceContrast
    }

@Composable
private fun NavBarDemo(
    @LayoutRes layoutId: Int,
    modifier: Modifier = Modifier,
    init: BpkNavBar.() -> Unit = {},
) {
    val notificationState = rememberBpkFloatingNotificationState()
    val scope = rememberCoroutineScope()

    Box(modifier) {
        AndroidLayout<BpkNavBar>(layoutId, R.id.appBar) {
            title = if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                "عنوان الصفحة"
            } else {
                "Nav Bar"
            }
            navAction = {
                scope.launch {
                    notificationState.show(text = "Nav is clicked!")
                }
            }
            menuAction = {
                scope.launch {
                    notificationState.show(text = "${it.itemId.let(resources::getResourceEntryName)} is clicked!")
                }
            }
            init()
        }
        BpkFloatingNotification(notificationState)
    }
}
