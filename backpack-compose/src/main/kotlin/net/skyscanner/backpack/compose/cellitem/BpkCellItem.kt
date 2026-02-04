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

package net.skyscanner.backpack.compose.cellitem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import net.skyscanner.backpack.compose.annotation.BpkPreviews
import net.skyscanner.backpack.compose.divider.BpkDivider
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Account
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.applyIf
import net.skyscanner.backpack.compose.utils.clickableWithRipple

@Composable
fun BpkCellItem(
    title: String,
    modifier: Modifier = Modifier,
    icon: BpkIcon? = null,
    iconContentDescription: String? = null,
    onClick: (() -> Unit)? = null,
    description: String? = null,
    showDivider: Boolean = false,
    accessory: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier.applyIf(onClick != null) {
            clickableWithRipple(role = Role.Button) { onClick?.invoke() }
        },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(BpkSpacing.Base),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon?.let {
                BpkIcon(
                    icon = icon,
                    contentDescription = iconContentDescription,
                    size = BpkIconSize.Large,
                )

                Spacer(modifier = Modifier.width(BpkSpacing.Base))
            }

            Column(
                modifier = Modifier.weight(1f),
            ) {
                BpkText(
                    text = title,
                    style = BpkTheme.typography.heading5,
                    color = BpkTheme.colors.textPrimary,
                )

                description?.let {
                    BpkText(
                        text = it,
                        style = BpkTheme.typography.footnote,
                        color = BpkTheme.colors.textSecondary,
                    )
                }
            }

            // Render the accessory composable if provided
            accessory?.invoke()
        }

        if (showDivider) {
            BpkDivider(
                modifier = Modifier.padding(horizontal = BpkSpacing.Base),
            )
        }
    }
}

@BpkPreviews
@Composable
private fun BpkCellItemPreview() {
    BpkCellItem(
        icon = BpkIcon.Account,
        iconContentDescription = "Account",
        title = "Title",
        description = "Description",
        showDivider = true,
        onClick = {},
        accessory = {
            BpkCellAccessoryChevron()
        },
    )
}
