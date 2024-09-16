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

package net.skyscanner.backpack.compose.insetbanner.internal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.insetbanner.BpkInsetBannerCTA
import net.skyscanner.backpack.compose.insetbanner.BpkInsetBannerVariant
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.tokens.InformationCircle
import net.skyscanner.backpack.compose.utils.applyIf
import net.skyscanner.backpack.compose.utils.clickableWithRipple

@Composable
internal fun BpkInsetBannerImpl(
    backgroundColor: Color,
    variant: BpkInsetBannerVariant,
    title: String?,
    subHeadline: String?,
    callToAction: BpkInsetBannerCTA?,
    body: String?,
    modifier: Modifier = Modifier,
    logo: @Composable (() -> Unit)?,
) {
    val showBody = !body.isNullOrBlank()
    var isBodyVisible by remember { mutableStateOf(false) }
    val toggleShowBody = {
        isBodyVisible = !isBodyVisible
    }

    Column(
        modifier = modifier
            .background(
                color = BpkTheme.colors.canvasContrast,
                shape = RoundedCornerShape(BpkSpacing.Md),
            ),
    ) {
        BpkInsetBannerHeader(
            modifier = Modifier
                .clip(
                    shape = if (isBodyVisible) {
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
                ).applyIf(showBody) { clickableWithRipple { toggleShowBody() } },
            variant = variant,
            title = title,
            subHeadline = subHeadline,
            callToAction = callToAction,
            logo = logo,
        )
        if (showBody) {
            body?.let {
                AnimatedVisibility(
                    visible = isBodyVisible,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(BpkSpacing.Base),
                    ) {
                        BpkText(
                            text = body,
                            color = BpkTheme.colors.textPrimary,
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun BpkInsetBannerHeader(
    variant: BpkInsetBannerVariant,
    modifier: Modifier = Modifier,
    title: String? = null,
    subHeadline: String? = null,
    callToAction: BpkInsetBannerCTA? = null,
    logo: @Composable (() -> Unit)?,
) {
    Row(
        modifier = modifier
            .padding(horizontal = BpkSpacing.Base),
        horizontalArrangement = Arrangement.spacedBy(BpkSpacing.Md),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        logo?.let {
            Box(
                modifier = Modifier
                    .padding(vertical = BpkSpacing.Base)
                    .widthIn(max = LOGO_WIDTH)
                    .heightIn(max = LOGO_HEIGHT),
            ) {
                logo()
            }
        }
        Column(
            modifier = Modifier
                .padding(vertical = BpkSpacing.Base)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
        ) {
            if (!title.isNullOrBlank()) {
                BpkText(
                    text = title,
                    style = BpkTheme.typography.label2,
                    color = getTextColor(variant),
                )
            }
            if (!subHeadline.isNullOrBlank()) {
                BpkText(
                    text = subHeadline,
                    style = BpkTheme.typography.caption,
                    color = getTextColor(variant),
                )
            }
        }
        callToAction?.let {
            BpkText(
                text = callToAction.text,
                style = BpkTheme.typography.caption,
                color = getTextColor(variant),
            )
            BpkIcon(
                icon = BpkIcon.InformationCircle,
                contentDescription = callToAction.accessibilityLabel,
                tint = getTintColor(variant),
            )
        }
    }
}

private val LOGO_WIDTH = 88.dp
private val LOGO_HEIGHT = 22.dp

@Composable
private fun getTextColor(variant: BpkInsetBannerVariant): Color =
    when (variant) {
        BpkInsetBannerVariant.OnLight -> BpkTheme.colors.textOnLight
        BpkInsetBannerVariant.OnDark -> BpkTheme.colors.textOnDark
    }

@Composable
private fun getTintColor(variant: BpkInsetBannerVariant): Color =
    when (variant) {
        BpkInsetBannerVariant.OnLight -> BpkTheme.colors.textOnLight
        BpkInsetBannerVariant.OnDark -> BpkTheme.colors.textOnDark
    }
