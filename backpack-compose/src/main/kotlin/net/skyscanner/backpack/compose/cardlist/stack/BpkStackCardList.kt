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

package net.skyscanner.backpack.compose.cardlist.stack

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.cardlist.stack.internal.BpkStackCardListImpl
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderButton

sealed class BpkStackCardAccessoryStyle {
    data class Expand(
        val collapsedCount: Int,
        val expandText: String,
        val collapsedText: String,
        val expandedCount: Int? = null,
        val onExpansionChange: ((Boolean) -> Unit)? = null,
    ) : BpkStackCardAccessoryStyle()
    data class Button(val title: String, val icon: BpkIcon, val onClick: () -> Unit) : BpkStackCardAccessoryStyle()
}

/**
 * A stack of cards with optional full-width content below its section header and when empty.
 *
 * @param useTwoColumnsOnTablet When true, uses two equal-width columns when the current window is
 * wider than the Backpack tablet breakpoint (769 dp). Defaults to false. Counts refer to cards,
 * not rows; odd counts are not rounded.
 * @param controlsContent Optional content below the section header, including when there are no cards.
 * Null adds no spacing. The caller owns any state inside this slot, such as chip selection.
 * @param emptyContent Optional content shown instead of cards/accessories when [totalCount] is zero.
 * Null preserves the existing empty appearance. Expansion state survives an empty list.
 */
@Composable
fun BpkStackCardList(
    title: String,
    totalCount: Int,
    modifier: Modifier = Modifier,
    description: String? = null,
    accessoryStyle: BpkStackCardAccessoryStyle? = null,
    headerButton: BpkSectionHeaderButton? = null,
    accessibilityHeaderTagEnabled: Boolean? = true,
    isInScrollableContainer: Boolean = false,
    useTwoColumnsOnTablet: Boolean = false,
    controlsContent: (@Composable () -> Unit)? = null,
    emptyContent: (@Composable () -> Unit)? = null,
    content: @Composable (Int) -> Unit,
) {
    BpkStackCardListImpl(
        title = title,
        description = description,
        totalCount = totalCount,
        modifier = modifier,
        accessoryStyle = accessoryStyle,
        headerButton = headerButton,
        accessibilityHeaderTagEnabled = accessibilityHeaderTagEnabled,
        isInScrollableContainer = isInScrollableContainer,
        useTwoColumnsOnTablet = useTwoColumnsOnTablet,
        controlsContent = controlsContent,
        emptyContent = emptyContent,
        content = content,
    )
}
