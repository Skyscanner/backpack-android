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

package net.skyscanner.backpack.compose.link

/**
 * Builder for creating text segments with links.
 */
class TextSegmentBuilder {
    private val segments = mutableListOf<TextSegment>()

    fun text(content: String) {
        segments.add(TextSegment.Text(content))
    }

    fun link(text: String, url: String) {
        segments.add(TextSegment.Link(text, url))
    }

    internal fun build(): List<TextSegment> = segments.toList()
}

internal fun addSpacingBetweenSegments(segments: List<TextSegment>): List<TextSegment> {
    if (segments.size <= 1) return segments
    val spacedSegments = mutableListOf<TextSegment>()
    segments.forEachIndexed { index, segment ->
        spacedSegments.add(segment)
        if (index < segments.size - 1) {
            spacedSegments.add(TextSegment.Text(" "))
        }
    }
    return spacedSegments
}
