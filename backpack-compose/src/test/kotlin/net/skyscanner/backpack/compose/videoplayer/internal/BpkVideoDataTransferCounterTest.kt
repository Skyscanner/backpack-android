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

package net.skyscanner.backpack.compose.videoplayer.internal

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class BpkVideoDataTransferCounterTest {

    @Test
    fun `A new counter has transferred nothing`() {
        assertEquals(0L, BpkVideoDataTransferCounter().total)
    }

    @Test
    fun `Transfers accumulate`() {
        val counter = BpkVideoDataTransferCounter()

        counter.add(1_024)
        counter.add(2_048)

        assertEquals(3_072L, counter.total)
    }

    @Test
    fun `Empty and negative transfers are ignored`() {
        val counter = BpkVideoDataTransferCounter()

        counter.add(0)
        counter.add(-1)
        counter.add(512)

        assertEquals(512L, counter.total)
    }

    @Test
    fun `The total exceeds the range of an Int`() {
        // A long-running HLS impression can plausibly pass 2GB, and an Int total would wrap negative.
        val counter = BpkVideoDataTransferCounter()

        repeat(3) { counter.add(Int.MAX_VALUE) }

        assertEquals(3L * Int.MAX_VALUE, counter.total)
    }

    @Test
    fun `Concurrent transfers all count`() {
        // Media3 gives every ChunkSampleStream its own single-thread Loader (one per HLS rendition:
        // video, audio, subtitles), and all of them share this counter via the one TransferListener
        // attached to the player's DataSource.Factory. So this is a real scenario, not a contrived
        // one, and it is what makes the counter's atomicity load bearing rather than decorative.
        val counter = BpkVideoDataTransferCounter()
        val executor = Executors.newFixedThreadPool(THREADS)
        val start = CountDownLatch(1)
        val finished = CountDownLatch(THREADS)

        repeat(THREADS) {
            executor.execute {
                start.await()
                repeat(ADDITIONS_PER_THREAD) { counter.add(1) }
                finished.countDown()
            }
        }
        start.countDown()

        assertEquals(true, finished.await(TIMEOUT_SECONDS, TimeUnit.SECONDS))
        executor.shutdown()
        assertEquals((THREADS * ADDITIONS_PER_THREAD).toLong(), counter.total)
    }

    private companion object {
        const val THREADS = 8
        const val ADDITIONS_PER_THREAD = 10_000
        const val TIMEOUT_SECONDS = 10L
    }
}
