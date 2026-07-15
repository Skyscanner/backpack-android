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

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BpkVideoPlaybackStateTest {

    @Test
    fun `isPlaying is true only for Playing state`() {
        assertTrue(BpkVideoPlaybackState.Playing.isPlaying)
        assertFalse(BpkVideoPlaybackState.Idle.isPlaying)
        assertFalse(BpkVideoPlaybackState.Loading.isPlaying)
        assertFalse(BpkVideoPlaybackState.ReadyToPlay.isPlaying)
        assertFalse(BpkVideoPlaybackState.Buffering.isPlaying)
        assertFalse(BpkVideoPlaybackState.Paused.isPlaying)
        assertFalse(BpkVideoPlaybackState.Ended.isPlaying)
        assertFalse(BpkVideoPlaybackState.Failed(BpkVideoPlayerError.LoadTimeout).isPlaying)
    }

    @Test
    fun `isLoading is true for Loading and Buffering states`() {
        assertTrue(BpkVideoPlaybackState.Loading.isLoading)
        assertTrue(BpkVideoPlaybackState.Buffering.isLoading)
        assertFalse(BpkVideoPlaybackState.Idle.isLoading)
        assertFalse(BpkVideoPlaybackState.ReadyToPlay.isLoading)
        assertFalse(BpkVideoPlaybackState.Playing.isLoading)
        assertFalse(BpkVideoPlaybackState.Paused.isLoading)
        assertFalse(BpkVideoPlaybackState.Ended.isLoading)
        assertFalse(BpkVideoPlaybackState.Failed(BpkVideoPlayerError.LoadTimeout).isLoading)
    }
}
