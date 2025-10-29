/*
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
 *
 */

package net.skyscanner.backpack.compose.bottomnav

import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.BottomNavSample
import org.junit.Test

class BpkBottomNavTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        BottomNavSample()
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun selectionUpdated() = snap {
        BottomNavSample(defaultItemId = 2)
    }

    @Test
    fun plainPainterNoTintApplied() = snap {
        BottomNavSample(defaultItemId = 2, usePlainPainter = true)
    }
}
