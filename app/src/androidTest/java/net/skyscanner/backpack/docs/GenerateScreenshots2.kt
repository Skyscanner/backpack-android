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
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.rule.ActivityTestRule
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
@Ignore
open class GenerateScreenshots2(
  private val componentPath: String,
  private val screenshotName: String,
  private val componentType: String,
  private val setup: ((AndroidComposeTestRule<*, *>) -> Unit)?,
) {

  companion object {
    @JvmStatic
    @Parameterized.Parameters(name = "{0} Screenshot")
    fun data() = DocsRegistry.screenshots
  }

  @get:Rule
  var activityRule = ActivityTestRule(androidx.activity.ComponentActivity::class.java, true, false)

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
//    intent.putExtra(ComponentDetailFragment.ARG_ITEM_ID, componentPath)
//    intent.putExtra(ComponentDetailFragment.AUTOMATION_MODE, true)
    activityRule.launchActivity(intent)
    setup?.invoke(composeTestRule)
    takeScreenshot(suffix)
    activityRule.finishActivity()
  }

  private fun takeScreenshot(suffix: String?) {
    RemoteScreenGrab.takeScreenshot(
      type = componentType,
      component = componentPath.split(" - ").first().replace(" ", ""),
      file = listOfNotNull(screenshotName, suffix).joinToString(separator = "_"),
    )
  }
}
