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

package net.skyscanner.backpack.compose.flightleg

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import net.skyscanner.backpack.compose.flightleg.internal.BpkFlightLegImpl
import net.skyscanner.backpack.compose.theme.BpkTheme

@Composable
fun BpkFlightLeg(
    departureArrivalTime: String,
    /**
     * Please use `bpkAirportHighlightStyle()` on your `SpanStyle` if you need to highlight an airport
     * to indicate different arrival / departure airports between the legs. See the docs for usage example
     */
    description: AnnotatedString,
    stopsInfo: String,
    duration: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    nextDayArrival: String? = null,
    highlightStopsInfo: Boolean = false,
    operatedBy: String? = null,
    warning: String? = null,
    carrierLogoContent: @Composable (BoxScope.() -> Unit)? = null,
) {
    BpkFlightLegImpl(
        departureArrivalTime = departureArrivalTime,
        description = description,
        stopsInfo = stopsInfo,
        duration = duration,
        contentDescription = contentDescription,
        modifier = modifier,
        nextDayArrival = nextDayArrival,
        highlightStopsInfo = highlightStopsInfo,
        operatedBy = operatedBy,
        warning = warning,
        carrierLogoContent = carrierLogoContent,
    )
}

@Composable
fun bpkAirportHighlightStyle() = SpanStyle(
    background = BpkTheme.colors.statusDangerFill,
    color = BpkTheme.colors.textOnLight,
)
