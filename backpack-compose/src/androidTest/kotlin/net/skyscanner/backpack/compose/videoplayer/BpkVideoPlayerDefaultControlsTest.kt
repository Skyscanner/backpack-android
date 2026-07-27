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

import android.os.ParcelFileDescriptor.AutoCloseInputStream
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class BpkVideoPlayerDefaultControlsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private var originalAnimationScales: Pair<String?, String?>? = null

    private fun disableReducedMotionSignal() {
        originalAnimationScales =
            getGlobalSetting(ANIMATOR_DURATION_SCALE_KEY) to getGlobalSetting(TRANSITION_ANIMATION_SCALE_KEY)
        putGlobalSetting(ANIMATOR_DURATION_SCALE_KEY, "1")
        putGlobalSetting(TRANSITION_ANIMATION_SCALE_KEY, "1")
    }

    @After
    fun restoreReducedMotionSignalIfChanged() {
        originalAnimationScales?.let { (animator, transition) ->
            putGlobalSetting(ANIMATOR_DURATION_SCALE_KEY, animator ?: "1")
            putGlobalSetting(TRANSITION_ANIMATION_SCALE_KEY, transition ?: "1")
            originalAnimationScales = null
        }
    }

    private fun getGlobalSetting(key: String): String? =
        runShellCommand("settings get global $key")
            .trim()
            .takeUnless { it == "null" || it.isEmpty() }

    private fun putGlobalSetting(key: String, value: String) {
        runShellCommand("settings put global $key $value")
    }

    private fun runShellCommand(command: String): String =
        AutoCloseInputStream(InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand(command))
            .use { it.readBytes().decodeToString() }

    private fun bundledVideoUrl(): String {
        val targetPackage = InstrumentationRegistry.getInstrumentation().targetContext.packageName
        return "android.resource://$targetPackage/raw/bpk_video_player_test"
    }

    private fun playableConfig(autoPlay: Boolean = true) = BpkVideoPlayerConfig(
        videoUrl = BpkVideoUrl(bundledVideoUrl()),
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
        disableReducedMotionSignal()
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

        // Then
        composeTestRule.onNodeWithContentDescription(PLAY_LABEL).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(PAUSE_LABEL).assertDoesNotExist()
    }

    @Test
    fun givenPlayingState_whenControlsRendered_thenPauseButtonIsShown() {
        // Given
        disableReducedMotionSignal()
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

        // Then
        composeTestRule.onNodeWithContentDescription(PAUSE_LABEL).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(PLAY_LABEL).assertDoesNotExist()
    }

    @Test
    fun givenReadyToPlayState_whenPlayButtonClicked_thenStateTransitionsToPlaying() {
        // Given
        disableReducedMotionSignal()
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
        disableReducedMotionSignal()
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

    private companion object {
        const val PLAY_LABEL = "Play"
        const val PAUSE_LABEL = "Pause"
        const val ANIMATOR_DURATION_SCALE_KEY = "animator_duration_scale"
        const val TRANSITION_ANIMATION_SCALE_KEY = "transition_animation_scale"
        const val READY_STATE_TIMEOUT_MS = 7_000L
        const val PLAYING_STATE_TIMEOUT_MS = 5_000L
    }
}
