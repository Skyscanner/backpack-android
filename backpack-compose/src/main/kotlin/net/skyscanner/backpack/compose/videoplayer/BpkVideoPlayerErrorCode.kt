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

/**
 * A platform-neutral classification of a video playback failure.
 *
 * [wireName] is the reporting/join key shared with Backpack Web and iOS and must stay stable across
 * refactors and releases, unlike the enum constant name, which follows Kotlin naming conventions and
 * may change.
 */
enum class BpkVideoPlayerErrorCode(val wireName: String) {

    /**
     * Playback was aborted before it could complete. The decoder lost its resources to a higher-priority application
     * rather than a user-initiated cancellation.
     */
    MediaErrAborted("MEDIA_ERR_ABORTED"),

    /**
     * The media could not be fetched: connection failure, timeout, or an unusable HTTP response.
     * For an HLS stream this covers both manifest and segment fetch failures.
     */
    MediaErrNetwork("MEDIA_ERR_NETWORK"),

    /**
     * The media was fetched but could not be decoded or rendered. For an HLS stream this includes a
     * segment that could not be demuxed and a manifest that could not be parsed.
     */
    MediaErrDecode("MEDIA_ERR_DECODE"),

    /** The media was fetched but its container, codec or DRM scheme is not supported. */
    MediaErrSrcNotSupported("MEDIA_ERR_SRC_NOT_SUPPORTED"),

    /** A failure that does not fit any other code. */
    UnknownError("UNKNOWN_ERROR"),

    /** The video did not become playable within [BpkVideoPlayerConfig.loadTimeoutMs].*/
    LoadTimeout("LOAD_TIMEOUT"),
}
