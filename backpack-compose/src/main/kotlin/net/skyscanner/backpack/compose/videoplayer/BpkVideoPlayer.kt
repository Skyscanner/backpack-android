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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.PlayerSurface
import net.skyscanner.backpack.compose.videoplayer.internal.rememberReducedMotionEnabled

@OptIn(UnstableApi::class)
@Composable
fun BpkVideoPlayer(
    controller: BpkVideoPlayerController,
    modifier: Modifier = Modifier,
) {
    val reducedMotion by rememberReducedMotionEnabled()

    LaunchedEffect(reducedMotion) {
        if (reducedMotion && controller.playbackState.value.isPlaying) {
            controller.pause()
        }
    }

    Box(
        modifier = modifier.semantics {
            contentDescription = controller.config.accessibilityLabel
        },
    ) {
        PlayerSurface(
            player = controller.player,
            modifier = Modifier.fillMaxSize(),
        )
    }
}
