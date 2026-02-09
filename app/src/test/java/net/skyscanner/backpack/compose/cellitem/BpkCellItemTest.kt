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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.icon.BpkIconSize
import net.skyscanner.backpack.compose.switch.BpkSwitch
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Account
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.ChevronRight
import net.skyscanner.backpack.demo.R
import org.junit.Test

class BpkCellItemTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        BpkCellItem(
            title = "Title",
        )
    }

    @Test
    fun withBody() = snap {
        BpkCellItem(
            title = "Title",
            body = "Description",
        )
    }

    @Test
    fun withIconAndBody() = snap {
        BpkCellItem(
            icon = BpkIcon.Account,
            title = "Title",
            body = "Description",
        )
    }

    @Test
    fun withSlot() = snap {
        BpkCellItem(
            icon = BpkIcon.Account,
            title = "Title",
            body = "Description",
            slot = {
                BpkSwitch(
                    text = "",
                    checked = true,
                    onCheckedChange = {},
                )
            },
        )
    }

    @Test
    fun clickable() = snap {
        BpkCellItem(
            title = "Title",
            onClick = {},
        )
    }

    @Test
    fun withChevron() = snap {
        BpkCellItem(
            icon = BpkIcon.Account,
            title = "Title",
            body = "Description",
            onClick = {},
            slot = {
                BpkIcon(
                    icon = BpkIcon.ChevronRight,
                    contentDescription = null,
                    size = BpkIconSize.Small,
                )
            },
        )
    }

    @Test
    fun withSwitch() = snap {
        BpkCellItem(
            icon = BpkIcon.Account,
            title = "Title",
            body = "Description",
            slot = {
                BpkSwitch(
                    text = "",
                    checked = true,
                    onCheckedChange = {},
                )
            },
        )
    }

    @Test
    fun withText() = snap {
        BpkCellItem(
            icon = BpkIcon.Account,
            title = "Title",
            body = "Description",
            slot = {
                BpkText(
                    text = "Value",
                    style = BpkTheme.typography.bodyDefault,
                )
            },
        )
    }

    @Test
    fun withLogo() = snap {
        BpkCellItem(
            icon = BpkIcon.Account,
            title = "Partner Program",
            body = "Skyland Airlines",
            slot = {
                Image(
                    painter = painterResource(R.drawable.skyairlines),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(BpkTheme.colors.textPrimary),
                    modifier = Modifier.size(width = BpkSpacing.Xxl, height = BpkSpacing.Lg),
                )
            },
        )
    }

    @Test
    fun surfaceLowContrast() = snap {
        BpkCellItem(
            title = "Title",
            body = "Description",
            style = BpkCellItemStyle.SurfaceLowContrast,
        )
    }

    @Test
    fun roundedCorner() = snap {
        BpkCellItem(
            icon = BpkIcon.Account,
            title = "Title",
            body = "Description",
            corner = BpkCellItemCorner.Rounded,
        )
    }

    @Test
    fun surfaceLowContrastAndRoundedCorner() = snap {
        BpkCellItem(
            icon = BpkIcon.Account,
            title = "Title",
            body = "Description",
            onClick = {},
            style = BpkCellItemStyle.SurfaceLowContrast,
            corner = BpkCellItemCorner.Rounded,
            slot = {
                BpkIcon(
                    icon = BpkIcon.ChevronRight,
                    contentDescription = null,
                    size = BpkIconSize.Small,
                )
            },
        )
    }
}
