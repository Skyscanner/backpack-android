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

package net.skyscanner.backpack.compose.graphicpromotion

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.overlay.BpkOverlayType
import net.skyscanner.backpack.demo.compose.BpkGraphicPromoSample
import net.skyscanner.backpack.demo.compose.GraphicPromoStoryDefault
import org.junit.Test

class BpkGraphicPromoTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        GraphicPromoStoryDefault()
    }

    @Test
    fun defaultWithOverlay() = snap {
        BpkGraphicPromoSample(
            headline = "Three Parks Challenge",
            overlayType = BpkOverlayType.SolidHigh,
        )
    }

    @Test
    fun topAlignedKickerAvailable() = snap {
        BpkGraphicPromoSample(
            subHeadline = "How to complete the climb in 3 days",
        )
    }

    @Test
    fun topAlignedKickerSubHeadlineAvailable() = snap {
        BpkGraphicPromoSample(
            kicker = "Travel tips",
            subHeadline = "How to complete the climb in 3 days",
        )
    }

    @Test
    fun topAlignedKickerSubHeadlineSponsoredAvailable() = snap {
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

    @Test
    fun bottomAlignedKickerAvailable() = snap {
        BpkGraphicPromoSample(
            subHeadline = "How to complete the climb in 3 days",
            bpkGraphicPromoVerticalAlignment = BpkGraphicPromoVerticalAlignment.Bottom,
        )
    }

    @Test
    fun bottomAlignedKickerSubHeadlineAvailable() = snap {
        BpkGraphicPromoSample(
            kicker = "Travel tips",
            subHeadline = "How to complete the climb in 3 days",
            bpkGraphicPromoVerticalAlignment = BpkGraphicPromoVerticalAlignment.Bottom,
        )
    }

    @Test
    fun bottomAlignedKickerSubHeadlineSponsoredAvailable() = snap {
        BpkGraphicPromoSample(
            kicker = "Travel tips",
            subHeadline = "How to complete the climb in 3 days",
            sponsor = Sponsor(
                accessibilityLabel = "Sponsored",
                logo = "https://images.kiwi.com/airlines/64/FR.png",
                title = "Sponsored",
            ),
            bpkGraphicPromoVerticalAlignment = BpkGraphicPromoVerticalAlignment.Bottom,
        )
    }
}
