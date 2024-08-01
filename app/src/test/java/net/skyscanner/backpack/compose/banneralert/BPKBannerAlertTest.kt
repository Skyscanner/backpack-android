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

import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.AccountAdd
import net.skyscanner.backpack.compose.tokens.Ai
import net.skyscanner.backpack.compose.tokens.Airline
import net.skyscanner.backpack.compose.tokens.BaggageCabin
import org.junit.Test

private const val message =
    """Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt laborum."""

class BPKBannerAlertTest : BpkSnapshotTest() {

    @Test
    fun default() = snap {
        BPKBannerAlert(message = message)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerInfo() = snap {
        BPKBannerAlert(message = message, type = BPKBannerAlertType.Info)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerInfoOnContrast() = snap(background = { BpkTheme.colors.canvasContrast }) {
        BPKBannerAlert(message = message, type = BPKBannerAlertType.Info, style = BPKBannerAlertStyle.OnContrast)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerError() = snap {
        BPKBannerAlert(message = message, type = BPKBannerAlertType.Error)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerErrorOnContrast() = snap {
        BPKBannerAlert(message = message, type = BPKBannerAlertType.Error, style = BPKBannerAlertStyle.OnContrast)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerWarning() = snap {
        BPKBannerAlert(message = message, type = BPKBannerAlertType.Warning)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerWarningOnContrast() = snap {
        BPKBannerAlert(message = message, type = BPKBannerAlertType.Warning, style = BPKBannerAlertStyle.OnContrast)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerSuccess() = snap {
        BPKBannerAlert(message = message, type = BPKBannerAlertType.Success)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerSuccessOnContrast() = snap {
        BPKBannerAlert(message = message, type = BPKBannerAlertType.Success, style = BPKBannerAlertStyle.OnContrast)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerInfoOnContrastCustomIconInfo() = snap {
        BPKBannerAlert(
            message = message, type = BPKBannerAlertType.Info, icon = CustomIcon(
                icon = BpkIcon.Airline,
                accessibilityLabel = "Airline",
            ),
        )
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerInfoOnContrastCustomIconError() = snap {
        BPKBannerAlert(
            message = message, type = BPKBannerAlertType.Error,
            icon = CustomIcon(
                icon = BpkIcon.BaggageCabin,
                accessibilityLabel = "Airline",
            ),
        )
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun bannerInfoOnContrastCustomIconWarning() = snap {
        BPKBannerAlert(
            message = message, type = BPKBannerAlertType.Warning,
            icon = CustomIcon(
                icon = BpkIcon.AccountAdd,
                accessibilityLabel = "Airline",
            ),
        )
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    fun bannerInfoOnContrastCustomIconSuccess() = snap {
        BPKBannerAlert(
            message = message, type = BPKBannerAlertType.Success,
            icon = CustomIcon(
                icon = BpkIcon.Ai,
                accessibilityLabel = "Airline",
            ),
        )
    }
}
