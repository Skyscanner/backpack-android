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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.cardlist.BpkCardList
import net.skyscanner.backpack.compose.cardlist.BpkCardListButtonAccessory
import net.skyscanner.backpack.compose.cardlist.BpkCardListLayout
import net.skyscanner.backpack.compose.snippet.BpkSnippet
import net.skyscanner.backpack.compose.snippet.ImageOrientation
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.CardListComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

private val dataList = cardListSamples()

@Composable
@CardListComponent
@ComposeStory("Rail")
fun CardListRailStory(modifier: Modifier = Modifier) {
    Column(modifier) {
        BpkCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            layout = BpkCardListLayout.Rail(),
            modifier = Modifier,
            dataList = dataList,
        ) { position ->
            CardLayout(dataList[position])
        }
    }
}

@Composable
@CardListComponent
@ComposeStory("RailWithHeaderButton")
fun CardListRailWithHewStory(modifier: Modifier = Modifier) {
    Column(modifier) {
        BpkCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            layout = BpkCardListLayout.Rail(
                headerButton = BpkCardListButtonAccessory.SectionHeaderButton(
                    text = stringResource(R.string.card_list_header_button_text),
                    onClick = {},
                )),
            modifier = Modifier,
            dataList = dataList,
        ) { position ->
            CardLayout(dataList[position])
        }
    }
}

@Composable
private fun CardLayout(data: CardListSample) {
    BpkSnippet(
        modifier = Modifier.width(CARD_LAYOUT_WIDTH.dp),
        imageOrientation = ImageOrientation.Landscape,
        headline = stringResource(data.headline),
        bodyText = data.bodyText?.let { stringResource(it) },
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

private fun cardListSamples(): List<CardListSample> {
    val listOf = listOf(
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
    )
    return listOf
}

private const val CARD_LAYOUT_WIDTH = 281
