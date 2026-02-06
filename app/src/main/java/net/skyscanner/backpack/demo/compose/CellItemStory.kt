/*
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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryChevron
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryLogo
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessorySwitch
import net.skyscanner.backpack.compose.cellitem.BpkCellAccessoryText
import net.skyscanner.backpack.compose.cellitem.BpkCellItem
import net.skyscanner.backpack.compose.cellitem.BpkCellItemCorner
import net.skyscanner.backpack.compose.cellitem.BpkCellItemStyle
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Account
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.CellItemComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@CellItemComponent
@ComposeStory
fun CellItemDefaultStory(modifier: Modifier = Modifier) {
    CellItemSample(modifier)
}

@Composable
@CellItemComponent
@ComposeStory("With Chevron Accessory")
fun CellItemWithChevronStory(modifier: Modifier = Modifier) {
    CellItemWithChevron(modifier)
}

@Composable
@CellItemComponent
@ComposeStory("With Switch Accessory")
fun CellItemWithSwitchStory(modifier: Modifier = Modifier) {
    CellItemWithSwitch(modifier)
}

@Composable
@CellItemComponent
@ComposeStory("With Text Accessory")
fun CellItemWithTextStory(modifier: Modifier = Modifier) {
    CellItemWithText(modifier)
}

@Composable
@CellItemComponent
@ComposeStory("With Logo Accessory")
fun CellItemWithLogoStory(modifier: Modifier = Modifier) {
    CellItemWithLogo(modifier)
}

@Composable
internal fun CellItemSample(modifier: Modifier = Modifier) {
    BpkCellItem(
        icon = BpkIcon.Account,
        iconContentDescription = "Account",
        title = "Title",
        description = "Description",
        onClick = {},
        modifier = modifier,
    )
}

@Composable
internal fun CellItemWithChevron(modifier: Modifier = Modifier) {
    BpkCellItem(
        icon = BpkIcon.Account,
        iconContentDescription = "Account",
        title = "Profile Settings",
        description = "Manage your account",
        onClick = {},
        modifier = modifier,
        accessory = {
            BpkCellAccessoryChevron()
        },
    )
}

@Composable
internal fun CellItemWithSwitch(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(true) }
    BpkCellItem(
        icon = BpkIcon.Account,
        iconContentDescription = "Account",
        title = "Notifications",
        description = "Enable push notifications",
        modifier = modifier,
        accessory = {
            BpkCellAccessorySwitch(
                checked = checked,
                onCheckedChange = { checked = it },
            )
        },
    )
}

@Composable
internal fun CellItemWithText(modifier: Modifier = Modifier) {
    BpkCellItem(
        icon = BpkIcon.Account,
        iconContentDescription = "Account",
        title = "Language",
        description = "App display language",
        onClick = {},
        modifier = modifier,
        accessory = {
            BpkCellAccessoryText("English")
        },
    )
}

@Composable
internal fun CellItemWithLogo(modifier: Modifier = Modifier) {
    BpkCellItem(
        icon = BpkIcon.Account,
        iconContentDescription = "Account",
        title = "Partner Program",
        description = "Skyland Airlines",
        onClick = {},
        modifier = modifier,
        accessory = {
            BpkCellAccessoryLogo(R.drawable.skyairlines)
        },
    )
}

@Composable
@CellItemComponent
@ComposeStory("With Surface Low Contrast Style")
fun CellItemWithSurfaceLowContrastStory(modifier: Modifier = Modifier) {
    CellItemWithSurfaceLowContrast(modifier)
}

@Composable
@CellItemComponent
@ComposeStory("With Rounded Corner")
fun CellItemWithRoundedCornerStory(modifier: Modifier = Modifier) {
    CellItemWithRoundedCorner(modifier)
}

@Composable
@CellItemComponent
@ComposeStory("With Surface Low Contrast And Rounded Corner")
fun CellItemWithSurfaceLowContrastAndRoundedCornerStory(modifier: Modifier = Modifier) {
    CellItemWithSurfaceLowContrastAndRoundedCorner(modifier)
}

@Composable
internal fun CellItemWithSurfaceLowContrast(modifier: Modifier = Modifier) {
    BpkCellItem(
        icon = BpkIcon.Account,
        iconContentDescription = "Account",
        title = "Title",
        description = "Description",
        style = BpkCellItemStyle.SurfaceLowContrast,
        modifier = modifier,
    )
}

@Composable
internal fun CellItemWithRoundedCorner(modifier: Modifier = Modifier) {
    BpkCellItem(
        icon = BpkIcon.Account,
        iconContentDescription = "Account",
        title = "Profile Settings",
        description = "Manage your account",
        onClick = {},
        corner = BpkCellItemCorner.Rounded,
        modifier = modifier,
        accessory = {
            BpkCellAccessoryChevron()
        },
    )
}

@Composable
internal fun CellItemWithSurfaceLowContrastAndRoundedCorner(modifier: Modifier = Modifier) {
    BpkCellItem(
        icon = BpkIcon.Account,
        iconContentDescription = "Account",
        title = "Notifications",
        description = "Enable push notifications",
        style = BpkCellItemStyle.SurfaceLowContrast,
        corner = BpkCellItemCorner.Rounded,
        modifier = modifier,
        accessory = {
            BpkCellAccessorySwitch(
                checked = true,
                onCheckedChange = {},
            )
        },
    )
}
