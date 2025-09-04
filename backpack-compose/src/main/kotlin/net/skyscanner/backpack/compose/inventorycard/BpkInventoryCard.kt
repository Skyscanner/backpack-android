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

package net.skyscanner.backpack.compose.inventorycard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
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
 * BpkInventoryCard is a specialized card component that displays an image,
 * title, subtitle, and an action button. It's designed for presenting inventory
 * items or selections in a visually consistent manner.
 *
 * @param title The main title text to display
 * @param subtitle The subtitle or caption text to display
 * @param buttonText The text to display on the action button
 * @param onButtonClick Callback when the button is clicked
 * @param imagePainter The painter for the image to display
 * @param imageContentDescription Content description for the image
 * @param modifier Modifier to be applied to the card
 * @param enabled Whether the button is enabled
 */
@Composable
fun BpkInventoryCard(
    title: String,
    subtitle: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    imagePainter: Painter,
    imageContentDescription: String?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    BpkCard(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp),
        corner = BpkCardCorner.Small,
        padding = BpkCardPadding.None,
        elevation = BpkCardElevation.Default,
    ) {
        Column {
            Image(
                painter = imagePainter,
                contentDescription = imageContentDescription,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentScale = ContentScale.Crop,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = BpkSpacing.Md, vertical = BpkSpacing.Md),
                horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
                ) {
                    BpkText(
                        text = title,
                        style = BpkTheme.typography.heading5,
                    )
                    BpkText(
                        text = subtitle,
                        style = BpkTheme.typography.caption,
                    )
                }

                BpkButton(
                    text = buttonText,
                    size = BpkButtonSize.Default,
                    type = BpkButtonType.Primary,
                    enabled = enabled,
                    onClick = onButtonClick,
                )
            }
        }
    }
}

/**
 * BpkInventoryCard variant with clickable card functionality.
 * The entire card is clickable in addition to the action button.
 *
 * @param title The main title text to display
 * @param subtitle The subtitle or caption text to display
 * @param buttonText The text to display on the action button
 * @param onButtonClick Callback when the button is clicked
 * @param onCardClick Callback when the card is clicked
 * @param imagePainter The painter for the image to display
 * @param imageContentDescription Content description for the image
 * @param modifier Modifier to be applied to the card
 * @param enabled Whether the button and card are enabled
 */
@Composable
fun BpkInventoryCard(
    title: String,
    subtitle: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    onCardClick: () -> Unit,
    imagePainter: Painter,
    imageContentDescription: String?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    BpkCard(
        onClick = onCardClick,
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp),
        corner = BpkCardCorner.Small,
        padding = BpkCardPadding.None,
        elevation = BpkCardElevation.Default,
        enabled = enabled,
    ) {
        Column {
            Image(
                painter = imagePainter,
                contentDescription = imageContentDescription,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentScale = ContentScale.Crop,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = BpkSpacing.Md, vertical = BpkSpacing.Md),
                horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
                ) {
                    BpkText(
                        text = title,
                        style = BpkTheme.typography.heading5,
                    )
                    BpkText(
                        text = subtitle,
                        style = BpkTheme.typography.caption,
                    )
                }

                BpkButton(
                    text = buttonText,
                    size = BpkButtonSize.Default,
                    type = BpkButtonType.Primary,
                    enabled = enabled,
                    onClick = onButtonClick,
                )
            }
        }
    }
}