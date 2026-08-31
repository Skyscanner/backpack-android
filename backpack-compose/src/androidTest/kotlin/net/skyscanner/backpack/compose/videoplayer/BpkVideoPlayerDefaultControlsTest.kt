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

import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import net.skyscanner.backpack.compose.button.BpkButtonSize
import net.skyscanner.backpack.compose.button.internal.minHeight
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.videoplayer.VideoPlayerTestRule.Companion.PLAYING_STATE_TIMEOUT_MS
import net.skyscanner.backpack.compose.videoplayer.VideoPlayerTestRule.Companion.READY_STATE_TIMEOUT_MS
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class BpkVideoPlayerDefaultControlsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val videoPlayerTestRule = VideoPlayerTestRule()

    private fun playableConfig(autoPlay: Boolean = true) = BpkVideoPlayerConfig(
        videoUrl = BpkVideoUrl(videoPlayerTestRule.bundledVideoUrl()),
        startsMuted = true,
        autoPlay = autoPlay,
        loadTimeoutMs = 7_000L,
        accessibilityLabel = "Test video",
    )

    @Test
    fun givenLoadingState_whenControlsRendered_thenPlayButtonIsNotShown() {
        // When
        composeTestRule.setContent {
            BpkTheme {
                val controller = rememberBpkVideoPlayerController(
                    BpkVideoPlayerConfig(
                        videoUrl = BpkVideoUrl("https://example.com/stub.mp4"),
                        accessibilityLabel = "Test video",
                    ),
                )
                BpkVideoPlayerDefaultControls(
                    controller = controller,
                    playContentDescription = PLAY_LABEL,
                    pauseContentDescription = PAUSE_LABEL,
                )
            }
        }

        // Then
        composeTestRule.onNodeWithContentDescription(PLAY_LABEL).assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription(PAUSE_LABEL).assertDoesNotExist()
    }

    @Test
    fun givenReadyToPlayState_whenControlsRendered_thenPlayButtonIsShown() {
        // Given
        videoPlayerTestRule.disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController

        // When
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = false))
                BpkVideoPlayerDefaultControls(
                    controller = controller,
                    playContentDescription = PLAY_LABEL,
                    pauseContentDescription = PAUSE_LABEL,
                )
            }
        }
        composeTestRule.waitUntil(timeoutMillis = READY_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.ReadyToPlay
        }

        // Then
        composeTestRule.onNodeWithContentDescription(PLAY_LABEL).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(PAUSE_LABEL).assertDoesNotExist()
    }

    @Test
    fun givenPlayingState_whenControlsRendered_thenPauseButtonIsShown() {
        // Given
        videoPlayerTestRule.disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController

        // When
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = true))
                BpkVideoPlayerDefaultControls(
                    controller = controller,
                    playContentDescription = PLAY_LABEL,
                    pauseContentDescription = PAUSE_LABEL,
                )
            }
        }
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Playing
        }

        // Then
        composeTestRule.onNodeWithContentDescription(PAUSE_LABEL).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(PLAY_LABEL).assertDoesNotExist()
    }

    @Test
    fun givenReadyToPlayState_whenPlayButtonClicked_thenStateTransitionsToPlaying() {
        // Given
        videoPlayerTestRule.disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = false))
                BpkVideoPlayerDefaultControls(
                    controller = controller,
                    playContentDescription = PLAY_LABEL,
                    pauseContentDescription = PAUSE_LABEL,
                )
            }
        }
        composeTestRule.waitUntil(timeoutMillis = READY_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.ReadyToPlay
        }

        // When
        composeTestRule.onNodeWithContentDescription(PLAY_LABEL).performClick()

        // Then
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Playing
        }
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Playing)
    }

    @Test
    fun givenPlayingState_whenPauseButtonClicked_thenStateTransitionsToPaused() {
        // Given
        videoPlayerTestRule.disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = true))
                BpkVideoPlayerDefaultControls(
                    controller = controller,
                    playContentDescription = PLAY_LABEL,
                    pauseContentDescription = PAUSE_LABEL,
                )
            }
        }
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Playing
        }

        // When
        composeTestRule.onNodeWithContentDescription(PAUSE_LABEL).performClick()

        // Then
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Paused
        }
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Paused)
    }

    @Test
    fun givenNoSizeSpecified_whenControlsRendered_thenDefaultButtonSizeIsApplied() {
        // Given
        videoPlayerTestRule.disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController

        // When
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = false))
                BpkVideoPlayerDefaultControls(
                    controller = controller,
                    playContentDescription = PLAY_LABEL,
                    pauseContentDescription = PAUSE_LABEL,
                )
            }
        }
        composeTestRule.waitUntil(timeoutMillis = READY_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.ReadyToPlay
        }

        // Then
        composeTestRule.onNodeWithContentDescription(PLAY_LABEL)
            .assertIsDisplayed()
            .assertHeightIsEqualTo(BpkButtonSize.Default.minHeight)
    }

    @Test
    fun givenLargeSize_whenControlsRendered_thenLargeButtonSizeIsApplied() {
        // Given
        videoPlayerTestRule.disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController

        // When
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = false))
                BpkVideoPlayerDefaultControls(
                    controller = controller,
                    playContentDescription = PLAY_LABEL,
                    pauseContentDescription = PAUSE_LABEL,
                    size = BpkButtonSize.Large,
                )
            }
        }
        composeTestRule.waitUntil(timeoutMillis = READY_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.ReadyToPlay
        }

        // Then
        composeTestRule.onNodeWithContentDescription(PLAY_LABEL)
            .assertIsDisplayed()
            .assertHeightIsEqualTo(BpkButtonSize.Large.minHeight)
    }

    @Test
    fun givenNoSizeSpecified_whenPlaybackStateChangesToPlaying_thenPauseButtonKeepsDefaultSize() {
        // Given
        videoPlayerTestRule.disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController

        // When
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = true))
                BpkVideoPlayerDefaultControls(
                    controller = controller,
                    playContentDescription = PLAY_LABEL,
                    pauseContentDescription = PAUSE_LABEL,
                )
            }
        }
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Playing
        }

        // Then
        composeTestRule.onNodeWithContentDescription(PAUSE_LABEL)
            .assertIsDisplayed()
            .assertHeightIsEqualTo(BpkButtonSize.Default.minHeight)
    }

    @Test
    fun givenLargeSize_whenPlaybackStateChangesToPlaying_thenPauseButtonKeepsLargeSize() {
        // Given
        videoPlayerTestRule.disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController

        // When
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = true))
                BpkVideoPlayerDefaultControls(
                    controller = controller,
                    playContentDescription = PLAY_LABEL,
                    pauseContentDescription = PAUSE_LABEL,
                    size = BpkButtonSize.Large,
                )
            }
        }
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Playing
        }

        // Then
        composeTestRule.onNodeWithContentDescription(PAUSE_LABEL)
            .assertIsDisplayed()
            .assertHeightIsEqualTo(BpkButtonSize.Large.minHeight)
    }

    private companion object {
        const val PLAY_LABEL = "Play"
        const val PAUSE_LABEL = "Pause"
    }
}
