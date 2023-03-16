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

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.view.ContextThemeWrapper
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import net.skyscanner.backpack.BpkTestVariant
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
    val composeTestRule = createComposeRule()

    @Test
    fun testTakeScreenshot() {
        runActivityAndTakeScreenshot(BpkTestVariant.Default)
    }

    @Test
    fun testTakeScreenshotDm() {
        runActivityAndTakeScreenshot(BpkTestVariant.DarkMode)
    }

    private fun runActivityAndTakeScreenshot(variant: BpkTestVariant) {
        composeTestRule.setContent {
            val currentContext = LocalContext.current
            val context = remember(currentContext, variant) { variant.newContext(currentContext) }

            CompositionLocalProvider(
                LocalContext provides context,
                LocalConfiguration provides context.resources.configuration,
            ) {
                DemoScaffold(automationMode = true) {
                    StoryScreen(
                        component = story.component.name,
                        story = story.name,
                        isCompose = story.isCompose,
                    )
                }
            }
        }

        takeScreenshot(variant.takeUnless { it == BpkTestVariant.Default }?.id)
    }

    private fun createDmContext(context: Context, isDarkMode: Boolean): Context {
        val configuration = Configuration(context.resources.configuration)
        val uiModeMask = if (isDarkMode) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO
        configuration.uiMode = uiModeMask
        return ContextThemeWrapper(context, 0).apply {
            applyOverrideConfiguration(configuration)
        }
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
