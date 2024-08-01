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

package net.skyscanner.backpack.compose.banneralert.internal

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import net.skyscanner.backpack.compose.banneralert.BPKBannerAlertStyle
import net.skyscanner.backpack.compose.banneralert.BPKBannerAlertType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.CloseCircle
import net.skyscanner.backpack.compose.tokens.InformationCircle
import net.skyscanner.backpack.compose.tokens.TickCircle

@Composable
internal fun BannerAlert(
    type: BPKBannerAlertType,
    message: String,
    style: BPKBannerAlertStyle,
    modifier: Modifier = Modifier,
    icon: BpkIcon? = null,
    iconContentDescription: String? = null,
) {

    val background = when (style) {
        BPKBannerAlertStyle.Default -> BpkTheme.colors.canvasContrast
        BPKBannerAlertStyle.OnContrast -> BpkTheme.colors.canvas
    }

    val iconFinal = icon ?: when (type) {
        BPKBannerAlertType.Info -> BpkIcon.InformationCircle
        BPKBannerAlertType.Success -> BpkIcon.TickCircle
        BPKBannerAlertType.Warning -> BpkIcon.InformationCircle
        BPKBannerAlertType.Error -> BpkIcon.CloseCircle
    }

    val tint = when (type) {
        BPKBannerAlertType.Info -> BpkTheme.colors.textSecondary
        BPKBannerAlertType.Success -> BpkTheme.colors.statusSuccessSpot
        BPKBannerAlertType.Warning -> BpkTheme.colors.statusWarningSpot
        BPKBannerAlertType.Error -> BpkTheme.colors.statusDangerSpot
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(BpkBorderRadius.Sm)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(background)
                .padding(horizontal = BpkSpacing.Base, vertical = BpkSpacing.Md),
        ) {
            BpkIcon(
                icon = iconFinal,
                tint = tint,
                contentDescription = iconContentDescription,
            )
            BpkText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = BpkSpacing.Md),
                text = message,
                textAlign = TextAlign.Start,
                style = BpkTheme.typography.footnote,
                color = BpkTheme.colors.textPrimary,
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun BannerAlertPreview(
    @PreviewParameter(BannerAlertPreviewParamsProvider::class) preview: Pair<BPKBannerAlertType, BPKBannerAlertStyle>,
) {
    BannerAlert(
        type = preview.first,
        message = """Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt laborum.""",
        icon = null,
        style = preview.second,
    )
}

private class BannerAlertPreviewParamsProvider : PreviewParameterProvider<Pair<BPKBannerAlertType, BPKBannerAlertStyle>> {
    override val values: Sequence<Pair<BPKBannerAlertType, BPKBannerAlertStyle>>
        get() = sequenceOf(
            Pair(BPKBannerAlertType.Info, BPKBannerAlertStyle.Default),
            Pair(BPKBannerAlertType.Info, BPKBannerAlertStyle.OnContrast),

            Pair(BPKBannerAlertType.Success, BPKBannerAlertStyle.Default),
            Pair(BPKBannerAlertType.Success, BPKBannerAlertStyle.OnContrast),

            Pair(BPKBannerAlertType.Warning, BPKBannerAlertStyle.Default),
            Pair(BPKBannerAlertType.Warning, BPKBannerAlertStyle.OnContrast),

            Pair(BPKBannerAlertType.Error, BPKBannerAlertStyle.Default),
            Pair(BPKBannerAlertType.Error, BPKBannerAlertStyle.OnContrast),
        )
}
