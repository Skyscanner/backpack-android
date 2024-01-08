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

package net.skyscanner.backpack.compose.card

import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.FocusableCardExample
import net.skyscanner.backpack.demo.compose.LargeCornersCardExample
import net.skyscanner.backpack.demo.compose.NoElevationCardExample
import net.skyscanner.backpack.demo.compose.NoPaddingCardExample
import net.skyscanner.backpack.demo.compose.NonClickableCardExample
import net.skyscanner.backpack.demo.compose.SmallCornersCardExample
import org.junit.Test

class BpkCardTest : BpkSnapshotTest() {

    @Test
    fun smallCorner() = snap {
        SmallCornersCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun largeCorner() = snap {
        LargeCornersCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun noPadding() = snap {
        NoPaddingCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun unfocused() = snap {
        NonClickableCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun focused() = snap {
        FocusableCardExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun noElevation() = snap {
        NoElevationCardExample()
    }
}
