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
 * Each [wireName] is one of the `MediaError` codes defined by the HTML media specification, which
 * iOS, Android and Web all map their own playback failures onto. Reporting that shared vocabulary is
 * what lets failures from the three platforms be grouped in a single dashboard.
 *
 * Report [wireName] rather than the constant name: constant names follow Kotlin conventions and may
 * be refactored, whereas [wireName] is a stable cross-platform contract.
 *
 * Backpack Web additionally emits a family of `HLS_*` codes, which come from the error taxonomy of
 * the `hls.js` library it plays HLS streams with. Those are deliberately absent here. Android plays
 * HLS through Media3, which classifies failures along entirely different lines, so an `HLS_*` code
 * on Android could only ever be a guess at which `hls.js` bucket a Media3 error would have fallen
 * into — a code that looks joinable with Web while meaning something different. A failure is
 * classified the same way here whether the media item is an HLS stream or progressive.
 */
enum class BpkVideoPlayerErrorCode(val wireName: String) {

    /**
     * Playback was aborted before it could complete. On Android this means the decoder lost its
     * resources to a higher-priority application rather than a user-initiated cancellation.
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

    /**
     * The video did not become playable within [BpkVideoPlayerConfig.loadTimeoutMs].
     *
     * Has no Web counterpart, which tracks load timeouts as a separate signal. It is kept distinct
     * from [MediaErrNetwork] so that timeouts do not inflate the network-error count on a shared
     * dashboard.
     */
    LoadTimeout("LOAD_TIMEOUT"),
}
