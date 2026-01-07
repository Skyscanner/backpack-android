/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.insetbanner

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import net.skyscanner.backpack.compose.insetbanner.internal.BpkSponsoredInsetBannerImpl

@Composable
fun BpkSponsoredInsetBanner(
    variant: BpkSponsoredInsetBannerVariant,
    callToAction: BpkSponsoredInsetBannerCTA,
    backgroundColor: Color,
    logo: @Composable (() -> Unit),
    modifier: Modifier = Modifier,
    title: String? = null,
    subHeadline: String? = null,
    image: @Composable (() -> Unit)? = null,
) {
    BpkSponsoredInsetBannerImpl(
        modifier = modifier,
        backgroundColor = backgroundColor,
        variant = variant,
        title = title,
        subHeadline = subHeadline,
        callToAction = callToAction,
        image = image,
        logo = logo,
    )
}

data class BpkSponsoredInsetBannerCTA(val text: String, val accessibilityLabel: String, val onClick: () -> Unit)

enum class BpkSponsoredInsetBannerVariant { OnLight, OnDark }
