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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.flightleg.BpkFlightLeg
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Aircraft
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R

@Composable
fun FlightLegStory(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
  ) {
    BpkText(text = stringResource(id = R.string.flight_leg_basic_example))
    BasicFlightLegSample()
    Spacer(modifier = Modifier.height(BpkSpacing.Lg))
    BpkText(text = stringResource(id = R.string.flight_leg_complete_example))
    CompleteFlightLegSample()
  }
}

@Composable
@Preview(showBackground = true)
fun BasicFlightLegSample() {
  BpkFlightLeg(
    modifier = Modifier.background(BpkTheme.colors.canvas),
    departureArrivalTime = "19:50 - 22:45",
    description = AnnotatedString("LHR-SIN,SwissAir"),
    stopsInfo = AnnotatedString("Direct"),
    duration = "7h 55m",
    carrierLogo = {
      Box(
        modifier = Modifier.padding(top = BpkSpacing.Sm),
      ) {
        BpkIcon(icon = BpkIcon.Aircraft, contentDescription = null)
      }
    },
  )
}

@Composable
@Preview(showBackground = true)
fun CompleteFlightLegSample() {
  BpkFlightLeg(
    departureArrivalTime = "19:50 - 22:45",
    description = buildAnnotatedString {
      withStyle(
        SpanStyle(
          background = BpkTheme.colors.statusDangerFill,
          color = BpkTheme.colors.textOnLight,
        ),
      ) {
        append("LHR")
      }
      append("-SIN, SwissAir")
    },
    stopsInfo = buildAnnotatedString {
      withStyle(
        SpanStyle(
          color = BpkTheme.colors.textError,
        ),
      ) {
        append("2 stops")
      }
    },
    duration = "7h 55m",
    nextDayArrival = "+1",
    operatedBy = "Operated by Ryanair",
    warning = "Change airports in London",
    carrierLogo = {
      Box(
        modifier = Modifier.padding(top = BpkSpacing.Sm),
      ) {
        BpkIcon(icon = BpkIcon.Aircraft, contentDescription = null)
      }
    },
  )
}
