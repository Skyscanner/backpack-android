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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.media3.common.Player
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.videoplayer.VideoPlayerTestRule.Companion.ENDED_STATE_TIMEOUT_MS
import net.skyscanner.backpack.compose.videoplayer.VideoPlayerTestRule.Companion.PLAYING_STATE_TIMEOUT_MS
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class BpkVideoPlayerTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val videoPlayerTestRule = VideoPlayerTestRule()

    private val stubConfig = BpkVideoPlayerConfig(
        videoUrl = BpkVideoUrl("https://example.com/stub.mp4"),
        accessibilityLabel = "Test video",
    )

    private fun playableConfig(
        loop: Boolean = false,
        startsMuted: Boolean = true,
        autoPlay: Boolean = true,
        loadTimeoutMs: Long = 7_000L,
    ) = BpkVideoPlayerConfig(
        videoUrl = BpkVideoUrl(videoPlayerTestRule.bundledVideoUrl()),
        loop = loop,
        startsMuted = startsMuted,
        autoPlay = autoPlay,
        loadTimeoutMs = loadTimeoutMs,
        accessibilityLabel = "Test video",
    )

    @Test
    fun givenAccessibilityLabel_whenRendered_thenLabelIsAppliedToSemantics() {
        // When
        composeTestRule.setContent {
            BpkTheme {
                val controller = rememberBpkVideoPlayerController(stubConfig)
                BpkVideoPlayer(controller = controller)
            }
        }

        // Then
        composeTestRule
            .onNodeWithContentDescription("Test video")
            .assertExists()
            .assertContentDescriptionContains("Test video")
    }

    @Test
    fun givenStubConfig_whenRendered_thenInitialStateIsLoading() {
        // When
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(stubConfig)
                BpkVideoPlayer(controller = controller)
            }
        }

        // Then
        assertTrue(controller.playbackState.value.isLoading)
    }

    @Test
    fun givenMutedController_whenSetMutedFalse_thenIsMutedIsFalse() {
        // Given
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(stubConfig)
                BpkVideoPlayer(controller = controller)
            }
        }
        assertTrue(controller.isMuted.value)

        // When
        composeTestRule.runOnIdle { controller.setMuted(false) }

        // Then
        assertFalse(controller.isMuted.value)
    }

    @Test
    fun givenMutedController_whenSetMutedToggledTwice_thenIsMutedIsRestored() {
        // Given
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(stubConfig)
                BpkVideoPlayer(controller = controller)
            }
        }

        // When
        composeTestRule.runOnIdle { controller.setMuted(false) }

        // Then
        assertFalse(controller.isMuted.value)

        // When
        composeTestRule.runOnIdle { controller.setMuted(true) }

        // Then
        assertTrue(controller.isMuted.value)
    }

    @Test
    fun givenFailedPlayback_whenPlayCalled_thenStateRemainsFailedWithoutCrash() {
        // Given
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(
                    BpkVideoPlayerConfig(
                        videoUrl = BpkVideoUrl("https://example.com/stub.mp4"),
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

        // When
        composeTestRule.runOnIdle { controller.play() }

        // Then
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Failed)
    }

    @Test
    fun givenLoopTrue_whenRendered_thenPlayerRepeatModeIsOne() {
        // When
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(loop = true))
                BpkVideoPlayer(controller = controller)
            }
        }

        // Then
        assertEquals(Player.REPEAT_MODE_ONE, controller.player.repeatMode)
    }

    @Test
    fun givenLoopFalse_whenRendered_thenPlayerRepeatModeIsOff() {
        // When
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(loop = false))
                BpkVideoPlayer(controller = controller)
            }
        }

        // Then
        assertEquals(Player.REPEAT_MODE_OFF, controller.player.repeatMode)
    }

    @Test
    fun givenStartsMutedTrue_whenRendered_thenPlayerVolumeIsZero() {
        // When
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(startsMuted = true))
                BpkVideoPlayer(controller = controller)
            }
        }

        // Then
        assertEquals(0f, controller.player.volume, 0f)
    }

    @Test
    fun givenStartsMutedFalse_whenRendered_thenPlayerVolumeIsFull() {
        // When
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(startsMuted = false))
                BpkVideoPlayer(controller = controller)
            }
        }

        // Then
        assertEquals(1f, controller.player.volume, 0f)
    }

    @Test
    fun givenPlayableConfig_whenSetMutedToggled_thenPlayerVolumeUpdates() {
        // Given
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig())
                BpkVideoPlayer(controller = controller)
            }
        }

        // When
        composeTestRule.runOnIdle { controller.setMuted(false) }

        // Then
        assertEquals(1f, controller.player.volume, 0f)

        // When
        composeTestRule.runOnIdle { controller.setMuted(true) }

        // Then
        assertEquals(0f, controller.player.volume, 0f)
    }

    @Test
    fun givenAutoPlayWithReducedMotionDisabled_whenRendered_thenStateReachesPlaying() {
        // Given
        videoPlayerTestRule.disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = true))
                BpkVideoPlayer(controller = controller)
            }
        }

        // When
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Playing
        }

        // Then
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Playing)
    }

    @Test
    fun givenAutoPlayOff_whenRendered_thenStateIsReadyToPlay() {
        // Given
        videoPlayerTestRule.disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = false))
                BpkVideoPlayer(controller = controller)
            }
        }

        // When
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.ReadyToPlay
        }

        // Then
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.ReadyToPlay)
    }

    @Test
    fun givenPlayingState_whenPauseAndToggleCalled_thenStateTransitionsCorrectly() {
        // Given
        videoPlayerTestRule.disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = true))
                BpkVideoPlayer(controller = controller)
            }
        }
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Playing
        }

        // When
        composeTestRule.runOnIdle { controller.pause() }

        // Then
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Paused
        }
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Paused)

        // When
        composeTestRule.runOnIdle { controller.toggle() }

        // Then
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Playing
        }
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Playing)

        // When
        composeTestRule.runOnIdle { controller.toggle() }

        // Then
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Paused
        }
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Paused)
    }

    @Test
    fun givenEndedPlayback_whenPlayCalled_thenStateReachesPlayingAgain() {
        // Given
        videoPlayerTestRule.disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = true))
                BpkVideoPlayer(controller = controller)
            }
        }
        composeTestRule.waitUntil(timeoutMillis = ENDED_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Ended
        }
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Ended)

        // When
        composeTestRule.runOnIdle { controller.play() }

        // Then
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Playing
        }
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Playing)
    }

    @Test
    fun givenEndedPlayback_whenResetToStartCalled_thenStateIsReadyToPlay() {
        // Given
        videoPlayerTestRule.disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = true))
                BpkVideoPlayer(controller = controller)
            }
        }
        composeTestRule.waitUntil(timeoutMillis = ENDED_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Ended
        }

        // When
        composeTestRule.runOnIdle { controller.resetToStart() }

        // Then
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.ReadyToPlay)
    }

    @Test
    fun givenPlayerComposed_whenRemovedFromComposition_thenUnderlyingPlayerIsReleased() {
        // Given
        videoPlayerTestRule.disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController
        var showPlayer by mutableStateOf(true)
        composeTestRule.setContent {
            BpkTheme {
                if (showPlayer) {
                    controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = true))
                    BpkVideoPlayer(controller = controller)
                }
            }
        }
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Playing
        }

        // When
        composeTestRule.runOnIdle { showPlayer = false }
        composeTestRule.waitForIdle()

        // Then
        assertEquals(Player.STATE_IDLE, controller.player.playbackState)
    }

    @Test
    fun givenStubConfig_whenRendered_thenProgressIsNull() {
        // When
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(stubConfig)
                BpkVideoPlayer(controller = controller)
            }
        }

        // Then
        assertNull(controller.progressState.value)
    }

    @Test
    fun givenAutoPlay_whenStateReachesPlaying_thenProgressIsNotNull() {
        // Given
        videoPlayerTestRule.disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = true))
                BpkVideoPlayer(controller = controller)
            }
        }

        // When
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Playing
        }

        // Then
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.progressState.value != null
        }
        assertNotNull(controller.progressState.value)
    }

    @Test
    fun givenPlayingVideo_whenPaused_thenProgressRetainsLastValue() {
        // Given
        videoPlayerTestRule.disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = true))
                BpkVideoPlayer(controller = controller)
            }
        }
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.progressState.value != null
        }

        // When
        composeTestRule.runOnIdle { controller.pause() }
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Paused
        }
        val progressWhenPaused = controller.progressState.value

        // Then — progress is retained (not nulled) and doesn't advance while paused
        composeTestRule.mainClock.advanceTimeBy(500L)
        assertEquals(progressWhenPaused, controller.progressState.value)
    }

    @Test
    fun givenLoopFalse_whenVideoEnds_thenProgressPercentageIs100() {
        // Given
        videoPlayerTestRule.disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = true, loop = false))
                BpkVideoPlayer(controller = controller)
            }
        }

        // When
        composeTestRule.waitUntil(timeoutMillis = ENDED_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Ended
        }

        // Then
        assertEquals(1f, controller.progressState.value?.percentage)
    }

    @Test
    fun givenLoopTrue_whenFirstCycleCompletes_thenProgressReaches100Percent() {
        // Given
        videoPlayerTestRule.disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = true, loop = true))
                BpkVideoPlayer(controller = controller)
            }
        }
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Playing
        }

        // When — wait for onPositionDiscontinuity (AUTO_TRANSITION) to emit 1f
        // waitUntil polls at ~16ms so it will catch the transient 1f value
        composeTestRule.waitUntil(timeoutMillis = ENDED_STATE_TIMEOUT_MS) {
            controller.progressState.value?.percentage == 1f
        }

        // Then
        assertEquals(1f, controller.progressState.value?.percentage)
    }
}
