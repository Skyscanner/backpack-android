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

package net.skyscanner.backpack.compose.appsearchmodal

import android.content.res.Configuration
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToIndex
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.AppSearchModalStoryContent
import net.skyscanner.backpack.demo.compose.AppSearchModalStoryContentInputText
import net.skyscanner.backpack.demo.compose.AppSearchModalStoryContentWithAccessory
import net.skyscanner.backpack.demo.compose.AppSearchModalStoryError
import net.skyscanner.backpack.demo.compose.AppSearchModalStoryErrorWithAccessory
import net.skyscanner.backpack.demo.compose.AppSearchModalStoryLoading
import net.skyscanner.backpack.demo.compose.AppSearchModalStoryLoadingWithAccessory
import net.skyscanner.backpack.demo.compose.AppSearchModalStoryPrefixText
import org.junit.Test

class BpkAppSearchModalTest : BpkSnapshotTest() {

    @Test
    fun content() = snap(captureFullScreen = true) {
        AppSearchModalStoryContent()
    }.also {
        composeTestRule.onNodeWithTag("appSearchModalAccessory").assertDoesNotExist()
    }

    @Test
    fun content_inputText() = snap(captureFullScreen = true) {
        AppSearchModalStoryContentInputText()
    }.also {
        composeTestRule.onNodeWithTag("appSearchModalAccessory").assertDoesNotExist()
    }

    @Test
    fun loading() = snap(captureFullScreen = true) {
        AppSearchModalStoryLoading()
    }.also {
        composeTestRule.onNodeWithTag("appSearchModalAccessory").assertDoesNotExist()
    }

    @Test
    fun error() = snap(captureFullScreen = true) {
        AppSearchModalStoryError()
    }.also {
        composeTestRule.onNodeWithTag("appSearchModalAccessory").assertDoesNotExist()
    }

    @Test
    fun prefixText() = snap(captureFullScreen = true) {
        AppSearchModalStoryPrefixText()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun contentWithAccessory() = snap(captureFullScreen = true, assertion = {
        val accessory = onNodeWithTag(ACCESSORY_TAG).fetchSemanticsNode()
        val input = onNodeWithTag(INPUT_TAG).fetchSemanticsNode()
        val results = onNodeWithTag(RESULTS_TAG).fetchSemanticsNode()

        onNodeWithTag(ACCESSORY_TAG).assertExists()
        check(accessory.boundsInRoot.top >= input.boundsInRoot.bottom)
        check(accessory.boundsInRoot.bottom <= results.boundsInRoot.top)
    }) {
        AppSearchModalStoryContentWithAccessory()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun loadingWithAccessory() = snap(captureFullScreen = true) {
        AppSearchModalStoryLoadingWithAccessory()
    }.also {
        composeTestRule.onNodeWithTag(ACCESSORY_TAG).assertExists()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun errorWithAccessory() = snap(captureFullScreen = true) {
        AppSearchModalStoryErrorWithAccessory()
    }.also {
        composeTestRule.onNodeWithTag(ACCESSORY_TAG).assertDoesNotExist()
    }

    @Test
    fun contentWithAccessoryAtLargeFontScale() {
        val resources = composeTestRule.activity.resources
        val originalConfiguration = Configuration(resources.configuration)
        val largeFontConfiguration = Configuration(originalConfiguration).apply {
            fontScale = LARGE_FONT_SCALE
        }
        resources.updateConfiguration(largeFontConfiguration, resources.displayMetrics)
        try {
            snap(captureFullScreen = true) {
                AppSearchModalStoryContentWithAccessory()
            }
        } finally {
            resources.updateConfiguration(originalConfiguration, resources.displayMetrics)
        }
    }

    @Test
    fun accessoryRemainsOutsideScrollableResultsAfterScroll() = snap(assertion = {
        val accessoryBeforeScroll = onNodeWithTag(ACCESSORY_TAG).fetchSemanticsNode().boundsInRoot

        onNodeWithTag(RESULTS_TAG).performScrollToIndex(2)
        onNodeWithTag(ACCESSORY_TAG).assertIsDisplayed()

        val accessoryAfterScroll = onNodeWithTag(ACCESSORY_TAG).fetchSemanticsNode().boundsInRoot
        check(accessoryAfterScroll == accessoryBeforeScroll)
        check(
            generateSequence(onNodeWithTag(ACCESSORY_TAG).fetchSemanticsNode().parent) { it.parent }
                .none { it.config.contains(SemanticsActions.ScrollBy) },
        )
    }) {
        AppSearchModalStoryContentWithAccessory()
    }

    private companion object {
        const val ACCESSORY_TAG = "appSearchModalAccessory"
        const val INPUT_TAG = "searchModalTextField"
        const val LARGE_FONT_SCALE = 2f
        const val RESULTS_TAG = "searchModalResults"
    }
}
