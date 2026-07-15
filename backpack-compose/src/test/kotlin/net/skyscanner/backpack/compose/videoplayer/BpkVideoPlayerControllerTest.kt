/*
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

import androidx.media3.common.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BpkVideoPlayerControllerTest {

    private fun config(
        loop: Boolean = false,
        startsMuted: Boolean = true,
        autoPlay: Boolean = true,
        respectsReducedMotion: Boolean = true,
        loadTimeoutMs: Long = 7_000L,
    ) = BpkVideoPlayerConfig(
        videoUrl = BpkVideoUrl("https://example.com/video.mp4"),
        loop = loop,
        startsMuted = startsMuted,
        autoPlay = autoPlay,
        respectsReducedMotion = respectsReducedMotion,
        loadTimeoutMs = loadTimeoutMs,
        accessibilityLabel = "Test video",
    )

    private fun controller(
        scope: CoroutineScope,
        handle: FakeVideoPlayerHandle = FakeVideoPlayerHandle(),
        config: BpkVideoPlayerConfig = config(),
        reducedMotionEnabled: Boolean = false,
    ) = BpkVideoPlayerController(
        config = config,
        scope = scope,
        handle = handle,
        reducedMotionEnabled = reducedMotionEnabled,
    )

    @Test
    fun `init prepares the player with the configured media item`() = runTest {
        val handle = FakeVideoPlayerHandle()
        controller(this, handle)
        assertEquals("https://example.com/video.mp4", handle.mediaUri)
        assertTrue(handle.prepared)
        assertEquals(1, handle.listeners.size)
    }

    @Test
    fun `loop config maps to repeat mode`() = runTest {
        val looping = FakeVideoPlayerHandle()
        controller(this, looping, config(loop = true))
        assertEquals(Player.REPEAT_MODE_ONE, looping.repeatMode)

        val once = FakeVideoPlayerHandle()
        controller(this, once, config(loop = false))
        assertEquals(Player.REPEAT_MODE_OFF, once.repeatMode)
    }

    @Test
    fun `startsMuted config maps to volume`() = runTest {
        val muted = FakeVideoPlayerHandle()
        controller(this, muted, config(startsMuted = true))
        assertEquals(0f, muted.volume, 0f)

        val loud = FakeVideoPlayerHandle()
        controller(this, loud, config(startsMuted = false))
        assertEquals(1f, loud.volume, 0f)
    }

    @Test
    fun `playWhenReady honours autoPlay and reduced motion`() = runTest {
        // autoPlay on, no reduced motion -> plays
        val a = FakeVideoPlayerHandle()
        controller(this, a, config(autoPlay = true), reducedMotionEnabled = false)
        assertTrue(a.playWhenReady)

        // autoPlay on, reduced motion respected -> blocked
        val b = FakeVideoPlayerHandle()
        controller(this, b, config(autoPlay = true, respectsReducedMotion = true), reducedMotionEnabled = true)
        assertFalse(b.playWhenReady)

        // autoPlay on, reduced motion NOT respected -> plays despite reduced motion
        val c = FakeVideoPlayerHandle()
        controller(this, c, config(autoPlay = true, respectsReducedMotion = false), reducedMotionEnabled = true)
        assertTrue(c.playWhenReady)

        // autoPlay off -> never plays
        val d = FakeVideoPlayerHandle()
        controller(this, d, config(autoPlay = false), reducedMotionEnabled = false)
        assertFalse(d.playWhenReady)
    }

    @Test
    fun `play from Ended seeks to start`() = runTest {
        val handle = FakeVideoPlayerHandle()
        val controller = controller(this, handle)
        handle.emitEnded()
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Ended)

        controller.play()
        assertEquals(0L, handle.lastSeekMs)
        assertTrue(handle.playCalled)
    }

    @Test
    fun `play is a no-op when Failed`() = runTest {
        val handle = FakeVideoPlayerHandle()
        val controller = controller(this, handle, config(loadTimeoutMs = 100L))
        advanceTimeBy(150L)
        advanceUntilIdle()
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Failed)

        controller.play()
        assertFalse(handle.playCalled)
    }

    @Test
    fun `setMuted toggles volume and state`() = runTest {
        val handle = FakeVideoPlayerHandle()
        val controller = controller(this, handle)

        controller.setMuted(false)
        assertFalse(controller.isMuted.value)
        assertEquals(1f, handle.volume, 0f)

        controller.setMuted(true)
        assertTrue(controller.isMuted.value)
        assertEquals(0f, handle.volume, 0f)
    }

    @Test
    fun `resetToStart seeks and clears Ended`() = runTest {
        val handle = FakeVideoPlayerHandle()
        val controller = controller(this, handle)
        handle.emitEnded()

        controller.resetToStart()
        assertEquals(0L, handle.lastSeekMs)
        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.ReadyToPlay)
    }

    @Test
    fun `load timeout transitions to Failed and stops the player`() = runTest {
        val handle = FakeVideoPlayerHandle()
        val controller = controller(this, handle, config(loadTimeoutMs = 500L))

        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.Loading)
        advanceTimeBy(600L)
        advanceUntilIdle()

        val state = controller.playbackState.value
        assertTrue(state is BpkVideoPlaybackState.Failed)
        assertEquals(BpkVideoPlayerError.LoadTimeout, (state as BpkVideoPlaybackState.Failed).cause)
        assertTrue(handle.stopped)
    }

    @Test
    fun `ready before timeout cancels the timeout`() = runTest(StandardTestDispatcher()) {
        val handle = FakeVideoPlayerHandle()
        val controller = controller(this, handle, config(loadTimeoutMs = 500L))

        handle.emitReady(isPlaying = false)
        advanceTimeBy(600L)
        advanceUntilIdle()

        assertTrue(controller.playbackState.value is BpkVideoPlaybackState.ReadyToPlay)
    }

    @Test
    fun `dispose releases the player`() = runTest {
        val handle = FakeVideoPlayerHandle()
        val controller = controller(this, handle)
        controller.dispose()
        assertTrue(handle.released)
    }
}
