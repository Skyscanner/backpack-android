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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification
import net.skyscanner.backpack.compose.floatingnotification.rememberBpkFloatingNotificationState
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.price.BpkPrice
import net.skyscanner.backpack.compose.price.BpkPriceAlign
import net.skyscanner.backpack.compose.price.BpkPriceSize
import net.skyscanner.backpack.compose.price.BpkPriceStyle
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.InformationCircle
import net.skyscanner.backpack.compose.tokens.NewWindow
import net.skyscanner.backpack.compose.tokens.TrendDown
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.PriceComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@PriceComponent
@ComposeStory("Default")
fun PriceStoryDefault(modifier: Modifier = Modifier) {
    PriceStory(style = BpkPriceStyle.default, modifier = modifier)
}

@Composable
@PriceComponent
@ComposeStory("OnContrast")
fun PriceStoryOnContrast(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BpkTheme.colors.corePrimary),
    ) {
        PriceStory(style = BpkPriceStyle.onContrast, modifier = modifier)
    }
}

@Composable
fun PriceStory(style: BpkPriceStyle, modifier: Modifier = Modifier) {
    val floatingNotificationState = rememberBpkFloatingNotificationState()
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .padding(BpkSpacing.Base)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                PriceExample(
                    size = BpkPriceSize.Large,
                    align = BpkPriceAlign.Start,
                    style = style,
                )
                PriceExample(
                    size = BpkPriceSize.Large,
                    align = BpkPriceAlign.Start,
                    style = style,
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
                    style = style,
                )
                PriceExample(
                    size = BpkPriceSize.Small,
                    align = BpkPriceAlign.End,
                    style = style,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                PriceExample(
                    size = BpkPriceSize.Small,
                    align = BpkPriceAlign.Start,
                    style = style,
                    onClick = {
                        scope.launch {
                            floatingNotificationState.show("Clicked small start price!")
                        }
                    },
                )
                PriceExample(
                    size = BpkPriceSize.Small,
                    align = BpkPriceAlign.End,
                    style = style,
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
                    style = style,
                )
                PriceExample(
                    size = BpkPriceSize.ExtraSmall,
                    align = BpkPriceAlign.End,
                    style = style,
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
                    style = style,
                )
                PriceExample(
                    size = BpkPriceSize.Small,
                    align = BpkPriceAlign.End,
                    icon = BpkIcon.NewWindow,
                    style = style,
                )
            }
            PriceExample(
                size = BpkPriceSize.Small,
                align = BpkPriceAlign.Row,
                style = style,
            )
            PriceExample(
                size = BpkPriceSize.ExtraSmall,
                align = BpkPriceAlign.Row,
                style = style,
            )
            PriceExample(
                size = BpkPriceSize.Small,
                align = BpkPriceAlign.Row,
                icon = BpkIcon.NewWindow,
                style = style,
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
                style = style,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                LeadingIconExample(
                    align = BpkPriceAlign.Start,
                    style = style,
                    leadingIcon = BpkIcon.TrendDown,
                    leadingIconBackgroundColor = BpkTheme.colors.statusSuccessSpot,
                    onClick = {
                        scope.launch {
                            floatingNotificationState.show("Start: leading icon only clicked!")
                        }
                    },
                )
                LeadingIconExample(
                    align = BpkPriceAlign.End,
                    style = style,
                    leadingIcon = BpkIcon.TrendDown,
                    leadingIconBackgroundColor = BpkTheme.colors.statusSuccessSpot,
                    onClick = {
                        scope.launch {
                            floatingNotificationState.show("End: leading icon only clicked!")
                        }
                    },
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                LeadingIconExample(
                    align = BpkPriceAlign.Start,
                    style = style,
                    trailingIcon = BpkIcon.InformationCircle,
                    onClick = {
                        scope.launch {
                            floatingNotificationState.show("Start: trailing icon only clicked!")
                        }
                    },
                )
                LeadingIconExample(
                    align = BpkPriceAlign.End,
                    style = style,
                    trailingIcon = BpkIcon.InformationCircle,
                    onClick = {
                        scope.launch {
                            floatingNotificationState.show("End: trailing icon only clicked!")
                        }
                    },
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                LeadingIconExample(
                    align = BpkPriceAlign.Start,
                    style = style,
                    leadingIcon = BpkIcon.TrendDown,
                    leadingIconBackgroundColor = BpkTheme.colors.statusSuccessSpot,
                    trailingIcon = BpkIcon.InformationCircle,
                    onClick = {
                        scope.launch {
                            floatingNotificationState.show("Start: both icons clicked!")
                        }
                    },
                )
                LeadingIconExample(
                    align = BpkPriceAlign.End,
                    style = style,
                    leadingIcon = BpkIcon.TrendDown,
                    leadingIconBackgroundColor = BpkTheme.colors.statusSuccessSpot,
                    trailingIcon = BpkIcon.InformationCircle,
                    onClick = {
                        scope.launch {
                            floatingNotificationState.show("End: both icons clicked!")
                        }
                    },
                )
            }

            // Long texts to wrapping onto two lines in a half-width
            // container - used to check how the second line aligns relative to the first, for both
            // Start (left) and End (right) alignment, side by side.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
            ) {
                BpkPrice(
                    modifier = Modifier
                        .weight(1f),
                    price = stringResource(id = R.string.price_price),
                    size = BpkPriceSize.Small,
                    align = BpkPriceAlign.Start,
                    style = style,
                    leadingIcon = BpkIcon.TrendDown,
                    leadingIconBackgroundColor = BpkTheme.colors.statusSuccessSpot,
                    leadingText = stringResource(id = R.string.price_leading_text_long),
                    onLeadingTextClicked = {
                        scope.launch {
                            floatingNotificationState.show("End: both icons clicked!")
                        }
                    },
                )
                BpkPrice(
                    modifier = Modifier
                        .weight(1f),
                    price = stringResource(id = R.string.price_price),
                    size = BpkPriceSize.Small,
                    align = BpkPriceAlign.End,
                    style = style,
                    leadingIcon = BpkIcon.TrendDown,
                    leadingIconBackgroundColor = BpkTheme.colors.statusSuccessSpot,
                    leadingText = stringResource(id = R.string.price_leading_text_long),
                    onLeadingTextClicked = {
                        scope.launch {
                            floatingNotificationState.show("End: both icons clicked!")
                        }
                    },
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
            ) {
                BpkPrice(
                    modifier = Modifier
                        .weight(1f),
                    price = stringResource(id = R.string.price_long),
                    size = BpkPriceSize.Large,
                    align = BpkPriceAlign.Start,
                    style = style,
                )
                BpkPrice(
                    modifier = Modifier
                        .weight(1f),
                    price = stringResource(id = R.string.price_long),
                    trailingText = stringResource(id = R.string.price_trailing_text_two_people),
                    size = BpkPriceSize.Large,
                    align = BpkPriceAlign.End,
                    style = style,
                )
            }
        }

        BpkFloatingNotification(floatingNotificationState)
    }
}

@Composable
private fun PriceExample(
    size: BpkPriceSize,
    align: BpkPriceAlign,
    style: BpkPriceStyle,
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
        style = style,
        onPriceClicked = onClick,
    )
}

@Composable
private fun LeadingIconExample(
    align: BpkPriceAlign,
    style: BpkPriceStyle,
    leadingIcon: BpkIcon? = null,
    leadingIconBackgroundColor: Color? = null,
    leadingText: String = stringResource(id = R.string.price_leading_text_cheaper),
    trailingIcon: BpkIcon? = null,
    onClick: (() -> Unit)? = null,
) {
    BpkPrice(
        price = stringResource(id = R.string.price_price),
        leadingText = leadingText,
        leadingIcon = leadingIcon,
        leadingIconBackgroundColor = leadingIconBackgroundColor,
        trailingIcon = trailingIcon,
        size = BpkPriceSize.Small,
        align = align,
        style = style,
        onLeadingTextClicked = onClick,
    )
}
