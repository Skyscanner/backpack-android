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

import android.os.Handler
import android.os.Looper
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.media3.common.ForwardingPlayer
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.ContentFrame
import net.skyscanner.backpack.compose.videoplayer.internal.rememberReducedMotionEnabled

@OptIn(UnstableApi::class)
@Composable
fun BpkVideoPlayer(
    controller: BpkVideoPlayerController,
    modifier: Modifier = Modifier,
    scaleToFill: Boolean = false,
) {
    val reducedMotion by rememberReducedMotionEnabled()

    LaunchedEffect(reducedMotion) {
        if (reducedMotion && controller.config.respectsReducedMotion && controller.playbackState.value.isPlaying) {
            controller.pause()
        }
    }

    // Wrap the player so removeListener is always dispatched to the main thread.
    // ContentFrame uses player.listen { } whose invokeOnCancellation fires on whatever thread
    // cancels the coroutine scope (e.g. the instrumentation thread in tests), causing ExoPlayer's
    // thread check to crash. Intercepting removeListener here and posting to main fixes that
    // without losing ContentFrame's resizeWithContentScale layout behavior.
    val mainThreadPlayer = remember(controller.player) { MainThreadRemoveListenerPlayer(controller.player) }

    Box(
        modifier = modifier
            .background(Color.Black)
            .semantics { contentDescription = controller.config.accessibilityLabel },
    ) {
        ContentFrame(
            player = mainThreadPlayer,
            contentScale = if (scaleToFill) ContentScale.Crop else ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@OptIn(UnstableApi::class)
private class MainThreadRemoveListenerPlayer(player: Player) : ForwardingPlayer(player) {
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun removeListener(listener: Player.Listener) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.removeListener(listener)
        } else {
            mainHandler.post {
                // In case the player was released while this was queued.
                runCatching {
                    super.removeListener(listener)
                }
            }
        }
    }
}
