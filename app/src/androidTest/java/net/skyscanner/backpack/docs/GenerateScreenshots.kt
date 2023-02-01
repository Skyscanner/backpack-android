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

package net.skyscanner.backpack.docs

import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.rule.ActivityTestRule
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification
import net.skyscanner.backpack.compose.floatingnotification.rememberBpkFloatingNotificationState
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.demo.BackpackDemoTheme
import net.skyscanner.backpack.demo.BpkBaseActivity
import net.skyscanner.backpack.demo.compose.LocalAutomationMode
import net.skyscanner.backpack.demo.compose.LocalFloatingNotification
import net.skyscanner.backpack.demo.meta.Stories
import net.skyscanner.backpack.demo.meta.StoryEntry
import net.skyscanner.backpack.demo.meta.all
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
open class GenerateScreenshots(
  private val storyEntry: StoryEntry,
) {

  companion object {
    @JvmStatic
    @Parameterized.Parameters(name = "{0} Screenshot")
    fun data(): List<StoryEntry> = Stories.all().filter { it.screenshot }
  }

  @get:Rule
  var activityRule = ActivityTestRule(BpkBaseActivity::class.java, true, false)

  @get:Rule
  val composeTestRule = AndroidComposeTestRule(activityRule) { it.activity }

  @Test
  fun testTakeScreenshot() {
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    runActivityAndTakeScreenshot()
  }

  @Test
  fun testTakeScreenshotDm() {
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    runActivityAndTakeScreenshot("dm")
  }

  private fun runActivityAndTakeScreenshot(suffix: String? = null) {
    val intent = Intent()
    activityRule.launchActivity(intent)
    composeTestRule.setContent {
      BackpackDemoTheme {
        val floatingNotificationState = rememberBpkFloatingNotificationState()
        CompositionLocalProvider(
          LocalAutomationMode provides true,
          LocalFloatingNotification provides floatingNotificationState,
        ) {
          Box {
            Column {
              BpkTopNavBar(
                navIcon = NavIcon.Back("back", {}),
                title = storyEntry.component.name,
              )
              storyEntry.content()
            }
            BpkFloatingNotification(state = floatingNotificationState)
          }
        }
      }
    }
    takeScreenshot(suffix)
    activityRule.finishActivity()
  }

  private fun takeScreenshot(suffix: String?) {
    RemoteScreenGrab.takeScreenshot(
      type = "Test",
      component = storyEntry.component.name,
      file = storyEntry.name
        .lowercase()
        .replace(" ", "")
        .replace("-", "_")
        .replace("–", "_")
        .let { if (suffix != null) "${it}_$suffix" else it }
      ,
    )
  }
}
