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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.cellitem.internal.BpkCellItemImpl
import net.skyscanner.backpack.compose.icon.BpkIcon

enum class BpkCellItemStyle {
    SurfaceDefault,
    SurfaceLowContrast,
}

enum class BpkCellItemCorner {
    Default,
    Rounded,
}

/**
 * Sealed interface defining the types of accessories (slots) that can be displayed
 * on the trailing edge of a [BpkCellItem].
 */
sealed interface BpkCellItemSlot {
    /**
     * Displays a right-pointing chevron icon, typically used to indicate navigation.
     */
    data object Chevron : BpkCellItemSlot

    /**
     * Displays a toggle switch control.
     *
     * @param checked The current checked state of the switch.
     * @param onCheckedChange Callback invoked when the switch is toggled.
     */
    data class Switch(
        val checked: Boolean,
        val onCheckedChange: (Boolean) -> Unit,
    ) : BpkCellItemSlot

    /**
     * Displays secondary text content, typically used for values or labels.
     *
     * @param text The text to display.
     */
    data class Text(
        val text: String,
    ) : BpkCellItemSlot

    /**
     * Displays clickable link-styled text.
     *
     * @param text The text to display.
     * @param url The URL associated with the link.
     * @param onClick Callback invoked when the link is clicked, receives the URL.
     */
    data class Link(
        val text: String,
        val url: String,
        val onClick: (String) -> Unit,
    ) : BpkCellItemSlot

    /**
     * Displays an image, typically used for branding or identification.
     *
     * @param imageDrawable The drawable resource ID for the image.
     */
    data class Image(
        @DrawableRes val imageDrawable: Int,
    ) : BpkCellItemSlot
}

@Composable
fun BpkCellItem(
    title: String,
    modifier: Modifier = Modifier,
    style: BpkCellItemStyle = BpkCellItemStyle.SurfaceDefault,
    corner: BpkCellItemCorner = BpkCellItemCorner.Default,
    icon: BpkIcon? = null,
    onClick: (() -> Unit)? = null,
    body: String? = null,
    slot: BpkCellItemSlot? = null,
) {
    BpkCellItemImpl(
        title = title,
        modifier = modifier,
        style = style,
        corner = corner,
        icon = icon,
        onClick = onClick,
        body = body,
        slot = slot,
    )
}
