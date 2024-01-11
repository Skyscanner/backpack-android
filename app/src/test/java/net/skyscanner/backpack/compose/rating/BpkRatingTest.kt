/*
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
 *
 */

package net.skyscanner.backpack.compose.rating

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.compose.BpkRatingCustomContentSample
import net.skyscanner.backpack.demo.compose.BpkRatingDefaultSample
import net.skyscanner.backpack.demo.compose.BpkRatingLargeCustomContentNoSubtitleSample
import net.skyscanner.backpack.demo.compose.BpkRatingLargeCustomContentSample
import net.skyscanner.backpack.demo.compose.BpkRatingLargeNoScaleSample
import net.skyscanner.backpack.demo.compose.BpkRatingLargeSample
import net.skyscanner.backpack.demo.compose.BpkRatingLargeTitleOnlySample
import net.skyscanner.backpack.demo.compose.BpkRatingLargeNoTitleSample
import net.skyscanner.backpack.demo.compose.BpkRatingNoTitleSample
import net.skyscanner.backpack.demo.compose.BpkRatingNoScaleSample
import net.skyscanner.backpack.demo.compose.BpkRatingTitleOnlySample
import org.junit.Test

class BpkRatingTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        BpkRatingDefaultSample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun titleOnly() {
        snap {
            BpkRatingTitleOnlySample()
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun noTitle() {
        snap {
            BpkRatingNoTitleSample()
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun noScale() {
        snap {
            BpkRatingNoScaleSample()
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun customContent() {
        snap {
            BpkRatingCustomContentSample()
        }
    }

    @Test
    fun large() = snap {
        BpkRatingLargeSample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun largeTitleOnly() {
        snap {
            BpkRatingLargeTitleOnlySample()
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun largeNoTitle() {
        snap {
            BpkRatingLargeNoTitleSample()
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun largeNoScale() {
        snap {
            BpkRatingLargeNoScaleSample()
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun largeCustomContent() {
        snap {
            BpkRatingLargeCustomContentSample()
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun largeCustomContentMoSubtitle() {
        snap {
            BpkRatingLargeCustomContentNoSubtitleSample()
        }
    }
}
