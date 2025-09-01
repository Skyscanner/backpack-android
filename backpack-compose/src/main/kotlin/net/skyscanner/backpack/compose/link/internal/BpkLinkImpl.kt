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

package net.skyscanner.backpack.compose.link.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import net.skyscanner.backpack.compose.LocalTextStyle
import net.skyscanner.backpack.compose.link.BpkLinkStyle
import net.skyscanner.backpack.compose.link.TextSegment
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme

private val MARKDOWN_LINK_REGEX = Regex("\\[([^]]*)]\\(([^)]*)\\)")

@Composable
internal fun BpkLinkImpl(
    text: String,
    onLinkClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    linkStyle: BpkLinkStyle = BpkLinkStyle.Default,
) {
    val textColor = when (linkStyle) {
        BpkLinkStyle.Default -> BpkTheme.colors.textPrimary
        BpkLinkStyle.OnContrast -> BpkTheme.colors.textOnDark
    }

    BpkText(
        text = buildAnnotatedStringFromMarkdown(text, textColor, onLinkClicked),
        style = style,
        modifier = modifier,
    )
}

@Composable
internal fun BpkLinkImpl(
    segments: List<TextSegment>,
    onLinkClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    linkStyle: BpkLinkStyle = BpkLinkStyle.Default,
) {
    val markdownText = convertSegmentsToMarkdown(segments)

    BpkLinkImpl(
        text = markdownText,
        onLinkClicked = onLinkClicked,
        modifier = modifier,
        style = style,
        linkStyle = linkStyle,
    )
}

@Composable
private fun buildAnnotatedStringFromMarkdown(
    text: String,
    textColor: Color,
    onLinkClicked: (String) -> Unit,
): AnnotatedString {
    return buildAnnotatedString {
        val linkMatches = MARKDOWN_LINK_REGEX.findAll(text).toList()

        if (linkMatches.isEmpty()) {
            AppendRawText(textColor, text)
        } else {
            var currentIndex = 0

            linkMatches.forEachIndexed { linkIndex, match ->
                if (match.range.first > currentIndex) {
                    AppendRawText(textColor, text.substring(currentIndex, match.range.first))
                }

                AppendLinkText(match, linkIndex, onLinkClicked, textColor)

                currentIndex = match.range.last + 1
            }

            if (currentIndex < text.length) {
                AppendRawText(textColor, text.substring(currentIndex))
            }
        }
    }
}

@Composable
private fun AnnotatedString.Builder.AppendLinkText(
    match: MatchResult,
    linkIndex: Int,
    onLinkClicked: (String) -> Unit,
    textColor: Color,
) {
    val linkText = match.groupValues.getOrNull(1) ?: ""
    val url = match.groupValues.getOrNull(2) ?: ""

    if (linkText.isNotEmpty() || url.isNotEmpty()) {
        val linkAnnotation = LinkAnnotation.Clickable(
            tag = "LINK_$linkIndex",
            linkInteractionListener = { onLinkClicked(url) },
        )
        withLink(linkAnnotation) {
            withStyle(
                SpanStyle(
                    color = textColor,
                    textDecoration = TextDecoration.Underline,
                ),
            ) {
                append(linkText)
            }
        }
    }
}

@Composable
private fun AnnotatedString.Builder.AppendRawText(
    textColor: Color,
    text: String,
) {
    withStyle(SpanStyle(color = textColor)) {
        append(text)
    }
}

private fun convertSegmentsToMarkdown(segments: List<TextSegment>): String = buildString {
    for (segment in segments) {
        when (segment) {
            is TextSegment.Text -> append(segment.content)
            is TextSegment.Link -> append("[${segment.text}](${segment.url})")
        }
    }
}
