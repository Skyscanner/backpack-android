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

package net.skyscanner.backpack.demo.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Expand
import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlayer
import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlayerConfig
import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlayerController
import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlayerDefaultControls
import net.skyscanner.backpack.compose.videoplayer.rememberBpkVideoPlayerController
import net.skyscanner.backpack.demo.components.VideoPlayerComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

private const val VIDEO_URL =
    "https://content.skyscnr.com/media/68afbd83-d09a-48e8-9821-90c117b8f842/593d0fe4-5459-4c43-beb9-49f9ce79d365.m3u8"

// Use case 1: 16:9 card with built-in play/pause overlay
@Composable
@VideoPlayerComponent
@ComposeStory(name = "Default Controls")
fun VideoPlayerDefaultControlsStory(modifier: Modifier = Modifier) {
    val controller = rememberBpkVideoPlayerController(
        config = BpkVideoPlayerConfig(
            videoUrl = VIDEO_URL,
            loop = true,
            startsMuted = true,
            accessibilityLabel = "Sample video",
        ),
    )
    VideoPlayerCard(controller = controller, scaleToFill = false, modifier = modifier)
}

// Use case 2: expand button opens the same controller fullscreen — playback continues uninterrupted
@Composable
@VideoPlayerComponent
@ComposeStory(name = "Continuous Playback")
fun VideoPlayerContinuousPlaybackStory(modifier: Modifier = Modifier) {
    val controller = rememberBpkVideoPlayerController(
        config = BpkVideoPlayerConfig(
            videoUrl = VIDEO_URL,
            loop = true,
            startsMuted = true,
            accessibilityLabel = "Sample video",
        ),
    )
    var fullscreen by remember { mutableStateOf(false) }

    if (fullscreen) {
        BackHandler { fullscreen = false }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .clickable { fullscreen = false },
        ) {
            BpkVideoPlayer(
                controller = controller,
                modifier = Modifier.fillMaxSize(),
                scaleToFill = true,
            )
            BpkVideoPlayerDefaultControls(
                controller = controller,
                modifier = Modifier.align(Alignment.TopEnd),
            )
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .padding(BpkSpacing.Base),
        ) {
            BpkVideoPlayer(
                controller = controller,
                scaleToFill = false,
                modifier = Modifier.matchParentSize(),
            )
            BpkIcon(
                icon = BpkIcon.Expand,
                contentDescription = "Open fullscreen",
                size = BpkIconSize.Large,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(BpkSpacing.Sm)
                    .clickable { fullscreen = true },
            )
        }
    }
}

// Use case 3: fullscreen-only, no card
@Composable
@VideoPlayerComponent
@ComposeStory(name = "Fullscreen")
fun VideoPlayerFullscreenStory(modifier: Modifier = Modifier) {
    val controller = rememberBpkVideoPlayerController(
        config = BpkVideoPlayerConfig(
            videoUrl = VIDEO_URL,
            loop = true,
            startsMuted = true,
            accessibilityLabel = "Sample video",
        ),
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable { controller.toggle() },
    ) {
        BpkVideoPlayer(
            controller = controller,
            modifier = Modifier.fillMaxSize(),
            scaleToFill = true,
        )
        BpkVideoPlayerDefaultControls(
            controller = controller,
            modifier = Modifier.align(Alignment.TopEnd),
        )
    }
}

@Composable
private fun VideoPlayerCard(
    controller: BpkVideoPlayerController,
    modifier: Modifier = Modifier,
    scaleToFill: Boolean,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .padding(BpkSpacing.Base),
    ) {
        BpkVideoPlayer(
            controller = controller,
            scaleToFill = scaleToFill,
            modifier = Modifier
                .matchParentSize()
                .clickable { controller.toggle() },
        )
        BpkVideoPlayerDefaultControls(
            controller = controller,
            modifier = Modifier.align(Alignment.TopEnd),
        )
    }
}
