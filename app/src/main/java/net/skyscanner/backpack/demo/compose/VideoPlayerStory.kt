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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlayer
import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlayerConfig
import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlayerDefaultControls
import net.skyscanner.backpack.compose.videoplayer.rememberBpkVideoPlayerController
import net.skyscanner.backpack.demo.components.VideoPlayerComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@VideoPlayerComponent
@ComposeStory
fun VideoPlayerStory(modifier: Modifier = Modifier) {
    val controller = rememberBpkVideoPlayerController(
        config = BpkVideoPlayerConfig(
            videoUrl = "https://content.skyscnr.com/media/68afbd83-d09a-48e8-9821-90c117b8f842/593d0fe4-5459-4c43-beb9-49f9ce79d365.m3u8",
            loop = true,
            startsMuted = true,
            accessibilityLabel = "Sample video",
        ),
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .padding(BpkSpacing.Base),
    ) {
        BpkVideoPlayer(
            controller = controller,
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
