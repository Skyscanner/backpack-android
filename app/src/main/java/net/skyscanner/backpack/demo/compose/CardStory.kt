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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.card.BpkCardCorner
import net.skyscanner.backpack.compose.card.BpkCardPadding
import net.skyscanner.backpack.compose.card.BpkCardStyle
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.components.CardComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@CardComponent
@ComposeStory
fun CardStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(BpkTheme.colors.canvasContrast)
            .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Xxl),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {

        val cardModifier = Modifier
            .fillMaxWidth()
            .weight(1f)

        OnDefaultStyleExample(cardModifier)

        OnContrastStyleExample(cardModifier)

        SmallCornersCardExample(cardModifier)

        LargeCornersCardExample(cardModifier)

        NoPaddingCardExample(Modifier.fillMaxWidth())

        NonClickableCardExample(cardModifier)

        OnStyleSwapExample(cardModifier)
    }
}

@Composable
internal fun OnDefaultStyleExample(modifier: Modifier = Modifier) {
    var cardStyle by remember { mutableStateOf(BpkCardStyle.onDefault) }

    BpkCard(
        modifier = modifier,
        cardStyle = cardStyle,
    ) {
        BpkText(
            text = "onDefault style",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )
    }
}

@Composable
internal fun OnContrastStyleExample(modifier: Modifier = Modifier) {
    var cardStyle by remember { mutableStateOf(BpkCardStyle.onContrast) }

    BpkCard(
        modifier = modifier,
        cardStyle = cardStyle,
    ) {
        BpkText(
            text = "onContrast style",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )
    }
}

@Composable
internal fun SmallCornersCardExample(modifier: Modifier = Modifier) {
    BpkCard(
        modifier = modifier,
    ) {

        BpkText(
            text = "Small corners",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )
    }
}

@Composable
internal fun LargeCornersCardExample(modifier: Modifier = Modifier) {
    BpkCard(
        modifier = modifier,
        corner = BpkCardCorner.Large,
    ) {
        BpkText(
            text = "Large corners",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )
    }
}

@Composable
internal fun NoPaddingCardExample(modifier: Modifier = Modifier) {
    BpkCard(
        modifier = modifier,
        padding = BpkCardPadding.None,
    ) {
        BpkText(
            text = "No padding",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )
    }
}

@Composable
internal fun NonClickableCardExample(modifier: Modifier = Modifier) {
    BpkCard(modifier) {
        BpkText(
            text = "Non clickable",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )
    }
}

@Composable
internal fun OnStyleSwapExample(modifier: Modifier = Modifier) {
    var cardStyle by remember { mutableStateOf(BpkCardStyle.onDefault) }

    BpkCard(
        modifier = modifier,
        cardStyle = cardStyle,
        onClick = {
            cardStyle = when (cardStyle) {
                BpkCardStyle.onDefault -> BpkCardStyle.onContrast
                BpkCardStyle.onContrast -> BpkCardStyle.onDefault
            }
        },
    ) {
        BpkText(
            text = if (cardStyle == BpkCardStyle.onDefault) "Tap to change to onContrast style" else "Tap to change to onDefault style",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )
    }
}
