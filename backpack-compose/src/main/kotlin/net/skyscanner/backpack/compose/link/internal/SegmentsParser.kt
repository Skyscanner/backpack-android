/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.link.internal

import net.skyscanner.backpack.compose.link.TextSegment

internal fun processLinkMatch(
    segments: MutableList<TextSegment>,
    remainingText: String,
    linkMatch: MatchResult,
    url: String,
): String {
    val beforeLink = remainingText.substring(0, linkMatch.range.first)
    if (beforeLink.isNotEmpty()) {
        segments.add(TextSegment.Text(beforeLink))
    }

    segments.add(
        TextSegment.Link(
            text = linkMatch.groupValues[1],
            url = url,
        ),
    )

    return remainingText.substring(linkMatch.range.last + 1)
}

internal val DEFAULT_REGEX = Regex("<link(\\d+)>(.*?)</link\\1>")
