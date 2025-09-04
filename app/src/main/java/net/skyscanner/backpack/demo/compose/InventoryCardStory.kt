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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import net.skyscanner.backpack.compose.inventorycard.BpkInventoryCard
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.components.InventoryCardComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@InventoryCardComponent
@ComposeStory
fun InventoryCardStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(BpkTheme.colors.canvas)
            .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Xxl),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        BasicInventoryCardExample()

        ClickableInventoryCardExample()

        DisabledInventoryCardExample()
    }
}

@Composable
internal fun BasicInventoryCardExample(modifier: Modifier = Modifier) {
    BpkInventoryCard(
        title = "London to Barcelona",
        subtitle = "Direct • 2h 15m",
        buttonText = "Select",
        onButtonClick = { },
        imagePainter = painterResource(net.skyscanner.backpack.demo.R.drawable.beach),
        imageContentDescription = "Flight route",
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
internal fun ClickableInventoryCardExample(modifier: Modifier = Modifier) {
    var clickCount by remember { mutableIntStateOf(0) }

    BpkInventoryCard(
        title = "Hotel Marina Bay",
        subtitle = "5 stars • Beach view",
        buttonText = "Book",
        onButtonClick = { clickCount++ },
        onCardClick = { },
        imagePainter = painterResource(net.skyscanner.backpack.demo.R.drawable.amsterdam),
        imageContentDescription = "Hotel view",
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
internal fun DisabledInventoryCardExample(modifier: Modifier = Modifier) {
    BpkInventoryCard(
        title = "Car Rental",
        subtitle = "Economy • Automatic",
        buttonText = "Unavailable",
        onButtonClick = { },
        imagePainter = painterResource(net.skyscanner.backpack.demo.R.drawable.london_towerbridge),
        imageContentDescription = "Car rental",
        modifier = modifier.fillMaxWidth(),
        enabled = false,
    )
}
