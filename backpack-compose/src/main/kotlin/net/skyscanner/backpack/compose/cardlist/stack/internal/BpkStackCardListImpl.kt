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

package net.skyscanner.backpack.compose.cardlist.stack.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonIconPosition
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.cardlist.stack.BpkStackCardAccessoryStyle
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeader
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderButton
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.ChevronDown
import net.skyscanner.backpack.compose.tokens.ChevronUp

@Composable
internal fun BpkStackCardListImpl(
    title: String,
    description: String?,
    totalCount: Int,
    modifier: Modifier = Modifier,
    accessoryStyle: BpkStackCardAccessoryStyle? = null,
    headerButton: BpkSectionHeaderButton? = null,
    accessibilityHeaderTagEnabled: Boolean? = true,
    content: @Composable ((Int) -> Unit),
) {

    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val targetCount = when (accessoryStyle) {
        is BpkStackCardAccessoryStyle.Expand -> {
            if (accessoryStyle.expandedCount < 0 || accessoryStyle.collapsedCount < 0) {
                throw IllegalArgumentException("expandedCount/collapsedCount can not be negative")
            }
            if (accessoryStyle.expandedCount < accessoryStyle.collapsedCount) {
                throw IllegalArgumentException("expandedCount should be greater than collapsedCount")
            }
            if (accessoryStyle.expandedCount <= MINIMUM_EXPANDED_COUNT) {
                totalCount
            } else {
                if (isExpanded) accessoryStyle.expandedCount else accessoryStyle.collapsedCount
            }
        }
        else -> totalCount
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = BpkSpacing.Base, end = BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        item {
            BpkSectionHeader(
                title = title,
                description = description,
                modifier = Modifier,
                button = headerButton,
                accessibilityHeaderTagEnabled = accessibilityHeaderTagEnabled,
            )
        }

        items(targetCount) {
            Box(Modifier.animateItem()) {
                content(it)
            }
        }

        item {
            accessoryStyle?.let {
                when (it) {
                    is BpkStackCardAccessoryStyle.Expand -> {
                        if (it.expandedCount > MINIMUM_EXPANDED_COUNT) {
                            BpkButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = BpkSpacing.Base),
                                text = if (isExpanded) it.collapsedText else it.expandText,
                                icon = if (isExpanded) BpkIcon.ChevronUp else BpkIcon.ChevronDown,
                                position = BpkButtonIconPosition.End,
                                type = BpkButtonType.Link,
                                onClick = { isExpanded = !isExpanded },
                            )
                        }
                    }

                    is BpkStackCardAccessoryStyle.Button -> {
                        if (headerButton == null) {
                            BpkButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = BpkSpacing.Base),
                                text = it.title,
                                type = BpkButtonType.Primary,
                                position = BpkButtonIconPosition.End,
                                icon = it.icon,
                                onClick = it.onClick,
                            )
                        }
                    }
                }
            }
        }
    }
}

internal const val MINIMUM_EXPANDED_COUNT = 2
