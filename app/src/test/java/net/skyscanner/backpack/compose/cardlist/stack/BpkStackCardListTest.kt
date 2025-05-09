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

package net.skyscanner.backpack.compose.cardlist.stack

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderButton
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.AddCircle
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import org.junit.Test

class BpkStackCardListTest : BpkSnapshotTest() {

    @Test
    fun defaultLazyColumnStack() = snap {
        BpkStackCardList(
            title = stringResource(R.string.card_list_title),
            totalCount = TOTAL_CARDS,
        ) { index -> StackItem(dataList[index]) }
    }

    @Test
    fun defaultLazyColumnStackWithDescription() = snap {
        BpkStackCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            totalCount = TOTAL_CARDS,
        ) { index -> StackItem(dataList[index]) }
    }

    @Test
    fun lazyColumnStackWithExpandAccessory() = snap {
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

    @Test
    fun lazyColumnStackWithExpandAccessoryWithLessThanMinimumExpandCount() = snap {
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

    @Test
    fun lazyColumnStackWithButtonAccessory() = snap {
        BpkStackCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            totalCount = 5,
            accessoryStyle = BpkStackCardAccessoryStyle.Button(
                title = stringResource(R.string.card_list_add_item),
                icon = BpkIcon.AddCircle,
                onClick = {},
            ),
        ) { index -> StackItem(dataList[index]) }
    }

    @Test
    fun lazyColumnStackWithExpandAccessoryAndSectionHeader() = snap {
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
                onClick = {},
            ),
        ) { index -> StackItem(dataList[index]) }
    }

    @Test
    fun lazyColumnStackWithButtonAccessoryAndSectionHeader() = snap {
        BpkStackCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            totalCount = 5,
            accessoryStyle = BpkStackCardAccessoryStyle.Button(
                title = stringResource(R.string.card_list_add_item),
                icon = BpkIcon.AddCircle,
                onClick = {},
            ),
            headerButton = BpkSectionHeaderButton(
                text = stringResource(R.string.card_list_header_button_text),
                onClick = {},
            ),
        ) { index -> StackItem(dataList[index]) }
    }

    @Test
    fun defaultColumnStack() = snap {
        BpkStackCardList(
            title = stringResource(R.string.card_list_title),
            totalCount = TOTAL_CARDS,
            isInScrollableContainer = true,
        ) { index -> StackItem(dataList[index]) }
    }

    @Test
    fun defaultColumnStackWithDescription() = snap {
        BpkStackCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            totalCount = TOTAL_CARDS,
            isInScrollableContainer = true,
        ) { index -> StackItem(dataList[index]) }
    }

    @Test
    fun columnStackWithExpandAccessory() = snap {
        BpkStackCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            totalCount = 5,
            isInScrollableContainer = true,
            accessoryStyle = BpkStackCardAccessoryStyle.Expand(
                expandText = stringResource(R.string.card_list_show_more),
                collapsedText = stringResource(R.string.card_list_show_less),
                collapsedCount = 2,
                expandedCount = 5,
            ),
        ) { index -> StackItem(dataList[index]) }
    }

    @Test
    fun columnStackWithExpandAccessoryWithLessThanMinimumExpandCount() = snap {
        BpkStackCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            totalCount = 2,
            isInScrollableContainer = true,
            accessoryStyle = BpkStackCardAccessoryStyle.Expand(
                expandText = stringResource(R.string.card_list_show_more),
                collapsedText = stringResource(R.string.card_list_show_less),
                collapsedCount = 1,
                expandedCount = 2,
            ),
        ) { index -> StackItem(dataList[index]) }
    }

    @Test
    fun columnStackWithButtonAccessory() = snap {
        BpkStackCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            totalCount = 5,
            isInScrollableContainer = true,
            accessoryStyle = BpkStackCardAccessoryStyle.Button(
                title = stringResource(R.string.card_list_add_item),
                icon = BpkIcon.AddCircle,
                onClick = {},
            ),
        ) { index -> StackItem(dataList[index]) }
    }

    @Test
    fun columnStackWithExpandAccessoryAndSectionHeader() = snap {
        BpkStackCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            totalCount = 5,
            isInScrollableContainer = true,
            accessoryStyle = BpkStackCardAccessoryStyle.Expand(
                expandText = stringResource(R.string.card_list_show_more),
                collapsedText = stringResource(R.string.card_list_show_less),
                collapsedCount = 2,
                expandedCount = 5,
            ),
            headerButton = BpkSectionHeaderButton(
                text = stringResource(R.string.card_list_header_button_text),
                onClick = {},
            ),
        ) { index -> StackItem(dataList[index]) }
    }

    @Test
    fun columnStackWithButtonAccessoryAndSectionHeader() = snap {
        BpkStackCardList(
            title = stringResource(R.string.card_list_title),
            description = stringResource(R.string.card_list_description),
            totalCount = 5,
            isInScrollableContainer = true,
            accessoryStyle = BpkStackCardAccessoryStyle.Button(
                title = stringResource(R.string.card_list_add_item),
                icon = BpkIcon.AddCircle,
                onClick = {},
            ),
            headerButton = BpkSectionHeaderButton(
                text = stringResource(R.string.card_list_header_button_text),
                onClick = {},
            ),
        ) { index -> StackItem(dataList[index]) }
    }
}

@Composable
private fun StackItem(data: CardListSample) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
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
)

private const val TOTAL_CARDS = 9
