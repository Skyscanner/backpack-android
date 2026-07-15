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

import androidx.media3.common.Player

/**
 * A narrow seam over the media player, exposing only the members [net.skyscanner.backpack.compose.videoplayer.BpkVideoPlayerController]
 * needs. This keeps the concrete `ExoPlayer` out of the controller's logic so it can be driven by a
 * fake in JVM unit tests, while [player] still hands the render surface a real [Player] for
 * `ContentFrame`.
 */
internal interface VideoPlayerHandle {
    val player: Player
    var repeatMode: Int
    var volume: Float
    var playWhenReady: Boolean
    val isPlaying: Boolean
    fun addListener(listener: Player.Listener)
    fun setMediaItem(uri: String)
    fun prepare()
    fun play()
    fun pause()
    fun seekTo(positionMs: Long)
    fun stop()
    fun release()
}
