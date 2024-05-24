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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.snippet.BpkSnippet
import net.skyscanner.backpack.compose.snippet.ImageOrientation
import net.skyscanner.backpack.demo.R
import org.junit.Test

class BpkCardListTest : BpkSnapshotTest() {

    private val dataList = cardListSamples()

    @Test
    fun defaultRail() = snap {
        BpkCardList(
            title = "Must-visit spots",
            description = "Check out these world-famous destinations perfect for visiting in spring.",
            layout = BpkCardListLayout.Rail(),
            modifier = Modifier,
            dataList = dataList,
        ) { position ->
            CardLayout(dataList[position])
        }
    }

    @Test
    fun defaultRailWithHeaderButton() = snap {
        BpkCardList(
            title = "Must-visit spots",
            description = "Check out these world-famous destinations perfect for visiting in spring.",
            layout = BpkCardListLayout.Rail(
                button = BpkCardListButtonAccessory.SectionHeaderButton(
                    text = "Text",
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
        modifier = Modifier
            .width(281.dp),
        imageOrientation = ImageOrientation.Landscape,
        headline = data.headline,
        bodyText = data.bodyText,
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
    val headline: String,
    val bodyText: String? = null,
)

private fun cardListSamples() = listOf(
    CardListSample(
        image = R.drawable.amsterdam,
        headline = "Amsterdam",
        bodyText = "Fall in love with this artistic metropolis by boat or foot.",
    ),
    CardListSample(
        image = R.drawable.london_towerbridge,
        headline = "London",
        bodyText = "Watch the city come alive in bloom as the warmer climes approach.",
    ),
    CardListSample(
        image = R.drawable.dublin,
        headline = "Dublin",
        bodyText = "Immerse yourself in the rich history and culture of the Irish capital.",
    ),
    CardListSample(
        image = R.drawable.paris,
        headline = "Paris",
    ),
    CardListSample(
        image = R.drawable.mallorca,
        headline = "Mallorca",
    ),
    CardListSample(
        image = R.drawable.alicante,
        headline = "Alicante",
    ),
    CardListSample(
        image = R.drawable.barcelona,
        headline = "Barcelona",
    ),
    CardListSample(
        image = R.drawable.berlin,
        headline = "Berlin",
    ),
    CardListSample(
        image = R.drawable.london_saintpancrasstation,
        headline = "London",
        bodyText = "Watch the city come alive in bloom as the warmer climes approach.",
    ),
)
