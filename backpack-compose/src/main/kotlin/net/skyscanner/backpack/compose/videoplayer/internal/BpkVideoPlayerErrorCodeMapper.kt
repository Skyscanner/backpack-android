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

import androidx.media3.common.PlaybackException
import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlayerErrorCode

/**
 * Normalises a Media3 `PlaybackException.errorCode` into a [BpkVideoPlayerErrorCode].
 *
 * Takes the raw `Int` rather than the exception so that the mapping stays free of Media3 types and
 * can be unit tested on the JVM: `PlaybackException` is abstract and its useful factories are
 * `@UnstableApi`, whereas its error code constants are compile-time inlined.
 *
 * The media item's container format deliberately plays no part: see [BpkVideoPlayerErrorCode] for
 * why HLS streams are classified the same way as progressive media.
 */
internal fun bpkVideoPlayerErrorCode(media3ErrorCode: Int): BpkVideoPlayerErrorCode =
    when (media3ErrorCode) {
        PlaybackException.ERROR_CODE_DECODING_RESOURCES_RECLAIMED -> BpkVideoPlayerErrorCode.MediaErrAborted
        PlaybackException.ERROR_CODE_BEHIND_LIVE_WINDOW,
        PlaybackException.ERROR_CODE_TIMEOUT,
        -> BpkVideoPlayerErrorCode.MediaErrNetwork
        PlaybackException.ERROR_CODE_IO_INVALID_HTTP_CONTENT_TYPE,
        PlaybackException.ERROR_CODE_PARSING_CONTAINER_UNSUPPORTED,
        PlaybackException.ERROR_CODE_PARSING_MANIFEST_UNSUPPORTED,
        PlaybackException.ERROR_CODE_DECODING_FORMAT_EXCEEDS_CAPABILITIES,
        PlaybackException.ERROR_CODE_DECODING_FORMAT_UNSUPPORTED,
        PlaybackException.ERROR_CODE_DRM_SCHEME_UNSUPPORTED,
        -> BpkVideoPlayerErrorCode.MediaErrSrcNotSupported
        else -> errorCodeByFamily(media3ErrorCode)
    }

/**
 * Classifies by Media3's error code families rather than by individual constant, so that error
 * codes added in a future Media3 release are classified rather than silently becoming unknown.
 * It also avoids naming the `@UnstableApi` video-frame-processing constants.
 *
 * Codes outside these families — Media3's negative session codes, the miscellaneous `1000` family,
 * DRM errors other than an unsupported scheme, and `CUSTOM_ERROR_CODE_BASE` and above — are
 * deliberately unknown.
 */
private fun errorCodeByFamily(media3ErrorCode: Int): BpkVideoPlayerErrorCode =
    when (media3ErrorCode) {
        in IO_ERROR_CODES -> BpkVideoPlayerErrorCode.MediaErrNetwork
        in PARSING_ERROR_CODES,
        in DECODING_ERROR_CODES,
        in AUDIO_TRACK_ERROR_CODES,
        in VIDEO_FRAME_PROCESSING_ERROR_CODES,
        -> BpkVideoPlayerErrorCode.MediaErrDecode
        else -> BpkVideoPlayerErrorCode.UnknownError
    }

private val IO_ERROR_CODES = 2000..2999
private val PARSING_ERROR_CODES = 3000..3999
private val DECODING_ERROR_CODES = 4000..4999
private val AUDIO_TRACK_ERROR_CODES = 5000..5999
private val VIDEO_FRAME_PROCESSING_ERROR_CODES = 7000..7999
