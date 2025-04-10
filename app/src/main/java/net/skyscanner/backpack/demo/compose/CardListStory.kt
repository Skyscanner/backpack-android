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

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.cardlist.rail.BpkRailCardList
import net.skyscanner.backpack.compose.cardlist.stack.BpkStackCardAccessoryStyle
import net.skyscanner.backpack.compose.cardlist.stack.BpkStackCardList
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderButton
import net.skyscanner.backpack.compose.snippet.BpkSnippet
import net.skyscanner.backpack.compose.snippet.ImageOrientation
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.AddCircle
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.CardListComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.demo.ui.LocalFloatingNotification

@Composable
@CardListComponent
@ComposeStory("Rail")
fun CardListRailStory(modifier: Modifier = Modifier) {
    Column(modifier) {
        BpkRailCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            totalCards = TOTAL_CARDS,
        ) { index -> Column { CardLayout(dataList[index]) } }
    }
}

@Composable
@CardListComponent
@ComposeStory("Rail with HeaderButton")
fun CardListRailWithHeaderButtonStory(modifier: Modifier = Modifier) {
    val floatingNotification = LocalFloatingNotification.current
    val coroutineScope = rememberCoroutineScope()
    val notificationText = stringResource(R.string.card_list_section_header_click)

    Column(modifier) {
        BpkRailCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            modifier = Modifier,
            headerButton = BpkSectionHeaderButton(
                text = stringResource(R.string.card_list_header_button_text),
                onClick = {
                    coroutineScope.launch {
                        floatingNotification.show(notificationText)
                    }
                },
            ),
            totalCards = TOTAL_CARDS,
        ) { index -> Column { CardLayout(dataList[index]) } }
    }
}

@Composable
@CardListComponent
@ComposeStory("Stack")
fun CardListStackStory(modifier: Modifier = Modifier) {
    Column(modifier) {
        BpkStackCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            totalCount = TOTAL_CARDS,
        ) { index -> StackItem(dataList[index]) }
    }
}

@Composable
@CardListComponent
@ComposeStory("Stack with expand accessory")
fun CardListStackWithExpandAccessoryStory(modifier: Modifier = Modifier) {
    Column(modifier) {
        BpkStackCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            totalCount = 5,
            accessoryStyle = BpkStackCardAccessoryStyle.Expand(
                expandText = stringResource(R.string.card_list_show_more),
                collapsedText = stringResource(R.string.card_list_show_less),
                collapsedCount = 2,
                expandedCount = 5,
            ),
        ) { index -> StackItem(dataList[index]) }
    }
}

@Composable
@CardListComponent
@ComposeStory("Stack with expand accessory with less than minimum expanded count")
fun CardListStackWithExpandAccessoryWithLessThanMinimumExpandedCountStory(modifier: Modifier = Modifier) {
    Column(modifier) {
        BpkStackCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            totalCount = 2,
            accessoryStyle = BpkStackCardAccessoryStyle.Expand(
                expandText = stringResource(R.string.card_list_show_more),
                collapsedText = stringResource(R.string.card_list_show_less),
                collapsedCount = 1,
                expandedCount = 2,
            ),
        ) { index -> StackItem(dataList[index]) }
    }
}

@Composable
@CardListComponent
@ComposeStory("Stack with button accessory")
fun CardListStackWithButtonAccessoryStory(modifier: Modifier = Modifier) {
    val floatingNotification = LocalFloatingNotification.current
    val coroutineScope = rememberCoroutineScope()
    val notificationText = stringResource(R.string.card_list_accessory_button_clicked)

    Column(modifier) {
        BpkStackCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            totalCount = 5,
            accessoryStyle = BpkStackCardAccessoryStyle.Button(
                title = stringResource(R.string.card_list_add_item),
                icon = BpkIcon.AddCircle,
                onClick = {
                    coroutineScope.launch {
                        floatingNotification.show(notificationText)
                    }
                },
            ),
        ) { index -> StackItem(dataList[index]) }
    }
}

@Composable
@CardListComponent
@ComposeStory("Stack with expand accessory and section header button")
fun CardListStackWithExpandAndSectionHeaderAccessoryStory(modifier: Modifier = Modifier) {
    val floatingNotification = LocalFloatingNotification.current
    val coroutineScope = rememberCoroutineScope()
    val notificationText = stringResource(R.string.card_list_section_header_click)

    Column(modifier) {
        BpkStackCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            totalCount = 5,
            accessoryStyle = BpkStackCardAccessoryStyle.Expand(
                expandText = stringResource(R.string.card_list_show_more),
                collapsedText = stringResource(R.string.card_list_show_less),
                collapsedCount = 2,
                expandedCount = 5,
            ),
            headerButton = BpkSectionHeaderButton(
                text = stringResource(R.string.card_list_header_button_text),
                onClick = {
                    coroutineScope.launch {
                        floatingNotification.show(notificationText)
                    }
                },
            ),
        ) { index -> StackItem(dataList[index]) }
    }
}

@Composable
@CardListComponent
@ComposeStory("Stack with button accessory and section header button")
fun CardListStackWithButtonAndSectionHeaderAccessoryStory(modifier: Modifier = Modifier) {
    val floatingNotification = LocalFloatingNotification.current
    val coroutineScope = rememberCoroutineScope()
    val notificationText1 = stringResource(R.string.card_list_section_header_click)
    val notificationText2 = stringResource(R.string.card_list_accessory_button_clicked)

    Column(modifier) {
        BpkStackCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            totalCount = 5,
            accessoryStyle = BpkStackCardAccessoryStyle.Button(
                title = stringResource(R.string.card_list_add_item),
                icon = BpkIcon.AddCircle,
                onClick = {
                    coroutineScope.launch {
                        floatingNotification.show(notificationText2)
                    }
                },
            ),
            headerButton = BpkSectionHeaderButton(
                text = stringResource(R.string.card_list_header_button_text),
                onClick = {
                    coroutineScope.launch {
                        floatingNotification.show(notificationText1)
                    }
                },
            ),
        ) { index -> StackItem(dataList[index]) }
    }
}

@Composable
private fun CardLayout(data: CardListSample) {
    BpkSnippet(
        modifier = Modifier.width(CARD_LAYOUT_WIDTH.dp),
        imageOrientation = ImageOrientation.Landscape,
        headline = stringResource(data.headline),
        bodyText = data.bodyText?.let { stringResource(it) },
        accessibilityHeaderTagEnabled = false,
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(data.image),
            contentDescription = stringResource(R.string.snippet_image_content_description),
            contentScale = ContentScale.Crop,
        )
    }
}

data class CardListSample(
    @DrawableRes val image: Int,
    @StringRes val headline: Int,
    @StringRes val bodyText: Int? = null,
)

private val dataList = listOf(
    CardListSample(
        image = R.drawable.amsterdam,
        headline = R.string.card_list_item_1_headline,
        bodyText = R.string.card_list_item_1_body_text,
    ),
    CardListSample(
        image = R.drawable.london_towerbridge,
        headline = R.string.card_list_item_2_headline,
        bodyText = R.string.card_list_item_2_body_text,
    ),
    CardListSample(
        image = R.drawable.dublin,
        headline = R.string.card_list_item_3_headline,
        bodyText = R.string.card_list_item_3_body_text,
    ),
    CardListSample(
        image = R.drawable.paris,
        headline = R.string.card_list_item_4_headline,
    ),
    CardListSample(
        image = R.drawable.mallorca,
        headline = R.string.card_list_item_5_headline,
    ),
    CardListSample(
        image = R.drawable.alicante,
        headline = R.string.card_list_item_6_headline,
    ),
    CardListSample(
        image = R.drawable.barcelona,
        headline = R.string.card_list_item_7_headline,
    ),
    CardListSample(
        image = R.drawable.berlin,
        headline = R.string.card_list_item_8_headline,
    ),
    CardListSample(
        image = R.drawable.london_saintpancrasstation,
        headline = R.string.card_list_item_9_headline,
        bodyText = R.string.card_list_item_9_body_text,
    ),
    CardListSample(
        image = R.drawable.paris,
        headline = R.string.card_list_item_4_headline,
    ),
    CardListSample(
        image = R.drawable.mallorca,
        headline = R.string.card_list_item_5_headline,
    ),
    CardListSample(
        image = R.drawable.alicante,
        headline = R.string.card_list_item_6_headline,
    ),
)

@Composable
private fun StackItem(data: CardListSample) {
    val floatingNotification = LocalFloatingNotification.current
    val coroutineScope = rememberCoroutineScope()
    val notificationText = stringResource(R.string.card_list_item_clicked)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                coroutineScope.launch {
                    floatingNotification.show(notificationText)
                }
            },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = data.image),
                contentDescription = stringResource(R.string.snippet_image_content_description),
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(BpkSpacing.Md)),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(BpkSpacing.Base))
            Column {
                BpkText(
                    text = stringResource(data.headline),
                    style = BpkTheme.typography.heading4,
                    color = BpkTheme.colors.textPrimary,
                )
                BpkText(
                    text = data.bodyText?.let { stringResource(it) } ?: "",
                    style = BpkTheme.typography.bodyDefault,
                    color = BpkTheme.colors.textPrimary,
                )
            }
        }
    }
}

private const val CARD_LAYOUT_WIDTH = 281
private const val TOTAL_CARDS = 12
