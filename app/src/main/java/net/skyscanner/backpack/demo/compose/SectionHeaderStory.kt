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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeader
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderButton
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderType.OnDark
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.SectionHeaderComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@SectionHeaderComponent
@ComposeStory("Default")
fun SectionHeaderStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(BpkTheme.colors.canvas)
            .fillMaxSize()
            .padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        BpkSectionHeader(
            title = stringResource(R.string.section_header_title),
        )
        BpkSectionHeader(
            title = stringResource(R.string.section_header_title),
            description = stringResource(R.string.section_header_description),
        )
        BpkSectionHeader(
            title = stringResource(R.string.section_header_title),
            description = stringResource(R.string.section_header_description),
            button = BpkSectionHeaderButton(
                text = stringResource(R.string.section_header_button_text),
                onClick = {},
            ),
        )
    }
}

@Composable
@SectionHeaderComponent
@ComposeStory("OnDark")
fun SectionHeaderOnDarkStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(BpkTheme.colors.surfaceContrast)
            .fillMaxSize()
            .padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        BpkSectionHeader(
            title = stringResource(R.string.section_header_title),
            type = OnDark,
        )
        BpkSectionHeader(
            title = stringResource(R.string.section_header_title),
            description = stringResource(R.string.section_header_description),
            type = OnDark,
        )
        BpkSectionHeader(
            title = stringResource(R.string.section_header_title),
            description = stringResource(R.string.section_header_description),
            button = BpkSectionHeaderButton(
                text = stringResource(R.string.section_header_button_text),
                onClick = {},
            ),
            type = OnDark,
        )
    }
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
@Composable
@SectionHeaderComponent
private fun SectionHeaderTabletStory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(BpkSpacing.Base),
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base),
    ) {
        BpkSectionHeader(
            title = stringResource(R.string.section_header_title),
        )
        BpkSectionHeader(
            title = stringResource(R.string.section_header_title),
            description = stringResource(R.string.section_header_description),
        )
        BpkSectionHeader(
            title = stringResource(R.string.section_header_title),
            description = stringResource(R.string.section_header_description),
            button = BpkSectionHeaderButton(
                text = stringResource(R.string.section_header_button_text),
                onClick = {},
            ),
        )
    }
}
