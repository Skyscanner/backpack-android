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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.divider.BpkDivider
import net.skyscanner.backpack.compose.link.BpkLink
import net.skyscanner.backpack.compose.link.BpkLinkStyle
import net.skyscanner.backpack.compose.link.TextType
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.LinkComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.demo.ui.LocalFloatingNotification

@Composable
@LinkComponent
@ComposeStory("Default")
fun LinkStoryDefault(modifier: Modifier = Modifier) {
    StoryContent(style = BpkLinkStyle.Default, modifier = modifier)
}

@Composable
@LinkComponent
@ComposeStory("OnContrast")
fun LinkStoryOnContrast(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BpkTheme.colors.corePrimary)
            .padding(BpkSpacing.Base),
    ) {
        StoryContent(style = BpkLinkStyle.OnContrast)
    }
}

@Composable
private fun StoryContent(
    style: BpkLinkStyle,
    modifier: Modifier = Modifier,
) {
    val floatingNotificationState = LocalFloatingNotification.current
    val scope = rememberCoroutineScope()

    val showNotification: (String) -> Unit = { message ->
        scope.launch {
            floatingNotificationState.show(text = message)
        }
    }
    val cases = listOf<@Composable () -> Unit>(
        { SimpleLinkExample(style = style, onNotification = showNotification) },
        { SimpleLinkWithoutUrlExample(style = style, onNotification = showNotification) },
        { MixedTextWithLinksExample(style = style, onNotification = showNotification) },
        { MultipleLinksInParagraphExample(style = style, onNotification = showNotification) },
        { LinkHeadingExample(style = style, onNotification = showNotification) },
        { LinkCaptionExample(style = style, onNotification = showNotification) },
    )
    LazyColumn(
        modifier = modifier.padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Lg),
    ) {
        itemsIndexed(cases) { index, item ->
            Column(verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md)) {
                item()
                if (index < cases.size - 1) {
                    BpkDivider()
                }
            }
        }
    }
}

@Composable
private fun LinkExample(
    title: String,
    style: BpkLinkStyle,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val textColor = when (style) {
        BpkLinkStyle.Default -> BpkTheme.colors.textPrimary
        BpkLinkStyle.OnContrast -> BpkTheme.colors.textOnDark
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
    ) {
        BpkText(
            text = title,
            color = textColor,
            style = BpkTheme.typography.heading5,
        )
        content()
    }
}

@Composable
internal fun SimpleLinkExample(
    modifier: Modifier = Modifier,
    style: BpkLinkStyle = BpkLinkStyle.Default,
    onNotification: (String) -> Unit = {},
) {
    val notificationTemplate = stringResource(R.string.link_story_notification_opening)
    val url = stringResource(R.string.link_story_simple_example_url)

    LinkExample(
        title = stringResource(R.string.link_story_simple_example_title),
        modifier = modifier,
        style = style,
    ) {
        BpkLink(
            text = listOf(
                TextType.LinkText(stringResource(R.string.link_story_simple_example_text)),
            ),
            style = style,
            onLinkClicked = { _ ->
                onNotification(notificationTemplate.format(url))
            },
        )
    }
}

@Composable
internal fun SimpleLinkWithoutUrlExample(
    modifier: Modifier = Modifier,
    style: BpkLinkStyle = BpkLinkStyle.Default,
    onNotification: (String) -> Unit = {},
) {
    val clickedMessage = stringResource(R.string.link_story_notification_clicked)

    LinkExample(
        title = stringResource(R.string.link_story_simple_without_url_title),
        modifier = modifier,
        style = style,
    ) {
        BpkLink(
            text = stringResource(R.string.link_story_simple_without_url_text),
            style = style,
            onLinkClicked = {
                onNotification(clickedMessage)
            },
        )
    }
}

@Composable
internal fun MixedTextWithLinksExample(
    modifier: Modifier = Modifier,
    style: BpkLinkStyle = BpkLinkStyle.Default,
    onNotification: (String) -> Unit = {},
) {
    val openingTemplate = stringResource(R.string.link_story_notification_opening)
    val urls = listOf(
        stringResource(R.string.link_story_mixed_text_url1),
        stringResource(R.string.link_story_mixed_text_url2),
    )

    LinkExample(
        title = stringResource(R.string.link_story_mixed_text_title),
        modifier = modifier,
        style = style,
    ) {
        BpkLink(
            text = listOf(
                TextType.RawText(stringResource(R.string.link_story_mixed_text_raw_start)),
                TextType.LinkText(stringResource(R.string.link_story_mixed_text_link1)),
                TextType.RawText(stringResource(R.string.link_story_mixed_text_raw_middle)),
                TextType.LinkText(stringResource(R.string.link_story_mixed_text_link2)),
                TextType.RawText(stringResource(R.string.link_story_mixed_text_raw_end)),
            ),
            style = style,
            onLinkClicked = { index ->
                onNotification(openingTemplate.format(urls[index]))
            },
        )
    }
}

@Composable
internal fun MultipleLinksInParagraphExample(
    modifier: Modifier = Modifier,
    style: BpkLinkStyle = BpkLinkStyle.Default,
    onNotification: (String) -> Unit = {},
) {
    val openingTemplate = stringResource(R.string.link_story_notification_opening)
    val urls = listOf(
        stringResource(R.string.link_story_terms_url1),
        stringResource(R.string.link_story_terms_url2),
        stringResource(R.string.link_story_terms_url3),
    )

    LinkExample(
        title = stringResource(R.string.link_story_terms_title),
        modifier = modifier,
        style = style,
    ) {
        BpkLink(
            text = listOf(
                TextType.RawText(stringResource(R.string.link_story_terms_raw_start)),
                TextType.LinkText(stringResource(R.string.link_story_terms_link1)),
                TextType.RawText(stringResource(R.string.link_story_terms_raw_middle)),
                TextType.LinkText(stringResource(R.string.link_story_terms_link2)),
                TextType.RawText(stringResource(R.string.link_story_terms_raw_middle2)),
                TextType.LinkText(stringResource(R.string.link_story_terms_link3)),
                TextType.RawText(stringResource(R.string.link_story_terms_raw_end)),
            ),
            style = style,
            onLinkClicked = { index ->
                onNotification(openingTemplate.format(urls[index]))
            },
        )
    }
}

@Composable
internal fun LinkHeadingExample(
    modifier: Modifier = Modifier,
    style: BpkLinkStyle = BpkLinkStyle.Default,
    onNotification: (String) -> Unit = {},
) {
    val url = stringResource(R.string.link_story_price_example_url)

    LinkExample(
        title = stringResource(R.string.link_story_link_heading_title),
        modifier = modifier,
        style = style,
    ) {
        BpkLink(
            text = stringResource(R.string.link_story_price_example_link),
            textStyle = BpkTheme.typography.heading4,
            style = style,
            onLinkClicked = {
                onNotification(url)
            },
        )
    }
}

@Composable
internal fun LinkCaptionExample(
    modifier: Modifier = Modifier,
    style: BpkLinkStyle = BpkLinkStyle.Default,
    onNotification: (String) -> Unit = {},
) {
    val openingTemplate = stringResource(R.string.link_story_notification_opening)
    val url = stringResource(R.string.link_story_styled_text_url)

    LinkExample(
        title = stringResource(R.string.link_story_link_caption_title),
        modifier = modifier,
        style = style,
    ) {
        BpkLink(
            text = listOf(
                TextType.RawText(stringResource(R.string.link_story_styled_text_raw)),
                TextType.LinkText(stringResource(R.string.link_story_styled_text_link)),
            ),
            textStyle = BpkTheme.typography.caption,
            style = style,
            onLinkClicked = { _ ->
                onNotification(openingTemplate.format(url))
            },
        )
    }
}
