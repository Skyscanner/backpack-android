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

package net.skyscanner.backpack.compose.map

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkPriceMapMarkerTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        PriceMarkerLayout(title = "£100", status = BpkPriceMarkerStatus.Default)
    }

    @Test
    fun focused() = snap {
        PriceMarkerLayout(title = "£100", status = BpkPriceMarkerStatus.Focused)
    }

    @Test
    fun viewed() = snap {
        PriceMarkerLayout(title = "£100", status = BpkPriceMarkerStatus.Viewed)
    }

    @Test
    fun disabled() = snap(background = { BpkTheme.colors.canvasContrast }) {
        PriceMarkerLayout(title = "£100", status = BpkPriceMarkerStatus.Disabled)
    }
}
