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
import org.junit.Assert.assertTrue
import org.junit.Test

class BpkVideoPlayerErrorTest {

    @Test
    fun `LoadTimeout reports the load timeout code`() {
        assertEquals(BpkVideoPlayerErrorCode.LoadTimeout, BpkVideoPlayerError.LoadTimeout.code)
    }

    @Test
    fun `PlaybackFailed defaults to the unknown code`() {
        val error = BpkVideoPlayerError.PlaybackFailed(IllegalStateException("boom"))
        assertEquals(BpkVideoPlayerErrorCode.UnknownError, error.code)
    }

    @Test
    fun `PlaybackFailed reports the code it was given`() {
        val error = BpkVideoPlayerError.PlaybackFailed(IllegalStateException("boom"), BpkVideoPlayerErrorCode.MediaErrDecode)
        assertEquals(BpkVideoPlayerErrorCode.MediaErrDecode, error.code)
    }

    @Test
    fun `Wire names are unique`() {
        val wireNames = BpkVideoPlayerErrorCode.entries.map { it.wireName }
        assertEquals(wireNames.size, wireNames.toSet().size)
    }

    @Test
    fun `Wire names are upper snake case`() {
        BpkVideoPlayerErrorCode.entries.forEach {
            assertTrue("${it.name} has a malformed wire name: '${it.wireName}'", it.wireName.matches(WIRE_NAME))
        }
    }

    @Test
    fun `Wire names match the cross-platform vocabulary`() {
        // Asserted literally rather than derived from the constant names: these strings are the join key
        // for dashboards shared with iOS and Web, so a Kotlin-side rename must fail here rather than
        // silently splitting a dashboard bucket in two.
        assertEquals(
            mapOf(
                BpkVideoPlayerErrorCode.MediaErrAborted to "MEDIA_ERR_ABORTED",
                BpkVideoPlayerErrorCode.MediaErrNetwork to "MEDIA_ERR_NETWORK",
                BpkVideoPlayerErrorCode.MediaErrDecode to "MEDIA_ERR_DECODE",
                BpkVideoPlayerErrorCode.MediaErrSrcNotSupported to "MEDIA_ERR_SRC_NOT_SUPPORTED",
                BpkVideoPlayerErrorCode.UnknownError to "UNKNOWN_ERROR",
                BpkVideoPlayerErrorCode.LoadTimeout to "LOAD_TIMEOUT",
            ),
            BpkVideoPlayerErrorCode.entries.associateWith { it.wireName },
        )
    }

    private companion object {
        val WIRE_NAME = Regex("[A-Z]+(_[A-Z]+)*")
    }
}
