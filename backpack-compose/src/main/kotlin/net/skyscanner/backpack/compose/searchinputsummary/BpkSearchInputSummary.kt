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

package net.skyscanner.backpack.compose.searchinputsummary

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.fieldset.LocalFieldStatus
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.textfield.BpkClearAction
import net.skyscanner.backpack.compose.textfield.BpkTextField
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Search

data class SearchInputSummary(
    val inputText: String,
    val inputHint: String,
    val prefix: Prefix = Prefix.Icon(),
) {
    sealed class Prefix {
        data class Icon(
            val icon: BpkIcon = BpkIcon.Search,
        ) : Prefix()

        data class Text(
            val prefixText: String,
        ) : Prefix()
    }
}

@Composable
fun BpkSearchInputSummary(
    summary: SearchInputSummary,
    onInputChanged: (String) -> Unit,
    clearAction: BpkClearAction,
    modifier: Modifier = Modifier,
) {
    BpkTextField(
        value = summary.inputText,
        onValueChange = onInputChanged,
        modifier = modifier,
        placeholder = summary.inputHint,
        prefix = {
            when (summary.prefix) {
                is SearchInputSummary.Prefix.Text ->
                    BpkText(
                        text = summary.prefix.prefixText,
                        modifier = Modifier.padding(start = BpkSpacing.Sm),
                    )

                is SearchInputSummary.Prefix.Icon ->
                    BpkIcon(
                        icon = summary.prefix.icon,
                        contentDescription = null,
                        size = BpkIconSize.Large,
                        modifier = Modifier.padding(start = BpkSpacing.Sm),
                        tint = animateColorAsState(
                            when (LocalFieldStatus.current) {
                                is BpkFieldStatus.Disabled -> BpkTheme.colors.textDisabled
                                else -> BpkTheme.colors.textSecondary
                            },
                        ).value,
                    )
            }
        },
        status = BpkFieldStatus.Default,
        clearAction = clearAction,
    )
}
