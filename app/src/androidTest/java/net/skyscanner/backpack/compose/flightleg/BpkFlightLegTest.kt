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

package net.skyscanner.backpack.compose.flightleg

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Aircraft
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import org.junit.Test

class BpkFlightLegTest : BpkSnapshotTest() {

  @Test
  fun simpleLegTest() = snap {
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

  @Test
  fun completeLegTest() = snap {
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
}
