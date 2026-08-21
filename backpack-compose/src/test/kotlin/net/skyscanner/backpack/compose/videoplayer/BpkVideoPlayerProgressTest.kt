/*
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

import org.junit.Assert.assertEquals
import org.junit.Test

class BpkVideoPlayerProgressTest {

    @Test
    fun `given positionMs equals durationMs, then percentage is 1f`() {
        val progress = BpkVideoPlayerProgress(positionMs = 5_000L, durationMs = 5_000L)
        assertEquals(1f, progress.percentage)
    }

    @Test
    fun `given positionMs is half of durationMs, then percentage is 0_5f`() {
        val progress = BpkVideoPlayerProgress(positionMs = 2_500L, durationMs = 5_000L)
        assertEquals(0.5f, progress.percentage)
    }

    @Test
    fun `given zero positionMs, then percentage is 0f`() {
        val progress = BpkVideoPlayerProgress(positionMs = 0L, durationMs = 5_000L)
        assertEquals(0f, progress.percentage)
    }

    @Test
    fun `given zero durationMs, then percentage is 0f without throwing`() {
        val progress = BpkVideoPlayerProgress(positionMs = 1_000L, durationMs = 0L)
        assertEquals(0f, progress.percentage)
    }

    @Test
    fun `given positionMs greater than durationMs, then percentage is clamped to 1f`() {
        val progress = BpkVideoPlayerProgress(positionMs = 1100L, durationMs = 1000L)
        assertEquals(1f, progress.percentage)
    }

    @Test
    fun `given negative positionMs, then percentage is clamped to 0f`() {
        val progress = BpkVideoPlayerProgress(positionMs = -100L, durationMs = 1000L)
        assertEquals(0f, progress.percentage)
    }
}
