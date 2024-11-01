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

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.graphicpromotion.BpkGraphicPromoVariant
import net.skyscanner.backpack.compose.graphicpromotion.BpkGraphicPromoVerticalAlignment
import net.skyscanner.backpack.compose.graphicpromotion.BpkGraphicsPromoSponsor
import net.skyscanner.backpack.compose.overlay.BpkOverlay
import net.skyscanner.backpack.compose.overlay.BpkOverlayType
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.BpkBorderRadius
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.compose.utils.isDesktop
import net.skyscanner.backpack.compose.utils.isSmallTablet
import net.skyscanner.backpack.compose.utils.isTablet

@Composable
internal fun BpkGraphicPromoImpl(
    headline: String,
    image: @Composable BoxScope.() -> Unit,
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
    kicker: String? = null,
    subHeadline: String? = null,
    overlayType: BpkOverlayType? = null,
    variant: BpkGraphicPromoVariant = BpkGraphicPromoVariant.OnDark,
    verticalAlignment: BpkGraphicPromoVerticalAlignment = BpkGraphicPromoVerticalAlignment.Top,
    sponsor: BpkGraphicsPromoSponsor? = null,
    sponsorLogo: (@Composable () -> Unit)? = null,
    tapAction: () -> Unit = {},
) {
    val (aspectRatio, maxHeight) = getDeviceConstrains()
    val roundedCornerShape = RoundedCornerShape(BpkBorderRadius.Md)
    val contentDescription = listOfNotNull(kicker, headline, subHeadline, sponsor?.accessibilityLabel)
        .joinToString(separator = ", ")
    Box(
        modifier = modifier
            .aspectRatio(ratio = aspectRatio)
            .heightIn(max = maxHeight.dp)
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
        if (overlayType != null) {
            BpkOverlay(
                modifier = Modifier
                    .matchParentSize()
                    .indication(interactionSource, InteractiveBackgroundIndicationNodeFactory),
                overlayType = overlayType,
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
        } else {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .indication(interactionSource, InteractiveBackgroundIndicationNodeFactory),
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
    sponsor: BpkGraphicsPromoSponsor,
    sponsorLogo: (@Composable () -> Unit),
) {
    var fitsInline by remember { mutableStateOf(true) }
    if (fitsInline) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SponsoredLogo(sponsorLogo)
            Spacer(Modifier.width(BpkSpacing.Md))
            SponsoredMessage(sponsor, textColor, maxLines = 1) { textLayoutResult ->
                if (fitsInline && textLayoutResult.hasVisualOverflow) {
                    fitsInline = false
                }
            }
        }
    } else {
        Column {
            SponsoredLogo(sponsorLogo)
            Spacer(Modifier.height(BpkSpacing.Md))
            SponsoredMessage(sponsor, textColor)
        }
    }
}

@Composable
private fun SponsoredMessage(
    sponsor: BpkGraphicsPromoSponsor,
    textColor: Color,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
) {
    BpkText(
        text = sponsor.title,
        style = BpkTheme.typography.caption,
        color = textColor,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
    )
}

@Composable
private fun SponsoredLogo(
    sponsorLogo: @Composable (() -> Unit)?,
) {
    Box(
        modifier = Modifier
            .heightIn(max = SPONSOR_LOGO_HEIGHT.dp)
            .widthIn(max = SPONSOR_LOGO_WIDTH.dp),
        content = { sponsorLogo?.let { it() } },
    )
}

@Composable
private fun MessageOverlay(
    headline: String,
    kicker: String?,
    subHeadline: String?,
    textColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
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
    modifier: Modifier = Modifier,
    kicker: String? = null,
    subHeadline: String? = null,
    variant: BpkGraphicPromoVariant = BpkGraphicPromoVariant.OnDark,
    verticalAlignment: BpkGraphicPromoVerticalAlignment = BpkGraphicPromoVerticalAlignment.Top,
    sponsor: BpkGraphicsPromoSponsor? = null,
    sponsorLogo: (@Composable () -> Unit)? = null,
) {
    val textColor: Color = when (variant) {
        BpkGraphicPromoVariant.OnDark -> BpkTheme.colors.textOnDark
        BpkGraphicPromoVariant.OnLight -> BpkTheme.colors.textOnLight
    }

    if (sponsor != null && sponsorLogo != null) {
        Column(modifier = modifier.padding(getPadding())) {
            Spacer(Modifier.weight(1f))
            MessageOverlay(
                headline = headline,
                textColor = textColor,
                kicker = null,
                subHeadline = null,
            )

            Spacer(Modifier.height(BpkSpacing.Md))

            SponsorOverlayView(
                sponsor = sponsor,
                textColor = textColor,
                sponsorLogo = sponsorLogo,
            )
        }
        return
    }

    Column(modifier = modifier.padding(BpkSpacing.Lg)) {
        when (verticalAlignment) {
            BpkGraphicPromoVerticalAlignment.Top -> {
                MessageOverlay(
                    headline = headline,
                    kicker = kicker,
                    subHeadline = subHeadline,
                    textColor = textColor,
                )

                Spacer(Modifier.weight(1f))
            }

            BpkGraphicPromoVerticalAlignment.Bottom -> {
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

@Composable
private fun getPadding(): Dp =
    if (isDesktop()) {
        BpkSpacing.Xxl
    } else if (isTablet()) {
        BpkSpacing.Lg
    } else if (isSmallTablet()) {
        BpkSpacing.Xl
    } else {
        BpkSpacing.Lg
    }

@Composable
private fun getDeviceConstrains(): Array<Float> = if (isDesktop()) {
    arrayOf(RATIO_PORTRAIT_DESKTOP, 436f)
} else if (isTablet()) {
    arrayOf(RATIO_PORTRAIT_TABLET, 436f)
} else if (isSmallTablet()) {
    arrayOf(RATIO_PORTRAIT_SMALL_TABLET, 617f)
} else {
    arrayOf(RATIO_PORTRAIT, 360f)
}

private object InteractiveBackgroundIndicationNodeFactory : IndicationNodeFactory {

    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return InteractiveBackgroundIndicationNode(interactionSource)
    }

    override fun hashCode(): Int = -1

    override fun equals(other: Any?) = other === this
}

private class InteractiveBackgroundIndicationNode(private val interactionSource: InteractionSource) : Modifier.Node(),
    DrawModifierNode {

    val animatedScalePercent = Animatable(1f)
    val animatedOverlayAlpha = Animatable(0f)

    private suspend fun animateToPressed() {
        animatedScalePercent.animateTo(1.05f, interactiveBackgroundAnimationSpec)
        animatedOverlayAlpha.animateTo(0.2f, interactiveBackgroundAnimationSpec)
    }

    private suspend fun animateToResting() {
        animatedScalePercent.animateTo(1f, spring())
        animatedOverlayAlpha.animateTo(0f, spring())
    }

    override fun onAttach() {
        coroutineScope.launch {
            interactionSource.interactions.collectLatest { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> animateToPressed()
                    is PressInteraction.Release -> animateToResting()
                    is PressInteraction.Cancel -> animateToResting()
                }
            }
        }
    }

    override fun ContentDrawScope.draw() {
        scale(animatedScalePercent.value, animatedScalePercent.value) {
            this@draw.drawContent()
        }
        drawRect(color = Color.Black, alpha = animatedOverlayAlpha.value)
    }
}

private const val RATIO_PORTRAIT: Float = 3 / 4f
private const val RATIO_PORTRAIT_SMALL_TABLET: Float = 464 / 617f
private const val RATIO_PORTRAIT_TABLET: Float = 705 / 360f
private const val RATIO_PORTRAIT_DESKTOP: Float = 1024 / 460f
private const val SPONSOR_LOGO_HEIGHT = 32
private const val SPONSOR_LOGO_WIDTH = 160

private val interactiveBackgroundAnimationSpec: AnimationSpec<Float> = spring(
    stiffness = 800f,
    dampingRatio = 1f,
)
