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
import net.skyscanner.backpack.compose.banneralert.BpkBannerAlertStyle
import net.skyscanner.backpack.compose.banneralert.BpkBannerAlertType
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
    type: BpkBannerAlertType,
    message: String,
    style: BpkBannerAlertStyle,
    alertTypeContentDescription: String,
    modifier: Modifier = Modifier,
    icon: BpkIcon? = null,
) {

    val background = when (style) {
        BpkBannerAlertStyle.Default -> BpkTheme.colors.canvasContrast
        BpkBannerAlertStyle.OnContrast -> BpkTheme.colors.canvas
    }

    val iconFinal = icon ?: when (type) {
        BpkBannerAlertType.Info -> BpkIcon.InformationCircle
        BpkBannerAlertType.Success -> BpkIcon.TickCircle
        BpkBannerAlertType.Warning -> BpkIcon.InformationCircle
        BpkBannerAlertType.Error -> BpkIcon.CloseCircle
    }

    val tint = when (type) {
        BpkBannerAlertType.Info -> BpkTheme.colors.textSecondary
        BpkBannerAlertType.Success -> BpkTheme.colors.statusSuccessSpot
        BpkBannerAlertType.Warning -> BpkTheme.colors.statusWarningSpot
        BpkBannerAlertType.Error -> BpkTheme.colors.statusDangerSpot
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
                contentDescription = alertTypeContentDescription,
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
    @PreviewParameter(BannerAlertPreviewParamsProvider::class) preview: Pair<BpkBannerAlertType, BpkBannerAlertStyle>,
) {
    BannerAlert(
        type = preview.first,
        message = "Hello world!",
        alertTypeContentDescription = "Content description",
        icon = null,
        style = preview.second,
    )
}

private class BannerAlertPreviewParamsProvider : PreviewParameterProvider<Pair<BpkBannerAlertType, BpkBannerAlertStyle>> {
    override val values: Sequence<Pair<BpkBannerAlertType, BpkBannerAlertStyle>>
        get() = sequenceOf(
            Pair(BpkBannerAlertType.Info, BpkBannerAlertStyle.Default),
            Pair(BpkBannerAlertType.Info, BpkBannerAlertStyle.OnContrast),

            Pair(BpkBannerAlertType.Success, BpkBannerAlertStyle.Default),
            Pair(BpkBannerAlertType.Success, BpkBannerAlertStyle.OnContrast),

            Pair(BpkBannerAlertType.Warning, BpkBannerAlertStyle.Default),
            Pair(BpkBannerAlertType.Warning, BpkBannerAlertStyle.OnContrast),

            Pair(BpkBannerAlertType.Error, BpkBannerAlertStyle.Default),
            Pair(BpkBannerAlertType.Error, BpkBannerAlertStyle.OnContrast),
        )
}
