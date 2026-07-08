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

import androidx.compose.runtime.Immutable

@Immutable
data class BpkVideoPlayerConfig(
    val videoUrl: String,
    val posterImageUrl: String? = null,
    val loadTimeoutMs: Long = 7_000L,
    val autoPlay: Boolean = true,
    val respectsReducedMotion: Boolean = true,
    val loop: Boolean = false,
    val startsMuted: Boolean = true,
    val accessibilityLabel: String,
)
