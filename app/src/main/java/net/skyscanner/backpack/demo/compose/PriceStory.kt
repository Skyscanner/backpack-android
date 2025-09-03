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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification
import net.skyscanner.backpack.compose.floatingnotification.rememberBpkFloatingNotificationState
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.price.BpkPrice
import net.skyscanner.backpack.compose.price.BpkPriceAlign
import net.skyscanner.backpack.compose.price.BpkPriceSize
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.NewWindow
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.PriceComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@PriceComponent
@ComposeStory
fun PriceStory(modifier: Modifier = Modifier) {
    val floatingNotificationState = rememberBpkFloatingNotificationState()
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .padding(BpkSpacing.Base)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                PriceExample(
                    size = BpkPriceSize.Large,
                    align = BpkPriceAlign.Start,
                )
                PriceExample(
                    size = BpkPriceSize.Large,
                    align = BpkPriceAlign.Start,
                    onClick = {
                        scope.launch {
                            floatingNotificationState.show("Clicked large price!")
                        }
                    },
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                PriceExample(
                    size = BpkPriceSize.Small,
                    align = BpkPriceAlign.Start,
                )
                PriceExample(
                    size = BpkPriceSize.Small,
                    align = BpkPriceAlign.End,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                PriceExample(
                    size = BpkPriceSize.Small,
                    align = BpkPriceAlign.Start,
                    onClick = {
                        scope.launch {
                            floatingNotificationState.show("Clicked small start price!")
                        }
                    },
                )
                PriceExample(
                    size = BpkPriceSize.Small,
                    align = BpkPriceAlign.End,
                    onClick = {
                        scope.launch {
                            floatingNotificationState.show("Clicked small end price!")
                        }
                    },
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                PriceExample(
                    size = BpkPriceSize.ExtraSmall,
                    align = BpkPriceAlign.Start,
                )
                PriceExample(
                    size = BpkPriceSize.ExtraSmall,
                    align = BpkPriceAlign.End,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                PriceExample(
                    size = BpkPriceSize.Small,
                    align = BpkPriceAlign.Start,
                    icon = BpkIcon.NewWindow,
                )
                PriceExample(
                    size = BpkPriceSize.Small,
                    align = BpkPriceAlign.End,
                    icon = BpkIcon.NewWindow,
                )
            }
            PriceExample(
                size = BpkPriceSize.Small,
                align = BpkPriceAlign.Row,
            )
            PriceExample(
                size = BpkPriceSize.ExtraSmall,
                align = BpkPriceAlign.Row,
            )
            PriceExample(
                size = BpkPriceSize.Small,
                align = BpkPriceAlign.Row,
                icon = BpkIcon.NewWindow,
            )

            PriceExample(
                size = BpkPriceSize.Small,
                align = BpkPriceAlign.Row,
                icon = BpkIcon.NewWindow,
                onClick = {
                    scope.launch {
                        floatingNotificationState.show("Clicked row price with icon!")
                    }
                },
            )
        }

        BpkFloatingNotification(floatingNotificationState)
    }
}

@Composable
private fun PriceExample(
    size: BpkPriceSize,
    align: BpkPriceAlign,
    icon: BpkIcon? = null,
    onClick: (() -> Unit)? = null,
) {
    BpkPrice(
        price = stringResource(id = R.string.price_price),
        previousPrice = stringResource(id = R.string.price_line_through_text),
        leadingText = stringResource(id = R.string.price_leading_text),
        trailingText = stringResource(id = R.string.price_trailing_text),
        size = size,
        align = align,
        icon = icon,
        onPriceClicked = onClick,
    )
}
