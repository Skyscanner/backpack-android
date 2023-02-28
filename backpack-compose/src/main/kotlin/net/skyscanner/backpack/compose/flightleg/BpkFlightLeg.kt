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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
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
  carrierLogoContent: @Composable (BoxScope.() -> Unit)? = null,
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .semantics(mergeDescendants = true) {
        if (contentDescription == null) {
          // prevent screen readers from reading this allowing a broader
          // content description to be set on the parent node
          invisibleToUser()
        } else {
          this.contentDescription = contentDescription
        }
      },
  ) {
    Row(modifier = Modifier.fillMaxWidth()) {
      carrierLogoContent?.let {
        Box(content = it)
        Spacer(modifier = Modifier.width(BpkSpacing.Base))
      }
      Column(
        horizontalAlignment = Alignment.Start,
      ) {
        BpkText(
          text = buildAnnotatedString {
            append(departureArrivalTime)
            nextDayArrival?.let {
              withStyle(
                SpanStyle(
                  color = BpkTheme.colors.textPrimary,
                  fontSize = BpkTheme.typography.label3.fontSize,
                  baselineShift = BaselineShift.Superscript,
                ),
              ) {
                append(it)
              }
            }
          },
          color = BpkTheme.colors.textPrimary,
        )
        mutableListOf<AnnotatedString>().apply {
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
                    fontSize = BpkTheme.typography.caption.fontSize,
                  ),
                ) {
                  append(it)
                }
              },
            )
          }
        }.let { details ->
          details.map {
            BpkText(
              text = it,
              style = BpkTheme.typography.caption,
              color = BpkTheme.colors.textSecondary,
            )
          }
        }
      }
      Spacer(modifier = Modifier.weight(1f))
      Column(
        horizontalAlignment = Alignment.End,
      ) {
        BpkText(
          text = stopsInfo,
          style = BpkTheme.typography.label3,
          color = BpkTheme.colors.textPrimary,
        )
        BpkText(
          text = duration,
          style = BpkTheme.typography.caption,
          color = BpkTheme.colors.textSecondary,
        )
      }
    }
  }
}
