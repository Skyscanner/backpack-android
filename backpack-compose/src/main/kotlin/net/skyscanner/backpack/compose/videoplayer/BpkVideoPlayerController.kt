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

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.ForwardingPlayer
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.videoplayer.internal.PlaybackEvent
import net.skyscanner.backpack.compose.videoplayer.internal.isReducedMotionEnabled
import net.skyscanner.backpack.compose.videoplayer.internal.reducePlaybackState

@Stable
@OptIn(UnstableApi::class)
class BpkVideoPlayerController internal constructor(
    val config: BpkVideoPlayerConfig,
    private val scope: CoroutineScope,
    context: Context,
    reducedMotionEnabled: Boolean,
) {
    private val _playbackState = mutableStateOf<BpkVideoPlaybackState>(BpkVideoPlaybackState.Loading)
    val playbackState: State<BpkVideoPlaybackState> get() = _playbackState

    private val _isMuted = mutableStateOf(config.startsMuted)
    val isMuted: State<Boolean> get() = _isMuted

    private val exoPlayer: ExoPlayer = run {
        val applicationContext = context.applicationContext
        ExoPlayer.Builder(applicationContext)
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(applicationContext).setDataSourceFactory(
                    DefaultDataSource.Factory(applicationContext, DefaultHttpDataSource.Factory()),
                ),
            )
            .build()
    }

    // ContentFrame uses player.listen {} whose invokeOnCancellation fires on whatever thread
    // cancels the coroutine scope (e.g. the instrumentation thread in tests), causing ExoPlayer's
    // thread check to crash. Wrapping removeListener to always run on the main thread fixes that.
    internal val player: Player = object : ForwardingPlayer(exoPlayer) {
        private val mainHandler = Handler(Looper.getMainLooper())
        override fun removeListener(listener: Player.Listener) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                super.removeListener(listener)
            } else {
                mainHandler.post { runCatching { super.removeListener(listener) } }
            }
        }
    }

    private var timeoutJob: Job? = null

    init {
        player.repeatMode = if (config.loop) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
        player.volume = if (config.startsMuted) 0f else 1f
        player.playWhenReady = config.autoPlay && !(config.respectsReducedMotion && reducedMotionEnabled)
        player.addListener(playerListener())
        player.setMediaItem(MediaItem.fromUri(config.videoUrl.value))
        player.prepare()
        startLoadTimeout()
    }

    fun play() {
        if (_playbackState.value is BpkVideoPlaybackState.Failed) return
        if (_playbackState.value is BpkVideoPlaybackState.Ended) player.seekTo(0)
        player.play()
    }

    fun pause() {
        player.pause()
    }

    fun toggle() {
        if (_playbackState.value.isPlaying) pause() else play()
    }

    fun setMuted(muted: Boolean) {
        _isMuted.value = muted
        player.volume = if (muted) 0f else 1f
    }

    fun resetToStart() {
        player.seekTo(0)
        if (_playbackState.value is BpkVideoPlaybackState.Ended) {
            _playbackState.value = BpkVideoPlaybackState.ReadyToPlay
        }
    }

    fun dispose() {
        timeoutJob?.cancel()
        player.release()
    }

    private fun startLoadTimeout() {
        timeoutJob?.cancel()
        timeoutJob = scope.launch {
            delay(config.loadTimeoutMs)
            if (_playbackState.value == BpkVideoPlaybackState.Loading) {
                _playbackState.value = BpkVideoPlaybackState.Failed(BpkVideoPlayerError.LoadTimeout)
                player.stop()
            }
        }
    }

    private fun apply(event: PlaybackEvent) {
        _playbackState.value = reducePlaybackState(_playbackState.value, event)
    }

    private fun playerListener() = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                Player.STATE_READY -> {
                    timeoutJob?.cancel()
                    apply(PlaybackEvent.Ready(isPlaying = player.isPlaying))
                }
                Player.STATE_BUFFERING -> apply(PlaybackEvent.Buffering)
                Player.STATE_ENDED -> apply(PlaybackEvent.Ended)
                Player.STATE_IDLE -> Unit
            }
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            apply(PlaybackEvent.IsPlayingChanged(isPlaying))
        }

        override fun onPlayerError(error: PlaybackException) {
            timeoutJob?.cancel()
            apply(PlaybackEvent.Error(error))
        }
    }
}

@Composable
fun rememberBpkVideoPlayerController(config: BpkVideoPlayerConfig): BpkVideoPlayerController {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val controller = remember(config) {
        BpkVideoPlayerController(
            config = config,
            scope = scope,
            context = context,
            reducedMotionEnabled = isReducedMotionEnabled(context),
        )
    }
    DisposableEffect(controller) {
        onDispose { controller.dispose() }
    }
    return controller
}
