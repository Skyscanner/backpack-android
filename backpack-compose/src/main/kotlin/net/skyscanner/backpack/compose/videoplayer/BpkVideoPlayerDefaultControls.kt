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

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Pause
import net.skyscanner.backpack.compose.tokens.Play

@Composable
fun BpkVideoPlayerDefaultControls(
    controller: BpkVideoPlayerController,
    modifier: Modifier = Modifier,
) {
    val playbackState by controller.playbackState
    if (playbackState.isLoading) return

    VideoPlayerControlButton(
        isPlaying = playbackState.isPlaying,
        modifier = modifier,
    )
}

@Composable
private fun VideoPlayerControlButton(
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(BpkSpacing.Md)
            .size(40.dp)
            .clip(RoundedCornerShape(BpkBorderRadius.Sm))
            .background(Color.White.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center,
    ) {
        BpkIcon(
            icon = if (isPlaying) BpkIcon.Pause else BpkIcon.Play,
            contentDescription = null,
            size = BpkIconSize.Large,
            tint = BpkTheme.colors.textOnDark,
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun VideoPlayerControlButtonPreview() {
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center,
    ) {
        VideoPlayerControlButton(isPlaying = false)
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun VideoPlayerControlButtonPlayingPreview() {
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center,
    ) {
        VideoPlayerControlButton(isPlaying = true)
    }
}
