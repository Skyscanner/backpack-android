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
import net.skyscanner.backpack.compose.link.buildTextSegments
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
        { SingleLinkExample(style = style, onNotification = showNotification) },
        { LinkWithoutUrlExample(style = style, onNotification = showNotification) },
        { TextWithMultipleLinksExample(style = style, onNotification = showNotification) },
        { TypeSafeBuilderExample(style = style, onNotification = showNotification) },
        { PriceLinksExample(style = style, onNotification = showNotification) },
        { TextStyleVariationsExample(style = style, onNotification = showNotification) },
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
internal fun SingleLinkExample(
    modifier: Modifier = Modifier,
    style: BpkLinkStyle = BpkLinkStyle.Default,
    onNotification: (String) -> Unit = {},
) {
    val notificationTemplate = stringResource(R.string.link_story_notification_opening)

    LinkExample(
        title = stringResource(R.string.link_story_simple_example_title),
        modifier = modifier,
        style = style,
    ) {
        BpkLink(
            text = stringResource(R.string.link_story_simple_example_text),
            style = style,
            onLinkClicked = { url ->
                onNotification(notificationTemplate.format(url))
            },
        )
    }
}

@Composable
internal fun LinkWithoutUrlExample(
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
            onLinkClicked = { url ->
                val message = if (url.isEmpty()) {
                    "$clickedMessage (empty URL)"
                } else {
                    clickedMessage
                }
                onNotification(message)
            },
        )
    }
}

@Composable
internal fun TextWithMultipleLinksExample(
    modifier: Modifier = Modifier,
    style: BpkLinkStyle = BpkLinkStyle.Default,
    onNotification: (String) -> Unit = {},
) {
    val openingTemplate = stringResource(R.string.link_story_notification_opening)

    LinkExample(
        title = stringResource(R.string.link_story_terms_title),
        modifier = modifier,
        style = style,
    ) {
        BpkLink(
            text = stringResource(R.string.link_story_terms_full),
            style = style,
            onLinkClicked = { url ->
                onNotification(openingTemplate.format(url))
            },
        )
    }
}

@Composable
internal fun TypeSafeBuilderExample(
    modifier: Modifier = Modifier,
    style: BpkLinkStyle = BpkLinkStyle.Default,
    onNotification: (String) -> Unit = {},
) {
    val openingTemplate = stringResource(R.string.link_story_notification_opening)
    val searchPrefix = stringResource(R.string.link_story_search_prefix)
    val flightsText = stringResource(R.string.link_story_flights_text)
    val commaSeparator = stringResource(R.string.link_story_comma_separator)
    val hotelsText = stringResource(R.string.link_story_hotels_text)
    val andSeparator = stringResource(R.string.link_story_and_separator)
    val carsText = stringResource(R.string.link_story_cars_text)
    val searchSuffix = stringResource(R.string.link_story_search_suffix)
    val flightsUrl = stringResource(R.string.link_story_url_flights)
    val hotelsUrl = stringResource(R.string.link_story_url_hotels)
    val carsUrl = stringResource(R.string.link_story_url_cars)

    LinkExample(
        title = stringResource(R.string.link_story_builder_title),
        modifier = modifier,
        style = style,
    ) {
        BpkLink(
            segments = buildTextSegments(autoSpace = true) {
                text(searchPrefix)
                link(flightsText, flightsUrl)
                text(commaSeparator)
                link(hotelsText, hotelsUrl)
                text(andSeparator)
                link(carsText, carsUrl)
                text(searchSuffix)
            },
            style = style,
            onLinkClicked = { url ->
                onNotification(openingTemplate.format(url))
            },
        )
    }
}

@Composable
internal fun PriceLinksExample(
    modifier: Modifier = Modifier,
    style: BpkLinkStyle = BpkLinkStyle.Default,
    onNotification: (String) -> Unit = {},
) {
    val openingTemplate = stringResource(R.string.link_story_notification_opening)

    LinkExample(
        title = stringResource(R.string.link_story_price_links_title),
        modifier = modifier,
        style = style,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(BpkSpacing.Sm)) {
            BpkLink(
                text = stringResource(R.string.link_story_price_flight_markdown),
                style = style,
                onLinkClicked = { url -> onNotification(openingTemplate.format(url)) },
            )

            BpkLink(
                text = stringResource(R.string.link_story_hotel_deals_markdown),
                style = style,
                onLinkClicked = { url -> onNotification(openingTemplate.format(url)) },
            )
        }
    }
}

@Composable
internal fun TextStyleVariationsExample(
    modifier: Modifier = Modifier,
    style: BpkLinkStyle = BpkLinkStyle.Default,
    onNotification: (String) -> Unit = {},
) {
    val openingTemplate = stringResource(R.string.link_story_notification_opening)
    val termsPrefix = stringResource(R.string.link_story_terms_prefix)
    val termsText = stringResource(R.string.link_story_terms_text)
    val termsUrl = stringResource(R.string.link_story_url_terms)

    LinkExample(
        title = stringResource(R.string.link_story_text_styles_title),
        modifier = modifier,
        style = style,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(BpkSpacing.Sm)) {
            BpkLink(
                text = stringResource(R.string.link_story_flight_deal_link),
                textStyle = BpkTheme.typography.heading4,
                style = style,
                onLinkClicked = { url -> onNotification(openingTemplate.format(url)) },
            )

            BpkLink(
                text = stringResource(R.string.link_story_guarantee_link),
                textStyle = BpkTheme.typography.bodyDefault,
                style = style,
                onLinkClicked = { url -> onNotification(openingTemplate.format(url)) },
            )

            BpkLink(
                segments = buildTextSegments {
                    text(termsPrefix)
                    link(termsText, termsUrl)
                },
                textStyle = BpkTheme.typography.caption,
                style = style,
                onLinkClicked = { url -> onNotification(openingTemplate.format(url)) },
            )
        }
    }
}
