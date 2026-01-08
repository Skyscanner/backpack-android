/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.insetbanner

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.SponsoredInsetBannerStoryOnDarkWithImage
import net.skyscanner.backpack.demo.compose.SponsoredInsetBannerStoryOnDarkWithoutImage
import net.skyscanner.backpack.demo.compose.SponsoredInsetBannerStoryOnDarkWithoutSubHeadline
import net.skyscanner.backpack.demo.compose.SponsoredInsetBannerStoryOnDarkWithoutTitle
import net.skyscanner.backpack.demo.compose.SponsoredInsetBannerStoryOnDarkWithoutTitleNorSubHeadline
import net.skyscanner.backpack.demo.compose.SponsoredInsetBannerStoryOnLightWithImage
import net.skyscanner.backpack.demo.compose.SponsoredInsetBannerStoryOnLightWithoutImage
import net.skyscanner.backpack.demo.compose.SponsoredInsetBannerStoryOnLightWithoutSubHeadline
import net.skyscanner.backpack.demo.compose.SponsoredInsetBannerStoryOnLightWithoutTitle
import net.skyscanner.backpack.demo.compose.SponsoredInsetBannerStoryOnLightWithoutTitleNorSubHeadline
import org.junit.Test

class BpkSponsoredInsetBannerTest : BpkSnapshotTest() {

    @Test
    fun sponsoredInsetBannerStoryOnLightWithImage() = snap {
        SponsoredInsetBannerStoryOnLightWithImage()
    }

    @Test
    fun sponsoredInsetBannerStoryOnLightWithoutImage() = snap {
        SponsoredInsetBannerStoryOnLightWithoutImage()
    }

    @Test
    fun sponsoredInsetBannerStoryOnLightWithoutSubHeadline() = snap {
        SponsoredInsetBannerStoryOnLightWithoutSubHeadline()
    }

    @Test
    fun sponsoredInsetBannerStoryOnLightWithoutTitle() = snap {
        SponsoredInsetBannerStoryOnLightWithoutTitle()
    }

    @Test
    fun sponsoredInsetBannerStoryOnLightWithoutTitleNorSubHeadline() = snap {
        SponsoredInsetBannerStoryOnLightWithoutTitleNorSubHeadline()
    }

    @Test
    fun sponsoredInsetBannerStoryOnDarkWithImage() = snap {
        SponsoredInsetBannerStoryOnDarkWithImage()
    }

    @Test
    fun sponsoredInsetBannerStoryOnDarkWithoutImage() = snap {
        SponsoredInsetBannerStoryOnDarkWithoutImage()
    }

    @Test
    fun sponsoredInsetBannerStoryOnDarkWithoutSubHeadline() = snap {
        SponsoredInsetBannerStoryOnDarkWithoutSubHeadline()
    }

    @Test
    fun sponsoredInsetBannerStoryOnDarkWithoutTitle() = snap {
        SponsoredInsetBannerStoryOnDarkWithoutTitle()
    }

    @Test
    fun sponsoredInsetBannerStoryOnDarkWithoutTitleNorSubHeadline() = snap {
        SponsoredInsetBannerStoryOnDarkWithoutTitleNorSubHeadline()
    }
}
