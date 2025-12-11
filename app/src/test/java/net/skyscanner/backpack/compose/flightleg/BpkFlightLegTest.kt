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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Aircraft
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.compose.BasicFlightLegSample
import net.skyscanner.backpack.demo.compose.CompleteFlightLegLongStopsSample
import net.skyscanner.backpack.demo.compose.CompleteFlightLegShortStopsSample
import org.junit.Test
import org.robolectric.RuntimeEnvironment

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

    @Test
    fun carrierLogoFillsWholeSpace() = snap {
        BpkFlightLeg(
            departureArrivalTime = "19:50 - 22:45",
            description = AnnotatedString("LHR-SIN, SwissAir"),
            stopsInfo = "Direct",
            duration = "7h 55m",
            contentDescription = null,
            carrierLogoContent = {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BpkTheme.colors.canvas),
                    painter = painterResource(id = R.drawable.sample_icon),
                    contentDescription = null,
                )
            },
        )
    }

    @Test
    fun carrierLogoConstrained() = snap {
        BpkFlightLeg(
            departureArrivalTime = "19:50 - 22:45",
            description = AnnotatedString("LHR-SIN, SwissAir"),
            stopsInfo = "Direct",
            duration = "7h 55m",
            contentDescription = null,
            carrierLogoContent = {
                BpkIcon(
                    icon = BpkIcon.Aircraft,
                    tint = BpkTheme.colors.textOnLight,
                    contentDescription = null,
                )
            },
        )
    }

    @Test
    fun noCarrierLogo() = snap {
        BpkFlightLeg(
            departureArrivalTime = "19:50 - 22:45",
            description = AnnotatedString("LHR-SIN, SwissAir"),
            stopsInfo = "Direct",
            duration = "7h 55m",
            contentDescription = null,
            carrierLogoContent = null,
        )
    }

    @Test
    fun simpleLegTest_landscape() {
        RuntimeEnvironment.setQualifiers("+land")
        snap {
            BasicFlightLegSample()
        }
    }

    @Test
    fun carrierLogoFillsWholeSpace_landscape() {
        RuntimeEnvironment.setQualifiers("+land")
        snap {
            BpkFlightLeg(
                departureArrivalTime = "19:50 - 22:45",
                description = AnnotatedString("LHR-SIN, SwissAir"),
                stopsInfo = "Direct",
                duration = "7h 55m",
                contentDescription = null,
                carrierLogoContent = {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(BpkTheme.colors.canvas),
                        painter = painterResource(id = R.drawable.sample_icon),
                        contentDescription = null,
                    )
                },
            )
        }
    }
}
