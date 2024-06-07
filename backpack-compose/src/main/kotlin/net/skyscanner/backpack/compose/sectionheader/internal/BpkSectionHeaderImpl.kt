/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2023 Skyscanner Ltd
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderButton
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderType
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderType.Default
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderType.OnDark
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.LongArrowRight
import net.skyscanner.backpack.compose.utils.isTablet

@Composable
internal fun BpkSectionHeaderImpl(
    title: String,
    type: BpkSectionHeaderType,
    description: String?,
    button: BpkSectionHeaderButton?,
    modifier: Modifier = Modifier,
) {
    val isTablet = isTablet()
    Row(
        horizontalArrangement = getHorizontalArrangement(isTablet),
        verticalAlignment = Alignment.Top,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(BpkSpacing.Sm, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            BpkText(
                text = title,
                style = if (isTablet) {
                    BpkTheme.typography.heading2
                } else {
                    BpkTheme.typography.heading3
                },
                color = getTextColor(type),
                modifier = Modifier.semantics { heading() },
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
            Row {
                if (isTablet) {
                    BpkButton(
                        text = it.text,
                        onClick = it.onClick,
                        type = getButtonType(type),
                    )
                } else {
                    BpkButton(
                        icon = BpkIcon.LongArrowRight,
                        contentDescription = it.text,
                        onClick = it.onClick,
                        type = getButtonType(type),
                    )
                }
            }
        }
    }
}

private fun getButtonType(type: BpkSectionHeaderType): BpkButtonType = when (type) {
    Default -> BpkButtonType.Primary
    OnDark -> BpkButtonType.PrimaryOnDark
}

private fun getHorizontalArrangement(tablet: Boolean): Arrangement.HorizontalOrVertical {
    val size = if (tablet) {
        BpkSpacing.Lg.times(2)
    } else {
        BpkSpacing.Lg
    }
    return Arrangement.spacedBy(size)
}

@Composable
private fun getTextColor(type: BpkSectionHeaderType): Color = when (type) {
    Default -> BpkTheme.colors.textPrimary
    OnDark -> BpkTheme.colors.textOnDark
}
