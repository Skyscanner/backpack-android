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

package net.skyscanner.backpack.compose.sponsoredbanner

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.SponsoredBannerStoryOnDark
import net.skyscanner.backpack.demo.compose.SponsoredBannerStoryOnLight
import net.skyscanner.backpack.demo.compose.SponsoredBannerStoryWithoutCTA
import net.skyscanner.backpack.demo.compose.SponsoredBannerStoryWithoutSubHeadline
import net.skyscanner.backpack.demo.compose.SponsoredBannerStoryWithoutTitle
import net.skyscanner.backpack.demo.compose.SponsoredBannerStoryWithoutTitleAndSubHeaderAndCallToAction
import net.skyscanner.backpack.demo.compose.SponsoredBannerStoryWithoutTitleAndSubHeadline
import org.junit.Test

class BpkSponsoredBannerTest : BpkSnapshotTest() {

    @Test
    fun defaultOnDark() = snap {
        SponsoredBannerStoryOnDark()
    }

    @Test
    fun defaultOnLight() = snap {
        SponsoredBannerStoryOnLight()
    }

    @Test
    fun sponsoredBannerWithoutSubheadline() = snap {
        SponsoredBannerStoryWithoutSubHeadline()
    }

    @Test
    fun sponsoredBannerWithoutTitle() = snap {
        SponsoredBannerStoryWithoutTitle()
    }

    @Test
    fun sponsoredBannerWithoutTitleAndSubheadline() = snap {
        SponsoredBannerStoryWithoutTitleAndSubHeadline()
    }

    @Test
    fun sponsoredBannerWithoutCallToAction() = snap {
        SponsoredBannerStoryWithoutCTA()
    }

    @Test
    fun sponsoredBannerWithoutTitleAndSubHeadlineAndCallToAction() = snap {
        SponsoredBannerStoryWithoutTitleAndSubHeaderAndCallToAction()
    }
}
