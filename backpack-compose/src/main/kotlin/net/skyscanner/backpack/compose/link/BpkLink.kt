/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import net.skyscanner.backpack.compose.LocalTextStyle
import net.skyscanner.backpack.compose.link.internal.BpkLinkImpl

enum class BpkLinkStyle {
    Default,
    OnContrast,
}

/**
 * Represents a segment of text that can be either plain text or a clickable link.
 */
sealed class TextSegment {
    data class Text(val content: String) : TextSegment()
    data class Link(val text: String, val url: String) : TextSegment()
}

/**
 * BpkLink component for displaying text with clickable links using markdown syntax.
 *
 * @param text The text containing markdown-style links [text](url)
 * @param onLinkClicked Callback invoked when a link is clicked, receives the URL
 * @param modifier The modifier to be applied to the composable
 * @param textStyle The style to be applied to the text
 * @param style The visual style of the link (Default or OnContrast)
 */
@Composable
fun BpkLink(
    text: String,
    onLinkClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    style: BpkLinkStyle = BpkLinkStyle.Default,
) {
    BpkLinkImpl(
        text = text,
        onLinkClicked = onLinkClicked,
        modifier = modifier,
        style = textStyle,
        linkStyle = style,
    )
}

/**
 * BpkLink component for displaying text with clickable links using type-safe segments.
 *
 * @param segments The list of text segments (plain text and links), you can use [buildTextSegments] to create it
 * @param onLinkClicked Callback invoked when a link is clicked, receives the URL
 * @param modifier The modifier to be applied to the composable
 * @param textStyle The style to be applied to the text
 * @param style The visual style of the link (Default or OnContrast)
 */
@Composable
fun BpkLink(
    segments: List<TextSegment>,
    onLinkClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    style: BpkLinkStyle = BpkLinkStyle.Default,
) {
    BpkLinkImpl(
        segments = segments,
        onLinkClicked = onLinkClicked,
        modifier = modifier,
        style = textStyle,
        linkStyle = style,
    )
}

fun buildTextSegments(
    autoSpace: Boolean = false,
    block: TextSegmentBuilder.() -> Unit,
): List<TextSegment> {
    val segments = TextSegmentBuilder().apply(block).build()
    return if (autoSpace) {
        addSpacingBetweenSegments(segments)
    } else {
        segments
    }
}
