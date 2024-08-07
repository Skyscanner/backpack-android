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

package net.skyscanner.backpack.compose.banneralert

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.AccountAdd
import net.skyscanner.backpack.compose.tokens.Ai
import net.skyscanner.backpack.compose.tokens.Airline
import net.skyscanner.backpack.compose.tokens.BaggageCabin
import net.skyscanner.backpack.demo.R
import org.junit.Test

class BPKBannerAlertTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        GetBannerAlert()
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerInfo() = snap {
        GetBannerAlert(type = BpkBannerAlertType.Info)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerInfoOnContrast() = snap(background = { BpkTheme.colors.canvasContrast }) {
        GetBannerAlert(type = BpkBannerAlertType.Info, style = BpkBannerAlertStyle.OnContrast)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerError() = snap {
        GetBannerAlert(type = BpkBannerAlertType.Error)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerErrorOnContrast() = snap {
        GetBannerAlert(type = BpkBannerAlertType.Error, style = BpkBannerAlertStyle.OnContrast)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerWarning() = snap {
        GetBannerAlert(type = BpkBannerAlertType.Warning)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerWarningOnContrast() = snap {
        GetBannerAlert(type = BpkBannerAlertType.Warning, style = BpkBannerAlertStyle.OnContrast)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerSuccess() = snap {
        GetBannerAlert(type = BpkBannerAlertType.Success)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerSuccessOnContrast() = snap {
        GetBannerAlert(type = BpkBannerAlertType.Success, style = BpkBannerAlertStyle.OnContrast)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerInfoOnContrastCustomIconInfo() = snap {
        BpkBannerAlert(
            message = stringResource(id = R.string.stub_md),
            alertTypeContentDescription = stringResource(id = R.string.content_description),
            type = BpkBannerAlertType.Info,
            icon = BpkIcon.Airline,
        )
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerInfoOnContrastCustomIconError() = snap {
        BpkBannerAlert(
            message = stringResource(id = R.string.stub_md),
            alertTypeContentDescription = stringResource(id = R.string.content_description),
            type = BpkBannerAlertType.Error,
            icon = BpkIcon.BaggageCabin,
        )
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerInfoOnContrastCustomIconWarning() = snap {
        BpkBannerAlert(
            message = stringResource(id = R.string.stub_md),
            alertTypeContentDescription = stringResource(id = R.string.content_description),
            type = BpkBannerAlertType.Warning,
            icon = BpkIcon.AccountAdd,
        )
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun bannerInfoOnContrastCustomIconSuccess() = snap {
        BpkBannerAlert(
            message = stringResource(id = R.string.stub_md),
            alertTypeContentDescription = stringResource(id = R.string.content_description),
            type = BpkBannerAlertType.Success,
            icon = BpkIcon.Ai,
        )
    }

    @Composable
    fun GetBannerAlert(
        type: BpkBannerAlertType = BpkBannerAlertType.Info,
        style: BpkBannerAlertStyle = BpkBannerAlertStyle.Default,
    ) {
        BpkBannerAlert(
            message = stringResource(id = R.string.stub_md),
            alertTypeContentDescription = stringResource(id = R.string.content_description),
            type = type,
            style = style,
        )
    }
}
