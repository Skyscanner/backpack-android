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

package net.skyscanner.backpack.compose.horizontalnav

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.compose.HorizontalNav_LargeWithIcon_Sample
import net.skyscanner.backpack.demo.compose.HorizontalNav_Large_Sample
import net.skyscanner.backpack.demo.compose.HorizontalNav_SmallWithIcon_Sample
import net.skyscanner.backpack.demo.compose.HorizontalNav_Small_Sample
import org.junit.Test

class BpkHorizontalNavTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        HorizontalNav_Large_Sample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun small() {
        snap {
            HorizontalNav_Small_Sample()
        }
    }

    @Test
    fun smallWithIcon() {
        snap {
            HorizontalNav_SmallWithIcon_Sample()
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun large() {
        snap {
            HorizontalNav_Large_Sample()
        }
    }

    @Test
    fun largeWithIcon() {
        snap {
            HorizontalNav_LargeWithIcon_Sample()
        }
    }
}
