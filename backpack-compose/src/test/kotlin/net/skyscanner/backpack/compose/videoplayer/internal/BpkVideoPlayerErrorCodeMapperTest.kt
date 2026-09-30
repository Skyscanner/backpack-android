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

import androidx.media3.common.PlaybackException
import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlayerErrorCode
import org.junit.Assert.assertEquals
import org.junit.Test

class BpkVideoPlayerErrorCodeMapperTest {

    @Test
    fun `Unspecified errors resolve to the unknown code`() {
        assertMapsTo(PlaybackException.ERROR_CODE_UNSPECIFIED, BpkVideoPlayerErrorCode.UnknownError)
        assertMapsTo(PlaybackException.ERROR_CODE_REMOTE_ERROR, BpkVideoPlayerErrorCode.UnknownError)
        assertMapsTo(PlaybackException.ERROR_CODE_FAILED_RUNTIME_CHECK, BpkVideoPlayerErrorCode.UnknownError)
    }

    @Test
    fun `Live window and timeout errors resolve to the network code`() {
        assertMapsTo(PlaybackException.ERROR_CODE_BEHIND_LIVE_WINDOW, BpkVideoPlayerErrorCode.MediaErrNetwork)
        assertMapsTo(PlaybackException.ERROR_CODE_TIMEOUT, BpkVideoPlayerErrorCode.MediaErrNetwork)
    }

    @Test
    fun `IO errors resolve to the network code`() {
        listOf(
            PlaybackException.ERROR_CODE_IO_UNSPECIFIED,
            PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED,
            PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT,
            PlaybackException.ERROR_CODE_IO_BAD_HTTP_STATUS,
            PlaybackException.ERROR_CODE_IO_FILE_NOT_FOUND,
            PlaybackException.ERROR_CODE_IO_NO_PERMISSION,
            PlaybackException.ERROR_CODE_IO_CLEARTEXT_NOT_PERMITTED,
            PlaybackException.ERROR_CODE_IO_READ_POSITION_OUT_OF_RANGE,
        ).forEach { assertMapsTo(it, BpkVideoPlayerErrorCode.MediaErrNetwork) }
    }

    @Test
    fun `An invalid HTTP content type resolves to an unsupported source`() {
        assertMapsTo(
            PlaybackException.ERROR_CODE_IO_INVALID_HTTP_CONTENT_TYPE,
            BpkVideoPlayerErrorCode.MediaErrSrcNotSupported,
        )
    }

    @Test
    fun `Malformed media resolves to the decode code`() {
        // Covers an HLS manifest as well as a progressive container: both are media that arrived but
        // could not be read, which is what the decode code means.
        assertMapsTo(PlaybackException.ERROR_CODE_PARSING_CONTAINER_MALFORMED, BpkVideoPlayerErrorCode.MediaErrDecode)
        assertMapsTo(PlaybackException.ERROR_CODE_PARSING_MANIFEST_MALFORMED, BpkVideoPlayerErrorCode.MediaErrDecode)
    }

    @Test
    fun `Unsupported containers and manifests resolve to an unsupported source`() {
        listOf(
            PlaybackException.ERROR_CODE_PARSING_CONTAINER_UNSUPPORTED,
            PlaybackException.ERROR_CODE_PARSING_MANIFEST_UNSUPPORTED,
        ).forEach { assertMapsTo(it, BpkVideoPlayerErrorCode.MediaErrSrcNotSupported) }
    }

    @Test
    fun `Decoder failures resolve to the decode code`() {
        listOf(
            PlaybackException.ERROR_CODE_DECODER_INIT_FAILED,
            PlaybackException.ERROR_CODE_DECODER_QUERY_FAILED,
            PlaybackException.ERROR_CODE_DECODING_FAILED,
        ).forEach { assertMapsTo(it, BpkVideoPlayerErrorCode.MediaErrDecode) }
    }

    @Test
    fun `Unsupported formats resolve to an unsupported source`() {
        listOf(
            PlaybackException.ERROR_CODE_DECODING_FORMAT_EXCEEDS_CAPABILITIES,
            PlaybackException.ERROR_CODE_DECODING_FORMAT_UNSUPPORTED,
        ).forEach { assertMapsTo(it, BpkVideoPlayerErrorCode.MediaErrSrcNotSupported) }
    }

    @Test
    fun `Reclaimed decoder resources resolve to aborted`() {
        assertMapsTo(
            PlaybackException.ERROR_CODE_DECODING_RESOURCES_RECLAIMED,
            BpkVideoPlayerErrorCode.MediaErrAborted,
        )
    }

    @Test
    fun `Audio track failures resolve to the decode code`() {
        listOf(
            PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED,
            PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED,
            PlaybackException.ERROR_CODE_AUDIO_TRACK_OFFLOAD_WRITE_FAILED,
            PlaybackException.ERROR_CODE_AUDIO_TRACK_OFFLOAD_INIT_FAILED,
        ).forEach { assertMapsTo(it, BpkVideoPlayerErrorCode.MediaErrDecode) }
    }

    @Test
    fun `Video frame processing failures resolve to the decode code`() {
        // Referenced as literals rather than as PlaybackException constants: the video frame
        // processing codes are @UnstableApi, and opting in for a test assertion is not worth it.
        listOf(VIDEO_FRAME_PROCESSOR_INIT_FAILED, VIDEO_FRAME_PROCESSING_FAILED).forEach {
            assertMapsTo(it, BpkVideoPlayerErrorCode.MediaErrDecode)
        }
    }

    @Test
    fun `An unsupported DRM scheme resolves to an unsupported source`() {
        assertMapsTo(
            PlaybackException.ERROR_CODE_DRM_SCHEME_UNSUPPORTED,
            BpkVideoPlayerErrorCode.MediaErrSrcNotSupported,
        )
    }

    @Test
    fun `Other DRM errors resolve to the unknown code`() {
        listOf(
            PlaybackException.ERROR_CODE_DRM_UNSPECIFIED,
            PlaybackException.ERROR_CODE_DRM_PROVISIONING_FAILED,
            PlaybackException.ERROR_CODE_DRM_CONTENT_ERROR,
            PlaybackException.ERROR_CODE_DRM_LICENSE_ACQUISITION_FAILED,
            PlaybackException.ERROR_CODE_DRM_DISALLOWED_OPERATION,
            PlaybackException.ERROR_CODE_DRM_SYSTEM_ERROR,
            PlaybackException.ERROR_CODE_DRM_DEVICE_REVOKED,
            PlaybackException.ERROR_CODE_DRM_LICENSE_EXPIRED,
        ).forEach { assertMapsTo(it, BpkVideoPlayerErrorCode.UnknownError) }
    }

    @Test
    fun `Negative session error codes resolve to the unknown code`() {
        listOf(
            PlaybackException.ERROR_CODE_INVALID_STATE,
            PlaybackException.ERROR_CODE_DISCONNECTED,
            PlaybackException.ERROR_CODE_CONTENT_ALREADY_PLAYING,
        ).forEach { assertMapsTo(it, BpkVideoPlayerErrorCode.UnknownError) }
    }

    @Test
    fun `Custom error codes resolve to the unknown code`() {
        assertMapsTo(PlaybackException.CUSTOM_ERROR_CODE_BASE, BpkVideoPlayerErrorCode.UnknownError)
    }

    @Test
    fun `An unrecognised code within a known family is classified by that family`() {
        // Guards forward compatibility: a code Media3 adds to an existing family should be
        // classified rather than silently becoming unknown.
        assertMapsTo(UNUSED_IO_ERROR_CODE, BpkVideoPlayerErrorCode.MediaErrNetwork)
        assertMapsTo(UNUSED_DECODING_ERROR_CODE, BpkVideoPlayerErrorCode.MediaErrDecode)
    }

    @Test
    fun `Every code except the load timeout is reachable from a Media3 error`() {
        // Asserts the vocabulary in both directions: no constant exists that no dashboard will ever
        // see, and the mapper never produces the load timeout, which the controller raises itself.
        val reachable = ALL_MEDIA3_ERROR_CODES.map { bpkVideoPlayerErrorCode(it) }.toSet()
        assertEquals(BpkVideoPlayerErrorCode.entries.toSet() - BpkVideoPlayerErrorCode.LoadTimeout, reachable)
    }

    private fun assertMapsTo(media3ErrorCode: Int, expected: BpkVideoPlayerErrorCode) {
        assertEquals(
            "Unexpected code for Media3 error $media3ErrorCode",
            expected,
            bpkVideoPlayerErrorCode(media3ErrorCode),
        )
    }

    private companion object {
        const val VIDEO_FRAME_PROCESSOR_INIT_FAILED = 7000
        const val VIDEO_FRAME_PROCESSING_FAILED = 7001
        const val UNUSED_IO_ERROR_CODE = 2999
        const val UNUSED_DECODING_ERROR_CODE = 4999

        /** Every error code Media3 1.11.0 documents, so the invariant tests cover the vocabulary. */
        val ALL_MEDIA3_ERROR_CODES = listOf(
            PlaybackException.ERROR_CODE_INVALID_STATE,
            PlaybackException.ERROR_CODE_BAD_VALUE,
            PlaybackException.ERROR_CODE_PERMISSION_DENIED,
            PlaybackException.ERROR_CODE_NOT_SUPPORTED,
            PlaybackException.ERROR_CODE_DISCONNECTED,
            PlaybackException.ERROR_CODE_AUTHENTICATION_EXPIRED,
            PlaybackException.ERROR_CODE_PREMIUM_ACCOUNT_REQUIRED,
            PlaybackException.ERROR_CODE_CONCURRENT_STREAM_LIMIT,
            PlaybackException.ERROR_CODE_PARENTAL_CONTROL_RESTRICTED,
            PlaybackException.ERROR_CODE_NOT_AVAILABLE_IN_REGION,
            PlaybackException.ERROR_CODE_SKIP_LIMIT_REACHED,
            PlaybackException.ERROR_CODE_SETUP_REQUIRED,
            PlaybackException.ERROR_CODE_END_OF_PLAYLIST,
            PlaybackException.ERROR_CODE_CONTENT_ALREADY_PLAYING,
            PlaybackException.ERROR_CODE_UNSPECIFIED,
            PlaybackException.ERROR_CODE_REMOTE_ERROR,
            PlaybackException.ERROR_CODE_BEHIND_LIVE_WINDOW,
            PlaybackException.ERROR_CODE_TIMEOUT,
            PlaybackException.ERROR_CODE_FAILED_RUNTIME_CHECK,
            PlaybackException.ERROR_CODE_IO_UNSPECIFIED,
            PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED,
            PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT,
            PlaybackException.ERROR_CODE_IO_INVALID_HTTP_CONTENT_TYPE,
            PlaybackException.ERROR_CODE_IO_BAD_HTTP_STATUS,
            PlaybackException.ERROR_CODE_IO_FILE_NOT_FOUND,
            PlaybackException.ERROR_CODE_IO_NO_PERMISSION,
            PlaybackException.ERROR_CODE_IO_CLEARTEXT_NOT_PERMITTED,
            PlaybackException.ERROR_CODE_IO_READ_POSITION_OUT_OF_RANGE,
            PlaybackException.ERROR_CODE_PARSING_CONTAINER_MALFORMED,
            PlaybackException.ERROR_CODE_PARSING_MANIFEST_MALFORMED,
            PlaybackException.ERROR_CODE_PARSING_CONTAINER_UNSUPPORTED,
            PlaybackException.ERROR_CODE_PARSING_MANIFEST_UNSUPPORTED,
            PlaybackException.ERROR_CODE_DECODER_INIT_FAILED,
            PlaybackException.ERROR_CODE_DECODER_QUERY_FAILED,
            PlaybackException.ERROR_CODE_DECODING_FAILED,
            PlaybackException.ERROR_CODE_DECODING_FORMAT_EXCEEDS_CAPABILITIES,
            PlaybackException.ERROR_CODE_DECODING_FORMAT_UNSUPPORTED,
            PlaybackException.ERROR_CODE_DECODING_RESOURCES_RECLAIMED,
            PlaybackException.ERROR_CODE_AUDIO_TRACK_INIT_FAILED,
            PlaybackException.ERROR_CODE_AUDIO_TRACK_WRITE_FAILED,
            PlaybackException.ERROR_CODE_AUDIO_TRACK_OFFLOAD_WRITE_FAILED,
            PlaybackException.ERROR_CODE_AUDIO_TRACK_OFFLOAD_INIT_FAILED,
            PlaybackException.ERROR_CODE_DRM_UNSPECIFIED,
            PlaybackException.ERROR_CODE_DRM_SCHEME_UNSUPPORTED,
            PlaybackException.ERROR_CODE_DRM_PROVISIONING_FAILED,
            PlaybackException.ERROR_CODE_DRM_CONTENT_ERROR,
            PlaybackException.ERROR_CODE_DRM_LICENSE_ACQUISITION_FAILED,
            PlaybackException.ERROR_CODE_DRM_DISALLOWED_OPERATION,
            PlaybackException.ERROR_CODE_DRM_SYSTEM_ERROR,
            PlaybackException.ERROR_CODE_DRM_DEVICE_REVOKED,
            PlaybackException.ERROR_CODE_DRM_LICENSE_EXPIRED,
            VIDEO_FRAME_PROCESSOR_INIT_FAILED,
            VIDEO_FRAME_PROCESSING_FAILED,
            PlaybackException.CUSTOM_ERROR_CODE_BASE,
        )
    }
}
