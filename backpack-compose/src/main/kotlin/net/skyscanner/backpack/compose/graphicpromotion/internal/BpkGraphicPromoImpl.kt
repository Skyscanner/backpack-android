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
package net.skyscanner.backpack.compose.graphicpromotion.internal

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.graphicpromotion.Sponsor
import net.skyscanner.backpack.compose.graphicpromotion.Variant
import net.skyscanner.backpack.compose.graphicpromotion.VerticalAlignment
import net.skyscanner.backpack.compose.overlay.BpkOverlay
import net.skyscanner.backpack.compose.overlay.BpkOverlayType
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
internal fun BpkGraphicPromoImpl(
    headline: String,
    image: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    kicker: String? = null,
    subHeadline: String? = null,
    overlayType: BpkOverlayType? = null,
    variant: Variant = Variant.OnDark,
    verticalAlignment: VerticalAlignment = VerticalAlignment.Top,
    sponsor: Sponsor? = null,
    sponsorLogo: @Composable () -> Unit? = {},
    tapAction: () -> Unit = {},
) {
    val roundedCornerShape = RoundedCornerShape(BpkBorderRadius.Md)
    val contentDescription = listOfNotNull(kicker, headline, subHeadline, sponsor?.accessibilityLabel)
        .joinToString(separator = ", ")

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .aspectRatio(RATIO_PORTRAIT)
            .clip(roundedCornerShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = tapAction,
            )
            .semantics(mergeDescendants = true) {
                role = Role.Button
                this.contentDescription = contentDescription
            },
    ) {
        overlayType?.let {
            BpkOverlay(
                modifier = Modifier
                    .matchParentSize()
                    .indication(interactionSource, InteractiveBackgroundIndication),
                overlayType = it,
                foregroundContent = {
                    ForegroundContent(
                        headline = headline,
                        kicker = kicker,
                        subHeadline = subHeadline,
                        variant = variant,
                        verticalAlignment = verticalAlignment,
                        sponsor = sponsor,
                        sponsorLogo = sponsorLogo,
                    )
                },
                content = image,
            )
        } ?: run {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .indication(interactionSource, InteractiveBackgroundIndication),
                content = image,
            )
            ForegroundContent(
                headline = headline,
                kicker = kicker,
                subHeadline = subHeadline,
                variant = variant,
                verticalAlignment = verticalAlignment,
                sponsor = sponsor,
                sponsorLogo = sponsorLogo,
            )
        }
    }
}

@Composable
private fun SponsorOverlayView(
    textColor: Color,
    sponsor: Sponsor?,
    sponsorLogo: @Composable () -> Unit? = {},
) {
    if (sponsor != null) {
        Column(
            verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md, Alignment.Top),
        ) {
            BpkText(
                text = sponsor.title,
                style = BpkTheme.typography.label1,
                color = textColor,
            )

            Box(
                modifier = Modifier
                    .heightIn(0.dp, SPONSOR_LOGO_HEIGHT.dp),
            ) {
                sponsorLogo()
            }
        }
    }
}

@Composable
private fun MessageOverlay(
    headline: String,
    kicker: String?,
    subHeadline: String?,
    textColor: Color,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(BpkSpacing.Md, Alignment.Top),
    ) {
        if (!kicker.isNullOrBlank()) {
            BpkText(
                text = kicker,
                style = BpkTheme.typography.subheading,
                color = textColor,
            )
        }

        BpkText(
            text = headline,
            style = BpkTheme.typography.heading2,
            color = textColor,
        )
        if (!subHeadline.isNullOrBlank()) {
            BpkText(
                text = subHeadline,
                style = BpkTheme.typography.heading5,
                color = textColor,
            )
        }
    }
}

@Composable
private fun ForegroundContent(
    headline: String,
    kicker: String? = null,
    subHeadline: String? = null,
    variant: Variant = Variant.OnDark,
    verticalAlignment: VerticalAlignment = VerticalAlignment.Top,
    sponsor: Sponsor? = null,
    sponsorLogo: @Composable () -> Unit? = {},
) {
    val textColor: Color = when (variant) {
        Variant.OnDark -> BpkTheme.colors.textOnDark
        Variant.OnLight -> BpkTheme.colors.textOnLight
    }

    Column(modifier = Modifier.padding(BpkSpacing.Lg)) {
        when (verticalAlignment) {
            VerticalAlignment.Top -> {
                MessageOverlay(
                    headline = headline,
                    kicker = kicker,
                    subHeadline = subHeadline,
                    textColor = textColor,
                )

                Spacer(Modifier.weight(1f))

                SponsorOverlayView(
                    sponsor = sponsor,
                    textColor = textColor,
                    sponsorLogo = sponsorLogo,
                )
            }
            VerticalAlignment.Bottom -> {
                SponsorOverlayView(
                    sponsor = sponsor,
                    textColor = textColor,
                    sponsorLogo = sponsorLogo,
                )

                Spacer(Modifier.weight(1f))

                MessageOverlay(
                    headline = headline,
                    kicker = kicker,
                    subHeadline = subHeadline,
                    textColor = textColor,
                )
            }
        }
    }
}

private object InteractiveBackgroundIndication : Indication {

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val isPressed by interactionSource.collectIsPressedAsState()

        val scale by animateFloatAsState(
            targetValue = if (isPressed) 1.05f else 1.0f,
            animationSpec = interactiveBackgroundAnimationSpec,
            label = "background scale",
        )

        val overlayAlpha by animateFloatAsState(
            targetValue = if (isPressed) 0.2f else 0f,
            animationSpec = interactiveBackgroundAnimationSpec,
            label = "overlay alpha",
        )

        return remember(interactionSource) {
            object : IndicationInstance {
                override fun ContentDrawScope.drawIndication() {
                    scale(scale, scale) {
                        this@drawIndication.drawContent()
                    }
                    drawRect(Color.Black, alpha = overlayAlpha)
                }
            }
        }
    }
}

const val RATIO_PORTRAIT: Float = 3 / 4f
const val SPONSOR_LOGO_HEIGHT = 60

private val interactiveBackgroundAnimationSpec: AnimationSpec<Float> = tween(durationMillis = 120)
