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

package net.skyscanner.backpack.compose.appsearchmodal.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import net.skyscanner.backpack.compose.appsearchmodal.BpkItem
import net.skyscanner.backpack.compose.appsearchmodal.BpkSectionHeading
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.BpkClickHandleScope
import net.skyscanner.backpack.compose.utils.clickableWithRipple

@Composable
internal fun BpkSectionHeading(
    sectionHeading: BpkSectionHeading,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        BpkText(
            text = sectionHeading.title,
            style = BpkTheme.typography.label1,
            modifier = Modifier.semantics { heading() },
        )
        sectionHeading.action?.let {
            BpkButton(
                type = BpkButtonType.Link,
                text = it.text,
                onClick = it.onActionSelected,
            )
        }
    }
}

@Composable
internal fun BpkSectionItem(item: BpkItem, modifier: Modifier = Modifier, clickHandleScope: BpkClickHandleScope? = null) {
    Row(
        modifier = modifier
            .clickableWithRipple {
                item.onItemSelected()
                clickHandleScope?.notifyClick()
            }
            .fillMaxWidth()
            .padding(BpkSpacing.Base),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        BpkIcon(
            icon = item.icon,
            contentDescription = null,
            size = BpkIconSize.Large,
        )
        Column(
            modifier = Modifier.weight(1f),
        ) {
            BpkText(text = item.title)
            BpkText(
                style = BpkTheme.typography.footnote,
                text = item.subtitle,
            )
        }
        item.tertiaryLabel?.let {
            BpkText(
                style = BpkTheme.typography.footnote,
                color = BpkTheme.colors.textSecondary,
                text = it,
            )
        }
    }
}
