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

import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import net.skyscanner.backpack.demo.BpkBaseActivity
import net.skyscanner.backpack.demo.meta.StoriesRepository
import net.skyscanner.backpack.demo.meta.Story
import net.skyscanner.backpack.demo.ui.DemoScaffold
import net.skyscanner.backpack.demo.ui.StoryScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
open class GenerateScreenshots(
    private val story: Story,
) {

    companion object {

        @JvmStatic
        @Parameterized.Parameters(name = "{0} Screenshot")
        fun data(): List<Story> = StoriesRepository.getInstance().screenshotStories
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule(BpkBaseActivity::class.java)

    @Test
    fun testTakeScreenshot() {
        runActivityAndTakeScreenshot(isDarkMode = false)
    }

    @Test
    fun testTakeScreenshotDm() {
        runActivityAndTakeScreenshot(isDarkMode = true)
    }

    private fun runActivityAndTakeScreenshot(isDarkMode: Boolean) {
        composeTestRule.runOnUiThread {
            AppCompatDelegate.setDefaultNightMode(if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }
        composeTestRule.setContent {
            DemoScaffold(automationMode = true) {
                StoryScreen(
                    component = story.component.name,
                    story = story.name,
                    isCompose = story.isCompose,
                )
            }
        }
        takeScreenshot(if (isDarkMode) "dm" else null)
    }

    private fun takeScreenshot(suffix: String?) {
        RemoteScreenGrab.takeScreenshot(
            component = story.component.name.replace(" ", ""),
            type = if (story.isCompose) "compose" else "view",
            file = story.name
                .lowercase()
                .replace("–", "-")
                .replace(" ", "-")
                .let { if (suffix != null) "${it}_$suffix" else it },
        )
    }
}
