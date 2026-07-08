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

package net.skyscanner.backpack.compose.floatingnotification

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalAccessibilityManager
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.createComposeRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Heart
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BpkFloatingNotificationAccessibilityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val hasLiveRegionPolite = SemanticsMatcher.expectValue(
        SemanticsProperties.LiveRegion,
        LiveRegionMode.Polite,
    )

    @Test
    fun liveRegion_isPolite_whenNotificationIsShown() {
        val state = BpkFloatingNotificationState()
        val scope = TestScope(UnconfinedTestDispatcher())

        scope.launch {
            state.show(text = "Saved")
        }

        composeTestRule.setContent {
            BpkTheme {
                BpkFloatingNotification(state = state)
            }
        }

        composeTestRule.onNode(hasLiveRegionPolite).assertExists()
    }

    @Test
    fun liveRegion_isPolite_whenNoNotificationIsShown() {
        val state = BpkFloatingNotificationState()

        composeTestRule.setContent {
            BpkTheme {
                BpkFloatingNotification(state = state)
            }
        }

        // Live region wrapper is always present, not just when content is visible
        composeTestRule.onNode(hasLiveRegionPolite).assertExists()
    }

    @Test
    fun accessibilityManager_recommendedTimeout_isUsedAsDelay() {
        val state = BpkFloatingNotificationState()
        val scope = TestScope(UnconfinedTestDispatcher())
        val recommendedTimeout = 8000L

        val fakeAccessibilityManager = FakeAccessibilityManager(recommendedTimeout)

        scope.launch {
            state.show(text = "Saved", hideAfter = 4000L)
        }

        composeTestRule.mainClock.autoAdvance = false

        composeTestRule.setContent {
            BpkTheme {
                CompositionLocalProvider(LocalAccessibilityManager provides fakeAccessibilityManager) {
                    BpkFloatingNotification(state = state)
                }
            }
        }

        composeTestRule.mainClock.advanceTimeByFrame()
        composeTestRule.waitForIdle()

        // Notification still visible after the original 4000ms timeout because the
        // accessibility manager extended it to 8000ms
        composeTestRule.mainClock.advanceTimeBy(5000L)
        composeTestRule.waitForIdle()

        assert(state.currentData != null) {
            "Notification should still be visible after original timeout when accessibility manager extends it"
        }
    }

    @Test
    fun accessibilityManager_calculateRecommendedTimeout_receivesCorrectFlags() {
        val state = BpkFloatingNotificationState()
        val scope = TestScope(UnconfinedTestDispatcher())
        val fakeAccessibilityManager = FakeAccessibilityManager(captureArgs = true)

        scope.launch {
            state.show(
                text = "Flight saved",
                icon = BpkIcon.Heart,
                cta = "View",
                onClick = {},
                hideAfter = 4000L,
            )
        }

        composeTestRule.setContent {
            BpkTheme {
                CompositionLocalProvider(LocalAccessibilityManager provides fakeAccessibilityManager) {
                    BpkFloatingNotification(state = state)
                }
            }
        }

        composeTestRule.waitForIdle()

        assert(fakeAccessibilityManager.lastOriginalTimeout == 4000L) {
            "Expected originalTimeoutMillis=4000, got ${fakeAccessibilityManager.lastOriginalTimeout}"
        }
        assert(fakeAccessibilityManager.lastContainsIcons) {
            "Expected containsIcons=true"
        }
        assert(fakeAccessibilityManager.lastContainsText) {
            "Expected containsText=true"
        }
        assert(fakeAccessibilityManager.lastContainsControls) {
            "Expected containsControls=true"
        }
    }

    @Test
    fun accessibilityManager_null_fallsBackToOriginalTimeout() {
        val state = BpkFloatingNotificationState()
        val scope = TestScope(UnconfinedTestDispatcher())

        scope.launch {
            state.show(text = "Saved", hideAfter = 4000L)
        }

        composeTestRule.mainClock.autoAdvance = false

        composeTestRule.setContent {
            BpkTheme {
                // No LocalAccessibilityManager provided — defaults to null
                CompositionLocalProvider(LocalAccessibilityManager provides null) {
                    BpkFloatingNotification(state = state)
                }
            }
        }

        composeTestRule.mainClock.advanceTimeByFrame()
        composeTestRule.waitForIdle()

        // Notification should dismiss after the original timeout
        composeTestRule.mainClock.advanceTimeBy(4001L)
        composeTestRule.waitForIdle()

        assert(state.currentData == null) {
            "Notification should be dismissed after original timeout when no accessibility manager"
        }
    }
}