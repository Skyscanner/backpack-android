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

package net.skyscanner.backpack.compose.sponsoredbanner

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import net.skyscanner.backpack.compose.sponsoredbanner.internal.BpkSponsoredBannerImpl

@Composable
fun BpkSponsoredBanner(
    variant: BpkSponsoredBannerVariant,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    title: String? = null,
    subHeadline: String? = null,
    callToAction: BpkSponsoredBannerCTA? = null,
    body: String? = null,
    logo: @Composable (() -> Unit)? = null,
) {
    BpkSponsoredBannerImpl(
        backgroundColor = backgroundColor,
        variant = variant,
        title = title,
        subHeadline = subHeadline,
        callToAction = callToAction,
        body = body,
        modifier = modifier,
        logo = logo,
    )
}

data class BpkSponsoredBannerCTA(val text: String, val accessibilityLabel: String)
enum class BpkSponsoredBannerVariant { OnLight, OnDark }
