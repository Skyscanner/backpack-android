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

package net.skyscanner.backpack.compose.flightleg

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.BasicFlightLegSample
import net.skyscanner.backpack.demo.compose.CompleteFlightLegLongStopsSample
import net.skyscanner.backpack.demo.compose.CompleteFlightLegShortStopsSample
import org.junit.Test

class BpkFlightLegTest : BpkSnapshotTest() {

    @Test
    fun simpleLegTest() = snap {
        BasicFlightLegSample()
    }

    @Test
    fun completeLegLongStopsTest() = snap {
        CompleteFlightLegLongStopsSample()
    }

    @Test
    fun completeLegShortStopsTest() = snap {
        CompleteFlightLegShortStopsSample()
    }
}
