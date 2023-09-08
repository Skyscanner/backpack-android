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

package net.skyscanner.backpack.compose.graphicpromotion

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.graphicpromotion.internal.BpkGraphicPromoImpl
import net.skyscanner.backpack.compose.overlay.BpkOverlayType

enum class Variant { OnDark, OnLight, }
enum class VerticalAlignment { Top, Bottom, }
enum class Type { Button, Link, }

data class Sponsor(
    val title: String,
    val logo: String,
    val accessibilityLabel: String,
)

@Composable
fun BpkGraphicPromo(
    headline: String,
    image: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    kicker: String? = null,
    subHeadline: String? = null,
    overlayType: BpkOverlayType = BpkOverlayType.SolidHigh,
    variant: Variant = Variant.OnDark,
    verticalAlignment: VerticalAlignment = VerticalAlignment.Top,
    sponsor: Sponsor? = null,
    sponsorLogo: @Composable () -> Unit? = {},
    tapAction: () -> Unit = {},
) {
    BpkGraphicPromoImpl(
        headline = headline,
        modifier = modifier,
        kicker = kicker,
        subHeadline = subHeadline,
        overlayType = overlayType,
        variant = variant,
        verticalAlignment = verticalAlignment,
        sponsor = sponsor,
        image = image,
        sponsorLogo = sponsorLogo,
        tapAction = tapAction,
    )
}
