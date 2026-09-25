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

sealed class BpkVideoPlayerError {

    /**
     * A platform-neutral classification of this failure, suitable for reporting alongside iOS and Web.
     *
     * Report [BpkVideoPlayerErrorCode.wireName] rather than the constant name — see
     * [BpkVideoPlayerErrorCode] for why.
     */
    abstract val code: BpkVideoPlayerErrorCode

    data object LoadTimeout : BpkVideoPlayerError() {
        override val code = BpkVideoPlayerErrorCode.LoadTimeout
    }

    /**
     * @param code defaults to [BpkVideoPlayerErrorCode.UnknownError] only for callers constructing this
     *   type themselves; failures raised by the player always carry a classified code.
     */
    data class PlaybackFailed(
        val cause: Exception,
        override val code: BpkVideoPlayerErrorCode = BpkVideoPlayerErrorCode.UnknownError,
    ) : BpkVideoPlayerError()
}
