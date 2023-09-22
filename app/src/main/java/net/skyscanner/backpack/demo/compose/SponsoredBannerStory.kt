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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.compose.sponsoredbanner.BpkSponsoredBanner
import net.skyscanner.backpack.compose.sponsoredbanner.CallToAction
import net.skyscanner.backpack.compose.sponsoredbanner.Variant
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.components.SponsoredBannerComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.meta.StoryKind

@Composable
@SponsoredBannerComponent
@ComposeStory("On Dark")
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
@ComposeStory("On Light")
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
@ComposeStory("Without Title")
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
@ComposeStory("Without Subheadline")
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
@ComposeStory("Without Title and Subheadline")
fun SponsoredBannerStoryWithoutTitleAndSubheadline(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = Variant.OnDark,
        callToAction = CallToAction("Sponsored", ""),
        body = "You can change your destination, date of travel, or both, with no change fee. Valid for all new bookings made up to 31 May for travel between now and 31 December 2020.",
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory("Without Call to Action")
fun SponsoredBannerStoryWithoutCallToAction(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = Variant.OnDark,
        title = "Title",
        subheadline = "Subheadline",
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory(kind = StoryKind.ScreenshotOnly)
fun SponsoredBannerStoryWithoutTitleAndSubHeaderAndCallToAction(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = Variant.OnDark,
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory("Without Logo")
fun SponsoredBannerStoryWithoutLogo(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = Variant.OnDark,
        title = "Title",
        subheadline = "Subheadline",
        callToAction = CallToAction("Sponsored", ""),
        body = "You can change your destination, date of travel, or both, with no change fee. Valid for all new bookings made up to 31 May for travel between now and 31 December 2020.",
        showImage = false,
    )
}

private fun getPartnerLogo(pred: Boolean, variant: Variant): @Composable (() -> Unit)? =
    if (pred) {
        {
            if (variant == Variant.OnDark) {
                Image(
                    painter = painterResource(R.drawable.easyjet_horizontal_logo),
                    contentDescription = "image description",
                    contentScale = ContentScale.Fit,
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.spirit_horizontal_logo),
                    contentDescription = "image description",
                    contentScale = ContentScale.Fit,
                )
            }
        }
    } else null

@Composable
internal fun DefaultSponsoredBannerSample(
    variant: Variant,
    modifier: Modifier = Modifier,
    title: String? = null,
    subheadline: String? = null,
    callToAction: CallToAction? = null,
    showImage: Boolean = true,
    body: String? = null,
) {
    Box(modifier = Modifier.padding(BpkSpacing.Lg)) {
        BpkSponsoredBanner(
            variant = variant,
            backgroundColor = if (variant == Variant.OnDark) Color(0xFFFF6601) else Color(0xFFFFE300),
            title = title,
            subheadline = subheadline,
            callToAction = callToAction,
            body = body,
            content = getPartnerLogo(showImage, variant),
        )
    }
}
