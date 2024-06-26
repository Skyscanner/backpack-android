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

package net.skyscanner.backpack.compose.nudger

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.fieldset.LocalFieldStatus
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.invisibleSemantic

@Composable
fun BpkNudger(
    value: Int,
    onValueChange: (Int) -> Unit,
    min: Int,
    max: Int,
    modifier: Modifier = Modifier,
    testTag: String? = null,
    enabled: Boolean = LocalFieldStatus.current != BpkFieldStatus.Disabled,
) {
    BpkNudgerImpl(
        value = value,
        onValueChange = onValueChange,
        min = min,
        max = max,
        enabled = enabled,
        testTag = testTag,
        modifier = modifier,
    )
}

@Composable
fun BpkNudger(
    value: Int,
    onValueChange: (Int) -> Unit,
    min: Int,
    max: Int,
    title: String,
    modifier: Modifier = Modifier,
    testTag: String? = null,
    subtitle: String? = null,
    icon: BpkIcon? = null,
    enabled: Boolean = LocalFieldStatus.current != BpkFieldStatus.Disabled,
) {
    Row(
        modifier = modifier
            .semantics {
                text = listOfNotNull(title, subtitle)
                    .joinToString(separator = " ")
                    .let(::AnnotatedString)
            }
            .nudgerSemantics(value, onValueChange, min..max, enabled),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
        ) {

            if (icon != null) {
                BpkIcon(
                    icon = icon,
                    size = BpkIconSize.Large,
                    contentDescription = null, // handled by semantics modifier
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                BpkText(
                    text = title,
                    style = BpkTheme.typography.heading5,
                    color = BpkTheme.colors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.invisibleSemantic(),
                )
                if (subtitle != null) {
                    BpkText(
                        text = subtitle,
                        style = BpkTheme.typography.bodyDefault,
                        color = BpkTheme.colors.textSecondary,
                        modifier = Modifier.invisibleSemantic(),
                    )
                }
            }
        }
        BpkNudgerImpl(
            value = value,
            onValueChange = onValueChange,
            min = min,
            max = max,
            enabled = enabled,
            testTag = testTag,
            allowSemantics = false,
        )
    }
}
