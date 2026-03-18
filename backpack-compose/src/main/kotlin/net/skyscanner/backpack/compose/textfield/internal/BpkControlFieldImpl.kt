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

package net.skyscanner.backpack.compose.textfield.internal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.fieldset.LocalFieldStatus
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.searchinputcontrol.BpkSearchInputControlStyle
import net.skyscanner.backpack.compose.searchinputcontrol.Docking
import net.skyscanner.backpack.compose.searchinputsummary.Prefix
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.textfield.BpkClearAction
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.hideContentIf

@Composable
internal fun BpkControlFieldImpl(
    value: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    prefix: Prefix? = null,
    status: BpkFieldStatus = LocalFieldStatus.current,
    isFocused: Boolean = false,
    clearAction: BpkClearAction? = null,
    style: BpkSearchInputControlStyle = BpkSearchInputControlStyle.OnDefault,
    docking: Docking = Docking.Float,
) {
    val tintColor = fieldTintColor(status)
    val shape = controlFieldShape(docking = docking)

    val backgroundColor = when (style) {
        BpkSearchInputControlStyle.OnDefault -> BpkTheme.colors.canvasContrast
        BpkSearchInputControlStyle.OnContrast -> BpkTheme.colors.surfaceDefault
    }

    Box(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .requiredHeightIn(min = BpkFieldMinHeight)
            .background(backgroundColor, shape),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = BpkSpacing.Md),
        ) {

            when (prefix) {
                is Prefix.Text ->
                    BpkText(
                        text = prefix.prefixText,
                        modifier = Modifier.padding(start = BpkSpacing.Sm),
                        color = tintColor,
                    )

                is Prefix.Icon ->
                    BpkIcon(
                        icon = prefix.icon,
                        contentDescription = null,
                        modifier = Modifier.padding(start = BpkSpacing.Sm),
                        tint = tintColor,
                    )

                else -> {}
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        vertical = BpkSpacing.Base,
                        horizontal = BpkSpacing.Md,
                    ),
            ) {
                BpkText(
                    text = placeholder ?: "",
                    color = tintColor,
                    maxLines = 1,
                    modifier = Modifier.hideContentIf(value.isNotEmpty()),
                    style = BpkTheme.typography.bodyDefault,
                    overflow = TextOverflow.Ellipsis,
                )

                if (value.isNotEmpty()) {
                    BpkText(
                        text = value,
                        color = when (status) {
                            is BpkFieldStatus.Disabled -> BpkTheme.colors.textDisabled
                            else -> BpkTheme.colors.textPrimary
                        },
                        maxLines = 1,
                        style = BpkTheme.typography.bodyDefault,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            BpkFieldTrailingIcon(
                trailingIcon = null,
                status = status,
                clearAction = clearAction,
                hasValue = value.isNotEmpty(),
                iconSize = if (clearAction != null) BpkIconSize.Large else BpkIconSize.Small,
            )
        }

        // Border only when focused — unfocused has no border per Figma spec
        if (isFocused) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .border(
                        width = 2.dp,
                        shape = shape,
                        color = BpkTheme.colors.surfaceDefault,
                    )
                    .padding(2.dp)
                    .border(
                        width = 2.dp,
                        shape = shape,
                        color = BpkTheme.colors.coreAccent,
                    ),
            )
        }
    }
}

@Composable
private fun controlFieldShape(
    docking: Docking,
): RoundedCornerShape {
    val radius = BpkBorderRadius.Md

    return when (docking) {
        Docking.Float -> RoundedCornerShape(radius)
        Docking.Top -> RoundedCornerShape(topStart = radius, topEnd = radius, bottomStart = 0.dp, bottomEnd = 0.dp)
        Docking.Middle -> RoundedCornerShape(0.dp)
        Docking.Bottom -> RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = radius, bottomEnd = radius)
    }
}
