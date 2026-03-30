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

package net.skyscanner.backpack.compose.cellitem.internal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.cellitem.BpkCellItemCorner
import net.skyscanner.backpack.compose.cellitem.BpkCellItemSlot
import net.skyscanner.backpack.compose.cellitem.BpkCellItemStyle
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.link.BpkLink
import net.skyscanner.backpack.compose.link.BpkLinkStyle
import net.skyscanner.backpack.compose.switch.BpkSwitch
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.ChevronRight
import net.skyscanner.backpack.compose.utils.applyIf
import net.skyscanner.backpack.compose.utils.clickableWithRipple

@Composable
internal fun BpkCellItemImpl(
    title: String,
    style: BpkCellItemStyle,
    corner: BpkCellItemCorner,
    icon: BpkIcon?,
    onClick: (() -> Unit)?,
    body: String?,
    slot: BpkCellItemSlot?,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = when (style) {
        BpkCellItemStyle.SurfaceDefault -> BpkTheme.colors.surfaceDefault
        BpkCellItemStyle.SurfaceLowContrast -> BpkTheme.colors.surfaceLowContrast
    }

    val shape = when (corner) {
        BpkCellItemCorner.Default -> RoundedCornerShape(0.dp)
        BpkCellItemCorner.Rounded -> RoundedCornerShape(BpkSpacing.Md)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(backgroundColor)
            .applyIf(onClick != null) {
                clickableWithRipple(role = Role.Button) { onClick?.invoke() }
            }
            .padding(BpkSpacing.Base),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon?.let {
            BpkIcon(
                icon = it,
                contentDescription = null,
                size = BpkIconSize.Large,
            )
            Spacer(modifier = Modifier.width(BpkSpacing.Base))
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(BpkSpacing.Sm),
        ) {
            BpkText(
                text = title,
                style = BpkTheme.typography.heading5,
                color = BpkTheme.colors.textPrimary,
            )

            body?.let {
                BpkText(
                    text = it,
                    style = BpkTheme.typography.footnote,
                    color = BpkTheme.colors.textPrimary,
                )
            }
        }

        slot?.let { slotType ->
            RenderSlot(slotType)
        }
    }
}

@Composable
private fun RenderSlot(slotType: BpkCellItemSlot) {
    when (slotType) {
        is BpkCellItemSlot.Chevron -> {
            BpkIcon(
                icon = BpkIcon.ChevronRight,
                contentDescription = null,
                size = BpkIconSize.Small,
                tint = BpkTheme.colors.textPrimary,
            )
        }
        is BpkCellItemSlot.Switch -> {
            BpkSwitch(
                text = "",
                checked = slotType.checked,
                onCheckedChange = slotType.onCheckedChange,
            )
        }
        is BpkCellItemSlot.Text -> {
            BpkText(
                text = slotType.text,
                style = BpkTheme.typography.bodyDefault,
                color = BpkTheme.colors.textPrimary,
            )
        }
        is BpkCellItemSlot.Link -> {
            BpkLink(
                text = "[${slotType.text}](${slotType.url})",
                onLinkClicked = slotType.onClick,
                textStyle = BpkTheme.typography.bodyDefault,
                style = BpkLinkStyle.Default,
            )
        }
        is BpkCellItemSlot.Image -> {
            Image(
                painter = painterResource(slotType.imageDrawable),
                contentDescription = null,
                modifier = Modifier.size(width = BpkSpacing.Xxl, height = BpkSpacing.Lg),
            )
        }
    }
}
