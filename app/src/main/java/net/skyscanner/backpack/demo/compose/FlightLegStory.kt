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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import net.skyscanner.backpack.compose.flightleg.BpkFlightLeg
import net.skyscanner.backpack.compose.flightleg.bpkAirportHighlightStyle
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Aircraft
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.FlightLegComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@FlightLegComponent
@ComposeStory
fun FlightLegStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
    ) {
        BpkText(text = stringResource(id = R.string.flight_leg_basic_example), color = BpkTheme.colors.textPrimary)
        BasicFlightLegSample()

        Spacer(modifier = Modifier.height(BpkSpacing.Lg))

        BpkText(text = stringResource(id = R.string.flight_leg_complete_long_stops_example), color = BpkTheme.colors.textPrimary)
        CompleteFlightLegLongStopsSample()

        Spacer(modifier = Modifier.height(BpkSpacing.Lg))

        BpkText(text = stringResource(id = R.string.flight_leg_complete_short_stops_example), color = BpkTheme.colors.textPrimary)
        CompleteFlightLegShortStopsSample()
    }
}

@Composable
internal fun BasicFlightLegSample(modifier: Modifier = Modifier) {
    BpkFlightLeg(
        modifier = modifier,
        departureArrivalTime = "19:50 - 22:45",
        description = AnnotatedString("LHR-SIN, SwissAir"),
        stopsInfo = "Direct",
        duration = "7h 55m",
        contentDescription = null,
        carrierLogoContent = {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.bpkCanvas)),
                painter = painterResource(id = R.drawable.sample_icon),
                contentDescription = null,
            )
        },
    )
}

@Composable
internal fun CompleteFlightLegLongStopsSample(modifier: Modifier = Modifier) {
    BpkFlightLeg(
        modifier = modifier,
        departureArrivalTime = "19:50 - 22:45",
        description = buildAnnotatedString {
            withStyle(
                bpkAirportHighlightStyle(),
            ) {
                append("LHR")
            }
            append("-SIN, SwissAir")
        },
        stopsInfo = "2 Zwischenlandungen",
        highlightStopsInfo = true,
        duration = "7h 55m",
        nextDayArrival = "+1",
        operatedBy = "Operated by Ryanair, partly operated by WestJet",
        warning = "Change airports in London. Very short layovers.",
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

@Composable
internal fun CompleteFlightLegShortStopsSample(modifier: Modifier = Modifier) {
    BpkFlightLeg(
        modifier = modifier,
        departureArrivalTime = "19:50 - 22:45",
        description = buildAnnotatedString {
            withStyle(
                bpkAirportHighlightStyle(),
            ) {
                append("LHR")
            }
            append("-SIN, SwissAir")
        },
        stopsInfo = "2 Stops",
        highlightStopsInfo = true,
        duration = "7h 55m",
        nextDayArrival = "+1",
        operatedBy = "Operated by Ryanair, partly operated by WestJet",
        warning = "Change airports in London. Very short layovers.",
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
