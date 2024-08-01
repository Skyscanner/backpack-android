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
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.banneralert.BPKBannerAlert
import net.skyscanner.backpack.compose.banneralert.BPKBannerAlertStyle
import net.skyscanner.backpack.compose.banneralert.BPKBannerAlertType
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.demo.components.BannerAlertComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

private const val message =
    """Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt laborum."""

@Composable
@BannerAlertComponent
@ComposeStory("Default")
fun BPKBannerAlertStoryDefault(modifier: Modifier = Modifier) =
    Column(
        modifier
            .fillMaxSize()
            .background(BpkTheme.colors.surfaceDefault)
            .verticalScroll(rememberScrollState()),
    ) {
        BPKBannerAlert(
            type = BPKBannerAlertType.Info,
            message = message,
            modifier = Modifier.padding(4.dp),
        )
        BPKBannerAlert(
            type = BPKBannerAlertType.Warning,
            message = message,
            modifier = Modifier.padding(4.dp),
        )
        BPKBannerAlert(
            type = BPKBannerAlertType.Error,
            message = message,
            modifier = Modifier.padding(4.dp),
        )
        BPKBannerAlert(
            type = BPKBannerAlertType.Success,
            message = message,
            modifier = Modifier.padding(4.dp),
        )
    }

@Composable
@BannerAlertComponent
@ComposeStory("OnContrast")
fun BPKBannerAlertStoryOnContrast(modifier: Modifier = Modifier) =
    Column(
        modifier
            .fillMaxSize()
            .background(BpkTheme.colors.surfaceContrast)
            .verticalScroll(rememberScrollState()),
    ) {
        BPKBannerAlert(
            modifier = Modifier.padding(4.dp),
            type = BPKBannerAlertType.Info,
            message = message,
            style = BPKBannerAlertStyle.OnContrast,
        )
        BPKBannerAlert(
            modifier = Modifier.padding(4.dp),
            type = BPKBannerAlertType.Warning,
            message = message,
            style = BPKBannerAlertStyle.OnContrast,
        )
        BPKBannerAlert(
            modifier = Modifier.padding(4.dp),
            type = BPKBannerAlertType.Error,
            message = message,
            style = BPKBannerAlertStyle.OnContrast,
        )
        BPKBannerAlert(
            modifier = Modifier.padding(4.dp),
            type = BPKBannerAlertType.Success,
            message = message,
            style = BPKBannerAlertStyle.OnContrast,
        )
    }
