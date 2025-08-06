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

package net.skyscanner.backpack.compose.inventorycard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonSize
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.card.BpkCardCorner
import net.skyscanner.backpack.compose.card.BpkCardElevation
import net.skyscanner.backpack.compose.card.BpkCardPadding
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing

/**
 * Backpack Inventory Card component.
 *
 * A card component designed to display inventory items with an image, title, description, and action button.
 * The component follows Backpack design specifications with proper spacing, typography, and accessibility features.
 *
 * @param title The main title text displayed in the card
 * @param description The description text displayed below the title
 * @param buttonText The text displayed on the action button
 * @param onButtonClick Callback invoked when the action button is clicked
 * @param modifier Modifier to be applied to the card container
 * @param image Optional composable content for the image area at the top of the card
 * @param buttonContentDescription Optional content description for the button for accessibility
 * @param onCardClick Optional callback invoked when the card itself is clicked
 */
@Composable
fun BpkInventoryCard(
    title: String,
    description: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    image: (@Composable BoxScope.() -> Unit)? = null,
    buttonContentDescription: String? = null,
    onCardClick: (() -> Unit)? = null,
) {
    val cardModifier = modifier.semantics {
        contentDescription = "Inventory card with title: $title"
    }

    if (onCardClick != null) {
        BpkCard(
            modifier = cardModifier.height(220.dp),
            corner = BpkCardCorner.Small,
            padding = BpkCardPadding.None,
            elevation = BpkCardElevation.Default,
        ) {
            InventoryCardContent(
                title = title,
                description = description,
                buttonText = buttonText,
                onButtonClick = onButtonClick,
                image = image,
                buttonContentDescription = buttonContentDescription,
            )
        }
    } else {
        BpkCard(
            modifier = cardModifier.height(220.dp),
            corner = BpkCardCorner.Small,
            padding = BpkCardPadding.None,
            elevation = BpkCardElevation.Default,
        ) {
            InventoryCardContent(
                title = title,
                description = description,
                buttonText = buttonText,
                onButtonClick = onButtonClick,
                image = image,
                buttonContentDescription = buttonContentDescription,
            )
        }
    }
}

@Composable
private fun InventoryCardContent(
    title: String,
    description: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    image: (@Composable BoxScope.() -> Unit)?,
    buttonContentDescription: String?,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
    ) {
        // Image section
        if (image != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(152.dp),
            ) {
                image()
            }
        }

        // Content section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = BpkSpacing.Md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Text content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
            ) {
                BpkText(
                    text = title,
                    style = BpkTheme.typography.heading5,
                )
                BpkText(
                    text = description,
                    style = BpkTheme.typography.caption,
                )
            }

            // Action button
            BpkButton(
                text = buttonText,
                onClick = onButtonClick,
                size = BpkButtonSize.Default,
                type = BpkButtonType.Primary,
                modifier = Modifier.semantics {
                    buttonContentDescription?.let { contentDescription = it }
                },
            )
        }
    }
}
