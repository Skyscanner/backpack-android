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

package net.skyscanner.backpack.compose.insetbanner

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.InsetBannerStoryOnDark
import net.skyscanner.backpack.demo.compose.InsetBannerStoryOnLight
import net.skyscanner.backpack.demo.compose.InsetBannerStoryWithoutCTA
import net.skyscanner.backpack.demo.compose.InsetBannerStoryWithoutSubHeadline
import net.skyscanner.backpack.demo.compose.InsetBannerStoryWithoutTitle
import net.skyscanner.backpack.demo.compose.InsetBannerStoryWithoutTitleAndSubHeadline
import org.junit.Test

class BpkInsetBannerTest : BpkSnapshotTest() {

    @Test
    fun defaultOnDark() = snap {
        InsetBannerStoryOnDark()
    }

    @Test
    fun defaultOnLight() = snap {
        InsetBannerStoryOnLight()
    }

    @Test
    fun insetBannerWithoutSubheadline() = snap {
        InsetBannerStoryWithoutSubHeadline()
    }

    @Test
    fun insetBannerWithoutTitle() = snap {
        InsetBannerStoryWithoutTitle()
    }

    @Test
    fun insetBannerWithoutTitleAndSubheadline() = snap {
        InsetBannerStoryWithoutTitleAndSubHeadline()
    }

    @Test
    fun insetBannerWithoutCallToAction() = snap {
        InsetBannerStoryWithoutCTA()
    }
}
