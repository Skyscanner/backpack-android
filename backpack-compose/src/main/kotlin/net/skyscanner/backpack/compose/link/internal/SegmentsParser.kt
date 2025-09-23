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

internal val DEFAULT_REGEX = Regex("<link\\d+>(.*?)</link\\d+>")
