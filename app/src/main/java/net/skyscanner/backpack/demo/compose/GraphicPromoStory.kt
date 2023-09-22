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

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import net.skyscanner.backpack.compose.graphicpromotion.BpkGraphicPromo
import net.skyscanner.backpack.compose.graphicpromotion.Sponsor
import net.skyscanner.backpack.compose.graphicpromotion.VerticalAlignment
import net.skyscanner.backpack.compose.overlay.BpkOverlayType
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.GraphicPromoComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@GraphicPromoComponent
@ComposeStory
internal fun GraphicPromoStoryDefault(modifier: Modifier = Modifier) {
    Column(modifier) {
        BpkGraphicPromoSample()
    }
}

@Composable
@GraphicPromoComponent
@ComposeStory("Top align with text")
internal fun GraphicPromoStoryAlignmentTopWithKicker() {
    BpkGraphicPromoSample(
        kicker = "Travel tips",
        subHeadline = "How to complete the climb in 3 days",
    )
}

@Composable
@GraphicPromoComponent
@ComposeStory("Bottom align with text")
internal fun GraphicPromoStoryAlignmentBottomWithText() {
    BpkGraphicPromoSample(
        kicker = "Travel tips",
        subHeadline = "How to complete the climb in 3 days",
        verticalAlignment = VerticalAlignment.Bottom,
    )
}

@Composable
@GraphicPromoComponent
@ComposeStory("Top align Sponsored")
internal fun GraphicPromoStoryAlignmentTopSponsored() {
    BpkGraphicPromoSample(
        kicker = "Travel tips",
        subHeadline = "How to complete the climb in 3 days",
        sponsor = Sponsor(
            accessibilityLabel = "Sponsored",
            logo = "https://images.kiwi.com/airlines/64/FR.png",
            title = "Sponsored",
        ),
    )
}

@Composable
@GraphicPromoComponent
@ComposeStory("Bottom align Sponsored")
internal fun GraphicPromoStoryAlignmentBottomSponsored() {
    BpkGraphicPromoSample(
        kicker = "Travel tips",
        subHeadline = "How to complete the climb in 3 days",
        verticalAlignment = VerticalAlignment.Bottom,
        sponsor = Sponsor(
            accessibilityLabel = "Sponsored",
            logo = "https://images.kiwi.com/airlines/64/FR.png",
            title = "Sponsored",
        ),
    )
}

@Composable
internal fun BpkGraphicPromoSample(
    modifier: Modifier = Modifier,
    headline: String = "Three Parks Challenge",
    subHeadline: String? = null,
    kicker: String? = null,
    verticalAlignment: VerticalAlignment = VerticalAlignment.Top,
    overlayType: BpkOverlayType? = null,
    sponsor: Sponsor? = null,
) {
    BpkGraphicPromo(
        modifier = modifier
            .fillMaxSize()
            .padding(BpkSpacing.Base),
        kicker = kicker,
        headline = headline,
        subHeadline = subHeadline,
        verticalAlignment = verticalAlignment,
        overlayType = overlayType,
        sponsor = sponsor,
        image = {
            Image(
                modifier = Modifier.matchParentSize(),
                painter = painterResource(
                    id = R.drawable.graphic_promo,
                ),
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
            )
        }, sponsorLogo = {
            if (sponsor != null) {
                Image(
                    painter = painterResource(
                        id = R.drawable.skyland,
                    ),
                    contentDescription = "Image",
                    contentScale = ContentScale.Fit,
                )
            }
        },
        tapAction = {
            Log.d("BpkGraphicPromo", "Tap on graphic promo")
        },
    )
}
