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

package net.skyscanner.backpack.compose.videoplayer.internal

import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlaybackState
import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlayerError

/**
 * A player signal, decoupled from the Media3 `Player.Listener` callbacks so the state machine can be
 * exercised without an [androidx.media3.exoplayer.ExoPlayer] instance.
 */
internal sealed interface PlaybackEvent {
    /** The player reached `STATE_READY`. [isPlaying] mirrors `Player.isPlaying` at that moment. */
    data class Ready(val isPlaying: Boolean) : PlaybackEvent
    data object Buffering : PlaybackEvent
    data object Ended : PlaybackEvent
    data class IsPlayingChanged(val isPlaying: Boolean) : PlaybackEvent
    data class Error(val cause: Exception) : PlaybackEvent
}

/**
 * Pure playback-state transition function. Returns [current] unchanged when an event should be ignored,
 * so the caller can assign the result unconditionally.
 */
internal fun reducePlaybackState(
    current: BpkVideoPlaybackState,
    event: PlaybackEvent,
): BpkVideoPlaybackState =
    when (event) {
        is PlaybackEvent.Ready ->
            if (event.isPlaying) BpkVideoPlaybackState.Playing else BpkVideoPlaybackState.ReadyToPlay

        PlaybackEvent.Buffering ->
            if (current is BpkVideoPlaybackState.Playing || current is BpkVideoPlaybackState.ReadyToPlay) {
                BpkVideoPlaybackState.Buffering
            } else {
                current
            }

        PlaybackEvent.Ended -> BpkVideoPlaybackState.Ended

        is PlaybackEvent.IsPlayingChanged ->
            if (event.isPlaying) {
                BpkVideoPlaybackState.Playing
            } else if (current is BpkVideoPlaybackState.Playing) {
                BpkVideoPlaybackState.Paused
            } else {
                current
            }

        is PlaybackEvent.Error ->
            BpkVideoPlaybackState.Failed(BpkVideoPlayerError.PlaybackFailed(event.cause))
    }
