/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.cardcarousel

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.CardCarouselMultiCardSample
import net.skyscanner.backpack.demo.compose.CardCarouselSingleCardSample
import org.junit.Test

class BpkCardCarouselTest : BpkSnapshotTest() {

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun singleCard() = snap {
        CardCarouselSingleCardSample(
            imageAspectRatio = 0.85f,
            modifier = Modifier.aspectRatio(ratio = 0.75f),
        )
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun multipleCard() = snap {
        CardCarouselMultiCardSample(
            imageAspectRatio = 0.85f,
            modifier = Modifier.aspectRatio(ratio = 0.75f),
        )
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun setCurrentCard() = snap {
        CardCarouselMultiCardSample(
            initialImage = 1,
            imageAspectRatio = 0.85f,
            modifier = Modifier.aspectRatio(ratio = 0.75f),
        )
    }
}
