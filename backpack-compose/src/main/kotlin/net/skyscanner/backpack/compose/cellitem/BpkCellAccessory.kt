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

package net.skyscanner.backpack.compose.cellitem

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.switch.BpkSwitch
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.ChevronRight

/**
 * Chevron accessory for [BpkCellItem].
 * Displays a right-pointing chevron icon, typically used to indicate navigation.
 *
 * @param modifier Optional modifier for the icon.
 */
@Composable
fun BpkCellAccessoryChevron(
    modifier: Modifier = Modifier,
) {
    BpkIcon(
        icon = BpkIcon.ChevronRight,
        contentDescription = null,
        size = BpkIconSize.Small,
        tint = BpkTheme.colors.textPrimary,
        modifier = modifier,
    )
}

/**
 * Switch accessory for [BpkCellItem].
 * Displays a toggle switch control.
 *
 * @param checked The current checked state of the switch.
 * @param onCheckedChange Callback invoked when the switch is toggled.
 * @param modifier Optional modifier for the switch.
 */
@Composable
fun BpkCellAccessorySwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    BpkSwitch(
        text = "",
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
    )
}

/**
 * Text accessory for [BpkCellItem].
 * Displays secondary text content, typically used for values or labels.
 *
 * @param text The text to display.
 * @param modifier Optional modifier for the text.
 */
@Composable
fun BpkCellAccessoryText(
    text: String,
    modifier: Modifier = Modifier,
) {
    BpkText(
        text = text,
        style = BpkTheme.typography.bodyDefault,
        color = BpkTheme.colors.textSecondary,
        modifier = modifier,
    )
}

/**
 * Logo accessory for [BpkCellItem].
 * Displays an image/logo, typically used for branding or identification.
 *
 * @param logoDrawable The drawable resource ID for the logo.
 * @param modifier Optional modifier for the image.
 */
@Composable
fun BpkCellAccessoryLogo(
    @DrawableRes logoDrawable: Int,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(logoDrawable),
        contentDescription = null,
        modifier = modifier.size(width = BpkSpacing.Xxl, height = BpkSpacing.Lg),
    )
}
