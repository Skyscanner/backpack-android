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

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.compose.sponsoredbanner.BpkSponsoredBanner
import net.skyscanner.backpack.compose.sponsoredbanner.CallToAction
import net.skyscanner.backpack.compose.sponsoredbanner.Variant
import net.skyscanner.backpack.demo.components.SponsoredBannerComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@SponsoredBannerComponent
@ComposeStory
fun SponsoredBannerStoryOnDark(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = Variant.OnDark,
        title = "Title",
        subheadline = "Subheadline",
        callToAction = CallToAction("Sponsored", ""),
        body = "You can change your destination, date of travel, or both, with no change fee. Valid for all new bookings made up to 31 May for travel between now and 31 December 2020.",
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory
fun SponsoredBannerStoryOnLight(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = Variant.OnLight,
        title = "Title",
        subheadline = "Subheadline",
        callToAction = CallToAction("Sponsored", ""),
        body = "You can change your destination, date of travel, or both, with no change fee. Valid for all new bookings made up to 31 May for travel between now and 31 December 2020.",
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory
fun SponsoredBannerStoryWithoutTitle(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = Variant.OnDark,
        subheadline = "subheadline",
        callToAction = CallToAction("Sponsored", ""),
        body = "You can change your destination, date of travel, or both, with no change fee. Valid for all new bookings made up to 31 May for travel between now and 31 December 2020.",
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory
fun SponsoredBannerStoryWithoutSubheadline(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = Variant.OnDark,
        title = "Title",
        callToAction = CallToAction("Sponsored", ""),
        body = "You can change your destination, date of travel, or both, with no change fee. Valid for all new bookings made up to 31 May for travel between now and 31 December 2020.",
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory
fun SponsoredBannerStoryWithoutTitleAndSubheadline(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = Variant.OnDark,
        callToAction = CallToAction("Sponsored", ""),
        body = "You can change your destination, date of travel, or both, with no change fee. Valid for all new bookings made up to 31 May for travel between now and 31 December 2020.",
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory
fun SponsoredBannerStoryWithoutCallToAction(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = Variant.OnDark,
        title = "Title",
        subheadline = "Subheadline",
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory
fun SponsoredBannerStoryWithoutTitleAndSubHeaderAndCallToAction(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = Variant.OnDark,
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory
fun SponsoredBannerStoryWithoutLogo(modifier: Modifier = Modifier) {
    EmptySponsoredBannerSample(
        variant = Variant.OnDark,
        title = "Title",
        subheadline = "Subheadline",
        callToAction = CallToAction("Sponsored", ""),
        body = "You can change your destination, date of travel, or both, with no change fee. Valid for all new bookings made up to 31 May for travel between now and 31 December 2020.",
    )
}

@Composable
internal fun EmptySponsoredBannerSample(
    variant: Variant,
    modifier: Modifier = Modifier,
    title: String? = null,
    subheadline: String? = null,
    callToAction: CallToAction? = null,
    body: String? = null,
) {
    BpkSponsoredBanner(
        variant = variant,
        backgroundColor = if (variant == Variant.OnDark) Color(0xFFFF6601) else Color(0xFFFFE300),
        modifier = modifier,
        title = title,
        subheadline = subheadline,
        callToAction = callToAction,
        body = body,
    ) {
    }
}

@Composable
internal fun DefaultSponsoredBannerSample(
    variant: Variant,
    modifier: Modifier = Modifier,
    title: String? = null,
    subheadline: String? = null,
    callToAction: CallToAction? = null,
    body: String? = null,
) {
    BpkSponsoredBanner(
        variant = variant,
        backgroundColor = if (variant == Variant.OnDark) Color(0xFFFF6601) else Color(0xFFFFE300),
        modifier = modifier,
        title = title,
        subheadline = subheadline,
        callToAction = callToAction,
        body = body,
    ) {
        Image(
            painter = if (variant == Variant.OnDark) painterResource(R.drawable.easyjet_horizontal_logo) else painterResource(
                R.drawable.spirit_horizontal_logo,
            ),
            contentDescription = "image description",
            contentScale = ContentScale.Fit,
        )
    }
}
