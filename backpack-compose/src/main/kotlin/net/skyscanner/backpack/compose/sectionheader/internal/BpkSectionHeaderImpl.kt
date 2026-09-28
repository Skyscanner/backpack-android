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

package net.skyscanner.backpack.compose.sectionheader.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonSize
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.button.internal.minHeight
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderButton
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderType
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderType.Default
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderType.OnDark
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.LongArrowRight
import net.skyscanner.backpack.compose.utils.isSmallTablet

@Composable
internal fun BpkSectionHeaderImpl(
    title: String,
    type: BpkSectionHeaderType,
    description: String?,
    button: BpkSectionHeaderButton?,
    accessibilityHeaderTagEnabled: Boolean?,
    modifier: Modifier = Modifier,
) {
    val isTablet = isSmallTablet()
    val density = LocalDensity.current
    var buttonTopOffset by remember { mutableStateOf(0.dp) }

    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
            horizontalAlignment = Alignment.Start,
        ) {
            BpkText(
                text = title,
                style = if (isTablet) BpkTheme.typography.heading2 else BpkTheme.typography.heading3,
                color = getTextColor(type),
                onTextLayout = { result ->
                    val firstLineHeightPx = result.getLineBottom(0) - result.getLineTop(0)
                    val buttonHeightPx = with(density) { BpkButtonSize.Default.minHeight.toPx() }
                    buttonTopOffset = with(density) {
                        ((firstLineHeightPx - buttonHeightPx) / 2f).coerceAtLeast(0f).toDp()
                    }
                },
                modifier = Modifier.semantics {
                    if (accessibilityHeaderTagEnabled == true) {
                        heading()
                    }
                },
            )
            if (!description.isNullOrBlank()) {
                BpkText(
                    text = description,
                    style = BpkTheme.typography.bodyDefault,
                    color = getTextColor(type),
                )
            }
        }
        button?.let {
            val startPadding: Dp = if (isTablet) BpkSpacing.Lg.times(2) else BpkSpacing.Lg
            if (isTablet) {
                BpkButton(
                    text = it.text,
                    onClick = it.onClick,
                    type = getButtonType(type),
                    modifier = Modifier.padding(start = startPadding, top = buttonTopOffset),
                )
            } else {
                BpkButton(
                    icon = BpkIcon.LongArrowRight,
                    contentDescription = it.text,
                    onClick = it.onClick,
                    type = getButtonType(type),
                    modifier = Modifier.padding(start = startPadding, top = buttonTopOffset),
                )
            }
        }
    }
}

private fun getButtonType(type: BpkSectionHeaderType): BpkButtonType = when (type) {
    Default -> BpkButtonType.Primary
    OnDark -> BpkButtonType.PrimaryOnDark
}

@Composable
private fun getTextColor(type: BpkSectionHeaderType): Color = when (type) {
    Default -> BpkTheme.colors.textPrimary
    OnDark -> BpkTheme.colors.textOnDark
}
