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

package net.skyscanner.backpack.demo.compose

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeader
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderButton
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderIcon
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.Heart
import net.skyscanner.backpack.demo.components.SectionHeaderComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@SectionHeaderComponent
@ComposeStory("Default")
fun PreViewDefault(modifier: Modifier = Modifier) {
    BpkSectionHeader(
        modifier = modifier
            .padding(horizontal = BpkSpacing.Lg, vertical = BpkSpacing.Md),
        title = "Section title",
        description = "Description about this section (optional)",
    )
}

@Composable
@SectionHeaderComponent
@ComposeStory("Default with Button")
fun PreViewWithButton(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    BpkSectionHeader(
        modifier = modifier
            .padding(horizontal = BpkSpacing.Lg, vertical = BpkSpacing.Md),
        title = "Section title",
        description = "Description about this section (optional)",
        button = BpkSectionHeaderButton(
            icon = BpkSectionHeaderIcon(
                bpkIcon = BpkIcon.Heart,
                contentDescription = "heart icon",
            ),
            buttonText = "Action",
            onClickAction = {
                Toast.makeText(context, "Click!", Toast.LENGTH_SHORT).show()
            },
        ),
    )
}

@Preview(
    name = "Tablet - Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    locale = "en",
    device = Devices.TABLET,
)

@Preview(
    name = "Tablet - Light Mode RTL",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    locale = "ar",
    device = Devices.TABLET,
)
@Preview(
    name = "Tablet - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    locale = "en",
    device = Devices.TABLET,
)
@SectionHeaderComponent
@Composable
private fun PreViewTablet(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .padding(horizontal = BpkSpacing.Lg, vertical = BpkSpacing.Md),
        verticalArrangement = Arrangement.spacedBy(16.dp),) {
        BpkSectionHeader(
            title = "Section title",
            description = "Description about this section (optional)",
        )
        BpkSectionHeader(
            title = "Section title",
            description = "Description about this section (optional)",
            button = BpkSectionHeaderButton(
                icon = BpkSectionHeaderIcon(
                    bpkIcon = BpkIcon.Heart,
                    contentDescription = "heart icon",
                ),
                buttonText = "Action",
                onClickAction = {
                    Toast.makeText(context, "Click!", Toast.LENGTH_SHORT).show()
                },
            ),
        )
    }
}
