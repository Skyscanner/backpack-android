/*
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

package net.skyscanner.backpack.compose.chip

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.chip.internal.BpkChipImpl
import net.skyscanner.backpack.compose.chip.internal.BpkDismissibleChipImpl
import net.skyscanner.backpack.compose.icon.BpkIcon

enum class BpkChipStyle {
    Default,
    OnDark,
    OnImage,
}

enum class BpkChipType {
    Selectable,
    Dropdown,
    Dismiss,
}

@Composable
fun BpkChip(
    text: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onSelectedChange: ((Boolean) -> Unit)? = null,
    enabled: Boolean = true,
    style: BpkChipStyle = BpkChipStyle.Default,
    icon: BpkIcon? = null,
) {
    BpkChipImpl(
        text = text,
        selected = selected,
        enabled = enabled,
        style = style,
        icon = icon,
        type = BpkChipType.Selectable,
        onSelectedChange = onSelectedChange,
        modifier = modifier,
    )
}

@Composable
fun BpkDropdownChip(
    text: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onSelectedChange: ((Boolean) -> Unit)? = null,
    enabled: Boolean = true,
    style: BpkChipStyle = BpkChipStyle.Default,
    icon: BpkIcon? = null,
) {
    BpkChipImpl(
        text = text,
        selected = selected,
        enabled = enabled,
        style = style,
        icon = icon,
        type = BpkChipType.Dropdown,
        onSelectedChange = onSelectedChange,
        modifier = modifier,
    )
}

@Composable
fun BpkDismissibleChip(
    text: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    style: BpkChipStyle = BpkChipStyle.Default,
    icon: BpkIcon? = null,
) {
    BpkDismissibleChipImpl(
        text = text,
        style = style,
        icon = icon,
        modifier = modifier,
        onClick = onClick,
    )
}
