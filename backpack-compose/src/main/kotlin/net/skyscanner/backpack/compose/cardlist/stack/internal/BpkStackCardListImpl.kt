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

package net.skyscanner.backpack.compose.cardlist.stack.internal

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
    description: String,
    totalCount: Int,
    modifier: Modifier = Modifier,
    accessoryStyle: BpkStackCardAccessoryStyle? = null,
    headerButton: BpkSectionHeaderButton? = null,
    accessibilityHeaderTagEnabled: Boolean? = true,
    content: @Composable ((Int) -> Unit),
) {

    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val targetCount = when (accessoryStyle) {
        is BpkStackCardAccessoryStyle.Expand ->
            if (isExpanded) accessoryStyle.expandedCount else accessoryStyle.collapsedCount
        else -> totalCount
    }
    val animatedVisibleCount by animateIntAsState(targetValue = targetCount)

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

            Spacer(modifier = Modifier.height(BpkSpacing.Base))

            StackLayout(
                content = content,
                visibleCount = animatedVisibleCount,
            )

            accessoryStyle?.let {
                when (it) {
                    is BpkStackCardAccessoryStyle.Expand -> {
                        BpkButton(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = BpkSpacing.Base, bottom = BpkSpacing.Base),
                            text = if (isExpanded) it.collapsedText else it.expandText,
                            icon = if (isExpanded) BpkIcon.ChevronUp else BpkIcon.ChevronDown,
                            position = BpkButtonIconPosition.End,
                            type = BpkButtonType.Link,
                            onClick = { isExpanded = !isExpanded },
                        )
                    }

                    is BpkStackCardAccessoryStyle.Button -> {
                        if (headerButton == null) {
                            BpkButton(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(top = BpkSpacing.Base, bottom = BpkSpacing.Base),
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

@Composable
fun StackLayout(
    visibleCount: Int,
    modifier: Modifier = Modifier,
    content: @Composable ((Int) -> Unit),
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        repeat(visibleCount) {
            content(it)
        }
    }
}
