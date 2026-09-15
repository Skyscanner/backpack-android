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

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.ContentFrame
import androidx.media3.ui.compose.SURFACE_TYPE_SURFACE_VIEW
import androidx.media3.ui.compose.SURFACE_TYPE_TEXTURE_VIEW
import net.skyscanner.backpack.compose.videoplayer.internal.rememberReducedMotionEnabled

@OptIn(UnstableApi::class)
@Composable
fun BpkVideoPlayer(
    controller: BpkVideoPlayerController,
    modifier: Modifier = Modifier,
    scaleToFill: Boolean = false,
    surfaceType: BpkVideoPlayerSurfaceType = BpkVideoPlayerSurfaceType.SurfaceView,
) {
    val reducedMotion by rememberReducedMotionEnabled()

    LaunchedEffect(reducedMotion) {
        if (reducedMotion && controller.config.respectsReducedMotion && controller.playbackState.value.isPlaying) {
            controller.pause()
        }
    }

    Box(
        modifier = modifier
            .background(Color.Black)
            .semantics { contentDescription = controller.config.accessibilityLabel },
    ) {
        ContentFrame(
            player = controller.player,
            contentScale = if (scaleToFill) ContentScale.Crop else ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
            surfaceType = when (surfaceType) {
                BpkVideoPlayerSurfaceType.SurfaceView -> SURFACE_TYPE_SURFACE_VIEW
                BpkVideoPlayerSurfaceType.TextureView -> SURFACE_TYPE_TEXTURE_VIEW
            },
        )
    }
}

/**
 * The kind of Android view the video frames are drawn into.
 *
 * [SurfaceView] renders into its own hardware layer: cheaper and lower latency, and its last frame is
 * held by the system compositor so it survives the app being backgrounded. The layer is composited
 * outside the view hierarchy, though, so Compose alpha, z-order and clipping do not apply to it.
 *
 * [TextureView] renders inside the view hierarchy, so alpha, z-order, clipping and transforms behave
 * like any other composable — the right choice when the video is faded, animated, cropped or overlaid.
 * It costs an extra GPU copy and cannot display DRM-protected content.
 */
enum class BpkVideoPlayerSurfaceType { SurfaceView, TextureView, }
