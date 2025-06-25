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

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Airports
import net.skyscanner.backpack.compose.tokens.Heart
import org.junit.Test

class BpkPriceMapMarkerV2Test : BpkSnapshotTest() {

    @Test
    fun unselected() = snap {
        PriceMarkerV2Layout(title = "£100", status = BpkPriceMarkerV2Status.Unselected)
    }

    @Test
    fun selected() = snap {
        PriceMarkerV2Layout(title = "£100", status = BpkPriceMarkerV2Status.Selected)
    }

    @Test
    fun previousSelected() = snap {
        PriceMarkerV2Layout(title = "£100", status = BpkPriceMarkerV2Status.PreviousSelected)
    }

    @Test
    fun unselectedWithIcon() = snap {
        PriceMarkerV2Layout(title = "£100", status = BpkPriceMarkerV2Status.Unselected, prefixIcon = BpkIcon.Airports)
    }

    @Test
    fun unselectedWithHeartIcon() = snap {
        PriceMarkerV2Layout(title = "£100", status = BpkPriceMarkerV2Status.Unselected, prefixIcon = BpkIcon.Heart)
    }

    @Test
    fun selectedWithIcon() = snap {
        PriceMarkerV2Layout(title = "£100", status = BpkPriceMarkerV2Status.Selected, prefixIcon = BpkIcon.Heart)
    }

    @Test
    fun previousSelectedWithIcon() = snap {
        PriceMarkerV2Layout(title = "£100", status = BpkPriceMarkerV2Status.PreviousSelected, prefixIcon = BpkIcon.Heart)
    }
}
