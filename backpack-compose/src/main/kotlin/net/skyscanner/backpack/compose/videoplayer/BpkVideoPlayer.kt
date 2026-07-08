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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.layout
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.PlayerSurface
import net.skyscanner.backpack.compose.videoplayer.internal.rememberReducedMotionEnabled
import kotlin.math.roundToInt

@OptIn(UnstableApi::class)
@Composable
fun BpkVideoPlayer(
    controller: BpkVideoPlayerController,
    modifier: Modifier = Modifier,
    scaleToFill: Boolean = false,
) {
    val reducedMotion by rememberReducedMotionEnabled()

    LaunchedEffect(reducedMotion) {
        if (reducedMotion && controller.playbackState.value.isPlaying) {
            controller.pause()
        }
    }

    Box(
        modifier = modifier
            .clipToBounds()
            .semantics {
                contentDescription = controller.config.accessibilityLabel
            },
    ) {
        PlayerSurface(
            player = controller.player,
            modifier = Modifier
                .fillMaxSize()
                .then(if (scaleToFill) Modifier.scaleToFill() else Modifier),
        )
    }
}

// Scales the composable up uniformly so its shorter dimension fills the container,
// then centers it — equivalent to AVLayerVideoGravity.resizeAspectFill / CSS object-fit: cover.
private fun Modifier.scaleToFill(): Modifier = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    val scale = maxOf(
        constraints.maxWidth.toFloat() / placeable.width,
        constraints.maxHeight.toFloat() / placeable.height,
    )
    val scaledWidth = (placeable.width * scale).roundToInt()
    val scaledHeight = (placeable.height * scale).roundToInt()
    layout(constraints.maxWidth, constraints.maxHeight) {
        placeable.placeWithLayer(
            x = ((constraints.maxWidth - scaledWidth) / 2f).roundToInt(),
            y = ((constraints.maxHeight - scaledHeight) / 2f).roundToInt(),
        ) {
            scaleX = scale
            scaleY = scale
        }
    }
}
