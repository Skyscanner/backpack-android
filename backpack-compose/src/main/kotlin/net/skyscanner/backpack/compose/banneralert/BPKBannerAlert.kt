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

package net.skyscanner.backpack.compose.banneralert

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.banneralert.internal.BannerAlert
import net.skyscanner.backpack.compose.icon.BpkIcon

enum class BPKBannerAlertType {
    Info,
    Success,
    Warning,
    Error,
}

enum class BPKBannerAlertStyle {
    Default,
    OnContrast,
}

data class CustomIcon(
    val icon: BpkIcon?,
    val accessibilityLabel: String,
)

@Composable
fun BPKBannerAlert(
    message: String,
    modifier: Modifier = Modifier,
    type: BPKBannerAlertType = BPKBannerAlertType.Info,
    icon: CustomIcon? = null,
    style: BPKBannerAlertStyle = BPKBannerAlertStyle.Default,
) {
    BannerAlert(
        modifier = modifier,
        type = type,
        message = message,
        icon = icon?.icon,
        iconContentDescription = icon?.accessibilityLabel,
        style = style,
    )
}
