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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.media3.common.Player
import androidx.test.platform.app.InstrumentationRegistry
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class BpkVideoPlayerTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val stubConfig = BpkVideoPlayerConfig(
        videoUrl = BpkVideoUrl("https://example.com/stub.mp4"),
        accessibilityLabel = "Test video",
    )

    private var originalAnimationScales: Pair<String?, String?>? = null

    // BpkVideoPlayerController defaults to respectsReducedMotion = true and treats animator/transition
    // scale 0 (a common test-emulator setting, used to speed up Espresso) as a reduced-motion signal,
    // which suppresses autoplay and leaves the player stuck at ReadyToPlay.
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

    // android.provider.Settings.Global requires WRITE_SECURE_SETTINGS, which the app under test does
    // not hold; UiAutomation shell commands run with shell identity, which does.
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

    private fun playableConfig(
        loop: Boolean = false,
        startsMuted: Boolean = true,
        autoPlay: Boolean = true,
        loadTimeoutMs: Long = 7_000L,
    ) = BpkVideoPlayerConfig(
        videoUrl = BpkVideoUrl(bundledVideoUrl()),
        loop = loop,
        startsMuted = startsMuted,
        autoPlay = autoPlay,
        loadTimeoutMs = loadTimeoutMs,
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

        // play() on a Failed state must be a no-op — no exception, state unchanged
        composeTestRule.runOnIdle { controller.play() }
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Failed)
    }

    @Test
    fun loopConfigTrue_mapsToPlayerRepeatModeOne() {
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(loop = true))
                BpkVideoPlayer(controller = controller)
            }
        }
        assertEquals(Player.REPEAT_MODE_ONE, controller.player.repeatMode)
    }

    @Test
    fun loopConfigFalse_mapsToPlayerRepeatModeOff() {
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(loop = false))
                BpkVideoPlayer(controller = controller)
            }
        }
        assertEquals(Player.REPEAT_MODE_OFF, controller.player.repeatMode)
    }

    @Test
    fun startsMutedTrue_mapsToZeroVolume() {
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(startsMuted = true))
                BpkVideoPlayer(controller = controller)
            }
        }
        assertEquals(0f, controller.player.volume, 0f)
    }

    @Test
    fun startsMutedFalse_mapsToFullVolume() {
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(startsMuted = false))
                BpkVideoPlayer(controller = controller)
            }
        }
        assertEquals(1f, controller.player.volume, 0f)
    }

    @Test
    fun setMuted_updatesPlayerVolume() {
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig())
                BpkVideoPlayer(controller = controller)
            }
        }

        composeTestRule.runOnIdle { controller.setMuted(false) }
        assertEquals(1f, controller.player.volume, 0f)

        composeTestRule.runOnIdle { controller.setMuted(true) }
        assertEquals(0f, controller.player.volume, 0f)
    }

    @Test
    fun autoPlay_withReducedMotionDisabled_reachesPlaying() {
        disableReducedMotionSignal()
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
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Playing)
    }

    @Test
    fun autoPlayOff_neverStartsPlaying() {
        disableReducedMotionSignal()
        lateinit var controller: BpkVideoPlayerController
        composeTestRule.setContent {
            BpkTheme {
                controller = rememberBpkVideoPlayerController(playableConfig(autoPlay = false))
                BpkVideoPlayer(controller = controller)
            }
        }

        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.ReadyToPlay
        }
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.ReadyToPlay)
    }

    @Test
    fun playPauseToggle_driveRealPlayback() {
        disableReducedMotionSignal()
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

        composeTestRule.runOnIdle { controller.pause() }
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Paused
        }
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Paused)

        composeTestRule.runOnIdle { controller.toggle() }
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Playing
        }
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Playing)

        composeTestRule.runOnIdle { controller.toggle() }
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Paused
        }
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Paused)
    }

    @Test
    fun playbackReachesEnded_thenPlayRestartsFromStart() {
        disableReducedMotionSignal()
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

        composeTestRule.runOnIdle { controller.play() }
        composeTestRule.waitUntil(timeoutMillis = PLAYING_STATE_TIMEOUT_MS) {
            controller.playbackState.value is BpkVideoPlaybackState.Playing
        }
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Playing)
    }

    @Test
    fun resetToStart_afterEnded_settlesReadyToPlay() {
        disableReducedMotionSignal()
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

        composeTestRule.runOnIdle { controller.resetToStart() }
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.ReadyToPlay)
    }

    @Test
    fun dispose_releasesTheUnderlyingPlayer() {
        lateinit var controller: BpkVideoPlayerController
        var showPlayer by mutableStateOf(true)
        composeTestRule.setContent {
            BpkTheme {
                if (showPlayer) {
                    controller = rememberBpkVideoPlayerController(playableConfig())
                    BpkVideoPlayer(controller = controller)
                }
            }
        }

        composeTestRule.runOnIdle { showPlayer = false }
        composeTestRule.waitForIdle()

        assertEquals(Player.STATE_IDLE, controller.player.playbackState)
    }

    private companion object {
        const val ANIMATOR_DURATION_SCALE_KEY = "animator_duration_scale"
        const val TRANSITION_ANIMATION_SCALE_KEY = "transition_animation_scale"
        const val PLAYING_STATE_TIMEOUT_MS = 5_000L
        const val ENDED_STATE_TIMEOUT_MS = 8_000L
    }
}
