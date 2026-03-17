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

package net.skyscanner.backpack.compose.swapbutton

import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.CanvasContrastSwapButtonExample
import net.skyscanner.backpack.demo.compose.CanvasDefaultSwapButtonExample
import net.skyscanner.backpack.demo.compose.SurfaceContrastSwapButtonExample
import org.junit.Test

@Variants(BpkTestVariant.Default)
class BpkSwapButtonTest : BpkSnapshotTest() {

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun canvasDefault() = snap {
        CanvasDefaultSwapButtonExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun canvasContrast() = snap {
        CanvasContrastSwapButtonExample()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun surfaceContrast() = snap {
        SurfaceContrastSwapButtonExample()
    }
}
