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

package net.skyscanner.backpack.compose.cardlist

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.demo.R
import org.junit.Test

class BpkCardListTest : BpkSnapshotTest() {

    private val dataList = cardListSamples()

    @Test
    fun defaultRail() = snap {
        BpkCardList(
            title = "Section Title",
            description = "Description about this section",
            layout = BpkCardListLayout.Rail,
            sectionHeaderButton = null,
            modifier = Modifier,
            dataList = dataList,
        ) { position ->
            CardLayout(dataList[position])
        }
    }
}

@Composable
private fun CardLayout(data: CardListSample) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(
                id = data.image,
            ),
            contentDescription = stringResource(R.string.snippet_image_content_description),
        )
        BpkText(
            text = data.description,
        )
    }
}

data class CardListSample(
    @DrawableRes val image: Int,
    val description: String,
)

private fun cardListSamples() = listOf(
    CardListSample(
        image = R.drawable.carousel_placeholder_1,
        description = "Title 1",
    ),
    CardListSample(
        image = R.drawable.carousel_placeholder_2,
        description = "Title 2",
    ),
    CardListSample(
        image = R.drawable.carousel_placeholder_3,
        description = "Title 3",
    ),
    CardListSample(
        image = R.drawable.carousel_placeholder_4,
        description = "Title 4",
    ),
)
