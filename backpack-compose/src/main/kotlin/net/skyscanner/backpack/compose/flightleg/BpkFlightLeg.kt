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

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import net.skyscanner.backpack.compose.flightleg.internal.BpkAccessoryTitleDetails
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Aircraft
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BpkFlightLeg(
  departureArrivalTime: String,
  description: AnnotatedString,
  stopsInfo: AnnotatedString,
  duration: String,
  modifier: Modifier = Modifier,
  nextDayArrival: String? = null,
  operatedBy: String? = null,
  warning: String? = null,
  contentDescription: String? = null,
  carrierLogo: @Composable (BoxScope.() -> Unit)? = null
) {
  Box(modifier = modifier
    .fillMaxWidth()
    .semantics(mergeDescendants = true) {
      if (contentDescription == null) {
        // prevent screen readers from reading this allowing a broader
        // content description to be set on the parent node
        invisibleToUser()
      } else {
        this.contentDescription = contentDescription
      }
    }
  ) {
    BpkAccessoryTitleDetails(
      modifier = Modifier.fillMaxWidth(fraction = 0.67f),
      title = buildAnnotatedString {
        append(departureArrivalTime)
        nextDayArrival?.let {
          withStyle(
            SpanStyle(
              color = BpkTheme.colors.textPrimary,
              fontSize = BpkTheme.typography.label3.fontSize,
              baselineShift = BaselineShift.Superscript
            )
          ) {
            append(it)
          }
        }
      },
      details = mutableListOf<AnnotatedString>().apply {
        add(description)
        operatedBy?.let {
          add(AnnotatedString(it))
        }
        warning?.let {
          add(
            buildAnnotatedString {
              withStyle(
                SpanStyle(
                  color = BpkTheme.colors.textError,
                  fontSize = BpkTheme.typography.caption.fontSize
                )
              ) {
                append(it)
              }
            }
          )
        }
      },
      accessoryView = carrierLogo
    )
    BpkAccessoryTitleDetails(
      modifier = Modifier.align(Alignment.TopEnd),
      title = stopsInfo,
      textAlignment = Alignment.End,
      titleStyle = BpkTheme.typography.label3,
      details = listOf(
        AnnotatedString(duration)
      ),
    )
  }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, backgroundColor = 0xffffff)
@Composable
private fun LightModeSimpleDirect() {
  BpkFlightLeg(
    departureArrivalTime = "19:50 - 22:45",
    description = AnnotatedString("LHR-SIN,SwissAir"),
    stopsInfo = AnnotatedString("Direct"),
    duration = "7h 55m",
    carrierLogo = {
      Box(
        modifier = Modifier.padding(top = BpkSpacing.Sm)
      ) {
        BpkIcon(icon = BpkIcon.Aircraft, contentDescription = null)
      }
    }
  )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, backgroundColor = 0xffffff)
@Composable
private fun LightModeComplete() {
  BpkFlightLeg(
    departureArrivalTime = "19:50 - 22:45",
    description = buildAnnotatedString {
      withStyle(
        SpanStyle(
          background = BpkTheme.colors.statusDangerFill,
          color = BpkTheme.colors.textOnLight,
        )
      ) {
        append("LHR")
      }
      append("-SIN, SwissAir")
    },
    stopsInfo = buildAnnotatedString {
      withStyle(
        SpanStyle(
          color = BpkTheme.colors.textError,
        )
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
        modifier = Modifier.padding(top = BpkSpacing.Sm)
      ) {
        BpkIcon(icon = BpkIcon.Aircraft, contentDescription = null)
      }
    }
  )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, backgroundColor = 0x00000)
@Composable
private fun DarkModeSimpleDirect() {
  BpkFlightLeg(
    departureArrivalTime = "19:50 - 22:45",
    description = AnnotatedString("LHR-SIN,SwissAir"),
    stopsInfo = AnnotatedString("Direct"),
    duration = "7h 55m",
    carrierLogo = {
      Box(
        modifier = Modifier.padding(top = BpkSpacing.Sm)
      ) {
        BpkIcon(icon = BpkIcon.Aircraft, contentDescription = null)
      }
    }
  )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, backgroundColor = 0x00000)
@Composable
private fun DarkModeComplete() {
  BpkFlightLeg(
    departureArrivalTime = "19:50 - 22:45",
    description = buildAnnotatedString {
      withStyle(
        SpanStyle(
          background = BpkTheme.colors.statusDangerFill,
          color = BpkTheme.colors.textOnLight,
        )
      ) {
        append("LHR")
      }
      append("-SIN, SwissAir")
    },
    stopsInfo = buildAnnotatedString {
      withStyle(
        SpanStyle(
          color = BpkTheme.colors.textError,
        )
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
        modifier = Modifier.padding(top = BpkSpacing.Sm)
      ) {
        BpkIcon(icon = BpkIcon.Aircraft, contentDescription = null)
      }
    }
  )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, backgroundColor = 0xffffff)
@Composable
private fun LightModeRTL() {
  CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
    BpkFlightLeg(
      departureArrivalTime = "۲۲:۴۵ - ۱۹:۵۰",
      description = AnnotatedString("هیترو-سوییس,سوییس ایر"),
      operatedBy = "توسط وستجت انجام می‌شود",
      warning = "تغییر فرودگاه در لندن",
      carrierLogo = {
        Box(
          modifier = Modifier.padding(top = BpkSpacing.Sm)
        ) {
          BpkIcon(icon = BpkIcon.Aircraft, contentDescription = null)
        }
      },
      stopsInfo = buildAnnotatedString {
        withStyle(
          SpanStyle(
            color = BpkTheme.colors.textError,
          )
        ) {
          append("۲ توقف")
        }
      },
      duration = "۷ ساعت و ۵۵ دقیقه"
    )
  }
}
