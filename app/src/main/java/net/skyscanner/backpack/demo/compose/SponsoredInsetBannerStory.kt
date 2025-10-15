/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.insetbanner.BpkSponsoredInsetBannerVariant.OnDark
import net.skyscanner.backpack.compose.insetbanner.BpkSponsoredInsetBannerVariant.OnLight
import net.skyscanner.backpack.compose.insetbanner.BpkSponsoredInsetBanner
import net.skyscanner.backpack.compose.insetbanner.BpkSponsoredInsetBannerCTA
import net.skyscanner.backpack.compose.insetbanner.BpkSponsoredInsetBannerVariant
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.InsetBannerComponent
import net.skyscanner.backpack.demo.meta.ComposeStory

@Composable
@InsetBannerComponent
@ComposeStory("Sponsored OnLight With Image")
fun SponsoredInsetBannerStoryOnLightWithImage(modifier: Modifier = Modifier) {
    DefaultSponsoredInsetBannerSample(
        variant = OnLight,
        title = stringResource(R.string.inset_banner_title),
        subHeadline = stringResource(R.string.inset_banner_sub_headline),
        callToAction = BpkSponsoredInsetBannerCTA(
            text = stringResource(R.string.inset_banner_cta_text),
            accessibilityLabel = stringResource(R.string.inset_banner_cta_accessibility_label),
            onClick = {},
        ),
        image = getImage(),
        logo = getPartnerLogo(variant = OnLight),
    )
}

@Composable
@InsetBannerComponent
@ComposeStory("Sponsored OnLight without Image")
fun SponsoredInsetBannerStoryOnLightWithoutImage() {
    DefaultSponsoredInsetBannerSample(
        variant = OnLight,
        title = stringResource(R.string.inset_banner_title),
        subHeadline = stringResource(R.string.inset_banner_sub_headline),
        callToAction = BpkSponsoredInsetBannerCTA(
            text = stringResource(R.string.inset_banner_cta_text),
            accessibilityLabel = stringResource(R.string.inset_banner_cta_accessibility_label),
            onClick = {},
        ),
        image = null,
        logo = getPartnerLogo(variant = OnLight),
    )
}

@Composable
@InsetBannerComponent
@ComposeStory("Sponsored OnLight Without subHeadline")
fun SponsoredInsetBannerStoryOnLightWithoutSubHeadline(modifier: Modifier = Modifier) {
    DefaultSponsoredInsetBannerSample(
        variant = OnLight,
        title = stringResource(R.string.inset_banner_title),
        callToAction = BpkSponsoredInsetBannerCTA(
            text = stringResource(R.string.inset_banner_cta_text),
            accessibilityLabel = stringResource(R.string.inset_banner_cta_accessibility_label),
            onClick = {},
        ),
        image = null,
        logo = getPartnerLogo(variant = OnLight),
    )
}

@Composable
@InsetBannerComponent
@ComposeStory("Sponsored OnLight Without Title")
fun SponsoredInsetBannerStoryOnLightWithoutTitle(modifier: Modifier = Modifier) {
    DefaultSponsoredInsetBannerSample(
        variant = OnLight,
        subHeadline = stringResource(R.string.inset_banner_sub_headline),
        callToAction = BpkSponsoredInsetBannerCTA(
            text = stringResource(R.string.inset_banner_cta_text),
            accessibilityLabel = stringResource(R.string.inset_banner_cta_accessibility_label),
            onClick = {},
        ),
        image = null,
        logo = getPartnerLogo(variant = OnLight),
    )
}

@Composable
@InsetBannerComponent
@ComposeStory("Sponsored OnLight Without Title nor subHeadline")
fun SponsoredInsetBannerStoryOnLightWithoutTitleNorSubHeadline(modifier: Modifier = Modifier) {
    DefaultSponsoredInsetBannerSample(
        variant = OnLight,
        callToAction = BpkSponsoredInsetBannerCTA(
            text = stringResource(R.string.inset_banner_cta_text),
            accessibilityLabel = stringResource(R.string.inset_banner_cta_accessibility_label),
            onClick = {},
        ),
        image = null,
        logo = getPartnerLogo(variant = OnLight),
    )
}

@Composable
@InsetBannerComponent
@ComposeStory("Sponsored OnDark With Image")
fun SponsoredInsetBannerStoryOnDarkWithImage(modifier: Modifier = Modifier) {
    DefaultSponsoredInsetBannerSample(
        variant = OnDark,
        title = stringResource(R.string.inset_banner_title),
        subHeadline = stringResource(R.string.inset_banner_sub_headline),
        callToAction = BpkSponsoredInsetBannerCTA(
            text = stringResource(R.string.inset_banner_cta_text),
            accessibilityLabel = stringResource(R.string.inset_banner_cta_accessibility_label),
            onClick = {},
        ),
        image = getImage(),
        logo = getPartnerLogo(variant = OnDark),
    )
}

@Composable
@InsetBannerComponent
@ComposeStory("Sponsored OnDark Without Image")
fun SponsoredInsetBannerStoryOnDarkWithoutImage() {
    DefaultSponsoredInsetBannerSample(
        variant = OnDark,
        title = stringResource(R.string.inset_banner_title),
        subHeadline = stringResource(R.string.inset_banner_sub_headline),
        callToAction = BpkSponsoredInsetBannerCTA(
            text = stringResource(R.string.inset_banner_cta_text),
            accessibilityLabel = stringResource(R.string.inset_banner_cta_accessibility_label),
            onClick = {},
        ),
        image = null,
        logo = getPartnerLogo(variant = OnDark),
    )
}

@Composable
@InsetBannerComponent
@ComposeStory("Sponsored OnDark Without subHeadline")
fun SponsoredInsetBannerStoryOnDarkWithoutSubHeadline(modifier: Modifier = Modifier) {
    DefaultSponsoredInsetBannerSample(
        variant = OnDark,
        title = stringResource(R.string.inset_banner_title),
        callToAction = BpkSponsoredInsetBannerCTA(
            text = stringResource(R.string.inset_banner_cta_text),
            accessibilityLabel = stringResource(R.string.inset_banner_cta_accessibility_label),
            onClick = {},
        ),
        image = null,
        logo = getPartnerLogo(variant = OnDark),
    )
}

@Composable
@InsetBannerComponent
@ComposeStory("Sponsored OnDark Without Title")
fun SponsoredInsetBannerStoryOnDarkWithoutTitle(modifier: Modifier = Modifier) {
    DefaultSponsoredInsetBannerSample(
        variant = OnDark,
        subHeadline = stringResource(R.string.inset_banner_sub_headline),
        callToAction = BpkSponsoredInsetBannerCTA(
            text = stringResource(R.string.inset_banner_cta_text),
            accessibilityLabel = stringResource(R.string.inset_banner_cta_accessibility_label),
            onClick = {},
        ),
        image = null,
        logo = getPartnerLogo(variant = OnDark),
    )
}

@Composable
@InsetBannerComponent
@ComposeStory("Sponsored OnDark Without Title nor subHeadline")
fun SponsoredInsetBannerStoryOnDarkWithoutTitleNorSubHeadline(modifier: Modifier = Modifier) {
    DefaultSponsoredInsetBannerSample(
        variant = OnDark,
        callToAction = BpkSponsoredInsetBannerCTA(
            text = stringResource(R.string.inset_banner_cta_text),
            accessibilityLabel = stringResource(R.string.inset_banner_cta_accessibility_label),
            onClick = {},
        ),
        image = null,
        logo = getPartnerLogo(variant = OnDark),
    )
}

@Composable
@InsetBannerComponent
@ComposeStory("Sponsored OnDark Without Image")
fun SponsoredInsetBannerStoryOnDarkWithoutImage(modifier: Modifier = Modifier) {
    DefaultSponsoredInsetBannerSample(
        variant = OnDark,
        title = stringResource(R.string.inset_banner_title),
        subHeadline = stringResource(R.string.inset_banner_sub_headline),
        callToAction = BpkSponsoredInsetBannerCTA(
            text = stringResource(R.string.inset_banner_cta_text),
            accessibilityLabel = stringResource(R.string.inset_banner_cta_accessibility_label),
            onClick = {},
        ),
        image = null,
        logo = getPartnerLogo(variant = OnDark),
    )
}

@Composable
private fun getPartnerLogo(variant: BpkSponsoredInsetBannerVariant): @Composable () -> Unit = {
    Image(
        painter = when (variant) {
            OnDark -> painterResource(R.drawable.inset_banner_skyland_white)
            OnLight -> painterResource(R.drawable.inset_banner_skyland_black)
        },
        contentDescription = stringResource(R.string.inset_banner_cta_accessibility_label),
        contentScale = ContentScale.Fit,
    )
}

@Composable
private fun getImage(): @Composable () -> Unit = {
    Image(
        painter = painterResource(R.drawable.city),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        alignment = BiasAlignment(horizontalBias = 0f, verticalBias = -0.6f),
    )
}

@Composable
internal fun DefaultSponsoredInsetBannerSample(
    variant: BpkSponsoredInsetBannerVariant,
    callToAction: BpkSponsoredInsetBannerCTA,
    logo: @Composable (() -> Unit),
    modifier: Modifier = Modifier,
    title: String? = null,
    subHeadline: String? = null,
    image: @Composable (() -> Unit)? = null,
) {
    Box(modifier = modifier.padding(BpkSpacing.Lg)) {
        BpkSponsoredInsetBanner(
            variant = variant,
            backgroundColor = when (variant) {
                OnDark -> androidx.compose.ui.graphics.Color(BACKGROUND_ONDARK_COLOR_HEX)
                OnLight -> androidx.compose.ui.graphics.Color(BACKGROUND_ONLIGHT_COLOR_HEX)
            },
            title = title,
            subHeadline = subHeadline,
            callToAction = callToAction,
            image = image,
            logo = logo,
        )
    }
}
