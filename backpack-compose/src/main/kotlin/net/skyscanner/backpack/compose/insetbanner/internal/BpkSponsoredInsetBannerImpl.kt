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

package net.skyscanner.backpack.compose.insetbanner.internal

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.insetbanner.BpkSponsoredInsetBannerVariant
import net.skyscanner.backpack.compose.insetbanner.BpkSponsoredInsetBannerCTA
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.InformationCircle
import net.skyscanner.backpack.compose.utils.clickableWithRipple

@Composable
internal fun BpkSponsoredInsetBannerImpl(
    variant: BpkSponsoredInsetBannerVariant,
    title: String?,
    subHeadline: String?,
    callToAction: BpkSponsoredInsetBannerCTA,
    backgroundColor: Color,
    image: @Composable (() -> Unit)?,
    logo: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(color = BpkTheme.colors.canvasContrast), // TODO: check if needed
    ) {
        BpkSponsoredInsetBannerHeader(
            modifier = Modifier
                .clip(
                    shape = if (image != null) {
                        RoundedCornerShape(
                            topStart = BpkSpacing.Md,
                            topEnd = BpkSpacing.Md,
                            bottomStart = BpkSpacing.None,
                            bottomEnd = BpkSpacing.None,
                        )
                    } else {
                        RoundedCornerShape(BpkSpacing.Md)
                    },
                )
                .background(
                    color = backgroundColor,
                ),
            variant = variant,
            title = title,
            subHeadline = subHeadline,
            callToAction = callToAction,
            logo = logo,
        )
        image?.let {
            Box(
                modifier = Modifier
                    // .padding(vertical = BpkSpacing.Base)
                    .heightIn(max = 120.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = BpkSpacing.None,
                            topEnd = BpkSpacing.None,
                            bottomStart = BpkSpacing.Md,
                            bottomEnd = BpkSpacing.Md,
                        ),
                    ),
            ) {
                image()
            }
        }
    }
}

@Composable
internal fun BpkSponsoredInsetBannerHeader(
    variant: BpkSponsoredInsetBannerVariant,
    callToAction: BpkSponsoredInsetBannerCTA,
    modifier: Modifier = Modifier,
    title: String? = null,
    subHeadline: String? = null,
    logo: @Composable (() -> Unit)?,
) {
    Column(modifier = modifier.fillMaxWidth().padding(bottom = BpkSpacing.Md)) {
        Row(
            modifier = Modifier.fillMaxWidth().heightIn(max = LOGO_HEIGHT + BpkSpacing.Md),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            logo?.let {
                Box(
                    modifier = Modifier
                        .padding(top = BpkSpacing.Md,
                            start = BpkSpacing.Base,)
                        .widthIn(max = LOGO_WIDTH)
                        .heightIn(max = LOGO_HEIGHT),
                ) {
                    logo()
                }
            }
            callToAction?.let {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickableWithRipple { callToAction.onClick() }
                        .padding(top = BpkSpacing.Md, start = BpkSpacing.Base, end = BpkSpacing.Base),
                    horizontalArrangement = Arrangement.spacedBy(space = BpkSpacing.Md),
                ) {
                    BpkText(
                        text = callToAction.text,
                        style = BpkTheme.typography.caption,
                        color = getTextColor(variant = variant),
                    )
                    BpkIcon(
                        icon = BpkIcon.InformationCircle,
                        contentDescription = callToAction.accessibilityLabel,
                        tint = getTintColor(variant = variant),
                    )
                }
            }
        }
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = BpkSpacing.Base)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                if (!title.isNullOrBlank()) {
                    BpkText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = BpkSpacing.Sm),
                        text = title,
                        style = BpkTheme.typography.label2,
                        color = getTextColor(variant = variant),
                    )
                }
                if (!subHeadline.isNullOrBlank()) {
                    BpkText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = if (title.isNullOrBlank()) BpkSpacing.Sm else 0.dp),
                        text = subHeadline,
                        style = BpkTheme.typography.caption,
                        color = getTextColor(variant = variant),
                    )
                }
            }
        }
    }
}

private val LOGO_WIDTH = 88.dp
private val LOGO_HEIGHT = 22.dp

@Composable
private fun getTextColor(variant: BpkSponsoredInsetBannerVariant): Color =
    when (variant) {
        BpkSponsoredInsetBannerVariant.OnLight -> BpkTheme.colors.textOnLight
        BpkSponsoredInsetBannerVariant.OnDark -> BpkTheme.colors.textOnDark
    }

@Composable
private fun getTintColor(variant: BpkSponsoredInsetBannerVariant): Color =
    when (variant) {
        BpkSponsoredInsetBannerVariant.OnLight -> BpkTheme.colors.textOnLight
        BpkSponsoredInsetBannerVariant.OnDark -> BpkTheme.colors.textOnDark
    }

@Preview(name = "Default",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "en",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF, // BpkTheme.colors.canvas, LM
)
@Preview(name = "RTL",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ar",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF, // BpkTheme.colors.canvas, LM
)
@Composable
private fun PreviewBpkSponsoredInsetBannerImpl() {
    BpkSponsoredInsetBannerImpl(
        variant = BpkSponsoredInsetBannerVariant.OnLight,
        title = "Title",
        subHeadline = "Subheadline",
        callToAction = BpkSponsoredInsetBannerCTA(
            text = "Sponsored",
            accessibilityLabel = "Call to action",
            onClick = { /* No-op */ },
        ),
        logo = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFF761C)),
            )
        },
        backgroundColor = Color(0xFFFFE300),
        image = null,
    )
}

@Preview(name = "DM",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "en",
    showBackground = true,
    backgroundColor = 0xFF010913, // BpkTheme.colors.canvas, DM
)
@Composable
private fun PreviewBpkSponsoredInsetBannerImplOnDark() {
    BpkSponsoredInsetBannerImpl(
        variant = BpkSponsoredInsetBannerVariant.OnDark,
        title = "Title",
        subHeadline = "Subheadline",
        callToAction = BpkSponsoredInsetBannerCTA(
            text = "Sponsored",
            accessibilityLabel = "Call to action",
            onClick = { /* No-op */ },
        ),
        logo = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFE300)),
            )
        },
        backgroundColor = Color(0xFFFF761C),
        image = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF7AC512)),
            )
        },
    )
}
