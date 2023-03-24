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
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@OptIn(ExperimentalComposeUiApi::class)
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
                Box(modifier = Modifier.padding(top = BpkSpacing.Sm)) {
                    Box(
                        modifier = Modifier
                            .size(BpkSpacing.Lg)
                            .background(color = BpkTheme.colors.textOnDark, shape = RoundedCornerShape(BpkBorderRadius.Xs)),
                        content = it,
                        contentAlignment = Alignment.Center,
                    )
                }
                Spacer(modifier = Modifier.width(BpkSpacing.Base))
            }
            Column(
                modifier = Modifier.weight(0.67f),
                horizontalAlignment = Alignment.Start,
            ) {
                Row {
                    BpkText(
                        text = departureArrivalTime,
                        style = BpkTheme.typography.heading5,
                        color = BpkTheme.colors.textPrimary,
                    )
                    nextDayArrival?.let {
                        BpkText(
                            modifier = Modifier.align(Alignment.Top),
                            text = it,
                            style = BpkTheme.typography.caption,
                            color = BpkTheme.colors.textPrimary,
                        )
                    }
                }
                BpkText(
                    text = description,
                    style = BpkTheme.typography.caption,
                    color = BpkTheme.colors.textSecondary,
                )
                operatedBy?.let {
                    BpkText(
                        text = it,
                        style = BpkTheme.typography.caption,
                        color = BpkTheme.colors.textSecondary,
                    )
                }
                warning?.let {
                    BpkText(
                        text = it,
                        style = BpkTheme.typography.label3,
                        color = BpkTheme.colors.textError,
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End,
            ) {
                BpkText(
                    text = stopsInfo,
                    style = BpkTheme.typography.label3,
                    color = if (highlightStopsInfo) BpkTheme.colors.textError else BpkTheme.colors.textPrimary,
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

@Composable
fun bpkAirportHighlightStyle() = SpanStyle(
    background = BpkTheme.colors.statusDangerFill,
    color = BpkTheme.colors.textOnLight,
)
