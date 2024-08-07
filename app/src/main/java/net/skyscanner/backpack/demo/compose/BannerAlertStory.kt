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

package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.banneralert.BpkBannerAlert
import net.skyscanner.backpack.compose.banneralert.BpkBannerAlertStyle
import net.skyscanner.backpack.compose.banneralert.BpkBannerAlertType
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.BannerAlertComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@BannerAlertComponent
@ComposeStory("Default")
fun BPKBannerAlertStoryDefault(modifier: Modifier = Modifier) {
    StoryContent(style = BpkBannerAlertStyle.Default, background = BpkTheme.colors.surfaceDefault)
}

@Composable
@BannerAlertComponent
@ComposeStory("OnContrast")
fun BPKBannerAlertStoryOnContrast(modifier: Modifier = Modifier) {
    StoryContent(style = BpkBannerAlertStyle.OnContrast, background = BpkTheme.colors.surfaceContrast)
}

@Composable
private fun StoryContent(
    style: BpkBannerAlertStyle,
    background: Color,
    modifier: Modifier = Modifier,
) {
    val message = stringResource(id = R.string.stub_md)
    val typeContentDescription = stringResource(id = R.string.content_description)
    Column(
        modifier
            .fillMaxSize()
            .background(background)
            .verticalScroll(rememberScrollState()),
    ) {
        BpkBannerAlert(
            modifier = Modifier.padding(BpkSpacing.Sm),
            type = BpkBannerAlertType.Info,
            message = message,
            alertTypeContentDescription = typeContentDescription,
            style = style,
        )
        BpkBannerAlert(
            modifier = Modifier.padding(BpkSpacing.Sm),
            type = BpkBannerAlertType.Warning,
            message = message,
            alertTypeContentDescription = typeContentDescription,
            style = style,
        )
        BpkBannerAlert(
            modifier = Modifier.padding(BpkSpacing.Sm),
            type = BpkBannerAlertType.Error,
            message = message,
            alertTypeContentDescription = typeContentDescription,
            style = style,
        )
        BpkBannerAlert(
            modifier = Modifier.padding(BpkSpacing.Sm),
            type = BpkBannerAlertType.Success,
            message = message,
            alertTypeContentDescription = typeContentDescription,
            style = style,
        )
    }
}
