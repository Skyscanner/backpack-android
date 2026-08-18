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
}
