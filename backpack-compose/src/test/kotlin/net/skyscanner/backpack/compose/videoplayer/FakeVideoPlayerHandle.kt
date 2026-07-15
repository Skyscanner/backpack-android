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
import net.skyscanner.backpack.compose.videoplayer.internal.VideoPlayerHandle

/**
 * A recording fake of [VideoPlayerHandle] for JVM unit tests. It captures the calls the controller
 * makes and lets a test drive the registered [Player.Listener] via the `emit*` helpers, without any
 * ExoPlayer or Android framework dependency.
 */
internal class FakeVideoPlayerHandle : VideoPlayerHandle {

    val listeners = mutableListOf<Player.Listener>()

    var mediaUri: String? = null
        private set
    var prepared = false
        private set
    var playCalled = false
        private set
    var pauseCalled = false
        private set
    var stopped = false
        private set
    var released = false
        private set
    var lastSeekMs: Long? = null
        private set

    override var repeatMode: Int = Player.REPEAT_MODE_OFF
    override var volume: Float = 1f
    override var playWhenReady: Boolean = false
    override var isPlaying: Boolean = false

    // The render surface never runs in these tests, so the concrete Player is not needed.
    override val player: Player get() = error("player is not available in FakeVideoPlayerHandle")

    override fun addListener(listener: Player.Listener) {
        listeners += listener
    }

    override fun setMediaItem(uri: String) {
        mediaUri = uri
    }

    override fun prepare() {
        prepared = true
    }

    override fun play() {
        playCalled = true
    }

    override fun pause() {
        pauseCalled = true
    }

    override fun seekTo(positionMs: Long) {
        lastSeekMs = positionMs
    }

    override fun stop() {
        stopped = true
    }

    override fun release() {
        released = true
    }

    fun emitReady(isPlaying: Boolean) {
        this.isPlaying = isPlaying
        listeners.forEach { it.onPlaybackStateChanged(Player.STATE_READY) }
    }

    fun emitEnded() {
        listeners.forEach { it.onPlaybackStateChanged(Player.STATE_ENDED) }
    }
}
