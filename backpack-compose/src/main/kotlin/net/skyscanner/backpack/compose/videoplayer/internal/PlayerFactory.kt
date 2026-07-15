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

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory

internal object PlayerFactory {
    @OptIn(UnstableApi::class)
    fun build(context: Context): VideoPlayerHandle {
        val exoPlayer = ExoPlayer.Builder(context)
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(context).setDataSourceFactory(DefaultHttpDataSource.Factory()),
            )
            .build()
        return ExoPlayerHandle(exoPlayer)
    }
}

private class ExoPlayerHandle(override val player: ExoPlayer) : VideoPlayerHandle {
    override var repeatMode: Int
        get() = player.repeatMode
        set(value) { player.repeatMode = value }

    override var volume: Float
        get() = player.volume
        set(value) { player.volume = value }

    override var playWhenReady: Boolean
        get() = player.playWhenReady
        set(value) { player.playWhenReady = value }

    override val isPlaying: Boolean get() = player.isPlaying

    override fun addListener(listener: Player.Listener) = player.addListener(listener)
    override fun setMediaItem(uri: String) = player.setMediaItem(MediaItem.fromUri(uri))
    override fun prepare() = player.prepare()
    override fun play() = player.play()
    override fun pause() = player.pause()
    override fun seekTo(positionMs: Long) = player.seekTo(positionMs)
    override fun stop() = player.stop()
    override fun release() = player.release()
}
