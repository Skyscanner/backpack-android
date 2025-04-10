/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2025 Skyscanner Ltd
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
        val expandedCount: Int,
        val expandText: String,
        val collapsedText: String,
    ) : BpkStackCardAccessoryStyle()
    data class Button(val title: String, val icon: BpkIcon, val onClick: () -> Unit) : BpkStackCardAccessoryStyle()
}

@Composable
fun BpkStackCardList(
    title: String,
    totalCount: Int,
    modifier: Modifier = Modifier,
    description: String? = null,
    accessoryStyle: BpkStackCardAccessoryStyle? = null,
    headerButton: BpkSectionHeaderButton? = null,
    accessibilityHeaderTagEnabled: Boolean? = true,
    content: @Composable ((Int) -> Unit),
) {
    BpkStackCardListImpl(
        title = title,
        description = description,
        totalCount = totalCount,
        modifier = modifier,
        accessoryStyle = accessoryStyle,
        headerButton = headerButton,
        accessibilityHeaderTagEnabled = accessibilityHeaderTagEnabled,
        content = content,
    )
}
