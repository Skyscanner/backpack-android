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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import net.skyscanner.backpack.compose.inventorycard.BpkInventoryCard
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.InventoryCardComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@InventoryCardComponent
@ComposeStory("InventoryCard")
fun InventoryCardStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        DefaultInventoryCardSample()
        InventoryCardWithImageSample()
        ClickableInventoryCardSample()
    }
}

@Composable
internal fun DefaultInventoryCardSample(modifier: Modifier = Modifier) {
    BpkInventoryCard(
        image = {
            Image(
                painter = painterResource(id = R.drawable.canadian_rockies_canada),
                contentDescription = "Luxury hotel suite interior",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        },
        title = "Premium Hotel Room",
        description = "Spacious room with city view and modern amenities",
        buttonText = "Book Now",
        onButtonClick = { /* Handle booking action */ },
        modifier = modifier,
    )
}

@Composable
internal fun InventoryCardWithImageSample(modifier: Modifier = Modifier) {
    BpkInventoryCard(
        title = "Luxury Suite",
        description = "Executive suite with panoramic views and premium services",
        buttonText = "Reserve",
        onButtonClick = { /* Handle reservation action */ },
        image = {
            Image(
                painter = painterResource(id = R.drawable.canadian_rockies_canada),
                contentDescription = "Luxury hotel suite interior",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        },
        buttonContentDescription = "Reserve luxury suite",
        modifier = modifier,
    )
}

@Composable
internal fun ClickableInventoryCardSample(modifier: Modifier = Modifier) {
    BpkInventoryCard(
        image = {
            Image(
                painter = painterResource(id = R.drawable.canadian_rockies_canada),
                contentDescription = "Luxury hotel suite interior",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        },
        title = "Standard Room",
        description = "Comfortable accommodation with essential amenities",
        buttonText = "Select",
        onButtonClick = { /* Handle selection action */ },
        onCardClick = { /* Handle card click action */ },
        buttonContentDescription = "Select standard room",
        modifier = modifier,
    )
}
