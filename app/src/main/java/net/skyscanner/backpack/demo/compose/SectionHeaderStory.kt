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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeader
import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderType.OnDark
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.SectionHeaderComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.toast.BpkToast

@Composable
@SectionHeaderComponent
@ComposeStory("Default")
fun SectionHeaderStory(modifier: Modifier = Modifier) {
    BpkSectionHeader(
        title = stringResource(R.string.section_header_title),
    )
}

@Composable
@SectionHeaderComponent
@ComposeStory("OnDark")
fun SectionHeaderOnDarkStory(modifier: Modifier = Modifier) {
    BpkSectionHeader(
        title = stringResource(R.string.section_header_title),
        type = OnDark,
    )
}

@Composable
@SectionHeaderComponent
@ComposeStory("Default with Description")
fun SectionHeaderWithDescriptionStory(modifier: Modifier = Modifier) {
    BpkSectionHeader(
        title = stringResource(R.string.section_header_title),
        description = stringResource(R.string.section_header_description),
    )
}

@Composable
@SectionHeaderComponent
@ComposeStory("OnDark with Description")
fun SectionHeaderOnDarkWithDescriptionStory(modifier: Modifier = Modifier) {
    BpkSectionHeader(
        title = stringResource(R.string.section_header_title),
        description = stringResource(R.string.section_header_description),
        type = OnDark,
    )
}

@Composable
@SectionHeaderComponent
@ComposeStory("Default with Description and Button")
fun SectionHeaderWithDescriptionAndButtonStory(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    BpkSectionHeader(
        title = stringResource(R.string.section_header_title),
        description = stringResource(R.string.section_header_description),
        buttonText = stringResource(R.string.section_header_button_text),
        onClick = {
            BpkToast.makeText(context, R.string.section_header_button_click_feedback_msg, Toast.LENGTH_SHORT).show()
        },
    )
}

@Composable
@SectionHeaderComponent
@ComposeStory("OnDark with Description and Button")
fun SectionHeaderOnDarkWithDescriptionAndButtonStory(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    BpkSectionHeader(
        title = stringResource(R.string.section_header_title),
        description = stringResource(R.string.section_header_description),
        buttonText = stringResource(R.string.section_header_button_text),
        onClick = {
            BpkToast.makeText(context, R.string.section_header_button_click_feedback_msg, Toast.LENGTH_SHORT).show()
        },
        type = OnDark,
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
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        BpkSectionHeader(
            title = stringResource(R.string.section_header_title),
            description = stringResource(R.string.section_header_description),
        )
        BpkSectionHeader(
            title = stringResource(R.string.section_header_title),
            description = stringResource(R.string.section_header_description),
            type = OnDark,
        )
        BpkSectionHeader(
            title = stringResource(R.string.section_header_title),
            description = stringResource(R.string.section_header_description),
            buttonText = stringResource(R.string.section_header_button_text),
            onClick = {
                BpkToast.makeText(context, R.string.section_header_button_click_feedback_msg, Toast.LENGTH_SHORT).show()
            },
        )
        BpkSectionHeader(
            title = stringResource(R.string.section_header_title),
            description = stringResource(R.string.section_header_description),
            buttonText = stringResource(R.string.section_header_button_text),
            onClick = {
                BpkToast.makeText(context, R.string.section_header_button_click_feedback_msg, Toast.LENGTH_SHORT).show()
            },
            type = OnDark,
        )
    }
}
