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

package net.skyscanner.backpack.compose.select.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.fieldset.LocalFieldStatus
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.textfield.internal.BpkTextFieldImpl
import net.skyscanner.backpack.compose.textfield.internal.BpkTextFieldType
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.ArrowDown
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.applyIf
import net.skyscanner.backpack.compose.utils.clickableWithRipple

enum class BpkDropDownWidth {
    MAX_WIDTH, MATCH_OPTION_WIDTH, MATCH_SELECT_WIDTH
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("ModifierNotUsedAtRoot")
internal fun BpkSelectImpl(
    options: List<String>,
    selectedIndex: Int?,
    placeholder: String,
    modifier: Modifier = Modifier,
    status: BpkFieldStatus = LocalFieldStatus.current,
    onSelectionChange: ((selectedIndex: Int) -> Unit)? = null,
    dropDownWidth: BpkDropDownWidth = BpkDropDownWidth.MAX_WIDTH,
) {
    var expanded by remember { mutableStateOf(false) }
    val selectText = selectedIndex?.let { options.getOrNull(selectedIndex) } ?: ""
    val focusManager = LocalFocusManager.current
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        BpkTextFieldImpl(
            modifier = modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
            value = selectText,
            onValueChange = {},
            type = BpkTextFieldType.Select,
            readOnly = true,
            placeholder = placeholder,
            status = status,
            trailingIcon = BpkIcon.ArrowDown,
        )
        ExposedDropdownMenu(
            expanded = if (status != BpkFieldStatus.Disabled) expanded else false,
            modifier = Modifier
                .background(BpkTheme.colors.surfaceDefault)
                .applyIf(dropDownWidth == BpkDropDownWidth.MAX_WIDTH) {
                    fillMaxWidth()
                },
            matchTextFieldWidth = dropDownWidth == BpkDropDownWidth.MATCH_SELECT_WIDTH,
            onDismissRequest = {
                expanded = false
                focusManager.clearFocus()
            },
        ) {
            options.forEachIndexed { index, option ->
                val itemBackgroundColor =
                    if (index == selectedIndex) BpkTheme.colors.surfaceHighlight else BpkTheme.colors.surfaceDefault
                DropdownMenuItem(
                    modifier = Modifier
                        .height(BpkSpacing.Lg.times(2))
                        .background(itemBackgroundColor),
                    text = {
                        BpkText(
                            text = option,
                            color = BpkTheme.colors.textPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = BpkTheme.typography.label1,
                        )
                    },
                    onClick = {
                        expanded = false
                        focusManager.clearFocus()
                        if (index != selectedIndex) {
                            onSelectionChange?.let { it(index) }
                        }
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
internal fun BpkSelectImpl(
    placeholder: String,
    text: String,
    modifier: Modifier = Modifier,
    status: BpkFieldStatus = LocalFieldStatus.current,
    onClick: (() -> Unit)? = null,
) {
    BpkTextFieldImpl(
        modifier = if (status == BpkFieldStatus.Disabled) modifier else modifier.clickableWithRipple(
            bounded = true,
            role = Role.Button,
        ) {
            onClick?.let { it() }
        },
        value = text,
        onValueChange = {},
        readOnly = true,
        placeholder = placeholder,
        status = status,
        trailingIcon = BpkIcon.ArrowDown,
    )
}
