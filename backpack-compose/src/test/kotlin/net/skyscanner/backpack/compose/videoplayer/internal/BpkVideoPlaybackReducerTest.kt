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

package net.skyscanner.backpack.compose.videoplayer.internal

import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlaybackState
import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlayerError
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BpkVideoPlaybackReducerTest {

    @Test
    fun `Ready while playing resolves to Playing`() {
        val result = reducePlaybackState(BpkVideoPlaybackState.Loading, PlaybackEvent.Ready(isPlaying = true))
        assertEquals(BpkVideoPlaybackState.Playing, result)
    }

    @Test
    fun `Ready while not playing resolves to ReadyToPlay`() {
        val result = reducePlaybackState(BpkVideoPlaybackState.Loading, PlaybackEvent.Ready(isPlaying = false))
        assertEquals(BpkVideoPlaybackState.ReadyToPlay, result)
    }

    @Test
    fun `Buffering from Playing resolves to Buffering`() {
        val result = reducePlaybackState(BpkVideoPlaybackState.Playing, PlaybackEvent.Buffering)
        assertEquals(BpkVideoPlaybackState.Buffering, result)
    }

    @Test
    fun `Buffering from ReadyToPlay resolves to Buffering`() {
        val result = reducePlaybackState(BpkVideoPlaybackState.ReadyToPlay, PlaybackEvent.Buffering)
        assertEquals(BpkVideoPlaybackState.Buffering, result)
    }

    @Test
    fun `Buffering from other states is ignored`() {
        assertEquals(
            BpkVideoPlaybackState.Loading,
            reducePlaybackState(BpkVideoPlaybackState.Loading, PlaybackEvent.Buffering),
        )
        assertEquals(
            BpkVideoPlaybackState.Paused,
            reducePlaybackState(BpkVideoPlaybackState.Paused, PlaybackEvent.Buffering),
        )
    }

    @Test
    fun `Ended always resolves to Ended`() {
        assertEquals(
            BpkVideoPlaybackState.Ended,
            reducePlaybackState(BpkVideoPlaybackState.Playing, PlaybackEvent.Ended),
        )
    }

    @Test
    fun `IsPlayingChanged true resolves to Playing`() {
        val result = reducePlaybackState(BpkVideoPlaybackState.ReadyToPlay, PlaybackEvent.IsPlayingChanged(true))
        assertEquals(BpkVideoPlaybackState.Playing, result)
    }

    @Test
    fun `IsPlayingChanged false from Playing resolves to Paused`() {
        val result = reducePlaybackState(BpkVideoPlaybackState.Playing, PlaybackEvent.IsPlayingChanged(false))
        assertEquals(BpkVideoPlaybackState.Paused, result)
    }

    @Test
    fun `IsPlayingChanged false from non-Playing is ignored`() {
        assertEquals(
            BpkVideoPlaybackState.Buffering,
            reducePlaybackState(BpkVideoPlaybackState.Buffering, PlaybackEvent.IsPlayingChanged(false)),
        )
    }

    @Test
    fun `Error resolves to Failed with PlaybackFailed cause`() {
        val cause = IllegalStateException("boom")
        val result = reducePlaybackState(BpkVideoPlaybackState.Playing, PlaybackEvent.Error(cause))
        assertTrue(result is BpkVideoPlaybackState.Failed)
        val error = (result as BpkVideoPlaybackState.Failed).cause
        assertTrue(error is BpkVideoPlayerError.PlaybackFailed)
        assertEquals(cause, (error as BpkVideoPlayerError.PlaybackFailed).cause)
    }
}
