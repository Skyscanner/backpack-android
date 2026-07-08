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

sealed class BpkVideoPlaybackState {
    data object Idle : BpkVideoPlaybackState()
    data object Loading : BpkVideoPlaybackState()
    data object ReadyToPlay : BpkVideoPlaybackState()
    data object Buffering : BpkVideoPlaybackState()
    data object Playing : BpkVideoPlaybackState()
    data object Paused : BpkVideoPlaybackState()
    data object Ended : BpkVideoPlaybackState()
    data class Failed(val cause: BpkVideoPlayerError) : BpkVideoPlaybackState()

    val isPlaying: Boolean get() = this is Playing
    val isLoading: Boolean get() = this is Loading || this is Buffering
}
