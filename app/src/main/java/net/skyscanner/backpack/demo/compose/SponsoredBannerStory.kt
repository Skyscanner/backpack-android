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
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.SponsoredBannerComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.meta.StoryKind

@Composable
@SponsoredBannerComponent
@ComposeStory("OnLight")
fun SponsoredBannerStoryOnLight() {
    DefaultSponsoredBannerSample(
        variant = BpkSponsoredBannerVariant.OnLight,
        title = stringResource(R.string.sponsored_banner_title),
        subHeadline = stringResource(R.string.sponsored_banner_sub_headline),
        callToAction = BpkSponsoredBannerCTA(
            stringResource(R.string.sponsored_banner_cta_text),
            stringResource(R.string.sponsored_banner_cta_accessibility_label),
        ),
        body = stringResource(R.string.sponsored_banner_body),
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory("OnDark")
fun SponsoredBannerStoryOnDark() {
    DefaultSponsoredBannerSample(
        variant = BpkSponsoredBannerVariant.OnDark,
        title = stringResource(R.string.sponsored_banner_title),
        subHeadline = stringResource(R.string.sponsored_banner_sub_headline),
        callToAction = BpkSponsoredBannerCTA(
            stringResource(R.string.sponsored_banner_cta_text),
            stringResource(R.string.sponsored_banner_cta_accessibility_label),
        ),
        body = stringResource(R.string.sponsored_banner_body),
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory("Without Title")
fun SponsoredBannerStoryWithoutTitle(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = BpkSponsoredBannerVariant.OnDark,
        subHeadline = stringResource(R.string.sponsored_banner_sub_headline),
        callToAction = BpkSponsoredBannerCTA(
            stringResource(R.string.sponsored_banner_cta_text),
            stringResource(R.string.sponsored_banner_cta_accessibility_label),
        ),
        body = stringResource(R.string.sponsored_banner_body),
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory("Without subHeadline")
fun SponsoredBannerStoryWithoutSubHeadline(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = BpkSponsoredBannerVariant.OnDark,
        title = stringResource(R.string.sponsored_banner_title),
        callToAction = BpkSponsoredBannerCTA(
            stringResource(R.string.sponsored_banner_cta_text),
            stringResource(R.string.sponsored_banner_cta_accessibility_label),
        ),
        body = stringResource(R.string.sponsored_banner_body),
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory("Without Title and subHeadline")
fun SponsoredBannerStoryWithoutTitleAndSubHeadline(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = BpkSponsoredBannerVariant.OnDark,
        callToAction = BpkSponsoredBannerCTA(
            stringResource(R.string.sponsored_banner_cta_text),
            stringResource(R.string.sponsored_banner_cta_accessibility_label),
        ),
        body = stringResource(R.string.sponsored_banner_body),
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory("Without CTA")
fun SponsoredBannerStoryWithoutCTA(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = BpkSponsoredBannerVariant.OnDark,
        title = stringResource(R.string.sponsored_banner_title),
        subHeadline = stringResource(R.string.sponsored_banner_sub_headline),
        body = stringResource(R.string.sponsored_banner_body),
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory("Without Body")
fun SponsoredBannerStoryWithoutBody(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = BpkSponsoredBannerVariant.OnDark,
        title = stringResource(R.string.sponsored_banner_title),
        subHeadline = stringResource(R.string.sponsored_banner_sub_headline),
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory(kind = StoryKind.ScreenshotOnly)
fun SponsoredBannerStoryWithoutTitleAndSubHeaderAndCallToAction(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = BpkSponsoredBannerVariant.OnDark,
    )
}

@Composable
@SponsoredBannerComponent
@ComposeStory("Without Logo")
fun SponsoredBannerStoryWithoutLogo(modifier: Modifier = Modifier) {
    DefaultSponsoredBannerSample(
        variant = BpkSponsoredBannerVariant.OnDark,
        title = stringResource(R.string.sponsored_banner_title),
        subHeadline = stringResource(R.string.sponsored_banner_sub_headline),
        callToAction = BpkSponsoredBannerCTA(
            stringResource(R.string.sponsored_banner_cta_text),
            stringResource(R.string.sponsored_banner_cta_accessibility_label),
        ),
        body = stringResource(R.string.sponsored_banner_body),
        showImage = false,
    )
}

private fun getPartnerLogo(pred: Boolean, variant: BpkSponsoredBannerVariant): @Composable (() -> Unit)? =
    if (pred) {
        {
            if (variant == BpkSponsoredBannerVariant.OnDark) {
                Image(
                    painter = painterResource(R.drawable.easyjet_horizontal_logo),
                    contentDescription = stringResource(R.string.sponsored_banner_cta_accessibility_label),
                    contentScale = ContentScale.Fit,
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.spirit_horizontal_logo),
                    contentDescription = stringResource(R.string.sponsored_banner_cta_accessibility_label),
                    contentScale = ContentScale.Fit,
                )
            }
        }
    } else null

@Composable
internal fun DefaultSponsoredBannerSample(
    variant: BpkSponsoredBannerVariant,
    modifier: Modifier = Modifier,
    title: String? = null,
    subHeadline: String? = null,
    callToAction: BpkSponsoredBannerCTA? = null,
    showImage: Boolean = true,
    body: String? = null,
) {
    Box(modifier = modifier.padding(BpkSpacing.Lg)) {
        BpkSponsoredBanner(
            variant = variant,
            backgroundColor = if (variant == BpkSponsoredBannerVariant.OnDark) Color(EASY_JET_COLOR_HEX) else Color(
                SPIRIT_COLOR_HEX,
            ),
            title = title,
            subHeadline = subHeadline,
            callToAction = callToAction,
            body = body,
            content = getPartnerLogo(showImage, variant),
        )
    }
}

const val EASY_JET_COLOR_HEX = 0xFFFF6601
const val SPIRIT_COLOR_HEX = 0xFFFFE300
