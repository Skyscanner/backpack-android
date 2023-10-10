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
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.sponsoredbanner.BpkSponsoredBanner
import net.skyscanner.backpack.compose.sponsoredbanner.BpkSponsoredBannerCTA
import net.skyscanner.backpack.compose.sponsoredbanner.BpkSponsoredBannerVariant
import net.skyscanner.backpack.compose.sponsoredbanner.BpkSponsoredBannerVariant.OnDark
import net.skyscanner.backpack.compose.sponsoredbanner.BpkSponsoredBannerVariant.OnLight
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.SponsoredBannerComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@SponsoredBannerComponent
@ComposeStory("OnLight")
fun SponsoredBannerStoryOnLight() {
    DefaultSponsoredBannerSample(
        variant = OnLight,
        title = stringResource(R.string.sponsored_banner_title),
        subHeadline = stringResource(R.string.sponsored_banner_sub_headline),
        callToAction = BpkSponsoredBannerCTA(
            stringResource(R.string.sponsored_banner_cta_text),
            stringResource(R.string.sponsored_banner_cta_accessibility_label),
        ),
        body = stringResource(R.string.sponsored_banner_body),
        logo = getPartnerLogo(variant = OnLight),
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory("OnDark")
fun SponsoredBannerStoryOnDark() {
    DefaultSponsoredBannerSample(
        variant = OnDark,
        title = stringResource(R.string.sponsored_banner_title),
        subHeadline = stringResource(R.string.sponsored_banner_sub_headline),
        callToAction = BpkSponsoredBannerCTA(
            stringResource(R.string.sponsored_banner_cta_text),
            stringResource(R.string.sponsored_banner_cta_accessibility_label),
        ),
        body = stringResource(R.string.sponsored_banner_body),
        logo = getPartnerLogo(variant = OnDark),
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory("Without Title")
fun SponsoredBannerStoryWithoutTitle(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = OnDark,
        subHeadline = stringResource(R.string.sponsored_banner_sub_headline),
        callToAction = BpkSponsoredBannerCTA(
            stringResource(R.string.sponsored_banner_cta_text),
            stringResource(R.string.sponsored_banner_cta_accessibility_label),
        ),
        body = stringResource(R.string.sponsored_banner_body),
        logo = getPartnerLogo(variant = OnDark),
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory("Without subHeadline")
fun SponsoredBannerStoryWithoutSubHeadline(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = OnDark,
        title = stringResource(R.string.sponsored_banner_title),
        callToAction = BpkSponsoredBannerCTA(
            stringResource(R.string.sponsored_banner_cta_text),
            stringResource(R.string.sponsored_banner_cta_accessibility_label),
        ),
        body = stringResource(R.string.sponsored_banner_body),
        logo = getPartnerLogo(variant = OnDark),
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory("Without Title and subHeadline")
fun SponsoredBannerStoryWithoutTitleAndSubHeadline(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = OnDark,
        callToAction = BpkSponsoredBannerCTA(
            stringResource(R.string.sponsored_banner_cta_text),
            stringResource(R.string.sponsored_banner_cta_accessibility_label),
        ),
        body = stringResource(R.string.sponsored_banner_body),
        logo = getPartnerLogo(variant = OnDark),
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory("Without CTA")
fun SponsoredBannerStoryWithoutCTA(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = OnDark,
        title = stringResource(R.string.sponsored_banner_title),
        subHeadline = stringResource(R.string.sponsored_banner_sub_headline),
        body = stringResource(R.string.sponsored_banner_body),
        logo = getPartnerLogo(variant = OnDark),
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory("Without Body")
fun SponsoredBannerStoryWithoutBody(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = OnDark,
        title = stringResource(R.string.sponsored_banner_title),
        subHeadline = stringResource(R.string.sponsored_banner_sub_headline),
        callToAction = BpkSponsoredBannerCTA(
            stringResource(R.string.sponsored_banner_cta_text),
            stringResource(R.string.sponsored_banner_cta_accessibility_label),
        ),
        logo = getPartnerLogo(variant = OnDark),
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory("Without Logo")
fun SponsoredBannerStoryWithoutLogo(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = OnDark,
        title = stringResource(R.string.sponsored_banner_title),
        subHeadline = stringResource(R.string.sponsored_banner_sub_headline),
        callToAction = BpkSponsoredBannerCTA(
            stringResource(R.string.sponsored_banner_cta_text),
            stringResource(R.string.sponsored_banner_cta_accessibility_label),
        ),
        body = stringResource(R.string.sponsored_banner_body),
        logo = null,
    )
}

@Composable
private fun getPartnerLogo(variant: BpkSponsoredBannerVariant): @Composable () -> Unit = {
    Image(
        painter = when (variant) {
            OnDark -> painterResource(R.drawable.sponsored_banner_skyland_white)
            OnLight -> painterResource(R.drawable.sponsored_banner_skyland_black)
        },
        contentDescription = stringResource(R.string.sponsored_banner_cta_accessibility_label),
        contentScale = ContentScale.Fit,
    )
}

@Composable
internal fun DefaultSponsoredBannerSample(
    variant: BpkSponsoredBannerVariant,
    modifier: Modifier = Modifier,
    title: String? = null,
    subHeadline: String? = null,
    callToAction: BpkSponsoredBannerCTA? = null,
    body: String? = null,
    logo: @Composable (() -> Unit)? = null,
) {
    Box(modifier = modifier.padding(BpkSpacing.Lg)) {
        BpkSponsoredBanner(
            variant = variant,
            backgroundColor = when (variant) {
                OnDark -> Color(BACKGROUND_ONDARK_COLOR_HEX)
                OnLight -> Color(BACKGROUND_ONLIGHT_COLOR_HEX)
            },
            title = title,
            subHeadline = subHeadline,
            callToAction = callToAction,
            body = body,
            logo = logo,
        )
    }
}

const val BACKGROUND_ONDARK_COLOR_HEX = 0xFFFF6601
const val BACKGROUND_ONLIGHT_COLOR_HEX = 0xFFFFE300
