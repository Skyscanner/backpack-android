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

package net.skyscanner.backpack.compose.videoplayer

import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class BpkVideoPlayerTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val stubConfig = BpkVideoPlayerConfig(
        videoUrl = "https://example.com/stub.mp4",
        accessibilityLabel = "Test video",
    )

    @Test
    fun accessibilityLabel_isAppliedToSemantics() {
        composeTestRule.setContent {
            BpkTheme {
                val controller = rememberBpkVideoPlayerController(stubConfig)
                BpkVideoPlayer(controller = controller)
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Test video")
            .assertExists()
            .assertContentDescriptionContains("Test video")
    }

    @Test
    fun initialState_isLoading() {
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(stubConfig)
                BpkVideoPlayer(controller = controller)
            }
        }

        assertTrue(controller.playbackState.value.isLoading)
    }

    @Test
    fun setMuted_false_updatesIsMuted() {
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(stubConfig)
                BpkVideoPlayer(controller = controller)
            }
        }

        assertTrue(controller.isMuted.value)
        composeTestRule.runOnIdle { controller.setMuted(false) }
        assertFalse(controller.isMuted.value)
    }

    @Test
    fun setMuted_toggle_isIdempotent() {
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(stubConfig)
                BpkVideoPlayer(controller = controller)
            }
        }

        composeTestRule.runOnIdle { controller.setMuted(false) }
        assertFalse(controller.isMuted.value)
        composeTestRule.runOnIdle { controller.setMuted(true) }
        assertTrue(controller.isMuted.value)
    }

    @Test
    fun play_whenFailed_doesNotCrash() {
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(
                    BpkVideoPlayerConfig(
                        videoUrl = "https://example.com/stub.mp4",
                        loadTimeoutMs = 100L,
                        accessibilityLabel = "Test video",
                    ),
                )
                BpkVideoPlayer(controller = controller)
            }
        }

        composeTestRule.waitUntil(timeoutMillis = 2_000L) {
            controller.playbackState.value is BpkVideoPlaybackState.Failed
        }

        // play() on a Failed state must be a no-op — no exception, state unchanged
        composeTestRule.runOnIdle { controller.play() }
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Failed)
    }
}
