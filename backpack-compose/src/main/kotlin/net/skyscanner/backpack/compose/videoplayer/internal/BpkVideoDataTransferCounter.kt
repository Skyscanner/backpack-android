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

package net.skyscanner.backpack.compose.videoplayer.internal

import java.util.concurrent.atomic.AtomicLong

/**
 * Accumulates the bytes a player has pulled over the network for its media item.
 *
 * Media3 reports transfers on whichever thread performed them, so accumulation has to be thread safe
 * and is deliberately kept separate from publishing the total as Compose state: the controller polls
 * [total] from the main thread instead of writing state from a loading thread.
 */
internal class BpkVideoDataTransferCounter {

    private val bytes = AtomicLong(0L)

    /**
     * Adds one transfer's byte count. Non-positive counts are ignored, as is anything the caller has
     * already filtered out — notably transfers that did not come from the network.
     */
    fun add(byteCount: Int) {
        if (byteCount > 0) bytes.addAndGet(byteCount.toLong())
    }

    /** The running total. Monotonic: it is never reset for the lifetime of its counter. */
    val total: Long get() = bytes.get()
}
